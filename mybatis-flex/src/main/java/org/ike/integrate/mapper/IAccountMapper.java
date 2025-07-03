package org.ike.integrate.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.ike.integrate.entity.Account;
import org.ike.integrate.provider.AccountProvider;
import org.ike.integrate.provider.postgresql.AccountProvider_pg;

import java.util.List;

@Mapper
public interface IAccountMapper extends BaseMapper<Account> {

    @Select(value = "select * from integrate.tb_account ta where user_name = #{name}", databaseId = "mysql")
    @Select(value = "select * from postgres.public.tb_account ta where user_name = #{name}", databaseId = "pg")
    @Select(value = "select * from integrate.dbo.tb_account ta where user_name = #{name}")
    public Account getByName(@Param("name") String name);

    @SelectProvider(type = AccountProvider.class, method = "getById")
    @SelectProvider(type = AccountProvider_pg.class, method = "getById", databaseId = "pg")
    public Account getById(@Param("id") String id);

    @SelectProvider(type = AccountProvider.class, method = "listEnableAccount")
    @SelectProvider(type = AccountProvider_pg.class, method = "listEnableAccount", databaseId = "pg")
    public List<Account> listEnableAccount(@Param("enable") boolean enable);
}
