package com.jeeplus.modules.lazyearn.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.lazyearn.entity.HdManager;


/**
 * 懒赚账户DAO
 * @author Stephen
 * @version 2017-9-27
 */
@MyBatisDao
public interface HdManagerDao extends CrudDao<HdManager> {
	List<HdManager> findNumByDate();
	
}