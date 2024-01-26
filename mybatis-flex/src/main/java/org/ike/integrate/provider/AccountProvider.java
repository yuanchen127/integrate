package org.ike.integrate.provider;

import org.apache.ibatis.jdbc.SQL;

public class AccountProvider {

    public String getById(String id) {
        return new SQL() {{
            SELECT("user_name");
            FROM("integrate..tb_account");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String listEnableAccount(boolean enable) {
        return new SQL(){{
            SELECT("*");
            FROM("integrate..tb_account");
            WHERE("enable = #{enable}");
        }}.toString();
    }

}
