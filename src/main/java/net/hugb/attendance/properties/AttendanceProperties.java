package net.hugb.attendance.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.hugb.attendance.Application;

public class AttendanceProperties {

	// 读取属性文件
	Properties prop = new Properties();

	// 服务器地址
	public String url;

	/**
	 * 构造函数
	 */
	public AttendanceProperties() {

		// 使用ClassLoader加载properties配置文件生成对应的输入流
		InputStream in = Application.class.getClassLoader().getResourceAsStream("application.properties");

		try {

			// 使用properties对象加载输入流
			prop.load(in);

			url = prop.getProperty("attendance.server.url");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
