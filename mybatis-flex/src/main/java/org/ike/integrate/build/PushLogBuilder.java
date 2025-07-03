package org.ike.integrate.build;

import org.ike.integrate.entity.PushLog;
import org.ike.integrate.slot.common.SlotRecordPoint;

import java.util.List;

public class PushLogBuilder {

    public static SlotRecordPoint toRecordPoint(PushLog log) {
        return new SlotRecordPoint(log.getId().toString(), log.getDeclareClazz(), log.getParam());
    }

    public static List<SlotRecordPoint> toRecordPointList(List<PushLog> logs) {
        return logs.stream().map(PushLogBuilder::toRecordPoint).collect(java.util.stream.Collectors.toList());
    }
}
