package com.zhanjixun.domain2;

import com.zhanjixun.utils.MyGson;

public class Address {
	private String getAddressId;
	private String userName;
	private String phone;
	private String address;
	private String postcode;
	private boolean defaultAddress;
	private long proginceId;

	@Override
	public String toString() {
		return MyGson.getInstance().toJson(this);
	}

	/**
	 * @return getAddressId
	 */
	public String getGetAddressId() {
		return getAddressId;
	}

	/**
	 * @param getAddressId
	 *            要设置的 getAddressId
	 */
	public void setGetAddressId(String getAddressId) {
		this.getAddressId = getAddressId;
	}

	/**
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            要设置的 userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            要设置的 phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            要设置的 address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param postcode
	 *            要设置的 postcode
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @return defaultAddress
	 */
	public boolean isDefaultAddress() {
		return defaultAddress;
	}

	/**
	 * @param defaultAddress
	 *            要设置的 defaultAddress
	 */
	public void setDefaultAddress(boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	/*获取省份ID*/
	public long getProginceId() {
		return proginceId;
	}
	/*设置省份ID*/
	public void setProginceId(long proginceId) {
		this.proginceId = proginceId;
	}
	
	
}
