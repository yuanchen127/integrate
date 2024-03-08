package org.ike.integrate.repository.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.ike.integrate.entity.PushLog;
import org.ike.integrate.mapper.IPushLogMapper;
import org.ike.integrate.repository.BusinessSlotRecordRepo;
import org.ike.integrate.slot.common.SlotRecordPoint;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BusinessSlotRecordRepoImpl implements BusinessSlotRecordRepo {

    @Resource
    private IPushLogMapper iPushLogMapper;
    @Override
    public boolean saveRecord(SlotRecordPoint point) {
        PushLog logInfo = new PushLog();
        logInfo.setDeclareClazz(point.getBeanClassName());
        logInfo.setParam(point.getParam());
        logInfo.setCreateTime(new Date());
        logInfo.setId(UUID.randomUUID().hashCode());
        return 0 < iPushLogMapper.insertWithPk(logInfo);
    }

    @Override
    public boolean deleteRecord(Serializable id) {
        return 0 <= iPushLogMapper.deleteById(id);
    }

    @Override
    public List<PushLog> listRecord() {
        return iPushLogMapper.selectListByCondition(null);
    }

    @Override
    public PushLog getRecord(Serializable id) {
        return iPushLogMapper.selectOneById(id);
    }
}
