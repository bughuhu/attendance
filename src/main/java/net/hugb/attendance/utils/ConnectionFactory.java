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
 * 数据库连接工厂
 * 
 * @author hugb
 *
 */
public class ConnectionFactory {

	// 读取属性文件
	Properties prop = new Properties();

	private Connection con;
	//
	private static ConnectionFactory conn;

	/**
	 * 构造函数
	 */
	private ConnectionFactory() {

		// 使用ClassLoader加载properties配置文件生成对应的输入流
		InputStream in = Application.class.getClassLoader().getResourceAsStream("application.properties");

		try {

			// 使用properties对象加载输入流
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 带参数构造函数
	 */
	private ConnectionFactory(String filePath) throws IOException {

		// 通过输入缓冲流进行读取配置文件
		try {
			InputStream InputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
			// 加载输入流
			prop.load(InputStream);

			// 根据关键字获取value值
			// value = prop.getProperty(keyWord);
		} catch (FileNotFoundException e) {
			System.out.println("------------路径:" + filePath + "文件未找到.----------------");
		}
	}

	static {
		conn = new ConnectionFactory();
	}

	/**
	 * 连接实例
	 */
	public static ConnectionFactory getInstance() {
		return conn;
	}

	/**
	 * 更新数据
	 */
	public int setData(String query) {
		int n = 0;
		try {

			// 这个驱动的地址不要改
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

			// 带密码的access数据库
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

			// 这个驱动的地址不要改
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

			// 带密码的access数据库
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

			// 这个驱动的地址不要改
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

			// 带密码的access数据库
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
			System.out.println("--------------连接数据库失败.---------------------");
			return null;

		}
		return V;
	}
}
