package com.jeeplus.modules.lazyearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.lazyearn.dao.AutoCityDao;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.AutoCity;
import com.jeeplus.modules.lazyearn.entity.AutoData;
/**
 * 选择城市生成号码Service
 * @author Stephen
 * @version 2017-9-27
 */
@Service
@Transactional(readOnly = true)
public class AutoCityService extends CrudService<AutoCityDao, AutoCity> {
	
	@Autowired
	private AutoCityDao autoCityDao;
	
	
	public AutoCity get(String id) {
		return super.get(id);
	}
	
	public List<AutoCity> findList(AutoCity autoCity) {
		return super.findList(autoCity);
	}
	
	public Page<AutoCity> findPage(Page<AutoCity> page, AutoCity autoCity) {
		Page<AutoCity> page1 =super.findPage(page, autoCity);
		for(int i=0;i<page1.getList().size();i++) {
			if(page1.getList().get(i).getMaindevices()==null||page1.getList().get(i).getMaindevices().equals("")) {
				page1.getList().get(i).setMaindevices("设备未绑定");	
			}
			if(page1.getList().get(i).getProvince()==null||page1.getList().get(i).getProvince().equals("")) {
				page1.getList().get(i).setProvince("请选择省份");		
			}
			if(page1.getList().get(i).getCity()==null||page1.getList().get(i).getCity().equals("")) {
				page1.getList().get(i).setCity("请选择城市");				
			}
			if(page1.getList().get(i).getDevices()==null||page1.getList().get(i).getDevices().equals("")) {
				page1.getList().get(i).setDevices("请选择设备");			
			}
		}
		return page1;
	}
	
	/**
	 * 保存或者更新数据
	 * @param autoCity
	 * @return
	 */
	
	
	@Transactional(readOnly = false)
	public int saveData(AutoCity autoCity) {
		if(autoCity.getCityId() == null){
			super.save(autoCity);
			return autoCity.getCityId();
		} else {
			int result = super.update(autoCity);
			if(result > 0){
				return autoCity.getCityId();
			}
			return -1;
		}
	}
	
	@Transactional(readOnly = false)
	public Integer saveAutoCity(AutoCity autoCity) {
		boolean isNewRecord = true;
		if (autoCity.getCityId() != null){
			isNewRecord = false;		
		}
		autoCity.setIsNewRecord(isNewRecord);
		super.saveCustomId(autoCity);
		return autoCity.getCityId();
	}
	
	@Transactional(readOnly = false)
	public void delete(AutoCity autoCity) {
		super.delete(autoCity);
	}
	
	public List<AutoCity> findAutoCityByDevices(String devices){		
		List<AutoCity> list = autoCityDao.findAutoCityByDevices(devices);
		return list;
	}
	
	/**
	 * 判断当前设备号城市是否存在
	 * @param wcsGoods
	 * @return
	 */
	@Transactional(readOnly = false)
	public int isExist(String partdevices) {
	   return autoCityDao.isExist(partdevices);
	}
	
	
	
}