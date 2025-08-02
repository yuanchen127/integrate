package org.ike.integrate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
//		registry.addConverter(new UppercaseConverter());
	}

	// 自定义转换器
	public static class UppercaseConverter implements Converter<String, String> {
		@Override
		public String convert(String source) {
			return source != null ? source.toUpperCase() : null;
		}
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//		resolvers.add(0, new UppercaseRequestParamArgumentResolver());
//		resolvers.add(0, new UpperCaseArgumentResolver());
	}
}
