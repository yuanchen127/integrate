package org.ike.integrate.provider.postgresql;

import org.apache.ibatis.jdbc.SQL;
import org.ike.integrate.provider.AccountProvider;


public class AccountProvider_pg extends AccountProvider{
    @Override
    public String getById(String id) {
        return new SQL() {{
            SELECT("id, RATIO");
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
