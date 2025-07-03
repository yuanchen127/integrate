package org.ike.integrate.entity;

import lombok.Data;

@Data
public class Question {

    //OA需求ID
    private String id;

    //禅道ID
    private String chaoDaoId;

    private String title;

    private String content;

    //录入时间
    private String createTime;

    //OA扩展信息
    private String oaExtend;
}
