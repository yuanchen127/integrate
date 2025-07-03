package org.ike.integrate.req;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Info {

    private String id;

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
