package org.ike.integrate.req;

import lombok.Data;

@Data
public class QuestionReq {

    /**
     * 主题、简介
     */
    private String title;

    private String id;

    private String content;

    private String createTime;




}
