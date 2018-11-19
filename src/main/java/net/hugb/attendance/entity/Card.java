package net.hugb.attendance.entity;

import java.io.Serializable;

public class Card implements Serializable {

	private static final long serialVersionUID = 1L;

	// ocs��Ա����,����
	private String SysNo;

	// ����,���16λ���Ų��㲹"0"��
	private String Serial;

	public String getSysNo() {
		return SysNo;
	}

	public void setSysNo(String sysNo) {
		SysNo = sysNo;
	}

	public String getSerial() {
		return Serial;
	}

	public void setSerial(String serial) {
		Serial = serial;
	}

	@Override
	public String toString() {
		return "Card [SysNo=" + SysNo + ", Serial=" + Serial + "]";
	}

}
