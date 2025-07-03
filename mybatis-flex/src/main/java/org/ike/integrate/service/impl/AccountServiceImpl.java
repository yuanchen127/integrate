package org.ike.integrate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ike.integrate.controller.AccountFillBulk;
import org.ike.integrate.entity.Account;
import org.ike.integrate.repository.AccountRepo;
import org.ike.integrate.service.AccountService;
import org.ike.integrate.tool.PostgreSQLBulk;
import org.ike.integrate.tool.SQLServerBulk;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    AccountRepo accountRepo;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBatchWithBulk(List<Account> accountList) {
        SQLServerBulk sqlserverBulk = null;
        boolean isRollbackOnly = false;
        try {
            sqlserverBulk = new SQLServerBulk();
            isRollbackOnly = TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
            sqlserverBulk.writeToServer("tb_account", accountList, new AccountFillBulk());
            if (!isRollbackOnly) {
                sqlserverBulk.commit();
            }
        } catch (Exception e) {
            if (null != sqlserverBulk) {
                try {
                    sqlserverBulk.rollBack();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }finally {
            try {
                if (isRollbackOnly) {
                    sqlserverBulk.rollBack();
                }
                if (null != sqlserverBulk) {
                    sqlserverBulk.clear();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean insertBatchWithBulkPG(String data) {
        PostgreSQLBulk bulk = new PostgreSQLBulk();
        try {
            bulk.writeToServer("tb_account", data);
        } catch (Exception e) {
            log.error("异常", e);
        }
        return false;
    }

    @Override
    public Account getFirst() {
        return accountRepo.getFirst();
    }

    @Override
    public List<Account> listEnableAccount(boolean enable) {
        return accountRepo.listEnableAccount(enable);
    }
}
