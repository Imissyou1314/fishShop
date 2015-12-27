package com.zhanjixun.domain;

import java.util.List;

public class Seller {
	/**
	 * id
	 */
	private String id;
	/**
	 * ����
	 */
	private String name;
	/**
	 * ����ֵ
	 */
	private String grade;
	/**
	 * ���ͼ�
	 */
	private double sendPrice;
	/**
	 * ͷ��URL
	 */
	private String headImage;
	
	private List<String> showImageUrls;

	/**
	 * @return headImage
	 */
	public String getHeadImage() {
		return headImage;
	}

	/**
	 * @param headImage Ҫ���õ� headImage
	 */
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            Ҫ���õ� id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            Ҫ���õ� name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return grade
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * @param grade
	 *            Ҫ���õ� grade
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * @return sendPrice
	 */
	public double getSendPrice() {
		return sendPrice;
	}

	/**
	 * @param sendPrice
	 *            Ҫ���õ� sendPrice
	 */
	public void setSendPrice(double sendPrice) {
		this.sendPrice = sendPrice;
	}

	public List<String> getShowImageUrls() {
		return showImageUrls;
	}

	public void setShowImageUrls(List<String> showImageUrls) {
		this.showImageUrls = showImageUrls;
	}

	
}
