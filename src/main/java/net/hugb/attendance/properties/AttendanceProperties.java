package net.hugb.attendance.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.hugb.attendance.Application;

public class AttendanceProperties {

	// ��ȡ�����ļ�
	Properties prop = new Properties();

	// ��������ַ
	public String url;

	/**
	 * ���캯��
	 */
	public AttendanceProperties() {

		// ʹ��ClassLoader����properties�����ļ����ɶ�Ӧ��������
		InputStream in = Application.class.getClassLoader().getResourceAsStream("application.properties");

		try {

			// ʹ��properties�������������
			prop.load(in);

			url = prop.getProperty("attendance.server.url");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
