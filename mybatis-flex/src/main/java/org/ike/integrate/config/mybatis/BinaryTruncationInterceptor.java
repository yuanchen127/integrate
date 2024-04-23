package org.ike.integrate.config.mybatis;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.mybatisflex.annotation.Table;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.ike.integrate.entity.TableColumnSchema;
import org.ike.integrate.repository.BinaryTruncationFieldRepo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

@Slf4j
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class BinaryTruncationInterceptor implements Interceptor {

    @Resource
    private BinaryTruncationFieldRepo binaryTruncationFieldRepo;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        Object[] args = invocation.getArgs();
        Method method = invocation.getMethod();
        Object process = null;
        try {
            process = invocation.proceed();
        } catch (Exception e) {
            if (isBinaryTruncationException(e) || isBinaryTruncationException(e.getCause())) {
                log.error("mybatis.interceptor: 自定义二进制阶段异常处理");
                String mapperId = ((MappedStatement) (args[0])).getId();
                Class entityClazz = getEntityGenericClass(mapperId);

                String tableName = ((Table)entityClazz.getAnnotation(Table.class)).value();
                Map<String, TableColumnSchema> tmpMap = binaryTruncationFieldRepo.getTableColumnSchema(null, tableName);

                BoundSql boundSql = ((MappedStatement) args[0]).getBoundSql(args[1]);
                Object entity = getEntityParam(boundSql, entityClazz);
                tmpMap.forEach((k, v) -> {
                    try {
                        v.validate(entity);
                    } catch (Exception e1) {
                        throw new RuntimeException(e1);
                    }
                });
            }
            log.warn("mybatis.interceptor: {}", e.getMessage());
        }

        return process;
    }

    /**
     * 判断异常类型是否为数据库二进制截断异常
     * @param throwable 异常
     * @return Boolean
     */
    private boolean isBinaryTruncationException(Throwable throwable) {
        if (null == throwable) {
            return false;
        }

        if (throwable instanceof SQLServerException && throwable.getMessage().contains("truncate")) {
            return true;
        }
        return false;
    }

    private Class getEntityGenericClass(String id) throws ClassNotFoundException {
        String mapperClazzStr = id.substring(0, id.lastIndexOf("."));
        Class<?> mapperClazz = Class.forName(mapperClazzStr);
        Class entityClazz = null;
        Type[] interfacesTypes = mapperClazz.getGenericInterfaces();
        for (Type type : interfacesTypes) {
            if (type instanceof ParameterizedType) {
                entityClazz = (Class) ((ParameterizedType)type).getActualTypeArguments()[0];
            }
        }
        return entityClazz;
    }

    private Object getEntityParam(BoundSql boundSql, Class entityClazz) {
        Object paramMap = boundSql.getParameterObject();
        if (paramMap instanceof Map) {
            Map<String, Object> tmpMap = ((Map) paramMap);

            for (Map.Entry entry : tmpMap.entrySet()) {
                Object v = entry.getValue();
                if (entityClazz.equals(v.getClass())) {
                    return v;
                }
            }
        }
        return null;
    }
}
