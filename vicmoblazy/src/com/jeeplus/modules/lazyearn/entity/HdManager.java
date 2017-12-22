package com.jeeplus.modules.lazyearn.entity;
import com.jeeplus.common.persistence.DataEntity;
/**
 * 
 * 懒赚账户实体 
 * @author Stephen
 * @version 2017-9-27
 *
 */
public class HdManager extends DataEntity<HdManager>{
	
	private static final long serialVersionUID = 1L;
	
	private Integer countId;// 主键ID
	private String wxNearbyCount;	//附近人数量
	private String wxAddresslistCount;// 通讯录
	private String wxOnekeystarCount; // 一键养号
	private String wxContactsCount; //
	private String wxGroupfriendCount;//逻辑删除（1：删除状态）
	private String wxPublicCount;//昵称
	private String wxIntelligentrecoveryCount;//
	private String wxCircleCount;//昵称
	private String wxSendmessageCount;//昵称
	private String wxChathistoryCount;//昵称
	private String wxWhitelistCount;//昵称
	private String wxAutochangeCount;//昵称
	private String wxOpentimingCount;//昵称
	private String time;//昵称
	
	public HdManager() {
		super();
	}
	public HdManager(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public Integer getCountId() {
		return countId;
	}
	public void setCountId(Integer countId) {
		this.countId = countId;
	}
	
	public String getWxNearbyCount() {
		return wxNearbyCount;
	}
	public void setWxNearbyCount(String wxNearbyCount) {
		this.wxNearbyCount = wxNearbyCount;
	}
	public String getWxAddresslistCount() {
		return wxAddresslistCount;
	}
	public void setWxAddresslistCount(String wxAddresslistCount) {
		this.wxAddresslistCount = wxAddresslistCount;
	}
	public String getWxOnekeystarCount() {
		return wxOnekeystarCount;
	}
	public void setWxOnekeystarCount(String wxOnekeystarCount) {
		this.wxOnekeystarCount = wxOnekeystarCount;
	}
	public String getWxContactsCount() {
		return wxContactsCount;
	}
	public void setWxContactsCount(String wxContactsCount) {
		this.wxContactsCount = wxContactsCount;
	}
	public String getWxGroupfriendCount() {
		return wxGroupfriendCount;
	}
	public void setWxGroupfriendCount(String wxGroupfriendCount) {
		this.wxGroupfriendCount = wxGroupfriendCount;
	}
	public String getWxPublicCount() {
		return wxPublicCount;
	}
	public void setWxPublicCount(String wxPublicCount) {
		this.wxPublicCount = wxPublicCount;
	}
	public String getWxIntelligentrecoveryCount() {
		return wxIntelligentrecoveryCount;
	}
	public void setWxIntelligentrecoveryCount(String wxIntelligentrecoveryCount) {
		this.wxIntelligentrecoveryCount = wxIntelligentrecoveryCount;
	}
	public String getWxCircleCount() {
		return wxCircleCount;
	}
	public void setWxCircleCount(String wxCircleCount) {
		this.wxCircleCount = wxCircleCount;
	}
	public String getWxSendmessageCount() {
		return wxSendmessageCount;
	}
	public void setWxSendmessageCount(String wxSendmessageCount) {
		this.wxSendmessageCount = wxSendmessageCount;
	}
	public String getWxChathistoryCount() {
		return wxChathistoryCount;
	}
	public void setWxChathistoryCount(String wxChathistoryCount) {
		this.wxChathistoryCount = wxChathistoryCount;
	}
	public String getWxWhitelistCount() {
		return wxWhitelistCount;
	}
	public void setWxWhitelistCount(String wxWhitelistCount) {
		this.wxWhitelistCount = wxWhitelistCount;
	}
	public String getWxAutochangeCount() {
		return wxAutochangeCount;
	}
	public void setWxAutochangeCount(String wxAutochangeCount) {
		this.wxAutochangeCount = wxAutochangeCount;
	}
	public String getWxOpentimingCount() {
		return wxOpentimingCount;
	}
	public void setWxOpentimingCount(String wxOpentimingCount) {
		this.wxOpentimingCount = wxOpentimingCount;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	
}