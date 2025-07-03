package org.ike.integrate.tool;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadExternalJavaFile {

	public static void main(String[] args) {
		try {
			// 项目外 Java 文件的绝对路径
			String javaFilePath = "/Users/ike/source/git/tt/PushLog.java";
			// 编译 Java 文件
			compileJavaFile(javaFilePath);
			// 加载编译后的类
			Class<?> clazz = loadClass(javaFilePath);
			if (clazz != null) {
				System.out.println("成功加载类: " + clazz.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 编译 Java 文件
	 * @param javaFilePath Java 文件的绝对路径
	 * @throws IOException 编译过程中可能出现的 IO 异常
	 */
	public static void compileJavaFile(String javaFilePath) throws IOException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int result = compiler.run(null, null, null, javaFilePath);
		if (result != 0) {
			throw new RuntimeException("Java 文件编译失败");
		}
	}

	/**
	 * 加载编译后的类
	 * @param javaFilePath Java 文件的绝对路径
	 * @return 加载后的 Class 对象
	 * @throws Exception 加载过程中可能出现的异常
	 */
	public static Class<?> loadClass(String javaFilePath) throws Exception {
		// 获取 Java 文件所在的目录
		File javaFile = new File(javaFilePath);
		File classDir = javaFile.getParentFile();
		// 创建 URLClassLoader 用于加载类
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classDir.toURI().toURL()});
		// 获取类名
		String className = getClassName(javaFilePath);
		// 加载类
		return classLoader.loadClass(className);
	}

	/**
	 * 获取 Java 文件对应的类名
	 * @param javaFilePath Java 文件的绝对路径
	 * @return 类名
	 */
	public static String getClassName(String javaFilePath) {
		File javaFile = new File(javaFilePath);
		String fileName = javaFile.getName();
		// 去掉 .java 后缀
		String className = fileName.substring(0, fileName.lastIndexOf('.'));
		return className;
	}
}