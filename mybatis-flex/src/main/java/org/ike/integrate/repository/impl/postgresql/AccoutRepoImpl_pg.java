package org.ike.integrate.repository.impl.postgresql;

import com.mybatisflex.core.query.QueryWrapper;
import org.ike.integrate.config.condition.DataBase;
import org.ike.integrate.entity.Account;
import org.ike.integrate.enums.DataBaseEnum;
import org.ike.integrate.mapper.IAccountMapper;
import org.ike.integrate.repository.impl.AccountRepoImpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@DataBase(profile = DataBaseEnum.PostgreSql)
@Repository
public class AccoutRepoImpl_pg extends AccountRepoImpl {

	@Resource
	IAccountMapper iAccountMapper;

	@Override
	public Account getFirst() {
		Account account = iAccountMapper.selectOneByQuery(new QueryWrapper() {
			private static final long serialVersionUID = -3447901145348969654L;

			{
				select("*");
				eq("enable", true);
				limit(1);
			}
		});
		return account;
	}
}
