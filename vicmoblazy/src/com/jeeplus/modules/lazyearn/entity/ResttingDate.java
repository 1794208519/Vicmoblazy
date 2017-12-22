package com.jeeplus.modules.lazyearn.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ResttingDate extends DataEntity<ResttingDate>{
	
	private static final long serialVersionUID = 1L;
	// 编号
	private Integer resttingId;
	// 用户名
	private String login_name;
	// 密码
	private String password;
	// email
	private String email;
	
	public ResttingDate() {
		super();
	}
	public ResttingDate(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public Integer getResttingId() {
		return resttingId;
	}
	public void setResttingId(Integer resttingId) {
		this.resttingId = resttingId;
	}
	
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
