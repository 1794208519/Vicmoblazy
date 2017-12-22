package com.jeeplus.modules.lazyearn.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.lazyearn.entity.VicCodeText;

/**
 * @author Stephen
 * @version 2017-9-27
 */
@MyBatisDao
public interface VicCodeTextDao extends CrudDao<VicCodeText> {
	Integer deleteByKeword(VicCodeText vicCodeText);
	Integer deleteByCodeTextId(VicCodeText vicCodeText);
	List<VicCodeText> findlikeList(VicCodeText vicCodeText);
	//分页查询
	List<VicCodeText> findPageList(VicCodeText vicCodeText);
	//根据关键词查询
	List<VicCodeText> findIntelligentResponse(VicCodeText vicCodeText);
}