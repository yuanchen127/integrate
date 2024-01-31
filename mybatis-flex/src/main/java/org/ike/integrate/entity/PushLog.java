package org.ike.integrate.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("tb_record")
public class PushLog {

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String param;

    private String method;

    private String clazz;

    private Date createTime;
}
