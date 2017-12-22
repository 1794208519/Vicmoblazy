package com.jeeplus.modules.lazyearn.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.lazyearn.entity.AutoCity;

/**
 * 选择城市生成号码DAO
 * @author Stephen
 * @version 2017-9-27
 */
@MyBatisDao
public interface AutoCityDao extends CrudDao<AutoCity> {
	public List<AutoCity> findAutoCityByDevices(String devices);
	public int  isExist(String devices);
}