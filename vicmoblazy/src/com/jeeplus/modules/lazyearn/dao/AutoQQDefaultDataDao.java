package com.jeeplus.modules.lazyearn.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.lazyearn.entity.AutoQQDefaultData;

@MyBatisDao
public interface AutoQQDefaultDataDao extends CrudDao<AutoQQDefaultData> {
	public AutoQQDefaultData findEntityById(Integer id);
}
