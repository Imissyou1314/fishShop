package com.zhanjixun.domain;

import java.util.Date;

/**
 * ʡ��
 */
public class Province {
	/*�Ƿ�ɾ��*/
	private boolean isDetele;
	
	/*����ʱ��*/
	private Date createTime;
	
	/*�汾*/
	private Integer version;
	
	/*ʡ����*/
	private  String name;
	
	/*ʡ���*/
	private  Integer regionId;

	public boolean isDetele() {
		return isDetele;
	}

	public void setDetele(boolean isDetele) {
		this.isDetele = isDetele;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	
	

}
