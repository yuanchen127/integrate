package org.ike.integrate.config.aops;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.ike.integrate.common.annotions.UpperCaseValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Slf4j
@Aspect
@Component
//@Order(-99)
public class UppercaseAspect {

	@Autowired
	private HttpServletRequest request;
	// 拦截所有Controller方法
	@Pointcut("execution(public * org.ike.*.controller.*.*(..))")
	public void beforeMethod(JoinPoint joinPoint) throws IllegalAccessException, NoSuchFieldException {

	}

	@Around("execution(public * org.ike.*.controller.*.*(..)))")
	public Object processParams(ProceedingJoinPoint joinPoint) throws Throwable {
		//获取方法签名
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Class<?> targetCls = joinPoint.getTarget().getClass();
		Method method = targetCls.getDeclaredMethod(signature.getName(),signature.getParameterTypes());
		Parameter[] parameters = method.getParameters();
		//获取指定方法的参数名
		ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
		String[] parameterNames = pnd.getParameterNames(method);
		//循环判断处理方法参数
		Object[] args = joinPoint.getArgs();
		for(int i = 0;i<parameters.length;i++){
			args[i] = processUppercase(parameters[i], args[i]);
		}
		return joinPoint.proceed(args);
	}

	private Object processUppercase(Parameter parameter, Object arg) throws IllegalAccessException, NoSuchFieldException {
		if (null == arg) {
			return arg;
		}

		Class<?> clazz = parameter.getType();
		if (String.class == clazz && parameter.isAnnotationPresent(UpperCaseValue.class)) {
			log.info("参数:{}", arg);
			return ((String) arg).toUpperCase();
		}
		return arg;
	}

	private void processUppercase(Object obj) throws IllegalAccessException {
		Class<?> clazz = obj.getClass();
		if (!clazz.isPrimitive()) {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(UpperCaseValue.class) && field.getType() == String.class) {
					field.setAccessible(true);
					String value = (String) field.get(obj);
					if (value != null) {
						field.set(obj, value.toUpperCase());
					}
				}
			}
		}
	}
}
