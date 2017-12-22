package com.jeeplus.modules.lazyearn.entity;
import com.jeeplus.common.persistence.DataEntity;
/**
 * 
 * @author Stephen
 * @version 2017-9-27
 *
 */
public class VicCodeText extends DataEntity<VicCodeText>{
	
	private static final long serialVersionUID = 1L;
	
	private Integer codeTextId;		// 主键ID
	private String keyword;	//
	private String text;//
	private String creatime; //
	private String type; //
	private String devices; //设备号
	private Integer pager;
	private Integer row;
	private Boolean dataShareFlag;//是否开启共享数据(1:开启)
	public VicCodeText() {
		super();
	}
	public VicCodeText(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public Integer getCodeTextId() {
		return codeTextId;
	}
	public void setCodeTextId(Integer codeTextId) {
		this.codeTextId = codeTextId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCreatime() {
		return creatime;
	}
	public void setCreatime(String creatime) {
		this.creatime = creatime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDevices() {
		return devices;
	}
	public void setDevices(String devices) {
		this.devices = devices;
	}
	public Integer getPager() {
		return pager;
	}
	public void setPager(Integer pager) {
		this.pager = pager;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Boolean getDataShareFlag() {
		return dataShareFlag;
	}
	public void setDataShareFlag(Boolean dataShareFlag) {
		this.dataShareFlag = dataShareFlag;
	}

	
	
}