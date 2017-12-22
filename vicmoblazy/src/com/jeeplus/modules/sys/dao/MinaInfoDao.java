package com.jeeplus.modules.sys.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.MinaInfo;
import com.jeeplus.modules.sys.entity.User;


/**
 * 授权小程序Dao接口
 * @author Stephen
 * @version 2017-7-2
 */
@MyBatisDao
public interface MinaInfoDao extends CrudDao<MinaInfo> {
	//通过主键minaid获取对应唯一记录 
	MinaInfo findEntityById(Integer minaid);
	//通过主键userid获取对应唯一记录 
	User findUserById(Integer userid);	
	//逻辑删除授权小程序账号
	void logicDelete(Integer minaid);
	//通过授权appid获取对应记录
	List<MinaInfo> findMinaByAppid(String appid);
	//通过授权userid获取对应记录
	List<MinaInfo> findMinaByUserid(Integer userid);
	
	/**
	 * API
	 * 通过minaId获取记录
	 * @param minaid
	 * @return
	 */
	public MinaInfo getMessageByminaId(Integer minaId);
}
