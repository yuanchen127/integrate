package org.ike.integrate.slot.common;

/**
 * 规范数据推送实现返回信息格式, 便于定义、判断推送是否正确
 */
public abstract class AbstractSlotReturn {

    public abstract boolean validateReturn();
}
