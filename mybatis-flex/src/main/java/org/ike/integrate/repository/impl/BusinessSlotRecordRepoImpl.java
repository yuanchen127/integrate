package org.ike.integrate.repository.impl;

import com.mybatisflex.core.query.QueryWrapper;
import org.ike.integrate.entity.PushLog;
import org.ike.integrate.mapper.IPushLogMapper;
import org.ike.integrate.repository.BusinessSlotRecordRepo;
import org.ike.integrate.slot.common.Page;
import org.ike.integrate.slot.common.SlotRecordPoint;
import org.ike.integrate.slot.req.PageSearchReq;
import org.ike.integrate.transfer.PushLogTransfer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
    public List<SlotRecordPoint> listRecord() {
        List<PushLog> list = iPushLogMapper.selectListByQuery(new QueryWrapper());
        return PushLogTransfer.toRecordPointList(list);
    }

    @Override
    public Set<String> listClass() {
        return iPushLogMapper.listClass();
    }

    @Override
    public Page<SlotRecordPoint> page(PageSearchReq req) {
        com.mybatisflex.core.paginate.Page<PushLog> result = iPushLogMapper.paginate(req.getCurrent(), req.getSize(), new QueryWrapper(){{
            if (StringUtils.hasText(req.getClassName())) {
                eq(PushLog::getDeclareClazz, req.getClassName());
            }
            if (StringUtils.hasText(req.getParam())) {
                like(PushLog::getParam, req.getParam());
            }
            orderBy(PushLog::getCreateTime, true);
        }});
        Page<SlotRecordPoint> page = new Page<SlotRecordPoint>();
        page.setCurrent(result.getPageNumber());
        page.setSize(result.getPageSize());
        page.setData(PushLogTransfer.toRecordPointList(result.getRecords()));
        page.setTotal(result.getTotalRow());
        return page;
    }

    @Override
    public SlotRecordPoint getRecord(Serializable id) {
        PushLog record = iPushLogMapper.selectOneById(id);
        return PushLogTransfer.toRecordPoint(record);
    }
}
