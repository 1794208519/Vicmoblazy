package com.jeeplus.modules.lazyearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.lazyearn.dao.AutoQQDefaultDataDao;
import com.jeeplus.modules.lazyearn.entity.AutoDefaultData;
import com.jeeplus.modules.lazyearn.entity.AutoQQDefaultData;


@Service
@Transactional(readOnly = true)
public class AutoQQDefaultDataService extends CrudService<AutoQQDefaultDataDao, AutoQQDefaultData>{
	@Autowired
	private AutoQQDefaultDataDao autoQQDefaultDataDao;
	
	
	public AutoQQDefaultData get(String id) {
		return super.get(id);
	}
	
	public List<AutoQQDefaultData> findList(AutoQQDefaultData autoQQDefaultData) {
		return super.findList(autoQQDefaultData);
	}
	
	public Page<AutoQQDefaultData> findPage(Page<AutoQQDefaultData> page, AutoQQDefaultData autoQQDefaultData) {
		return super.findPage(page, autoQQDefaultData);
	}
	
	/**
	 * 保存或者更新数据
	 * @param wcsGoods
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveData(AutoQQDefaultData autoQQDefaultData) {
		if(autoQQDefaultData.getQqDefaultDataId() == null){
			super.save(autoQQDefaultData);
			return autoQQDefaultData.getQqDefaultDataId();
		} else {
			int result = super.update(autoQQDefaultData);
			if(result > 0){
				return autoQQDefaultData.getQqDefaultDataId();
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
	public int saveAllData(AutoQQDefaultData autoQQDefaultData) {
		
		List<AutoQQDefaultData> list=findList(autoQQDefaultData);
		System.out.println("___List---" + list.size());
		if(autoQQDefaultData.getQqDefaultDataId() == null&&(list.size()<1||list==null)){
			super.save(autoQQDefaultData);
			return autoQQDefaultData.getQqDefaultDataId();
		} else {
			if(autoQQDefaultData.getQqDefaultDataId() == null) {
				autoQQDefaultData.setQqDefaultDataId(list.get(0).getQqDefaultDataId());
			}
			int result = super.update(autoQQDefaultData);
			if(result > 0){
				return autoQQDefaultData.getQqDefaultDataId();
			}
			return -1;
		}
	}
	@Transactional(readOnly = false)
	public Integer saveAutoQQDefaultData(AutoQQDefaultData autoQQDefaultData) {
		boolean isNewRecord = true;
		if (autoQQDefaultData.getQqDefaultDataId() != null){
			isNewRecord = false;		
		}
		autoQQDefaultData.setIsNewRecord(isNewRecord);
		super.saveCustomId(autoQQDefaultData);
		return autoQQDefaultData.getQqDefaultDataId();
	}
	
	@Transactional(readOnly = false)
	public void delete(AutoQQDefaultData autoQQDefaultData) {
		super.delete(autoQQDefaultData);
	}
	
	public AutoQQDefaultData findEntityById(Integer id){
		
		AutoQQDefaultData autoQQDefaultData = autoQQDefaultDataDao.findEntityById(id);
		return autoQQDefaultData;
	}
}
