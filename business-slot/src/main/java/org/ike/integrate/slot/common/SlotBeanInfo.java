package org.ike.integrate.slot.common;

import lombok.Data;

@Data
public class SlotBeanInfo {

    private Class<SlotService> declareClazz;

    private SlotService bean;

    private String beanName;

    private Class paramClass;

    private SlotEvent event;

}
