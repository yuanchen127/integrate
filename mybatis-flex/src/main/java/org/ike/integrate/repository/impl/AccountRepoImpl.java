package org.ike.integrate.repository.impl;

import com.mybatisflex.core.query.QueryWrapper;
import org.ike.integrate.config.condition.DataBase;
import org.ike.integrate.entity.Account;
import org.ike.integrate.enums.DataBaseEnum;
import org.ike.integrate.mapper.IAccountMapper;
import org.ike.integrate.repository.AccountRepo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@DataBase(profile = DataBaseEnum.SqlServer)
@Repository
public class AccountRepoImpl implements AccountRepo {

	@Resource
	private IAccountMapper iAccountMapper;

	@Override
	public Account getFirst() {
		Account var = iAccountMapper.selectOneWithRelationsByQuery(new QueryWrapper() {
			private static final long serialVersionUID = -3447901145348969654L;

			{
				select("top 1 *");
				eq("enable", true);
			}
		});
		return var;
	}

	@Override
	public long count() {
		return iAccountMapper.selectCountByQuery(null);
	}

	@Override
	public List<Account> listEnableAccount(boolean enable) {
		return iAccountMapper.listEnableAccount(true);
	}
}
