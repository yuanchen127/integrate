package org.ike.integrate.req;


import lombok.Data;
import lombok.experimental.Accessors;
import org.ike.integrate.common.annotions.UpperCaseValue;

@Data
@Accessors(chain = true)
public class Info {

    private String id;

    @UpperCaseValue
    private String name;

    private String cksmc;

    private String cksbm;

    private int lx;

    /**
     * 判断参数字段是否存在NULL
     */
    public boolean existNull() {
        return null == id || null == name || null == cksmc || null ==cksbm || 0==lx ;
    }
}
