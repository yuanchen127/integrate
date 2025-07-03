package org.ike.integrate.config.condition;

import org.ike.integrate.enums.DataBaseEnum;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

public class DataBaseProfileCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String dbProfile = context.getEnvironment().getProperty("spring.profiles.active");

		Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(
				DataBase.class.getName());

		if (null != annotationAttributes) {
			DataBaseEnum enumProfile = (DataBaseEnum) annotationAttributes.get("profile");
			return enumProfile.getDbType().equals(dbProfile);
		}
		return false;
	}
}
