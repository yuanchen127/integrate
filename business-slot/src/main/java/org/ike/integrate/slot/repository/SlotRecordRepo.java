package org.ike.integrate.slot.repository;

import org.ike.integrate.slot.common.SlotRecordPoint;

import java.io.Serializable;
import java.util.List;

public interface SlotRecordRepo<T> {

    /**
     * 保存记录信息
     * @param point 埋点记录提示信息
     * @return Boolean
     */
    boolean saveRecord(SlotRecordPoint point);

    /**
     * 根据主键删除记录信息
     * @param id 数据主键
     * @return Boolean
     */
    boolean deleteRecord(Serializable id);

    /**
     * 查询记录列表
     * @return 记录信息列表
     */
    List<T> listRecord();

    /**
     * 获取埋点记录信息
     * @param id 数据主键
     * @return 埋点记录
     */
    T getRecord(Serializable id);
}
