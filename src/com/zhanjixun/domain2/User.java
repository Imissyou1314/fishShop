package com.zhanjixun.domain2;

import android.content.Context;

import com.zhanjixun.data.Constants;
import com.zhanjixun.utils.MyGson;
import com.zhanjixun.utils.SPUtil;

public class User {
	private String userId;
	private String phoneNumber;
	private String password;
	private String userName;
	private String headImage;

	public boolean saveUserInfo(Context context) {
		return SPUtil.saveString(context, Constants.XML_USER,
				Constants.XML_USER, MyGson.getInstance().toJson(this));
	}

	public static User getUserFormSP(Context context) {
		return MyGson.getInstance().fromJson(SPUtil.getString(context,
				Constants.XML_USER, Constants.XML_USER, "{}"),
				User.class);
	}

	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            Ҫ���õ� userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            Ҫ���õ� phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            Ҫ���õ� password
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return headImage
	 */
	public String getHeadImage() {
		return headImage;
	}

	/**
	 * @param headImage
	 *            Ҫ���õ� headImage
	 */
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
}
