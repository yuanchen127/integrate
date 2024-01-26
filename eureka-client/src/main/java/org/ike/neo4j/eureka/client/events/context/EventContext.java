package org.ike.neo4j.eureka.client.events.context;

import lombok.extern.slf4j.Slf4j;
import org.ike.neo4j.eureka.client.events.AbstractEvent;
import org.ike.neo4j.eureka.client.events.enums.EventType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class EventContext implements ApplicationContextAware {

    private ConcurrentHashMap<EventType, AbstractEvent> context;

    public AbstractEvent getEvent(EventType type) {
        if (null == context) {
            return null;
        }
        return context.get(type);
    }

    public ConcurrentHashMap<EventType, AbstractEvent> setEvent(EventType type, AbstractEvent event) {
        if (null == context) {
            context = new ConcurrentHashMap<>();
        }
        if (null != context.get(type)) {
            log.error("重复定义事件");
        }
        context.put(type, event);
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, AbstractEvent> map = applicationContext.getBeansOfType(AbstractEvent.class);
        map.forEach((beanName, bean)->{
            setEvent(bean.getType(), bean);
        });
        log.info("context: {}", context);
    }
}
