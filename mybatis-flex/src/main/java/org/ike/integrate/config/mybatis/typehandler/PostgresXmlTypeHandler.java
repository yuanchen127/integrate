package org.ike.integrate.config.mybatis.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 直接使用 SQLServerXmlTypeHandler
 * Class Name: SQLServerXmlTypeHandler
 */
@MappedTypes(String.class)       // 处理的Java类型
@MappedJdbcTypes(JdbcType.OTHER) // 处理的JDBC类型
public class PostgresXmlTypeHandler extends DataBasesXMLTypeHandler<String> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
	                                String parameter, JdbcType jdbcType)
			throws SQLException {
		//postgres需要设置
		PGobject pgObject = new PGobject();
		pgObject.setType("xml");
		pgObject.setValue(parameter);
		ps.setObject(i, parameter);
	}

	@Override
	public String getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return rs.getString(columnName); // 直接返回XML字符串
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return rs.getString(columnIndex);
	}

	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return cs.getString(columnIndex);
	}
}
