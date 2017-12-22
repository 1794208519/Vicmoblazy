package com.jeeplus.modules.lazyearn.entity;
import com.jeeplus.common.persistence.DataEntity;
/**
 * 
 * 群控设备序列号表实体 
 * @author Stephen
 * @version 2017-10-20
 *
 */
public class AutoDevices extends DataEntity<AutoDevices>{
	
	private static final long serialVersionUID = 1L;
	
	private Integer devicesId;		// 主键ID
	private String file;	//文件名
	private String devices; //设备序列号
	private Integer status; //状态
	private String maindevices; //主控设备号
	public AutoDevices() {
		super();
	}
	public AutoDevices(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public Integer getDevicesId() {
		return devicesId;
	}
	public  void setDevicesId(Integer devicesId) {
		this.devicesId = devicesId;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getDevices() {
		return devices;
	}
	public void setDevices(String devices) {
		this.devices = devices;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMaindevices() {
		return maindevices;
	}
	public void setMaindevices(String maindevices) {
		this.maindevices = maindevices;
	}
	
}