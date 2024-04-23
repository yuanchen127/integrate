package org.ike.integrate.slot.req;

import lombok.Data;

@Data
public class PageSearchReq {

    private long current;

    private long size;

    private String className;

    private String param;

    /**
     * 检查参数current,size不能为空或者<=0
     */
    public void checkParam() {
        if (current <= 0 || size <= 0) {
            throw new IllegalArgumentException("current or size is illegal");
        }
    }


}
