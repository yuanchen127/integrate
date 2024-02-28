package org.ike.integrate.service.impl;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.ike.integrate.slot.common.SlotBeanInfo;
import org.ike.integrate.slot.common.SlotContext;
import org.ike.integrate.entity.PushLog;
import org.ike.integrate.mapper.IPushLogMapper;
import org.ike.integrate.service.PushDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Method;

@Slf4j
@Service
public class PushDataServiceImpl implements PushDataService {

    @Resource
    private SlotContext slotContext;

    @Resource
    private IPushLogMapper iPushLogMapper;

    @Override
    public boolean pushByLog(Serializable logId) {
        PushLog logInfo = iPushLogMapper.selectOneById(logId);
        String declareClassName = logInfo.getDeclareClazz();
        SlotBeanInfo pushBeanInfo = slotContext.getSlotBeanInfo(declareClassName);
        Object param = JSON.parseObject(logInfo.getParam(), pushBeanInfo.getParamClass());
        try {
            Method pushMethod = pushBeanInfo.getDeclareClazz().getMethod("pushData", Object.class);
            pushMethod.invoke(pushBeanInfo.getBean(), param);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        iPushLogMapper.deleteById(logId);
        return true;
    }
}
