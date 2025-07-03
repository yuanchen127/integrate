package org.ike.integrate.repository.impl;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.ike.integrate.entity.MedicalOrderExecution;
import org.ike.integrate.mapper.MedicalOrderExecutionMapper;
import org.ike.integrate.repository.MedicalOrderExecutionRepo;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MedicalOrderExecutionRepoImpl implements MedicalOrderExecutionRepo {

	private static final int COUNT_DATA = 10000;
	private static final String SQL = buildInsertSQL(MedicalOrderExecution.class);

	@Resource
	MedicalOrderExecutionMapper medicalOrderExecutionMapper;

	@Resource
	private DataSource dataSource;

	@Override
	public boolean deleteAll() {
		return medicalOrderExecutionMapper.deleteByQuery(new QueryWrapper(){{
			where("1 = 1");
		}}) > 0;
	}

	@Override
	public boolean saveBatchWithCOPY() {
		List<MedicalOrderExecution> records = buildDataWithReflection();

		//记录当前时间
		long start = System.currentTimeMillis();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			CopyManager copyManager = new CopyManager(convertToBaseConnection((DruidPooledConnection)conn));
			String csvData = records.stream()
					.map(MedicalOrderExecution::convertToCSV)
					.collect(Collectors.joining("\n"));

			copyManager.copyIn("COPY medical_order_execution FROM STDIN WITH CSV",
					new ByteArrayInputStream(csvData.getBytes()));
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		}
		long end = System.currentTimeMillis();
		log.info("MedicalOrderExecution保存数据完成_COPY:，耗时：{}ms", end - start);
		return false;
	}

	public static BaseConnection convertToBaseConnection(DruidPooledConnection druidConnection) throws SQLException {
		// 1. 获取底层的 java.sql.Connection
		Connection tmpConnection = druidConnection.getConnection();

		Connection rawConnection = ((ConnectionProxyImpl) tmpConnection).getConnectionRaw();
		// 2. 检查是否是 PostgreSQL 连接
		if (rawConnection instanceof org.postgresql.jdbc.PgConnection) {
			org.postgresql.jdbc.PgConnection pgConnection = (org.postgresql.jdbc.PgConnection) rawConnection;
			// 获取 BaseConnection
			return pgConnection.unwrap(BaseConnection.class);
		} else {
			throw new SQLException("The connection is not a PostgreSQL connection.");
		}
	}

	@Override
	public boolean saveBatchWithJDBC() {

		List<MedicalOrderExecution> data = buildDataWithReflection();

//		HikariDataSource ds = new HikariDataSource();
//		ds.setJdbcUrl("jdbc:postgresql://localhost:5432/db");
//		ds.setUsername("user");
//		ds.setPassword("pass");
//		ds.setMaximumPoolSize(20);


		//记录开始时间
		long start = System.currentTimeMillis();
		try (Connection conn = dataSource.getConnection();
		     PreparedStatement ps = conn.prepareStatement(SQL)) {

			conn.setAutoCommit(false); // 关闭自动提交

			for (MedicalOrderExecution record : data) {
				fillPreparedStatement(ps, record);
				ps.addBatch();

				if (data.indexOf(record) % 1000 == 0) {
					ps.executeBatch(); // 分批次提交
					ps.clearBatch();
				}
			}
			ps.executeBatch(); // 提交剩余数据
			conn.commit(); // 统一提交事务
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		long end = System.currentTimeMillis();
		log.info("MedicalOrderExecution保存数据完成_jdbc:，耗时：{}ms", end - start);
		return false;
	}

	//构建表的insert语句
	private static String buildInsertSQL(Class clazz) {
//		List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
//				.sorted(Comparator.comparing(Field::getName)).collect(Collectors.toList());

		Field[] fields = clazz.getDeclaredFields();
//		String fieldSQL = Arrays.stream(fields).map(Field::getName).collect(Collectors.joining(","));
		//拼接字段名称,','分隔
		StringBuilder fieldSQL = new StringBuilder();
		StringBuilder valueSQL = new StringBuilder();

		fieldSQL.append(fields[0].getName());
		valueSQL.append("?");

		int fieldLength = fields.length;
		for (int i=1;i<fieldLength; i++) {
			String fieldName = fields[i].getName();
			fieldSQL.append(",").append(fieldName);
			valueSQL.append(",").append("?");
		}

		return String.format("INSERT INTO medical_order_execution (%s) VALUES(%s)", fieldSQL.toString(), valueSQL.toString());
	}


	private void fillPreparedStatement(PreparedStatement ps, MedicalOrderExecution order) throws SQLException {

		Field[] fields = MedicalOrderExecution.class.getDeclaredFields();

		for (int i=1; i<=fields.length;i++) {
			Field field = fields[i - 1];
			String fieldName = field.getName();
			if ("id".equals(fieldName)) {
				ps.setLong(1, order.getId());
			} else {
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					Method method = order.getClass().getMethod(getMethodName);
					Object fieldValue = method.invoke(order);
					ps.setString(i, (String) fieldValue);
				} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}

	}



	@Override
	public boolean saveBatchMedicalOrderExecution() {
		List<MedicalOrderExecution> list = buildDataWithReflection();
		//记录开始保存的时间
		long start = System.currentTimeMillis();
		boolean success = medicalOrderExecutionMapper.insertBatch(list, 10) > 0;
		//计算数据库保存消耗时间
		long end = System.currentTimeMillis();
		log.info("MedicalOrderExecution保存数据完成，耗时：{}ms", end - start);
		return success;
	}

	private List<MedicalOrderExecution> buildDataWithReflection() {


		ArrayList<MedicalOrderExecution> list = new ArrayList<MedicalOrderExecution>();
		for (int i = 0; i < COUNT_DATA; i++) {
			MedicalOrderExecution var = new MedicalOrderExecution();
			//获取MedicalOrderExecution字段数量
			Field[] fields = MedicalOrderExecution.class.getDeclaredFields();
			//循环字段, 为字段设置值
			for (Field field : fields) {
				try {
					//判断字段名称是否为id
					if (field.getName().equals("id")) {
						var.setId(i+1);
						continue;
					}
					String fieldName = field.getName();
					//fieldName首字母大写
					String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					Method method = var.getClass().getMethod(setMethodName, String.class);
					method.invoke(var, createRandomString());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
			list.add(var);
		}
		return list;
	}

	/**
	 * 创建50个字符以内的随机字符串
	 */
	private String createRandomString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 50; i++) {
			sb.append((char) (Math.random() * 26 + 'a'));
		}
		return sb.toString();
	}
}
