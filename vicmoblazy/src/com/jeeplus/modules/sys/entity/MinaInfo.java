/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 授权小程序Entity
 * @author Stephen
 * @version 2017-07-02
 */
public class MinaInfo extends DataEntity<MinaInfo> {

	private static final long serialVersionUID = 1L;
	private Integer minaid;	// 主键（授权小程序id）
	private Integer userid;	// 用户id
	private String unicalid; //关联绑定的公众号的主键
	private String publicAppid; //关联绑定的公众号的APPID
	private String token;	// 第三方平台令牌
	private String encodingaeskey; //第三方平台加密秘钥
	private String appid; 			//授权小程序的appid
	private String appsecret; 		//授权小程序的appsecret
	private String nickName; 	//授权小程序昵称
	private String headImg;		//授权小程序头像
	private String userName; 	//授权小程序用户名
	private String verifyType;  //授权方认证类型，-1代表未认证，0代表微信认证
	private String serviceType; //默认为0（代表小程序）
	private String signature; 	//JS-SDK权限验证签名
	private String nonceStr; 	// 随机字符串
	private Integer timesTamp;	//时间戳(去掉毫秒10位)
	private String loginname; 	//系统登陆账号
	private String password; 	//系统登陆密码
	private String jsapiTicket;	//jsapi_ticket(时效7200s)
	private String authorizerAccessToken;			//授权小程序access_token
	private Integer authorizerAccessTokenEndTime;	//access_token到期时间
	private String authorizerRefreshToken;			//授权小程序刷新令牌
	private Integer authorizerRefreshTokenEndTime; 	//授权小程序刷新令牌到期时间
	private Boolean status;
	
	
	public MinaInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getMinaid() {
		return minaid;
	}


	public void setMinaid(Integer minaid) {
		this.minaid = minaid;
	}


	public Integer getUserid() {
		return userid;
	}


	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public String getUnicalid() {
		return unicalid;
	}

	public void setUnicalid(String unicalid) {
		this.unicalid = unicalid;
	}

	public String getPublicAppid() {
		return publicAppid;
	}

	public void setPublicAppid(String publicAppid) {
		this.publicAppid = publicAppid;
	}

	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getEncodingaeskey() {
		return encodingaeskey;
	}


	public void setEncodingaeskey(String encodingaeskey) {
		this.encodingaeskey = encodingaeskey;
	}


	public String getAppid() {
		return appid;
	}


	public void setAppid(String appid) {
		this.appid = appid;
	}

	
	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getHeadImg() {
		return headImg;
	}


	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}	
	
	public String getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public Integer getTimesTamp() {
		return timesTamp;
	}

	public void setTimesTamp(Integer timesTamp) {
		this.timesTamp = timesTamp;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

	public String getAuthorizerAccessToken() {
		return authorizerAccessToken;
	}

	public void setAuthorizerAccessToken(String authorizerAccessToken) {
		this.authorizerAccessToken = authorizerAccessToken;
	}

	public Integer getAuthorizerAccessTokenEndTime() {
		return authorizerAccessTokenEndTime;
	}

	public void setAuthorizerAccessTokenEndTime(Integer authorizerAccessTokenEndTime) {
		this.authorizerAccessTokenEndTime = authorizerAccessTokenEndTime;
	}

	public String getAuthorizerRefreshToken() {
		return authorizerRefreshToken;
	}

	public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
		this.authorizerRefreshToken = authorizerRefreshToken;
	}

	public Integer getAuthorizerRefreshTokenEndTime() {
		return authorizerRefreshTokenEndTime;
	}

	public void setAuthorizerRefreshTokenEndTime(Integer authorizerRefreshTokenEndTime) {
		this.authorizerRefreshTokenEndTime = authorizerRefreshTokenEndTime;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}