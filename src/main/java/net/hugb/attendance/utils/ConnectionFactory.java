package net.hugb.attendance.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import net.hugb.attendance.Application;

/**
 * ���ݿ����ӹ���
 * 
 * @author hugb
 *
 */
public class ConnectionFactory {

	// ��ȡ�����ļ�
	Properties prop = new Properties();

	private Connection con;
	//
	private static ConnectionFactory conn;

	/**
	 * ���캯��
	 */
	private ConnectionFactory() {

		// ʹ��ClassLoader����properties�����ļ����ɶ�Ӧ��������
		InputStream in = Application.class.getClassLoader().getResourceAsStream("application.properties");

		try {

			// ʹ��properties�������������
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���������캯��
	 */
	private ConnectionFactory(String filePath) throws IOException {

		// ͨ�����뻺�������ж�ȡ�����ļ�
		try {
			InputStream InputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
			// ����������
			prop.load(InputStream);

			// ���ݹؼ��ֻ�ȡvalueֵ
			// value = prop.getProperty(keyWord);
		} catch (FileNotFoundException e) {
			System.out.println("------------·��:" + filePath + "�ļ�δ�ҵ�.----------------");
		}
	}

	static {
		conn = new ConnectionFactory();
	}

	/**
	 * ����ʵ��
	 */
	public static ConnectionFactory getInstance() {
		return conn;
	}

	/**
	 * ��������
	 */
	public int setData(String query) {
		int n = 0;
		try {

			// ��������ĵ�ַ��Ҫ��
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

			// �������access���ݿ�
			con = DriverManager.getConnection("jdbc:ucanaccess://" + prop.getProperty("ms.access.path"));

			Statement stmt = con.createStatement();
			n = stmt.executeUpdate(query);
		} catch (Exception ex) {
			return -1;
		}
		return n;
	}

	/**
	 * 
	 */
	public ResultSet getResultSet(String query) {
		ResultSet rs = null;
		try {

			// ��������ĵ�ַ��Ҫ��
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

			// �������access���ݿ�
			con = DriverManager.getConnection("jdbc:ucanaccess://" + prop.getProperty("ms.access.path"));

			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(query);
		} catch (Exception ex) {
			System.out.print("Conn fail in resultset");
			return null;

		}
		return rs;
	}

	/**
	 * 
	 */
	public Vector getData(String query) {
		Vector V = new Vector();
		try {

			// ��������ĵ�ַ��Ҫ��
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

			// �������access���ݿ�
			con = DriverManager.getConnection("jdbc:ucanaccess://" + prop.getProperty("ms.access.path"));

			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colcount = rsmd.getColumnCount();
			while (rs.next()) {
				Vector sub = new Vector();
				for (int i = 1; i <= colcount; i++)
					sub.add(rs.getString(i));
				V.add(sub);
			}
			rs.close();
			con.close();
		} catch (Exception ex) {
			System.out.println("--------------�������ݿ�ʧ��.---------------------");
			return null;

		}
		return V;
	}
}
