package org.ike.integrate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ike.integrate.common.res.RecordAbstractSlotReturn;
import org.ike.integrate.service.RecordService;
import org.ike.integrate.slot.annotations.SlotRecord;
import org.ike.integrate.slot.common.SlotService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Slf4j
@Service("recordService")
public class RecordServiceImpl implements RecordService, SlotService<Map<String, String>, RecordAbstractSlotReturn> {

//    @SlotRecord
    public boolean record() {
        log.info("recorded");
        return false;
    }


    @Override
    public boolean record(Map<String, String> param) {

        return true;
    }

    @SlotRecord
    @Override
    public RecordAbstractSlotReturn pushData(Map<String, String> param) {
        log.info("trans param");
        RecordAbstractSlotReturn result = new RecordAbstractSlotReturn();
        result.setSuccess(0 == Integer.parseInt(param.get("count")) % 2);
        return result;
    }

    @Override
    public RecordAbstractSlotReturn pushData(Serializable id, Map<String, String> param) {
        return null;
    }
}
