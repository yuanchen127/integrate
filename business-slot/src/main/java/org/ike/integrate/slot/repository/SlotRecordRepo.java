package org.ike.integrate.slot.repository;

import com.alibaba.fastjson.JSON;
import org.ike.integrate.slot.common.*;
import org.ike.integrate.slot.req.PageSearchReq;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public interface SlotRecordRepo {

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
    List<SlotRecordPoint> listRecord();

    /**
     * 分页查询
     * @param req 查询请求信息
     * @return 分页查询结果
     */
    Page<SlotRecordPoint> page(PageSearchReq req);

    /**
     * 查询所有类名
     * @return 类名列表
     */
    Set<String> listClass();

    /**
     * 获取埋点记录信息
     * @param id 数据主键
     * @return 埋点记录
     */
    SlotRecordPoint getRecord(Serializable id);

    default boolean retry(Serializable id) {
        SlotRecordPoint logInfo = getRecord(id);
        String declareClassName = logInfo.getBeanClassName();
        SlotBeanInfo pushBeanInfo = SlotContext.getContext().getSlotBeanInfo(declareClassName);
        Object param = JSON.parseObject(logInfo.getParam(), pushBeanInfo.getParamClass());
        AbstractSlotReturn result = null;
        try {
            Method pushMethod = pushBeanInfo.getDeclareClazz().getMethod("pushData", Serializable.class, Object.class);
            result = (AbstractSlotReturn) (pushMethod.invoke(pushBeanInfo.getBean(), id, param));
            if (null != result && result.validateReturn()) {
                deleteRecord(id);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
