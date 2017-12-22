package com.jeeplus.modules.lazyearn.entity;
import com.jeeplus.common.persistence.DataEntity;
/**
 * 
 * 主控设备号与分控设备号数据实体 
 * @author Stephen
 * @version 2017-10-27
 *
 */
public class AutoControl extends DataEntity<AutoControl>{
	
	private static final long serialVersionUID = 1L;
	
	private Integer controlId;		// 主键ID
	private String maindevices;//主控设备号
	private String partdevices; //分控设备号
	public AutoControl() {
		super();
	}
	public AutoControl(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public Integer getControlId() {
		return controlId;
	}
	public void setControlId(Integer controlId) {
		this.controlId = controlId;
	}
	public String getMaindevices() {
		return maindevices;
	}
	public void setMaindevices(String maindevices) {
		this.maindevices = maindevices;
	}
	public String getPartdevices() {
		return partdevices;
	}
	public void setPartdevices(String partdevices) {
		this.partdevices = partdevices;
	}
	
	
}