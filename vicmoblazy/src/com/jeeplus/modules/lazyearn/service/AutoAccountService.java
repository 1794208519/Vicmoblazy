package com.jeeplus.modules.lazyearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.lazyearn.dao.AutoAccountDao;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.AutoCity;
import com.jeeplus.modules.lazyearn.entity.AutoData;
/**
 * 懒赚账户Service
 * @author Stephen
 * @version 2017-9-27
 */
@Service
@Transactional(readOnly = true)
public class AutoAccountService extends CrudService<AutoAccountDao, AutoAccount> {
	
	@Autowired
	private AutoAccountDao autoAccountDao;
	
	
	public AutoAccount get(String id) {
		return super.get(id);
	}
	
	public List<AutoAccount> findList(AutoAccount autoAccount) {
		return super.findList(autoAccount);
	}
	
	public Page<AutoAccount> findPage(Page<AutoAccount> page, AutoAccount autoAccount) {
		Page<AutoAccount> page1 =super.findPage(page, autoAccount);
		for(int i=0;i<page1.getList().size();i++) {
			if(page1.getList().get(i).getMaindevices()==null) {
				page1.getList().get(i).setMaindevices("未绑定懒懒");
			}
		}
		return page1;
	}
	
	/**
	 * 保存或者更新数据
	 * @param wcsGoods
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveData(AutoAccount autoAccount) {
		if(autoAccount.getAccountId() == null){
			super.save(autoAccount);
			return autoAccount.getAccountId();
		} else {
			int result = super.update(autoAccount);
			if(result > 0){
				return autoAccount.getAccountId();
			}
			return -1;
		}
	}
	
	@Transactional(readOnly = false)
	public Integer saveAutoAccount(AutoAccount autoAccount) {
		boolean isNewRecord = true;
		if (autoAccount.getAccountId() != null){
			isNewRecord = false;		
		}
		autoAccount.setIsNewRecord(isNewRecord);
		super.saveCustomId(autoAccount);
		return autoAccount.getAccountId();
	}
	
	@Transactional(readOnly = false)
	public void delete(AutoAccount AutoAccount) {
		super.delete(AutoAccount);
	}
	
	public List<AutoAccount> findAutoAccountByDevices(String devices){		
		List<AutoAccount> list = autoAccountDao.findAutoAccountByDevices(devices);
		return list;
	}
	
}