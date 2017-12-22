package com.jeeplus.modules.sys.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.VicmobFansInfo;

/**
 * 粉丝表DAO
 * @author squirrel
 * @version 2017-07-05
 */
@MyBatisDao
public interface VicmobFansInfoDao extends CrudDao<VicmobFansInfo> {

	//获取fans信息 API
	public VicmobFansInfo getFindList(VicmobFansInfo vicmobFansInfo);
	
}	
