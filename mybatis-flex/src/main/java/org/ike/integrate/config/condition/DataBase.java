package org.ike.integrate.config.condition;

import org.ike.integrate.enums.DataBaseEnum;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(DataBaseProfileCondition.class)
public @interface DataBase {

	DataBaseEnum profile();
}
