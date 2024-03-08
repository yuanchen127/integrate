package org.ike.integrate.controller;

import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.ec.ECElGamalDecryptor;
import org.ike.integrate.common.exception.ServiceException;
import org.ike.integrate.entity.Account;
import org.ike.integrate.entity.TableColumnSchema;
import org.ike.integrate.mapper.IAccountMapper;
import org.ike.integrate.repository.BinaryTruncationFieldRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("account")
public class AccountController {

    @Resource
    private IAccountMapper IAccountMapper;

    @Resource
    private BinaryTruncationFieldRepo binaryTruncationFieldRepo;
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

    @PostMapping("insert")
    public boolean insert(@RequestBody Account account) {
        boolean success = false;
        return 0 < IAccountMapper.insertWithPk(account);
    }

    @PostMapping("update")
    public boolean update(@RequestBody Account account) {
        return 0 < IAccountMapper.update(account);
    }


}
