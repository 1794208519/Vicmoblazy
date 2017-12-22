package com.jeeplus.modules.lazyearn.entity;

import com.jeeplus.common.persistence.DataEntity;

public class AutoQQDefaultData extends DataEntity<AutoQQDefaultData>{
	private static final long serialVersionUID = 1L;
	private Integer qqDefaultDataId;		// 主键ID
	private String qqNearbyNum; //附近的人
	private String qqFriendNum; //qq一键加好友
	private String qqGroupfriendNum; //qq一键加群好友
	private String qqNearbyContent; //qq附近的人内容
	private String qqAkeyContent; //
	private String devices; //设备号
	private Boolean deleteFlag;//逻辑删除（1：删除状态）
	public AutoQQDefaultData() {
		super();
	}
	public AutoQQDefaultData(String id) {
		super(id);
	}
	public Integer getQqDefaultDataId() {
		return qqDefaultDataId;
	}
	public void setQqDefaultDataId(Integer qqDefaultDataId) {
		this.qqDefaultDataId = qqDefaultDataId;
	}
	public String getQqNearbyNum() {
		return qqNearbyNum;
	}
	public void setQqNearbyNum(String qqNearbyNum) {
		this.qqNearbyNum = qqNearbyNum;
	}
	public String getQqFriendNum() {
		return qqFriendNum;
	}
	public void setQqFriendNum(String qqFriendNum) {
		this.qqFriendNum = qqFriendNum;
	}
	public String getQqGroupfriendNum() {
		return qqGroupfriendNum;
	}
	public void setQqGroupfriendNum(String qqGroupfriendNum) {
		this.qqGroupfriendNum = qqGroupfriendNum;
	}
	public String getQqNearbyContent() {
		return qqNearbyContent;
	}
	public void setQqNearbyContent(String qqNearbyContent) {
		this.qqNearbyContent = qqNearbyContent;
	}
	public String getQqAkeyContent() {
		return qqAkeyContent;
	}
	public void setQqAkeyContent(String qqAkeyContent) {
		this.qqAkeyContent = qqAkeyContent;
	}
	public String getDevices() {
		return devices;
	}
	public void setDevices(String devices) {
		this.devices = devices;
	}
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
}
