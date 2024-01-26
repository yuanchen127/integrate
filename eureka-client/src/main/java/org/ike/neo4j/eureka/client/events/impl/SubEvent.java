package org.ike.neo4j.eureka.client.events.impl;

import lombok.Data;
import org.ike.neo4j.eureka.client.events.AbstractEvent;
import org.ike.neo4j.eureka.client.events.enums.EventType;
import org.springframework.stereotype.Component;

@Component
@Data
public class SubEvent extends AbstractEvent {
    private EventType type = EventType.SUB;
}
