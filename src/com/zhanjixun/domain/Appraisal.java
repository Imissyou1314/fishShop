/**
 * 
 */
package com.zhanjixun.domain;

/**
 * @author Imissyou
 * @Time 2015��11��23��
 *
 */
public class Appraisal {
	// �û�ID
	private String User_id;
	// ����ID
	private String order_id;
	// �̵�ID
	private String shop_id;
	// ��������
	private int conmment_kind;
	// �����ı�
	private String conmment_text;
	// ���ַ���������
	private float conmment_weight;
	// ���ַ������ʶ�
	private float conmment_fresh;
	// ���ַ����ٶ�
	private float conmment_speed;
	// �Ƿ�����
	private int anonymous;
	/**
	 * @return user_id
	 */
	public String getUser_id() {
		return User_id;
	}
	/**
	 * @param user_id Ҫ���õ� user_id
	 */
	public void setUser_id(String user_id) {
		User_id = user_id;
	}
	/**
	 * @return order_id
	 */
	public String getOrder_id() {
		return order_id;
	}
	/**
	 * @param order_id Ҫ���õ� order_id
	 */
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	/**
	 * @return shop_id
	 */
	public String getShop_id() {
		return shop_id;
	}
	/**
	 * @param shop_id Ҫ���õ� shop_id
	 */
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	/**
	 * @return conmment_kind
	 */
	public int getConmment_kind() {
		return conmment_kind;
	}
	/**
	 * @param conmment_kind Ҫ���õ� conmment_kind
	 */
	public void setConmment_kind(int conmment_kind) {
		this.conmment_kind = conmment_kind;
	}
	/**
	 * @return conmment_text
	 */
	public String getConmment_text() {
		return conmment_text;
	}
	/**
	 * @param conmment_text Ҫ���õ� conmment_text
	 */
	public void setConmment_text(String conmment_text) {
		this.conmment_text = conmment_text;
	}
	/**
	 * @return conmment_weight
	 */
	public float getConmment_weight() {
		return conmment_weight;
	}
	/**
	 * @param conmment_weight Ҫ���õ� conmment_weight
	 */
	public void setConmment_weight(float conmment_weight) {
		this.conmment_weight = conmment_weight;
	}
	/**
	 * @return conmment_fresh
	 */
	public float getConmment_fresh() {
		return conmment_fresh;
	}
	/**
	 * @param conmment_fresh Ҫ���õ� conmment_fresh
	 */
	public void setConmment_fresh(float conmment_fresh) {
		this.conmment_fresh = conmment_fresh;
	}
	/**
	 * @return conmment_speed
	 */
	public float getConmment_speed() {
		return conmment_speed;
	}
	/**
	 * @param conmment_speed Ҫ���õ� conmment_speed
	 */
	public void setConmment_speed(float conmment_speed) {
		this.conmment_speed = conmment_speed;
	}
	/**
	 * @return anonymous
	 */
	public int getAnonymous() {
		return anonymous;
	}
	/**
	 * @param anonymous Ҫ���õ� anonymous
	 */
	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}


}
