package com.zhanjixun.domain2;

public class Location {
	private double longitude;//
	private double latitude;// γ��
	private String locationStr;

	/**
	 * @return longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            Ҫ���õ� longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            Ҫ���õ� latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return locationStr
	 */
	public String getLocationStr() {
		return locationStr;
	}

	/**
	 * @param locationStr
	 *            Ҫ���õ� locationStr
	 */
	public void setLocationStr(String locationStr) {
		this.locationStr = locationStr;
	}
}
