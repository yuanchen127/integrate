package org.ike.integrate.controller;

import lombok.extern.slf4j.Slf4j;
import org.ike.integrate.entity.Question;
import org.ike.integrate.trans.OAQuestionTrans;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("oa/question")
public class OAQuestionController {

    //同步需求信息
    @PostMapping(value = "sync")
    public boolean sync(@RequestBody List<Map<String, Object>> params) {
//        log.info("参数信息: {}", params);

        //判断params是否empty
        if (params.isEmpty()) {
            return false;
        }
        //循环params, 每20条数据插入一次数据库
        int size ;
        for (int i = 0; i < (size = params.size()); i += 20) {
            List<Map<String, Object>> subList = params.subList(i, Math.min(i + 20, size));
            //插入数据库
            List<Question> tmpList = OAQuestionTrans.trans(subList);
            log.info("tmpList:{}", tmpList);
        }
        return true;
    }
}
