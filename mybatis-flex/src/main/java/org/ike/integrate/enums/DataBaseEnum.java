package org.ike.integrate.enums;

public enum DataBaseEnum {
	 PostgreSql("postgresql", "pg"),
	 MySql("mysql", "mysql"),
	 SqlServer("sqlserver", "sqlserver");

	 private final String dbName;
	 private final String dbType;

	 DataBaseEnum(String dbName, String dbType) {
		 this.dbName = dbName;
		 this.dbType = dbType;
	 }

	 public String getDbName() {
		 return dbName;
	 }

	 public String getDbType(){
		 return dbType;
	 }
}
