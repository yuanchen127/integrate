package org.ike.integrate.service;

import org.ike.integrate.entity.Account;

import java.util.List;

public interface AccountService {

    boolean insertBatchWithBulk(List<Account> accountList);

    boolean insertBatchWithBulkPG(String data);

    Account getFirst();

    List<Account> listEnableAccount(boolean enable);
}
