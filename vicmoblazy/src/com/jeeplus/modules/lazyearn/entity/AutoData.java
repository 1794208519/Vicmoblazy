package com.jeeplus.modules.lazyearn.entity;
import com.jeeplus.common.persistence.DataEntity;
/**
 * 
 * 群控系统携带的参数实体 
 * @author Stephen
 * @version 2017-9-27
 *
 */
public class AutoData extends DataEntity<AutoData>{
	
	private static final long serialVersionUID = 1L;
	
	private Integer dataId;		// 主键ID
	private String address;	//添加需要模拟的地点
	private String hello;//添加的打招呼内容
	private String longitude; //地点对应的经度
	private String latitude; //地点对应的纬度
	private String devices; //设备号id
	private String maindevices; //设备号id
	private Integer lac; // 
	private Integer cid; // 
	private Integer mnc; //
	private Integer deleteFlag;
	public AutoData() {
		super();
	}
	public AutoData(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public Integer getDataId() {
		return dataId;
	}
	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHello() {
		return hello;
	}
	public void setHello(String hello) {
		this.hello = hello;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getDevices() {
		return devices;
	}
	public void setDevices(String devices) {
		this.devices = devices;
	}
	public Integer getLac() {
		return lac;
	}
	public void setLac(Integer lac) {
		this.lac = lac;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getMnc() {
		return mnc;
	}
	public void setMnc(Integer mnc) {
		this.mnc = mnc;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getMaindevices() {
		return maindevices;
	}
	public void setMaindevices(String maindevices) {
		this.maindevices = maindevices;
	}
	
}