package com.jeeplus.modules.sys.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.VicmobTemplate;

/**
 * 模板消息DAO
 * @author squirrel
 * @version 2017-10-11
 */
@MyBatisDao
public interface VicmobTemplateDao extends CrudDao<VicmobTemplate> {

	public VicmobTemplate findTemplate(VicmobTemplate vicmobTemplate);

	
}	
