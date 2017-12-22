package com.jeeplus.modules.lazyearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.lazyearn.dao.FeedBackDao;
import com.jeeplus.modules.lazyearn.entity.FeedBack;

/**
 * 懒赚账户Service
 * @author Stephen
 * @version 2017-9-27
 */
@Service
@Transactional(readOnly = true)
public class FeedBackService extends CrudService<FeedBackDao, FeedBack> {
	
	@Autowired
	private FeedBackDao feedBackDao;
	
	
	public FeedBack get(String id) {
		return super.get(id);
	}
	
	public List<FeedBack> findList(FeedBack feedBack) {
		return super.findList(feedBack);
	}
	
	public Page<FeedBack> findPage(Page<FeedBack> page, FeedBack feedBack) {
		Page<FeedBack> page1 =super.findPage(page, feedBack);
		
		return page1;
	}
	
	/**
	 * 保存或者更新数据
	 * @param wcsGoods
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveData(FeedBack feedBack) {
		if(feedBack.getFeedbackId() == null){
			super.save(feedBack);
			return feedBack.getFeedbackId();
		} else {
			int result = super.update(feedBack);
			if(result > 0){
				return feedBack.getFeedbackId();
			}
			return -1;
		}
	}
	
	
	
	@Transactional(readOnly = false)
	public void delete(FeedBack feedBack) {
		super.delete(feedBack);
	}
	
	
}