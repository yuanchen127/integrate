package org.ike.integrate.slot.common;

import lombok.Data;

@Data
public class SlotRecordPoint {

    /**
     * bean对象的class名称
     */
    private String beanClassName;

    /**
     * 埋点推送数据内容
     */
    private String param;
}
