package org.ike.neo4j.eureka.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.ike.neo4j.eureka.client.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {

    @Value("${integrate.trans.log.force:true}")
    private boolean ifTransLogForce;

    @Autowired
    private AsyncService asyncService;

    private AtomicInteger count = new AtomicInteger(0);

    @RequestMapping("sleep")
    public boolean test() throws InterruptedException {
        System.out.println("starting sleep……"+count.addAndGet(1));
        Thread.sleep(300+(int)(Math.random()*1000));
        System.out.println("end sleep……"+count.get());
        return false;
    }

    @RequestMapping("broken")
    public String testBrokenPipe() throws InterruptedException {
        System.out.println("进入睡眠");
        Thread.sleep(500);
        System.out.println("测试BROKEN结束");
        return "ok";
    }

    @RequestMapping("async")
    public boolean testAsync() {
        boolean isSuccess = false;
        boolean ifSaveTransLog = false;
        //todo 保存HIS发送参数日志
        if (!ifSaveTransLog && !ifTransLogForce) {
            isSuccess = true;
        }
        log.info("调用异步任务");
        asyncService.async_1("haha");
        log.info("等待执行异步任务");
        return false;
    }

    public static void main(String[] args) {
        log.info("date:{}", new Date("2022-11-28T11:11:11"));
    }

}
