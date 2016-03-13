package com.zhanjixun.domain;

import java.util.Date;

/**
 * 省份
 */
public class Province {
	/*是否删除*/
	private boolean isDetele;
	
	/*创建时间*/
	private Date createTime;
	
	/*版本*/
	private Integer version;
	
	/*省份名*/
	private  String name;
	
	/*省编号*/
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
