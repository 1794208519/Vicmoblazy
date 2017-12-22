package com.jeeplus.modules.lazyearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.lazyearn.dao.AutoControlDao;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.AutoControl;

/**
 * 主控设备号与分控设备号数据Service
 * @author Stephen
 * @version 2017-9-27
 */
@Service
@Transactional(readOnly = true)
public class AutoControlService extends CrudService<AutoControlDao, AutoControl> {
	
	@Autowired
	private AutoControlDao autoControlDao;
	
	
	public AutoControl get(String id) {
		return super.get(id);
	}
	
	public List<AutoControl> findList(AutoControl autoControl) {
		return super.findList(autoControl);
	}
	
	public Page<AutoControl> findPage(Page<AutoControl> page, AutoControl autoControl) {
		return super.findPage(page, autoControl);
	}
	
	/**
	 * 保存或者更新数据
	 * @param wcsGoods
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveData(AutoControl autoControl) {
		if(autoControl.getControlId() == null){
			super.save(autoControl);
			return autoControl.getControlId();
		} else {
			int result = super.update(autoControl);
			if(result > 0){
				return autoControl.getControlId();
			}
			return -1;
		}
	}
	
	
	/**
	 * 判断设备号是否存在
	 * @param wcsGoods
	 * @return
	 */
	@Transactional(readOnly = false)
	public int isExist(String partdevices) {
	   return autoControlDao.isExist(partdevices);
	}
	
	
	@Transactional(readOnly = false)
	public Integer saveAutoControl(AutoControl autoControl) {
		boolean isNewRecord = true;
		if (autoControl.getControlId() != null){   
			isNewRecord = false;		
		}
		autoControl.setIsNewRecord(isNewRecord);
		super.saveCustomId(autoControl);
		return autoControl.getControlId();
	}
	 
	
	
	@Transactional(readOnly = false)
	public void delete(AutoControl autoControl) {
		super.delete(autoControl);
	}
	
	@Transactional(readOnly = false)
	public int delcontrolId(AutoControl autoControl) {
		int result =  autoControlDao.delcontrolId(autoControl);
		return result;
	}
	public List<AutoControl> findNumList(AutoControl autoControl){		
		List<AutoControl> list = autoControlDao.findNumList(autoControl);
		return list;
	}
}