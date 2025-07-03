package org.ike.integrate.tool;

import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopyOptions;
import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import com.sun.rowset.CachedRowSetImpl;
import org.ike.integrate.common.exception.ServiceException;
import org.ike.integrate.config.SpringContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import javax.sql.RowSet;
import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * sqlserver 大批量数据插入
 */
public class SQLServerBulk {

    private final String GET_ROW_MAPPING = "SELECT * FROM %S WITH(NOLOCK) WHERE 0=1";

    private SQLServerConnection connection;

    private DataSource dataSource = SpringContext.getBean(DataSource.class);

    private LinkedList<CachedRowSetImpl> rowSets;

    private LinkedList<SQLServerBulkCopy> bulkCopies;

    private SQLServerConnection getNewConnection() throws SQLException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        return connection.unwrap(SQLServerConnection.class);
    }

    public SQLServerConnection getConnection() throws SQLException {
        if(null==connection )
            connection = getNewConnection();
        return connection;
    }

    private CachedRowSetImpl getCacheRowSet(SQLServerConnection connection, String tableName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(String.format(GET_ROW_MAPPING, tableName));
        ResultSet resultSet = statement.executeQuery();
        CachedRowSetImpl rowSet = new CachedRowSetImpl();
        rowSet.populate(resultSet);
        return rowSet;
    }

    private <R> void fillSQLServerBulkData(CachedRowSetImpl rowSet, List<R> resources, FillSQLServerBulk<R> fillFunc) throws SQLException {
        if (!CollectionUtils.isEmpty(resources)) {
            for (R resource : resources) {
                rowSet.moveToInsertRow();
                fillFunc.fill(rowSet, resource);
                rowSet.insertRow();
                rowSet.moveToCurrentRow();
            }
        }
    }

    public <R> void writeToServer(String tableName, List<R> resources, FillSQLServerBulk<R> fillFunc) throws SQLException {
        if (null == connection) {
            connection = getNewConnection();
        }
        writeToServer(connection, tableName, resources, fillFunc);
    }

    public <R> void writeToServer(SQLServerConnection connection, String tableName, List<R> resources, FillSQLServerBulk<R> fillFunc) throws SQLException {
        if (null == connection) {
            throw new ServiceException("获取数据库链接为空");
        }
        CachedRowSetImpl rowSet = getCacheRowSet(connection, tableName);
        fillSQLServerBulkData(rowSet, resources, fillFunc);

        SQLServerBulkCopyOptions copyOptions = new SQLServerBulkCopyOptions();
        copyOptions.setKeepIdentity(true);
        copyOptions.setKeepNulls(true);
        copyOptions.setBatchSize(rowSet.size());

        connection.setAutoCommit(false);
        SQLServerBulkCopy bulkCopy = new SQLServerBulkCopy(connection);
        bulkCopy.setBulkCopyOptions(copyOptions);
        bulkCopy.setDestinationTableName(tableName);

        addRowSet(rowSet);
        addBulkCopy(bulkCopy);
        bulkCopy.writeToServer(rowSet);
        //事务不能在该处提交, 兼容一个connection下多个SQLServerBulkCopy写入的情况
//        connection.commit();
    }

    private LinkedList<CachedRowSetImpl> addRowSet(CachedRowSetImpl rowSet) {
        if (null == rowSets) {
            rowSets = new LinkedList<>();
        }
        rowSets.add(rowSet);
        return rowSets;
    }

    private LinkedList<SQLServerBulkCopy> addBulkCopy(SQLServerBulkCopy bulkCopy) {
        if (null == bulkCopies) {
            bulkCopies = new LinkedList<>();
        }
        bulkCopies.add(bulkCopy);
        return bulkCopies;
    }

    public void clear() throws SQLException {
        if (!CollectionUtils.isEmpty(rowSets)) {
            for (CachedRowSetImpl rowSet : rowSets) {
                rowSet.close();
            }
        }
        if (!CollectionUtils.isEmpty(bulkCopies)) {
            for (SQLServerBulkCopy bulkCopy : bulkCopies) {
                bulkCopy.close();
            }
        }
    }

    public void commit() throws SQLException {
        try {
            if (null != connection) {
                connection.commit();
            }
        } catch (SQLException e) {
            throw e;
        }finally {
            clear();
        }
    }

    public void rollBack() throws SQLException {
        try {
            if (null != connection) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw e;
        }
//        finally {
//            clear();
//        }
    }

    private static void checkRowSetInfo(RowSet rowSet, String column) {
        if (null == rowSet || !StringUtils.hasText(column)) {
            throw new ServiceException("请检测更新行信息");
        }
    }

    public static void addColumn(RowSet rowSet, String column, Long value) throws SQLException {
        addColumn(rowSet, column, value, true, null);
    }

    public static void addColumn(RowSet rowSet, String column, Long value, boolean ifWithDefault, Long defaultValue) throws SQLException {
        checkRowSetInfo(rowSet, column);
        if (null == value) {
            if (ifWithDefault) {
                rowSet.updateLong(column, null == defaultValue ? 0L : defaultValue);
            } else {
                rowSet.updateNull(column);
            }
        } else {
            rowSet.updateLong(column, value);
        }
    }

    public static void addColumn(RowSet rowSet, String column, Integer value) throws SQLException {
        addColumn(rowSet, column, value, true, null);
    }

    /**
     * 向RowSet中添加一列，并为该列设置初始值。
     * 此方法用于在不重新查询数据库表结构的情况下，动态地为RowSet对象添加新列。
     * 添加列时，可以根据是否提供默认值来决定新列的初始状态。
     *
     * @param rowSet 需要添加列的RowSet对象。
     * @param column 新列的名称。
     * @param value 新列的初始值。如果为null，则根据ifWithDefault参数决定是否使用默认值。
     * @param ifWithDefault 指示是否为新列设置默认值。如果为true且value为null，则使用defaultValue作为默认值。
     * @param defaultValue 新列的默认值，仅在ifWithDefault为true且value为null时使用。
     * @throws SQLException 如果操作RowSet时发生错误。
     */
    public static void addColumn(RowSet rowSet, String column, Integer value, boolean ifWithDefault, Integer defaultValue) throws SQLException {
        // 检查RowSet对象和新列名称是否有效。
        checkRowSetInfo(rowSet, column);
        // 判断新列的初始值是否为null。
        if (null == value) {
            // 如果指定了使用默认值且默认值不为null，则设置默认值；否则设置为null。
            if (ifWithDefault) {
                rowSet.updateInt(column, null == defaultValue ? 0 : defaultValue);
            } else {
                rowSet.updateNull(column);
            }
        } else {
            // 如果初始值不为null，则直接设置该值。
            rowSet.updateInt(column, value);
        }
    }


    public static void addColumn(RowSet rowSet, String column, Float value) throws SQLException {
        addColumn(rowSet, column, value, true, null);
    }

    public static void addColumn(RowSet rowSet, String column, Float value, boolean ifWithDefault, Float defaultValue) throws SQLException {
        checkRowSetInfo(rowSet, column);
        if (null == value) {
            if (ifWithDefault) {
                rowSet.updateFloat(column, null == defaultValue ? 0f : defaultValue);
            } else {
                rowSet.updateNull(column);
            }
        } else {
            rowSet.updateFloat(column, value);
        }
    }

    public static void addColumn(RowSet rowSet, String column, String value) throws SQLException {
        addColumn(rowSet, column, value, true, null);
    }
    public static void addColumn(RowSet rowSet, String column, String value, boolean ifWithDefault, String defaultValue) throws SQLException {
        checkRowSetInfo(rowSet, column);
        if (null == value) {
            if (ifWithDefault) {
                rowSet.updateString(column, null == defaultValue ? "" : defaultValue);
            } else {
                rowSet.updateNull(column);
            }
        } else {
            rowSet.updateString(column, value);
        }
   }

    public static void addColumn(RowSet rowSet, String column, BigDecimal value) throws SQLException {
        addColumn(rowSet, column, value, true, null);
    }
    public static void addColumn(RowSet rowSet, String column, BigDecimal value, boolean ifWithDefault, BigDecimal defaultValue) throws SQLException {
        checkRowSetInfo(rowSet, column);
        if (null == value) {
            if (ifWithDefault) {
                rowSet.updateBigDecimal(column, null == defaultValue ? BigDecimal.ZERO : defaultValue);
            } else {
                rowSet.updateNull(column);
            }
        } else {
            rowSet.updateBigDecimal(column, value);
        }
    }

    public static void addColumn(RowSet rowSet, String column, java.sql.Date value) throws SQLException {
        addColumn(rowSet, column, value, true, null);
    }
    public static void addColumn(RowSet rowSet, String column, java.sql.Date value, boolean ifWithDefault, java.sql.Date defaultValue) throws SQLException {
        checkRowSetInfo(rowSet, column);
        if (null == value) {
            if (ifWithDefault) {
                rowSet.updateDate(column, null == defaultValue ? new java.sql.Date(System.currentTimeMillis()) : defaultValue);
            } else {
                rowSet.updateNull(column);
            }
        } else {
            rowSet.updateDate(column, value);
        }
    }

    public static void addColumn(RowSet rowSet, String column, java.util.Date value) throws SQLException {
        addColumn(rowSet, column, value, true, null);
    }
    public static void addColumn(RowSet rowSet, String column, java.util.Date value, boolean ifWithDefault, java.util.Date defaultValue) throws SQLException {
        checkRowSetInfo(rowSet, column);
        if (null == value) {
            if (ifWithDefault) {
                rowSet.updateDate(column, null == defaultValue ? new java.sql.Date(System.currentTimeMillis()) : new java.sql.Date(defaultValue.getTime()));
            } else {
                rowSet.updateNull(column);
            }
        } else {
            rowSet.updateDate(column, new java.sql.Date(value.getTime()));
        }
    }

}
