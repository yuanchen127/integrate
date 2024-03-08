package org.ike.integrate.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.ike.integrate.enums.DataType;
import org.ike.integrate.exceptions.BinaryTruncationException;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@Data
@Table("INFORMATION_SCHEMA.COLUMNS")
public class TableColumnSchema {

    @Column("COLUMN_NAME")
    private String columnName;

    @Column("DATA_TYPE")
    private DataType dataType;

    @Column("CHARACTER_MAXIMUM_LENGTH")
    private int maxCharLength;

    public boolean validateColumn(String param) throws Exception {
        if (!StringUtils.hasText(param)) {
            return true;
        }
        //检测字符串类型的数据的内容长度
        if (dataType.getType().contains("char")) {
            if (param.length() > maxCharLength) {
                String msg = String.format("字段:%s 超长, 参数:%s", columnName, param);
                throw new BinaryTruncationException(msg);
            }
        }
        return false;
    }

    public boolean validate(Object tableObj) throws Exception {
        String property = StringUtil.underlineToCamel(this.columnName);
        if (isJavaClass(tableObj.getClass())) {
            return false;
        }
        Object result = null;

        try {
            Method method = tableObj.getClass().getMethod("get".concat(StringUtils.capitalize(property)), null);
            result = method.invoke(tableObj, null);
        } catch (NoSuchMethodException e){
            log.warn("Class:{}中无属性:{}", tableObj.getClass(), property);
        }catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e1) {
            throw e1;
        }
        if (result instanceof String) {
            return validateColumn((String) result);
        }
        return false;
    }

    private boolean isJavaClass(Class<?> clazz) {
        return clazz != null && clazz.getClassLoader() == null;
    }

}
