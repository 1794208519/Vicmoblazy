package com.jeeplus.modules.lazyearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.lazyearn.dao.AutoDefaultDataDao;
import com.jeeplus.modules.lazyearn.entity.AutoDefaultData;
/**
 * @author Stephen
 * @version 2017-9-27
 */
@Service
@Transactional(readOnly = true)
public class AutoDefaultDataService extends CrudService<AutoDefaultDataDao, AutoDefaultData> {
	
	@Autowired
	private AutoDefaultDataDao autoDefaultDataDao;
	
	
	public AutoDefaultData get(String id) {
		return super.get(id);
	}
	
	public List<AutoDefaultData> findList(AutoDefaultData autoDefaultData) {
		return super.findList(autoDefaultData);
	}
	
	public Page<AutoDefaultData> findPage(Page<AutoDefaultData> page, AutoDefaultData autoDefaultData) {
		return super.findPage(page, autoDefaultData);
	}
	
	/**
	 * 保存或者更新数据
	 * @param wcsGoods
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveData(AutoDefaultData autoDefaultData) {
		if(autoDefaultData.getDefaultDataId() == null){
			super.save(autoDefaultData);
			return autoDefaultData.getDefaultDataId();
		} else {
			int result = super.update(autoDefaultData);
			if(result > 0){
				return autoDefaultData.getDefaultDataId();
			}
			return -1;
		}
	}
	/**
	 * 保存或者更新数据
	 * @param wcsGoods
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveAllData(AutoDefaultData autoDefaultData) {
		
		List<AutoDefaultData> list=findList(autoDefaultData);
		System.out.println("___List---" + list.size());
		if(autoDefaultData.getDefaultDataId() == null&&(list.size()<1||list==null)){
			super.save(autoDefaultData);
			return autoDefaultData.getDefaultDataId();
		} else {
			if(autoDefaultData.getDefaultDataId() == null) {
				autoDefaultData.setDefaultDataId(list.get(0).getDefaultDataId());
			}
			int result = super.update(autoDefaultData);
			if(result > 0){
				return autoDefaultData.getDefaultDataId();
			}
			return -1;
		}
	}
	@Transactional(readOnly = false)
	public Integer saveAutoDefaultData(AutoDefaultData autoDefaultData) {
		boolean isNewRecord = true;
		if (autoDefaultData.getDefaultDataId() != null){
			isNewRecord = false;		
		}
		autoDefaultData.setIsNewRecord(isNewRecord);
		super.saveCustomId(autoDefaultData);
		return autoDefaultData.getDefaultDataId();
	}
	
	@Transactional(readOnly = false)
	public void delete(AutoDefaultData autoDefaultData) {
		super.delete(autoDefaultData);
	}
	
	public AutoDefaultData findEntityById(Integer id){
		
		AutoDefaultData autoDefaultData = autoDefaultDataDao.findEntityById(id);
		return autoDefaultData;
	}
}