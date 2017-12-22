package com.jeeplus.modules.sys.entity;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 模板消息实体
 * @author squirrel
 * @version 2017-10-11
 */
public class VicmobTemplate extends DataEntity<VicmobTemplate>{

	
	private static final long serialVersionUID = 1L;
	private Integer templateId;		// 主键ID
	private Integer minaId;		// 小程序ID
	private Integer informType;		// 模板消息类型
	private String informId;		// 模板消息Id
	
	public VicmobTemplate() {
		super();
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getMinaId() {
		return minaId;
	}

	public void setMinaId(Integer minaId) {
		this.minaId = minaId;
	}

	public Integer getInformType() {
		return informType;
	}

	public void setInformType(Integer informType) {
		this.informType = informType;
	}

	public String getInformId() {
		return informId;
	}

	public void setInformId(String informId) {
		this.informId = informId;
	}


}