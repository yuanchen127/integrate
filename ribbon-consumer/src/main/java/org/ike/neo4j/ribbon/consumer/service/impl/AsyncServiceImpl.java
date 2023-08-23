package org.ike.neo4j.ribbon.consumer.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ike.neo4j.ribbon.consumer.common.AsyncResponse;
import org.ike.neo4j.ribbon.consumer.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {
    @Async
    @Override
    public CompletableFuture<AsyncResponse> test(int count, Date start) throws InterruptedException {
//        Thread.sleep(1000);
        log.info("test sync starting……");
        AsyncResponse response = new AsyncResponse();
        response.setInternal(new Date().getTime() - start.getTime());
        response.setCount(count);
        return CompletableFuture.completedFuture(response);
    }
}
