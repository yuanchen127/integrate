package org.ike.integrate.config.resolvers;

import org.ike.integrate.common.annotions.UpperCaseValue;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UppercaseRequestParamArgumentResolver extends AbstractNamedValueMethodArgumentResolver {


	public UppercaseRequestParamArgumentResolver() {
		super();
	}

	public UppercaseRequestParamArgumentResolver(ConfigurableBeanFactory beanFactory) {
		super(beanFactory);
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		RequestParam requestParamAnn = parameter.getParameterAnnotation(RequestParam.class);
		PathVariable pathVariableAnn = parameter.getParameterAnnotation(PathVariable.class);
		if (null != pathVariableAnn) {
			return new UpperCaseNamedValueInfo(pathVariableAnn);
		} else if (null != requestParamAnn) {
			return new UpperCaseNamedValueInfo(requestParamAnn);
		} else {
			return new UpperCaseNamedValueInfo();
		}
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);

		if (servletRequest != null) {
			Object mpArg = MultipartResolutionDelegate.resolveMultipartArgument(name, parameter, servletRequest);
			if (mpArg != MultipartResolutionDelegate.UNRESOLVABLE) {
				return mpArg;
			}
		}

		Object arg = null;
		MultipartRequest multipartRequest = request.getNativeRequest(MultipartRequest.class);
		if (multipartRequest != null) {
			List<MultipartFile> files = multipartRequest.getFiles(name);
			if (!files.isEmpty()) {
				arg = (files.size() == 1 ? files.get(0) : files);
			}
		}
		if (arg == null) {
			String[] paramValues = request.getParameterValues(name);
			if (paramValues != null) {
				arg = (paramValues.length == 1 ? paramValues[0].toUpperCase() : paramValues);
			}
		}
		return arg;
	}

	@Override
	protected void handleMissingValue(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		super.handleMissingValue(name, parameter, request);
	}

	@Override
	protected void handleMissingValue(String name, MethodParameter parameter) throws ServletException {
		super.handleMissingValue(name, parameter);
	}

	@Override
	protected void handleMissingValueAfterConversion(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		super.handleMissingValueAfterConversion(name, parameter, request);
	}

	@Override
	protected void handleResolvedValue(Object arg, String name, MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) {
		super.handleResolvedValue(arg, name, parameter, mavContainer, webRequest);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.hasParameterAnnotation(UpperCaseValue.class) || parameter.hasParameterAnnotation(PathVariable.class)) {
			return true;
		}
		return false;
	}


	private static class UpperCaseNamedValueInfo extends NamedValueInfo {

		public UpperCaseNamedValueInfo() {
			super("", false, ValueConstants.DEFAULT_NONE);
		}

		public UpperCaseNamedValueInfo(RequestParam annotation) {
			super(annotation.name(), annotation.required(), annotation.defaultValue());
		}

		public UpperCaseNamedValueInfo(PathVariable annotation) {
			super(annotation.name(), annotation.required(), ValueConstants.DEFAULT_NONE);
		}
	}
}
