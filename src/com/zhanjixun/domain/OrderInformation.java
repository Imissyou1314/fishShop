/**
 * 
 */
package com.zhanjixun.domain;

import java.util.List;
import java.util.Map;

/**
 * @author Imissyou
 * @Time  2015��11��25��
 *
 */
public class OrderInformation {
	
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
	
	private List<Map<String,Object>> ordersDatil ;
	/**
	 * @return ordersDatil
	 */
	public List<Map<String, Object>> getOrdersDatil() {
		return ordersDatil;
	}
	/**
	 * @param ordersDatil Ҫ���õ� ordersDatil
	 */
	public void setOrdersDatil(List<Map<String, Object>> ordersDatil) {
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
	

}
