package org.ike.integrate.ribbon.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ribbon/consumer")
public class TestConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("test")
    public boolean testConsumer() {
        return restTemplate.getForEntity("http://EUREKA-CLIENT/test/sleep", Boolean.class).getBody();
    }

}
