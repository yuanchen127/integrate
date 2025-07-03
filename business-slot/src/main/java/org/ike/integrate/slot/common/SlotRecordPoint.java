package org.ike.integrate.slot.common;

import lombok.Data;

@Data
public class SlotRecordPoint {

    /**
     * 记录ID
     */
    private String id;

    /**
     * bean对象的class名称
     */
    private String beanClassName;

    /**
     * 埋点推送数据内容
     */
    private String param;

    /**
     * 业务事件ID
     */
    private Integer eventId;

    /**
     * 构造函数
     */
    public SlotRecordPoint() {
    }

    /**
     * 参数构造函数
     */
    public SlotRecordPoint(String id, String beanClassName, String param) {
        this.id = id;
        this.beanClassName = beanClassName;
        this.param = param;
    }
}
