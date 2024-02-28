package org.ike.integrate.common.res;

import lombok.Data;
import org.ike.integrate.slot.common.AbstractSlotReturn;

@Data
public class RecordAbstractSlotReturn extends AbstractSlotReturn {

    private boolean success;

    @Override
    public boolean validateReturn() {
        return success;
    }
}
