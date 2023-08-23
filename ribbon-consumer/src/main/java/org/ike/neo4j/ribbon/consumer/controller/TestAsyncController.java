package org.ike.neo4j.ribbon.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.ike.neo4j.ribbon.consumer.common.AsyncResponse;
import org.ike.neo4j.ribbon.consumer.service.AsyncService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("test/async")
public class TestAsyncController {

    @Resource
    private AsyncService asyncService;

    @RequestMapping("{count}/{time_out}")
    public boolean test(@PathVariable(value = "count") int count, @PathVariable(value = "time_out") long timeOut) throws InterruptedException, ExecutionException {
        if (count <= 0) {
            return true;
        }
        int var_count = count;
        while (count > 0) {
            int times = var_count - count;
            log.info("已第{}次触发调用异步方法", times);
            CompletableFuture<AsyncResponse> result = asyncService.test(times, new Date());
            AsyncResponse response = result.get();
            long internal = response.getInternal();
            if(timeOut<=0 || internal>timeOut)
                log.info("第{}次异步方法调用耗时:{}ms", response.getCount(), internal);
            count--;
        }
        return true;
    }
}
