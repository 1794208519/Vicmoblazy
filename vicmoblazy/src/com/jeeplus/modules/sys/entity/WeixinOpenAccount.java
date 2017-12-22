/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 保存微信Ticket实体
 * @author jeeplus
 * @version 2013-05-15
 */
public class WeixinOpenAccount extends DataEntity<WeixinOpenAccount>{

	private static final long serialVersionUID = 1L;
	private Integer ticketId;	// 主键
	private String componentAppid;//第三方平台的APPID	
	private String componentAppsecret;//第三方平台的APPSECRET	
	private String ticket;//微信推送的TICKET
	private Date getTicketTime;//获取微信推送ticket的时间
	private String componentAccessToken;//第三方平台的component_access_token
	private Integer componentAccessTokenEndtime;//第三方平台的component_access_token到期时间
	
	
	public WeixinOpenAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public String getComponentAppid() {
		return componentAppid;
	}

	public void setComponentAppid(String componentAppid) {
		this.componentAppid = componentAppid;
	}

	public String getComponentAppsecret() {
		return componentAppsecret;
	}

	public void setComponentAppsecret(String componentAppsecret) {
		this.componentAppsecret = componentAppsecret;
	}

	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public Date getGetTicketTime() {
		return getTicketTime;
	}
	public void setGetTicketTime(Date getTicketTime) {
		this.getTicketTime = getTicketTime;
	}

	public String getComponentAccessToken() {
		return componentAccessToken;
	}

	public void setComponentAccessToken(String componentAccessToken) {
		this.componentAccessToken = componentAccessToken;
	}

	public Integer getComponentAccessTokenEndtime() {
		return componentAccessTokenEndtime;
	}

	public void setComponentAccessTokenEndtime(Integer componentAccessTokenEndtime) {
		this.componentAccessTokenEndtime = componentAccessTokenEndtime;
	} 	
	
	

}