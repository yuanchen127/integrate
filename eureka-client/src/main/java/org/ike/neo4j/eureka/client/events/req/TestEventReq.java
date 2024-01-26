package org.ike.neo4j.eureka.client.events.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestEventReq extends EventReq{
    private String name;

    TestEventReq(String name) {
        this.name = name;
    }
}
