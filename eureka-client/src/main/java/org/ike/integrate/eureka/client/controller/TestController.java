package org.ike.integrate.eureka.client.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/test")
public class TestController {
    private AtomicInteger count = new AtomicInteger(0);

    @RequestMapping("sleep")
    public boolean test() throws InterruptedException {
        System.out.println("starting sleep……"+count.addAndGet(1));
        Thread.sleep(300+(int)(Math.random()*1000));
        System.out.println("end sleep……"+count.get());
        return false;
    }
}
