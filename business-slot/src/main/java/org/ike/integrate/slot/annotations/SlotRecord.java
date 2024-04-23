package org.ike.integrate.slot.annotations;

import java.lang.annotation.*;

/**
 * 添加该注解的实现方法被标记为埋点业务数据推送实现方法, 添加该注解的方法执行异常时将推送参数信息存储为记录
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SlotRecord {
}
