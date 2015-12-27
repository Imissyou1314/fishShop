package com.zhanjixun.domain2;

import com.zhanjixun.utils.MyGson;

public class Address {
	private String getAddressId;
	private String userName;
	private String phone;
	private String address;
	private String postcode;
	private boolean defaultAddress;

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
	 *            Ҫ���õ� getAddressId
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
	 *            Ҫ���õ� userName
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
	 *            Ҫ���õ� phone
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
	 *            Ҫ���õ� address
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
	 *            Ҫ���õ� postcode
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
	 *            Ҫ���õ� defaultAddress
	 */
	public void setDefaultAddress(boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
}
