package com.jeeplus.modules.lazyearn.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.AutoCity;
import com.jeeplus.modules.lazyearn.entity.AutoControl;

/**
 * 主控设备号与分控设备号数据DAO
 * @author Stephen
 * @version 2017-10-27
 */
@MyBatisDao
public interface AutoControlDao extends CrudDao<AutoControl> {
	//public List<AutoControl> delAutoControlByDevices(String devices);
	public int delcontrolId(AutoControl autoControl);
	public int  isExist(String partdevices);
	List<AutoControl> findNumList(AutoControl autoControl);
}