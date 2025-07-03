package org.ike.integrate.slot.aops;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.ike.integrate.slot.common.AbstractSlotReturn;
import org.ike.integrate.slot.common.SlotContext;
import org.ike.integrate.slot.common.SlotRecordPoint;
import org.ike.integrate.slot.common.SlotService;
import org.ike.integrate.slot.repository.SlotRecordRepo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Slf4j
@Aspect
@Component
public class RecordAspect {

    @Resource
    private SlotContext slotContext;

    @Resource
    private SlotRecordRepo slotRecordRepo;

//    @Pointcut("@annotation(org.ike.integrate.slot.annotations.SlotRecord)")
    @Pointcut("execution(* org.ike.integrate.slot.common.SlotService.pushData(..))")
    public void addAdvice() {}

    @Before("addAdvice()")
    public void before(JoinPoint point) {}

    @AfterThrowing(pointcut = "addAdvice()", throwing = "exception")
    public void throwingAdvice(JoinPoint point, Throwable exception) {
        if (isRetry(point)) {
            return;
        }
        saveRecord(point);
        log.error("系统发生异常：{}， 保存埋点记录信息", exception.getMessage());
    }

    @AfterReturning(pointcut = "addAdvice()", returning = "returnObj")
    public void returningAdvice(JoinPoint point, Object returnObj) {
        if (isRetry(point)) {
            return;
        }
        if (!((AbstractSlotReturn) returnObj).validateReturn()) {
            saveRecord(point);
        }
    }

    /**
     * 根据方法参数中id是否为空, 判断是否为重试
     * @param point 关注点信息
     * @return Boolean 是否重试
     */
    private boolean isRetry(JoinPoint point) {
        Object[] args = point.getArgs();
        if (null != args && args.length > 1) {
            return null != args[0];
        }
        return false;
    }

    private boolean saveRecord(JoinPoint point) {
        Object[] args = point.getArgs();
        Serializable id = null;
        Object param = null;
        if (null == args) {
            return false;
        }
        if (args.length > 1) {
            id = (Serializable) args[0];
            param = args[1];
        } else {
            param = args[0];
        }

        int eventId = ((SlotService) point.getTarget()).registerEvent().getId();
        log.info("record param:{}", JSON.toJSONString(param));
        Class superClazz = point.getThis().getClass().getSuperclass();
//        Class<?> beanClass = (superClazz.getInterfaces())[0];
//        String declareTypeName = point.getSignature().getDeclaringTypeName();
//        Class declareType = point.getSignature().getDeclaringType();
//        String name = point.getSignature().getName();
        SlotRecordPoint msg = new SlotRecordPoint();
        msg.setBeanClassName(superClazz.getName());
        msg.setParam(null==param? null: JSON.toJSONString(param));
        msg.setEventId(eventId);
        return slotRecordRepo.saveRecord(msg);
    }

}
