package org.ike.integrate.slot.common;

import java.io.Serializable;

/**
 * 业务埋点顶层接口
 */
public interface SlotService<R, T extends AbstractSlotReturn> {

    default T pushData(R param){
        return pushData(null, param);
    }

    T pushData(Serializable messageId, R param);
}
