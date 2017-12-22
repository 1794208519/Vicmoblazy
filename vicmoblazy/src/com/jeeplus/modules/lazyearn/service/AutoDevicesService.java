package com.jeeplus.modules.lazyearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.lazyearn.dao.AutoDevicesDao;
import com.jeeplus.modules.lazyearn.entity.AutoData;
import com.jeeplus.modules.lazyearn.entity.AutoDevices;
/**
 * 群控设备序列号表Service
 * @author Stephen
 * @version 2017-9-27
 */
@Service
@Transactional(readOnly = true)
public class AutoDevicesService extends CrudService<AutoDevicesDao, AutoDevices> {
	
	@Autowired
	private AutoDevicesDao autoDevicesDao;
	
	
	public AutoDevices get(String id) {
		return super.get(id);
	}
	
	public List<AutoDevices> findList(AutoDevices autoDevices) {
		return super.findList(autoDevices);
	}
	
	public Page<AutoDevices> findPage(Page<AutoDevices> page, AutoDevices autoDevices) {
		Page<AutoDevices> page1 =super.findPage(page, autoDevices);
		for(int i=0;i<page1.getList().size();i++) {
			if(page1.getList().get(i).getMaindevices()==null) {
				page1.getList().get(i).setMaindevices("未绑定懒懒");
			}
		}
		return page1;
	}
	
	@Transactional(readOnly = false)
	public Integer saveAutoDevices(AutoDevices autoDevices) {
		boolean isNewRecord = true;
		if (autoDevices.getDevicesId() != null){
			isNewRecord = false;		
		}
		autoDevices.setIsNewRecord(isNewRecord);
		super.saveCustomId(autoDevices);
		return autoDevices.getDevicesId();
	}
	
	@Transactional(readOnly = false)
	public Integer whiteListDelete(String devicesId) {
		return autoDevicesDao.whiteListDelete(devicesId);
	}

	
	
	//传入状态值将表内所有记录的状态值都改变
	@Transactional(readOnly = false)
	public void updateAllStatus(String status) {
		autoDevicesDao.updateAllStatus(status);
	}
	
}