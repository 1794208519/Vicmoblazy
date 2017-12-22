package com.jeeplus.modules.lazyearn.entity;
import com.jeeplus.common.persistence.DataEntity;
/**
 * 
 * 懒赚账户实体 
 * @author Stephen
 * @version 2017-9-27
 *
 */
public class AutoAccount extends DataEntity<AutoAccount>{
	
	private static final long serialVersionUID = 1L;
	
	private Integer accountId;// 主键ID
	private String account;	//账户名
	private String password;// 密码
	private String devices; // 设备号
	private String maindevices; //设备号id
	private Boolean deleteFlag;//逻辑删除（1：删除状态）
	private String nickname;//昵称
	public AutoAccount() {
		super();
	}
	public AutoAccount(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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

	public String getMaindevices() {
		return maindevices;
	}
	public void setMaindevices(String maindevices) {
		this.maindevices = maindevices;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}