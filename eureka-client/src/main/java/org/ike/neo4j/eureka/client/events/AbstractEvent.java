package org.ike.neo4j.eureka.client.events;

import org.ike.neo4j.eureka.client.events.enums.EventType;

public abstract class AbstractEvent {

    protected EventType type;

    public abstract EventType getType();


}
