package org.ike.neo4j.eureka.client.events.enums;

import org.ike.neo4j.eureka.client.events.context.EventContext;

public enum EventType {
    ADD(1),
    SUB(2);
    private Integer code;

    private EventType(Integer code) {
        this.code = code;
    }
    public Integer getCode() {
        return this.code;
    }
}
