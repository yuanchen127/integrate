package org.ike.integrate.controller;

import com.mybatisflex.core.query.QueryWrapper;
import org.ike.integrate.entity.Account;
import org.ike.integrate.mapper.IAccountMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {

    @Resource
    private IAccountMapper IAccountMapper;
    @RequestMapping("list")
    public List<Account> list() {
        QueryWrapper query = new QueryWrapper();
//        query.select(ACCOUNT.ALL_COLUMNS);
//        query.where(ACCOUNT.ID.ge(0));
        List<Account> list = IAccountMapper.selectListByQuery(query);
        return IAccountMapper.selectAll();
    }

    @RequestMapping("/{id}")
    public Account getById(@PathVariable("id") String id) {
        return IAccountMapper.getById(id);
    }

    @RequestMapping("/list/{enable}")
    public List<Account> listEnableAccount(@PathVariable("enable")boolean enable) {
        return IAccountMapper.listEnableAccount(enable);
    }
}
