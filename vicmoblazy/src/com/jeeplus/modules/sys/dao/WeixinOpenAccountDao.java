package com.jeeplus.modules.sys.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.WeixinOpenAccount;

/**
 * 获取并保存微信推送的ticket的Dao接口
 * @author Stephen
 * @version 2017-7-2
 */
@MyBatisDao
public interface WeixinOpenAccountDao extends CrudDao<WeixinOpenAccount> {

}
