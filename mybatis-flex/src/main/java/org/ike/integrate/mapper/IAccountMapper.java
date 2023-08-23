package org.ike.integrate.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.ike.integrate.entity.Account;
import org.springframework.stereotype.Component;

@Mapper
public interface IAccountMapper extends BaseMapper<Account> {
}
