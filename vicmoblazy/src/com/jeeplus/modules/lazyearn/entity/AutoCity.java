package com.jeeplus.modules.lazyearn.entity;
import com.jeeplus.common.persistence.DataEntity;
/**
 * 
 * 选择城市生成号码实体 
 * @author Stephen
 * @version 2017-9-27
 *
 */
public class AutoCity extends DataEntity<AutoCity>{
	
	private static final long serialVersionUID = 1L;
	
	private Integer cityId;		// 主键ID
	private String city;	//选择的城市
	private String operator;//运营商
	private String devices; //设备号
	private Boolean deleteFlag;//逻辑删除（1：删除状态）
	private String province;//省份
	private String maindevices; //主设备号id
	

	public String getMaindevices() {
		return maindevices;
	}
	public void setMaindevices(String maindevices) {
		this.maindevices = maindevices;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public AutoCity() {
		super();
	}
	public AutoCity(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getDevices() {
		return devices;
	}
	public void setDevices(String devices) {
		this.devices = devices;
	}
	
}