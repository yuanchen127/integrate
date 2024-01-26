package org.ike.neo4j.eureka.client.events.req;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
    public static void main(String[] args) {
        TestEventReq req = new TestEventReq("a");
        log.info("code:{}", req.getCode());

    }
}
