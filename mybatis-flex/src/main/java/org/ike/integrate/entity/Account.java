package org.ike.integrate.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table("tb_account")
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id(keyType = KeyType.Auto)
    private String id;

    private String userName;

    private Date birthday;

    private boolean enable;

    @Column("RATIO")
    private BigDecimal RATIO;

}

