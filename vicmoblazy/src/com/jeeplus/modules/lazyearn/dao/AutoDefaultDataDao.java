package com.jeeplus.modules.lazyearn.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.lazyearn.entity.AutoDefaultData;

/**
 * 选择城市生成号码DAO
 * @author Stephen
 * @version 2017-9-27
 */
@MyBatisDao
public interface AutoDefaultDataDao extends CrudDao<AutoDefaultData> {
	public AutoDefaultData findEntityById(Integer id);
}