package org.ike.integrate.service;

import org.ike.integrate.entity.ModelCheckInfo;

import java.util.List;

public interface CheckModelService {

	/**
	 * 或者model的注解信息
	 * @param dirPath model项目文件夹路径
	 */
	public List<ModelCheckInfo> getModelAnnotationValue(String dirPath);
}