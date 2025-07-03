package org.ike.integrate.repository;

import org.ike.integrate.entity.Account;

import java.util.List;

public interface AccountRepo {

	Account getFirst();

	long count();

	List<Account> listEnableAccount(boolean enable);
}
