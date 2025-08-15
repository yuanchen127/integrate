package org.ike.integrate.controller;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import lombok.extern.slf4j.Slf4j;
import org.ike.integrate.build.AccountBuilder;
import org.ike.integrate.entity.Account;
import org.ike.integrate.mapper.IAccountMapper;
import org.ike.integrate.repository.BinaryTruncationFieldRepo;
import org.ike.integrate.service.AccountService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("account")
public class AccountController {

    @Resource
    private IAccountMapper iAccountMapper;

    @Resource
    private AccountService accountService;

    @Resource
    private BinaryTruncationFieldRepo binaryTruncationFieldRepo;

    @RequestMapping("first")
    public Account getFirst() {
        return accountService.getFirst();
    }

    @RequestMapping("list")
    public List<Account> list() {
        QueryWrapper query = new QueryWrapper();
//        query.select(ACCOUNT.ALL_COLUMNS);
//        query.where(ACCOUNT.ID.ge(0));
        List<Account> list = iAccountMapper.selectListByQuery(query);
        return iAccountMapper.selectAll();
    }

    @RequestMapping("/{id}")
    public Account getById(@PathVariable("id") String id) {
        Account account = iAccountMapper.getById(id);
        return account;
    }

    @RequestMapping("/list/{enable}")
    public List<Account> listEnableAccount(@PathVariable("enable")boolean enable) {
        return accountService.listEnableAccount(enable);
    }

    @PostMapping("insert")
    public boolean insert(@RequestBody Account account) {
        boolean success = false;
        return 0 < iAccountMapper.insertWithPk(account);
    }

    /**
     * 批量插入Account数据
     * @param accountList
     * @return
     */
    @PostMapping("insert/batch")
    public boolean insertBatch(@RequestBody List<Account> accountList) throws SQLException {
        return accountService.insertBatchWithBulk(accountList);
//        return 0 < iAccountMapper.insertBatch(accountList);
    }

    @PostMapping("insert/batch/pg/{count}")
    public boolean insertBatchPG(@PathVariable int count) {
        List<Account> accoutList = generateAccountList(count);
        String dataStr = AccountBuilder.toCSV(accoutList);
        long start = System.currentTimeMillis();
        accountService.insertBatchWithBulkPG(dataStr);
        log.info("保存数据完成，耗时：{}ms", System.currentTimeMillis() - start);
        return false;
    }

    @PostMapping("insert/large/{count}")
    public boolean insertLargeWithBulk(@PathVariable int count) {
        //记录方法执行消耗时间
        long start = System.currentTimeMillis();
        log.info("开始生成数据");
        List<Account> accountList = generateAccountList(count);
        log.info("生成数据完成，耗时：{}ms", System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        log.info("开始保存数据");
        accountService.insertBatchWithBulk(accountList);
        log.info("保存数据完成，耗时：{}ms", System.currentTimeMillis() - start);
        return true;
    }

    @PostMapping("insert/batch/large/{count}")
    public boolean insertLargeWithBatchExecutor(@PathVariable int count) {
        //记录方法执行消耗时间
        long start = System.currentTimeMillis();
        log.info("开始生成数据");
        List<Account> accountList = generateAccountList(count);
        log.info("生成数据完成，耗时：{}ms", System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        log.info("开始保存数据");
        Db.executeBatch(accountList, 1000, IAccountMapper.class, BaseMapper::insertWithPk);
        log.info("保存数据完成，耗时：{}ms", System.currentTimeMillis() - start);
        return true;
    }

    @PostMapping("update")
    public boolean update(@RequestBody Account account) {
        return 0 < iAccountMapper.update(account);
    }

    /**
     * 根据指定的数量生成账户列表。
     *
     * @param count 要生成的账户数量。如果此值小于等于0，则返回null。
     * @return 一个包含指定数量账户的列表。每个账户的用户名以"user_"开头，后跟其顺序号，
     *         ID与其顺序号相同，生日和比例字段被设为null，账户启用状态设为true。
     * @see Account
     */
    public List<Account> generateAccountList(int count) {
        // 检查要生成的账户数量是否大于0，如果不是，则返回null
        if (count <= 0) {
            return null;
        }

        // 初始化账户列表
        List<Account> accoutList = new ArrayList<>();

        // 循环创建并配置账户
        for (int i = 0; i < count; i++) {
            Account var = new Account();
            // 设置账户用户名，格式为"user_"加上顺序号
            var.setUserName("user_" + i);
            // 设置账户ID为顺序号的字符串形式
            var.setId(String.valueOf(i));
            // 设置账户生日为null
            var.setBirthday(null);
            // 设置账户启用状态为true
            var.setEnable(0==i%2);
            // 设置账户比例为null
            var.setRatio(new BigDecimal("0.1").multiply(new BigDecimal(i)));
//            var.setRATIO(new BigDecimal("0.1").multiply(new BigDecimal(i)));
            // 将账户添加到列表中
            accoutList.add(var);
        }

        // 返回生成的账户列表
        return accoutList;
    }

}
