package com.zhanjixun.domain;

import java.util.Date;

/**
 * �滧
 * 
 * @author ղ������
 *
 */
public class Fishermen extends Seller {
	/**
	 * ͣ���ۿ�
	 */
	private String shipPort;
	/**
	 * ����ʱ��
	 */
	private Date portTime;
	/**
	 * ���̷�ʽ
	 */
	private String getType;
	
	/**
	 * ��������
	 */
	private String enginePower;
	
	/**
	 *  ����λ�� 
	 */
	private String tonnage;
	
	

	/**
	 * @return shipPort
	 */
	public String getShipPort() {
		return shipPort;
	}
	
	/**
	 * @param shipPort
	 *            Ҫ���õ� shipPort
	 */
	public void setShipPort(String shipPort) {
		this.shipPort = shipPort;
	}

	/**
	 * @return portTime
	 */
	public Date getPortTime() {
		return portTime;
	}

	/**
	 * @param portTime
	 *            Ҫ���õ� portTime
	 */
	public void setPortTime(Date portTime) {
		this.portTime = portTime;
	}

	/**
	 * @return getType
	 */
	public String getGetType() {
		return getType;
	}

	/**
	 * @param getType
	 *            Ҫ���õ� getType
	 */
	public void setGetType(String getType) {
		this.getType = getType;
	}

	public String getEnginePower() {
		return enginePower;
	}

	public void setEnginePower(String enginePower) {
		this.enginePower = enginePower;
	}

	public String getTonnage() {
		return tonnage;
	}

	public void setTonnage(String tonnage) {
		this.tonnage = tonnage;
	}

	
}
