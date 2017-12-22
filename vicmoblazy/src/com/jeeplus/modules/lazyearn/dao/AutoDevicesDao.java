package com.jeeplus.modules.lazyearn.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.lazyearn.entity.AutoDevices;

/**
 * 群控设备序列号表DAO
 * @author Stephen
 * @version 2017-9-27
 */
@MyBatisDao
public interface AutoDevicesDao extends CrudDao<AutoDevices> {
	//传入状态值将表内所有记录的状态值都改变
	void updateAllStatus(String status);
	Integer whiteListDelete(String devicesId);
}