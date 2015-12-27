package com.zhanjixun.domain2;

import java.util.Date;

public class Fisherman extends Seller {
	private String shipPort;
	private Date portTime;
	private String seaRecordId;

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
	 * @return seaRecordId
	 */
	public String getSeaRecordId() {
		return seaRecordId;
	}

	/**
	 * @param seaRecordId
	 *            Ҫ���õ� seaRecordId
	 */
	public void setSeaRecordId(String seaRecordId) {
		this.seaRecordId = seaRecordId;
	}
}
