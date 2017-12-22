package com.jeeplus.modules.lazyearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.lazyearn.dao.ResttingDateDao;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.ResttingDate;
import com.jeeplus.modules.sys.dao.UserDao;


@Service
@Transactional(readOnly = true)
public class ResttingDateService extends CrudService<ResttingDateDao, ResttingDate> {
	@Autowired
	private ResttingDateDao resttingDateDao;
	
	public List<ResttingDate> findList(ResttingDate resttingDate) {
		return super.findList(resttingDate);
	}
	
}
