package com.jeeplus.modules.sys.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 用户产品表实体类
 * 
 * @author Jeezy
 *
 * @version 2017-11-03
 */
public class VicmobUserProducts extends DataEntity<VicmobUserProducts> {

	private static final long serialVersionUID = 1L;
	private Integer productsId;// 主键ID
	private Integer userId;// 用户ID
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private Date updateTime;// 更新时间
	private String versionNum;// 版本号
	private Integer versionLevel;// 版本等级
	private Integer status;// 状态

	public Integer getProductsId() {
		return productsId;
	}

	public void setProductsId(Integer productsId) {
		this.productsId = productsId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getVersionLevel() {
		return versionLevel;
	}

	public void setVersionLevel(Integer versionLevel) {
		this.versionLevel = versionLevel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
