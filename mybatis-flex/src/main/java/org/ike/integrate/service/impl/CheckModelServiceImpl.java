package org.ike.integrate.service.impl;

import com.mybatisflex.annotation.Table;
import org.ike.integrate.entity.ModelCheckInfo;
import org.ike.integrate.service.CheckModelService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class CheckModelServiceImpl implements CheckModelService {

	private static final String CLASS_PATH_WIN = "src\\main\\java\\";
	private static final String CLASS_PATH_LINUX = "src/main/java/";
	private static final String SUFFIX_JAVA = ".java";

	@Override
	public List<ModelCheckInfo> getModelAnnotationValue(String dirPath) {
		List<ModelCheckInfo> infoList = new ArrayList<ModelCheckInfo>();
		HashSet<String> files = getChildrenFileList(dirPath);
		files.forEach(filePath -> {
			ModelCheckInfo var = getModelAnnotationInfo(filePath);
			if (null != var) {
				infoList.add(var);
			}
		});
		return infoList;
	}

	private HashSet<String> getChildrenFileList(String filePath) {
		HashSet<String> fileSet = new HashSet<String>();
		File file = new File(filePath);
		if (file.isFile()) {
			fileSet.add(file.getAbsolutePath());
		}
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles != null) {
				for (File childFile : childFiles) {
					if (childFile.isFile()) {
						fileSet.add(childFile.getAbsolutePath());
					}
					if (childFile.isDirectory()) {
						fileSet.addAll(getChildrenFileList(childFile.getAbsolutePath()));
					}
				}
			}
		}
		return fileSet;
	}

	private ModelCheckInfo getModelAnnotationInfo(String filePath) {
		ModelCheckInfo info = new ModelCheckInfo();
		//根据java文件加载类, 并获取指定注解信息
		try {
			//根据java文件路径转换为package+class名称
			Class<?> clazz = Class.forName(convertPathToClassName(filePath));
			Annotation[] annotations = clazz.getAnnotations();
			if (0 == annotations.length) {
				return null;
			}
			for (Annotation annotation1 : annotations) {
				if (Table.class.equals(annotation1.annotationType())) {
					info.setTableName(((Table) annotation1).value());
				}
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		return info;
	}

	private String convertPathToClassName(String filePath) {
		// 检查绝对路径是否以类路径开头
		String classPath = isWindows() ? CLASS_PATH_WIN : CLASS_PATH_LINUX;
		boolean containClasspath = filePath.contains(classPath);
		if (!(containClasspath && filePath.endsWith(SUFFIX_JAVA))) {
			return null;
		}
		// 去除类路径前缀
		String relativePath = filePath.substring(filePath.lastIndexOf(classPath)+ classPath.length());

		// 去除开头的路径分隔符
		if (relativePath.startsWith("/") || relativePath.startsWith("\\")) {
			relativePath = relativePath.substring(1);
		}

		// 去除 .java 后缀
		if (relativePath.endsWith(SUFFIX_JAVA)) {
			relativePath = relativePath.substring(0, relativePath.length() - 5);
		}

		// 将路径分隔符转换为点号
		relativePath = relativePath.replace('/', '.').replace('\\', '.');

		return relativePath;
	}

	private boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		return os.contains("win");
	}

}
