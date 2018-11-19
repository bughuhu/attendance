package net.hugb.attendance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.hugb.attendance.entity.Card;
import net.hugb.attendance.entity.Employee;
import net.hugb.attendance.properties.AttendanceProperties;
import net.hugb.attendance.utils.ConnectionFactory;
import net.hugb.attendance.utils.HttpKit;
import net.sf.json.JSONObject;

/**
 * 主程序
 * 
 * @author hugb
 *
 */
public class Application {

	public static void main(String[] args) throws SQLException {

		// 查询用户信息表中数据
		String query = "select * from USERINFO";

		// 查询数据
		ResultSet rs = ConnectionFactory.getInstance().getResultSet(query);

		// 遍历查询结果
		while (rs.next()) {

			// 姓名
			String employeeName = rs.getString("name");
			// 部门id
			String departId = rs.getString("name");
			// 用户类型
			String employeeType = "A";
			// 证件号
			String employeeId = "";
			// 手机号
			String phone = "";
			// 性别 ,选填(1 :男，2 :女)
			String employeeSex = rs.getString("gender").equals("M") ? "1" : "2";
			// 地址
			String empAddress = "";
			// 卡号
			String serial = rs.getString("cardNo");

			Employee employee = new Employee();
			employee.setEmployeeName(employeeName);
			employee.setDepartId(departId);
			employee.setEmployeeType(employeeType);
			employee.setEmployeeId(employeeId);
			employee.setPhone(phone);
			employee.setEmployeeSex(employeeSex);
			employee.setEmpAddress(empAddress);
			employee.setSerial(serial);

			System.out.println("--------------------------------------------");
			/* 姓名、部门id、用户类型、证件号、手机号、性别、地址 */
			System.out.println(employee.toString());

			// JSON格式数据解析对象
			JSONObject jo = JSONObject.fromObject(employee);
			// System.out.println(jo.toString());

			AttendanceProperties properties = new AttendanceProperties();

			// 添加人员---根据添加人员返回的编号，调用发卡接口
			String addEmplyUrl = properties.url + "/reformer/interface/hr/addEmply?";
			String addEmplyData = "data=" + jo.toString();

			// 请求地址
			System.out.println("添加人员-请求地址:" + addEmplyUrl);
			// 请求数据
			System.out.println("添加人员-请求数据:" + addEmplyData);

			String result = HttpKit.sendPost(addEmplyUrl + addEmplyData, null);

			JSONObject resultObject = JSONObject.fromObject(result);

			// 是否添加成功
			if (resultObject.get("result").equals("200")) {

				// 成功返回的人员sysNo
				String sysNo = (String) resultObject.get("resultInfo");
				System.out.println("添加成功:" + sysNo);

				// 发卡
				if (sysNo != null) {

					Card card = new Card();
					card.setSysNo(sysNo);
					card.setSerial(employee.getSerial());

					JSONObject cardJo = JSONObject.fromObject(card);

					String sendCardUrl = properties.url + "/reformer/interface/sendCard?";
					String sendCardData = "data=" + cardJo.toString();
					// 请求地址
					System.out.println("发卡-请求地址:" + sendCardUrl);
					// 请求数据
					System.out.println("发卡-请求数据:" + sendCardData);

					String cardResult = HttpKit.sendPost(sendCardUrl + sendCardData, null);

					JSONObject cardResultObject = JSONObject.fromObject(cardResult);

					// 是否发卡成功
					if (resultObject.get("result").equals("200")) {

						System.out.println("发卡成功:" + (String) resultObject.get("resultInfo"));
					} else {
						System.out.println("发卡失败:" + (String) resultObject.get("resultInfo"));
					}

				}

			} else {
				System.out.println("添加失败:" + (String) resultObject.get("resultInfo"));
			}

		}

	}
}
