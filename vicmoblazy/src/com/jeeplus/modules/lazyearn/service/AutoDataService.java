package com.jeeplus.modules.lazyearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.lazyearn.dao.AutoDataDao;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.AutoData;
/**
 * 群控系统携带的参数Service
 * @author Stephen
 * @version 2017-9-27
 */
@Service
@Transactional(readOnly = true)
public class AutoDataService extends CrudService<AutoDataDao, AutoData> {
	
	@Autowired
	private AutoDataDao autoDataDao;
	
	
	public AutoData get(String id) {
		return super.get(id);
	}
	
	public List<AutoData> findList(AutoData autoData) {
		return super.findList(autoData);
	}
	//我的地址这边对主控设备：如果主控设备为null时，人为赋值空
	public Page<AutoData> findPage(Page<AutoData> page, AutoData autoData) {
		Page<AutoData> page1 =super.findPage(page, autoData);
		for(int i=0;i<page1.getList().size();i++) {
			if(page1.getList().get(i).getMaindevices()==null) {
				page1.getList().get(i).setMaindevices("未绑定懒懒");
			}
		}
		return page1;
	}
	
	@Transactional(readOnly = false)
	public Integer saveAutoData(AutoData autoData) {
		boolean isNewRecord = true;
		if (autoData.getDataId() != null){
			isNewRecord = false;		
		}
		autoData.setIsNewRecord(isNewRecord);
		super.saveCustomId(autoData);
		return autoData.getDataId();
	}
	
	@Transactional(readOnly = false)
	public void delete(AutoData autoData) {
		super.delete(autoData);
	}
	
	public List<AutoData> findAutoDataByDevices(String devices){		
		List<AutoData> list = autoDataDao.findAutoDataByDevices(devices);
		return list;
	}
	/**
	 * 保存或者更新数据
	 * @param wcsGoods
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveData(AutoData autoData) {
		if(autoData.getDataId() == null){
			super.save(autoData);
			return autoData.getDataId();
		} else {
			int result = super.update(autoData);
			if(result > 0){
				return autoData.getDataId();
			}
			return -1;
		}
	}
	@Transactional(readOnly = false)
	public int deletedataId(AutoData autoData) {
		int result=autoDataDao.deletedataId(autoData);
		return result;
	}
}