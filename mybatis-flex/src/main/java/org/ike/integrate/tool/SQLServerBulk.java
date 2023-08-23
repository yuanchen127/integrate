package org.ike.integrate.tool;

import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopyOptions;
import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import com.sun.rowset.CachedRowSetImpl;
import org.ike.integrate.common.exception.ServiceException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import javax.sql.RowSet;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * sqlserver 大批量数据插入
 */
public class SQLServerBulk {

    private final String GET_ROW_MAPPING = "SELECT * FROM %S WITH(NOLOCK) WHERE 0=1";

    private SQLServerConnection connection;

    private final DataSource dataSource = null;

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
        if (CollectionUtils.isEmpty(resources)) {
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
        copyOptions.setBatchSize(rowSets.size());

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
        checkRowSetInfo(rowSet, column);
        rowSet.updateLong(column, null != value ? value : 0L);
    }

    public static void addColumn(RowSet rowSet, String column, Integer value) throws SQLException {
        checkRowSetInfo(rowSet, column);
        rowSet.updateInt(column, null != value ? value : 0);
    }

    public static void addColumn(RowSet rowSet, String column, Float value) throws SQLException {
        checkRowSetInfo(rowSet, column);
        rowSet.updateFloat(column, null != value ? value : 0f);
    }

    public static void addColumn(RowSet rowSet, String column, String value) throws SQLException {
        checkRowSetInfo(rowSet, column);
        rowSet.updateString(column, value);
    }

    public static void addColumn(RowSet rowSet, String column, BigDecimal value) throws SQLException {
        checkRowSetInfo(rowSet, column);
        rowSet.updateBigDecimal(column, null != value ? value : BigDecimal.ZERO);
    }

    public static void addColumn(RowSet rowSet, String column, java.sql.Date value) throws SQLException {
        checkRowSetInfo(rowSet, column);
        rowSet.updateDate(column, value);
    }

    public static void addColumn(RowSet rowSet, String column, java.util.Date value) throws SQLException {
        checkRowSetInfo(rowSet, column);
        rowSet.updateDate(column, null != value ? new java.sql.Date(value.getTime()) : null);
    }

}
