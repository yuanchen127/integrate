package org.ike.integrate.slot.common;

/**
 * 业务埋点顶层接口
 */
public interface SlotService<R, T extends AbstractSlotReturn> {

    T pushData(R param);
}
