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
 * ������
 * 
 * @author hugb
 *
 */
public class Application {

	public static void main(String[] args) throws SQLException {

		// ��ѯ�û���Ϣ��������
		String query = "select * from USERINFO";

		// ��ѯ����
		ResultSet rs = ConnectionFactory.getInstance().getResultSet(query);

		// ������ѯ���
		while (rs.next()) {

			// ����
			String employeeName = rs.getString("name");
			// ����id
			String departId = rs.getString("name");
			// �û�����
			String employeeType = "A";
			// ֤����
			String employeeId = "";
			// �ֻ���
			String phone = "";
			// �Ա� ,ѡ��(1 :�У�2 :Ů)
			String employeeSex = rs.getString("gender").equals("M") ? "1" : "2";
			// ��ַ
			String empAddress = "";
			// ����
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
			/* ����������id���û����͡�֤���š��ֻ��š��Ա𡢵�ַ */
			System.out.println(employee.toString());

			// JSON��ʽ���ݽ�������
			JSONObject jo = JSONObject.fromObject(employee);
			// System.out.println(jo.toString());

			AttendanceProperties properties = new AttendanceProperties();

			// �����Ա---���������Ա���صı�ţ����÷����ӿ�
			String addEmplyUrl = properties.url + "/reformer/interface/hr/addEmply?";
			String addEmplyData = "data=" + jo.toString();

			// �����ַ
			System.out.println("�����Ա-�����ַ:" + addEmplyUrl);
			// ��������
			System.out.println("�����Ա-��������:" + addEmplyData);

			String result = HttpKit.sendPost(addEmplyUrl + addEmplyData, null);

			JSONObject resultObject = JSONObject.fromObject(result);

			// �Ƿ���ӳɹ�
			if (resultObject.get("result").equals("200")) {

				// �ɹ����ص���ԱsysNo
				String sysNo = (String) resultObject.get("resultInfo");
				System.out.println("��ӳɹ�:" + sysNo);

				// ����
				if (sysNo != null) {

					Card card = new Card();
					card.setSysNo(sysNo);
					card.setSerial(employee.getSerial());

					JSONObject cardJo = JSONObject.fromObject(card);

					String sendCardUrl = properties.url + "/reformer/interface/sendCard?";
					String sendCardData = "data=" + cardJo.toString();
					// �����ַ
					System.out.println("����-�����ַ:" + sendCardUrl);
					// ��������
					System.out.println("����-��������:" + sendCardData);

					String cardResult = HttpKit.sendPost(sendCardUrl + sendCardData, null);

					JSONObject cardResultObject = JSONObject.fromObject(cardResult);

					// �Ƿ񷢿��ɹ�
					if (resultObject.get("result").equals("200")) {

						System.out.println("�����ɹ�:" + (String) resultObject.get("resultInfo"));
					} else {
						System.out.println("����ʧ��:" + (String) resultObject.get("resultInfo"));
					}

				}

			} else {
				System.out.println("���ʧ��:" + (String) resultObject.get("resultInfo"));
			}

		}

	}
}
