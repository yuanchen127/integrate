package org.ike.neo4j.eureka.client.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ike.neo4j.eureka.client.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@EnableAsync
@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {




    @Async
    @Override
    public Boolean async_1(String ack) {
        try {
            Thread.sleep(3000);
            log.info("异步任务执行中……");
            log.info("参数：{}", ack);
            try {
                //todo 保存HIS发送参数日志
                return true;
            } catch (Exception e) {
                e.printStackTrace();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
