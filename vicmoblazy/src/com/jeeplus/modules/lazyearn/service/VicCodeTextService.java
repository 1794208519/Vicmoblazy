package com.jeeplus.modules.lazyearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.lazyearn.dao.VicCodeTextDao;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.VicCodeText;
/**
 * @author Stephen
 * @version 2017-9-27
 */
@Service
@Transactional(readOnly = true)
public class VicCodeTextService extends CrudService<VicCodeTextDao, VicCodeText> {
	
	@Autowired
	private VicCodeTextDao vicCodeTextDao;
	
	
	public VicCodeText get(String id) {
		return super.get(id);
	}
	
	public List<VicCodeText> findList(VicCodeText vicCodeText) {
		return super.findList(vicCodeText);
	}
	/**
	 * 智能回复分页查询
	 */
	public Page<VicCodeText> findPage(Page<VicCodeText> page, VicCodeText vicCodeText) {
		vicCodeText.setPage(page);
		page.setList(vicCodeTextDao.findPageList(vicCodeText));
		return page;
	}
	
	/**
	 * 保存或者更新数据
	 * @param wcsGoods
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveData(VicCodeText vicCodeText) {
		if(vicCodeText.getCodeTextId() == null){
			super.save(vicCodeText);
			return vicCodeText.getCodeTextId();
		} else {
			int result = super.update(vicCodeText);
			if(result > 0){
				return vicCodeText.getCodeTextId();
			}
			return -1;
		}
	}
	
	@Transactional(readOnly = false)
	public Integer saveVicCodeText(VicCodeText vicCodeText) {
		boolean isNewRecord = true;
		if (vicCodeText.getCodeTextId() != null){
			isNewRecord = false;		
		}
		vicCodeText.setIsNewRecord(isNewRecord);
		super.saveCustomId(vicCodeText);
		return vicCodeText.getCodeTextId();
	}
	
	@Transactional(readOnly = false)
	public Integer deleteByCodeTextId(VicCodeText vicCodeText) {
		Integer isDelete = vicCodeTextDao.deleteByCodeTextId(vicCodeText);
		return isDelete;
	}
	
	
	//通过关键字删除记录
	@Transactional(readOnly = false)
	public Integer deleteByKeword(VicCodeText vicCodeText) {
		Integer isDelete = vicCodeTextDao.deleteByKeword(vicCodeText);
		return isDelete;
	}
	//通过关键字字节长度大于4个就模糊
	public List<VicCodeText> findlikeList(VicCodeText vicCodeText) {
		List<VicCodeText> list=vicCodeTextDao.findlikeList(vicCodeText);
		return list;
	}
	
	//通过关键词查询所有相关记录
	@Transactional(readOnly = false)
	public List<VicCodeText> findIntelligentResponse(VicCodeText vicCodeText){
		List<VicCodeText> list = vicCodeTextDao.findIntelligentResponse(vicCodeText);
		return list;
	}
}