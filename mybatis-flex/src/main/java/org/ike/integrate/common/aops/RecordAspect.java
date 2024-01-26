package org.ike.integrate.common.aops;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.ike.integrate.common.annotations.RecordLog;
import org.ike.integrate.entity.PushLog;
import org.ike.integrate.mapper.IPushLogMapper;
import org.ike.integrate.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Aspect
@Component
public class RecordAspect {

    @Autowired
    private IPushLogMapper iPushLogMapper;

    @Pointcut("@annotation(org.ike.integrate.common.annotations.RecordLog)")
    public void addAdvice() {
        log.info("recording");
    }

    @Before("addAdvice()")
    public void before(JoinPoint point) {
        Object[] args = point.getArgs();
        Object param = null;
        if (null != args && args.length > 0) {
            param = args[0];
            log.info("record param:{}", JSON.toJSONString(param));
        }
        Class<?> beanClass = (point.getThis().getClass().getSuperclass().getInterfaces())[0];
        String declareTypeName = point.getSignature().getDeclaringTypeName();
        Class declareType = point.getSignature().getDeclaringType();
        String name = point.getSignature().getName();


        PushLog log = new PushLog();
        log.setId(UUID.randomUUID().node());
        log.setClazz(beanClass.getName());
        log.setParam(null==param? null: JSON.toJSONString(param));
        log.setMethod(name);
        log.setCreateTime(new Date());

        iPushLogMapper.insert(log);
        RecordAspect.log.info("before recording");
    }

//    @Around("addAdvice() && @annotation(anno)")
//    public void arround(ProceedingJoinPoint point, RecordLog anno) {
//        log.info(anno.toString());
//    }

    @After("addAdvice() && @annotation(anno)")
    public void after(JoinPoint point, RecordLog anno) {
        log.info(anno.toString());
        log.info("after recording");
    }

}
