package com.zhanjixun.domain;

import java.util.List;

/**
 * ��ֳ��
 * 
 * @author ղ������
 *
 */
public class Farmers extends Seller {
	/**
	 * ���̷�ʽ
	 */
	private List<String> getTypes;
	/**
	 * ����
	 */
	private long distance;
	/**
	 * ��ַ
	 */
	private String address;

	/**
	 * @return getTypes
	 */
	public List<String> getGetTypes() {
		return getTypes;
	}

	/**
	 * @return distance
	 */
	public long getDistance() {
		return distance;
	}

	/**
	 * @param distance Ҫ���õ� distance
	 */
	public void setDistance(long distance) {
		this.distance = distance;
	}

	/**
	 * @param getTypes
	 *            Ҫ���õ� getTypes
	 */
	public void setGetTypes(List<String> getTypes) {
		this.getTypes = getTypes;
	}

	/**
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            Ҫ���õ� address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}
