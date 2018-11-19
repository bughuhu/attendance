package net.hugb.attendance.entity;

import java.io.Serializable;

public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;
	// 姓名
	String employeeName;
	// 部门id
	String departId;
	// 用户类型
	String employeeType;
	// 证件号
	String employeeId;
	// 手机号
	String phone;
	// 性别 ,选填(1 :男，2 :女)
	String employeeSex;
	// 地址
	String empAddress;
	// 卡号
	String serial;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmployeeSex() {
		return employeeSex;
	}

	public void setEmployeeSex(String employeeSex) {
		this.employeeSex = employeeSex;
	}

	public String getEmpAddress() {
		return empAddress;
	}

	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	@Override
	public String toString() {
		return "Employee [employeeName=" + employeeName + ", departId=" + departId + ", employeeType=" + employeeType
				+ ", employeeId=" + employeeId + ", phone=" + phone + ", employeeSex=" + employeeSex + ", empAddress="
				+ empAddress + ", serial=" + serial + "]";
	}

}
