package org.ike.integrate.trans;

import org.ike.integrate.entity.Question;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OAQuestionTrans {

    public static Question trans(Map<String, Object> question) {
        Question q = new Question();
        q.setId(question.get("field_1").toString());
        q.setTitle(question.get("field_9").toString());
        q.setContent(String.valueOf(question.get("content")));
        q.setCreateTime(question.get("field_30").toString());
        q.setOaExtend(question.get("productname").toString());
        q.setChaoDaoId(question.get("field_22").toString());
        return q;
    }

    public static List<Question> trans(List<Map<String, Object>> questions) {
        return questions.stream().map(OAQuestionTrans::trans).collect(Collectors.toList());
    }
}
