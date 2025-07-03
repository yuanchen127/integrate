package org.ike.integrate.enums;

public enum DataType {

    nvarchar("nvarchar", "字符类型"),
    varchar("varchar", "字符类型"),
    datetimeoffset("datetimeoffset", "时间"),
    datetime("datetime", "时间"),
    bit("bit", "布尔"),
    decimal("decimal", "小数"),
    bigint("bigint", "整数");

    private final String type;

    private final String description;

    private DataType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
