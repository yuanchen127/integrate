package org.ike.integrate.slot.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SlotContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private final ConcurrentHashMap<String, SlotBeanInfo> context = new ConcurrentHashMap<>();

    private final Hashtable<Integer, SlotEvent> slotEvents = new Hashtable<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SlotContext.applicationContext = applicationContext;

        Map<String, SlotService> beanMap = applicationContext.getBeansOfType(SlotService.class);
        if (CollectionUtils.isEmpty(beanMap)) {
            return;
        }
        beanMap.forEach((beanName, bean) -> {
            SlotEvent event = bean.registerEvent();
            SlotBeanInfo beanInfo = new SlotBeanInfo();
            Class<SlotService> superClazz = (Class<SlotService>) bean.getClass().getSuperclass();
            Type[] genericInterfaces = superClazz.getGenericInterfaces();
            Arrays.stream(genericInterfaces).forEach((type) -> {
                if (SlotService.class.getName().equals(type.getTypeName())) {
                    beanInfo.setParamClass((Class) ((ParameterizedType) type).getActualTypeArguments()[0]);
                }
            });
            beanInfo.setBeanName(beanName);
            beanInfo.setDeclareClazz(superClazz);
            beanInfo.setBean(bean);
            beanInfo.setEvent(event);
            registerEvent(event);
            context.put(superClazz.getName(), beanInfo);
        });
    }

    public SlotService getSlotBean(String declareClazzName) {
        return getSlotBeanInfo(declareClazzName).getBean();
    }

    public SlotBeanInfo getSlotBeanInfo(String declareClazzName) {
        return context.get(declareClazzName);
    }

    public static SlotContext getContext() {
        return applicationContext.getBean(SlotContext.class);
    }

    private boolean registerEvent(SlotEvent event) {
        if (null == event) {
            return false;
        }
        if (slotEvents.containsKey(event.getId())) {
            throw new RuntimeException(String.format("埋点业务[%s]存在重复实现类或者业务事件ID设置重复", event.getName()));
        }
        slotEvents.put(event.getId(), event);
        return true;
    }

    public Collection<SlotEvent> listSlotEvent() {
        return slotEvents.values();
    }
}
