/**
 * 
 */
package com.zhanjixun.domain;

import java.util.List;

/**
 * @author Imissyou
 * @Time  2015��11��25��
 *  ��������ҳ��
 */
public class Order_Information {
	
	//�û���
	private String user_name;
	//�û��绰
	private String user_phome;
	//ʣ����
	private String day;
	//ʣ��ʱ
	private String hour;
	//�ջ���ַ
	private String Address;
    //����״̬
	private String order_state;
	//�̵���
	private String order_ShopName;
	//������Ʒ������
	private String order_goodNumber;
	//������Ʒ������Ʒ��
	private String order_goodSumPirce;
	//������Ʒ�����۸�
	private String order_goodLogisticsPrice;
	//������Ʒitem
	
	private List<Order_GoodItem> ordersDatil ;
	/**
	 * @return ordersDatil
	 */
	public List<Order_GoodItem> getOrdersDatil() {
		return ordersDatil;
	}
	/**
	 * @param ordersDatil Ҫ���õ� ordersDatil
	 */
	public void setOrdersDatil(List<Order_GoodItem> ordersDatil) {
		this.ordersDatil = ordersDatil;
	}
	/**
	 * @return user_name
	 */
	public String getUser_name() {
		return user_name;
	}
	/**
	 * @param user_name Ҫ���õ� user_name
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	/**
	 * @return user_phome
	 */
	public String getUser_phome() {
		return user_phome;
	}
	/**
	 * @param user_phome Ҫ���õ� user_phome
	 */
	public void setUser_phome(String user_phome) {
		this.user_phome = user_phome;
	}
	/**
	 * @return day
	 */
	public String getDay() {
		return day;
	}
	/**
	 * @param day Ҫ���õ� day
	 */
	public void setDay(String day) {
		this.day = day;
	}
	/**
	 * @return hour
	 */
	public String getHour() {
		return hour;
	}
	/**
	 * @param hour Ҫ���õ� hour
	 */
	public void setHour(String hour) {
		this.hour = hour;
	}
	/**
	 * @return address
	 */
	public String getAddress() {
		return Address;
	}
	/**
	 * @param address Ҫ���õ� address
	 */
	public void setAddress(String address) {
		Address = address;
	}
	/**
	 * @return order_goodNumber
	 */
	public String getOrder_goodNumber() {
		return order_goodNumber;
	}
	/**
	 * @param order_goodNumber Ҫ���õ� order_goodNumber
	 */
	public void setOrder_goodNumber(String order_goodNumber) {
		this.order_goodNumber = order_goodNumber;
	}
	/**
	 * @return order_goodSumPirce
	 */
	public String getOrder_goodSumPirce() {
		return order_goodSumPirce;
	}
	/**
	 * @param order_goodSumPirce Ҫ���õ� order_goodSumPirce
	 */
	public void setOrder_goodSumPirce(String order_goodSumPirce) {
		this.order_goodSumPirce = order_goodSumPirce;
	}
	/**
	 * @return order_goodLogisticsPrice
	 */
	public String getOrder_goodLogisticsPrice() {
		return order_goodLogisticsPrice;
	}
	/**
	 * @param order_goodLogisticsPrice Ҫ���õ� order_goodLogisticsPrice
	 */
	public void setOrder_goodLogisticsPrice(String order_goodLogisticsPrice) {
		this.order_goodLogisticsPrice = order_goodLogisticsPrice;
	}
	/**
	 * @return order_state
	 */
	public String getOrder_state() {
		return order_state;
	}
	/**
	 * @param order_state Ҫ���õ� order_state
	 */
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}
	/**
	 * @return order_ShopName
	 */
	public String getOrder_ShopName() {
		return order_ShopName;
	}
	/**
	 * @param order_ShopName Ҫ���õ� order_ShopName
	 */
	public void setOrder_ShopName(String order_ShopName) {
		this.order_ShopName = order_ShopName;
	}
}
