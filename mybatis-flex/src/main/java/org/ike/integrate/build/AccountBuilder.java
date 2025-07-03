package org.ike.integrate.build;

import org.ike.integrate.entity.Account;
import org.ike.integrate.tool.CSVUtil;

import java.util.List;

public class AccountBuilder {

    public static String toCSV(List<Account> list) {
        StringBuilder sb = null;
        if (!list.isEmpty()) {
            sb = new StringBuilder();
            for (Account account : list) {
                sb.append(CSVUtil.toCSV(account.getId())).append(",");
                sb.append(CSVUtil.toCSV(account.getUserName())).append(",");
                sb.append(CSVUtil.toCSV(account.getBirthday())).append(",");
                sb.append(CSVUtil.toCSV(account.isEnable())).append(",");
//                sb.append(CSVUtil.toCSV(account.getRatio()));
                sb.append(CSVUtil.toCSV(account.getRATIO()));
                sb.append("\n");
            }
        }
        return null == sb ? null : sb.toString();
    }
}
