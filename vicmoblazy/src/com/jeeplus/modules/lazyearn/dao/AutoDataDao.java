package com.jeeplus.modules.lazyearn.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.lazyearn.entity.AutoData;

/**
 * 群控系统携带的参数DAO
 * @author Stephen
 * @version 2017-9-27
 */
@MyBatisDao
public interface AutoDataDao extends CrudDao<AutoData> {
	public List<AutoData> findAutoDataByDevices(String devices);
	public int deletedataId(AutoData autodata);
}