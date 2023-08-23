package org.ike.neo4j.ribbon.consumer.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Unit {

    public boolean unit1() {
        log.info("unit 1");
        return false;
    }

    public int unit2() {
        log.info("unit 2");
        return 1;
    }
}
