package com.jeeplus.modules.sys.entity;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 微信支付实体
 * @author YeJR
 *
 */
public class VicmobPayAccount extends  DataEntity<VicmobPayAccount> {
	
	private static final long serialVersionUID = 1L;
	
	private Integer weChatPayId;//主键ID
	private Integer minaId;//小程序ID
	private String appId;//支付appId
	private String mchId;//支付商户号
	private String apiKey;//支付apikey
	private String certificatePath;//证书路径
	private Boolean state;//状态（0：不启用支付， 1：启用支付）
	
	public VicmobPayAccount() {
		super();
	}

	public VicmobPayAccount(Integer weChatPayId, Integer minaId, String appId, String mchId, String apiKey,
			Boolean state) {
		super();
		this.weChatPayId = weChatPayId;
		this.minaId = minaId;
		this.appId = appId;
		this.mchId = mchId;
		this.apiKey = apiKey;
		this.state = state;
	}

	public Integer getWeChatPayId() {
		return weChatPayId;
	}

	public void setWeChatPayId(Integer weChatPayId) {
		this.weChatPayId = weChatPayId;
	}

	public Integer getMinaId() {
		return minaId;
	}

	public void setMinaId(Integer minaId) {
		this.minaId = minaId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public String getCertificatePath() {
		return certificatePath;
	}

	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}
	
	
}