package com.jeeplus.modules.sys.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 粉丝表实体
 * @author squirrel
 * @version 2017-07-05
 */
public class VicmobFansInfo extends DataEntity<VicmobFansInfo>{

	
	private static final long serialVersionUID = 1L;
	private Integer fansId;		// 主键ID
	private Integer minaId;		// 小程序ID
	private String openId;		// openid
	private String nickname;		// 微信昵称
	private Integer sex;		// 性别（0：没有设置；1：男；2：女）
	private String avatar;		// 微信头像
	private Date addtime;		// 添加时间
	private Double balance;		// 可用余额
	private Double usedBalance;		// 已用余额
	private Double totalBalance;		// 累计余额
	private Integer integral;		// 可用积分
	private Integer usedIntegral;		// 已用积分
	private Integer totalIntegral;		// 累计积分
	private String payPassword;		// 余额支付密码
	
	public VicmobFansInfo() {
		super();
	}

	public Integer getFansId() {
		return fansId;
	}

	public void setFansId(Integer fansId) {
		this.fansId = fansId;
	}

	public Integer getMinaId() {
		return minaId;
	}

	public void setMinaId(Integer minaId) {
		this.minaId = minaId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getUsedBalance() {
		return usedBalance;
	}

	public void setUsedBalance(Double usedBalance) {
		this.usedBalance = usedBalance;
	}

	public Double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getUsedIntegral() {
		return usedIntegral;
	}

	public void setUsedIntegral(Integer usedIntegral) {
		this.usedIntegral = usedIntegral;
	}

	public Integer getTotalIntegral() {
		return totalIntegral;
	}

	public void setTotalIntegral(Integer totalIntegral) {
		this.totalIntegral = totalIntegral;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	

}