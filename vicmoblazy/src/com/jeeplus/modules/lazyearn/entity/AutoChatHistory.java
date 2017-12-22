package com.jeeplus.modules.lazyearn.entity;

import com.jeeplus.common.persistence.DataEntity;

public class AutoChatHistory extends DataEntity<AutoChatHistory>{

	private static final long serialVersionUID = 1L;
	Integer chatId;
	String deviceId;
	String chatPeople;
	String chatFriends;
	String chatTime;
	String chatFlag;
	String chatContents;
	public AutoChatHistory() {
	
	}
	public AutoChatHistory(Integer chatId, String deviceId, String chatPeople, String chatFriends, String chatTime,
			String chatFlag, String chatContents) {
		super();
		this.chatId = chatId;
		this.deviceId = deviceId;
		this.chatPeople = chatPeople;
		this.chatFriends = chatFriends;
		this.chatTime = chatTime;
		this.chatFlag = chatFlag;
		this.chatContents = chatContents;
	}
	public Integer getChatId() {
		return chatId;
	}
	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getChatPeople() {
		return chatPeople;
	}
	public void setChatPeople(String chatPeople) {
		this.chatPeople = chatPeople;
	}
	public String getChatFriends() {
		return chatFriends;
	}
	public void setChatFriends(String chatFriends) {
		this.chatFriends = chatFriends;
	}
	public String getChatTime() {
		return chatTime;
	}
	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}
	public String getChatFlag() {
		return chatFlag;
	}
	public void setChatFlag(String chatFlag) {
		this.chatFlag = chatFlag;
	}
	public String getChatContents() {
		return chatContents;
	}
	public void setChatContents(String chatContents) {
		this.chatContents = chatContents;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
