package org.ike.integrate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ike.integrate.common.res.RecordAbstractSlotReturn;
import org.ike.integrate.service.RecordService;
import org.ike.integrate.slot.common.SlotService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Slf4j
@Service("recordParamWithName")
public class RecordServiceWithNameImpl implements RecordService, SlotService<Map<String, String>, RecordAbstractSlotReturn> {
    @Override
    public boolean record() {
        return false;
    }

    @Override
    public boolean record(Map<String, String> param) {
        log.warn("recordParamWithName, param:{}", param);
        return false;
    }

    @Override
    public RecordAbstractSlotReturn pushData(Serializable messageId, Map<String, String> param) {
        log.warn("recordParamWithName, param:{}", param);
        RecordAbstractSlotReturn result = new RecordAbstractSlotReturn();
        result.setSuccess(Integer.parseInt(param.get("count"))>100);
        return result;
    }
}

