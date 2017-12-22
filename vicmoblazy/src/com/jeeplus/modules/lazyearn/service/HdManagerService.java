package com.jeeplus.modules.lazyearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.lazyearn.dao.AutoAccountDao;
import com.jeeplus.modules.lazyearn.dao.HdManagerDao;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.AutoCity;
import com.jeeplus.modules.lazyearn.entity.AutoData;
import com.jeeplus.modules.lazyearn.entity.HdManager;
/**
 * 懒赚账户Service
 * @author Stephen
 * @version 2017-9-27
 */
@Service
@Transactional(readOnly = true)
public class HdManagerService extends CrudService<HdManagerDao, HdManager> {
	
	@Autowired
	private HdManagerDao hdManagerDao;
	
	
	public HdManager get(String id) {
		return super.get(id);
	}
	
	
	public List<HdManager> findNumByDate(){		
		List<HdManager> list = hdManagerDao.findNumByDate();
		return list;
	}
	
}