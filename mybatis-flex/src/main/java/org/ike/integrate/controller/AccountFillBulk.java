package org.ike.integrate.controller;

import org.ike.integrate.entity.Account;
import org.ike.integrate.tool.FillSQLServerBulk;

import javax.sql.RowSet;
import java.sql.SQLException;

import static org.ike.integrate.tool.SQLServerBulk.addColumn;

public class AccountFillBulk implements FillSQLServerBulk<Account> {
    @Override
    public void fill(RowSet rowSet, Account resource) throws SQLException {
        addColumn(rowSet, "id", resource.getId());
        addColumn(rowSet, "user_name", resource.getUserName());
        addColumn(rowSet, "birthday", resource.getBirthday());
//        addColumn(rowSet, "ratio", resource.getRatio(),false,null);
        addColumn(rowSet, "ratio", resource.getRATIO(),false,null);
        addColumn(rowSet, "enable", resource.isEnable() ? 1 : 0);
    }
}
