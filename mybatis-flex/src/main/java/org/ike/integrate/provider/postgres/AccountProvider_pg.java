package org.ike.integrate.provider.postgres;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.ike.integrate.provider.AccountProvider;


public class AccountProvider_pg extends AccountProvider{
    @Override
    public String getById(String id) {
        return new SQL() {{
            SELECT("user_name");
            FROM("postgres.public.tb_account");
            WHERE("id = #{id}");
        }}.toString();
    }

    @Override
    public String listEnableAccount(boolean enable) {
        return new SQL(){{
            SELECT("*");
            FROM("postgres.public.tb_account");
            WHERE("enable = #{enable}");
        }}.toString();
    }
}
