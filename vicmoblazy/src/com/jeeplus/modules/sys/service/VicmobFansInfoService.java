
package com.jeeplus.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.VicmobFansInfoDao;
import com.jeeplus.modules.sys.entity.VicmobFansInfo;


/**
 * 粉丝表Service
 * @author squirrel
 * @version 2017-07-05
 */
@Service
@Transactional(readOnly = true)
public class VicmobFansInfoService extends CrudService<VicmobFansInfoDao,VicmobFansInfo> {
	
	@Autowired
	private VicmobFansInfoDao vicmobFansInfoDao;
	
	/**
	 * 查询粉丝信息
	 */
	public VicmobFansInfo get(VicmobFansInfo vicmobFansInfo){
		return super.get(vicmobFansInfo);
	}
	
	/**
	 * 分页查询
	 */
	public Page<VicmobFansInfo> findPage(Page<VicmobFansInfo> page, VicmobFansInfo vicmobFansInfo) {
		return super.findPage(page, vicmobFansInfo);
	}
	
	/**
	 * API
	 * 保存数据
	 * @param wcsStoreCategory
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveData(VicmobFansInfo vicmobFansInfo) {
		
		super.save(vicmobFansInfo);
		return vicmobFansInfo.getFansId();
		
	}
	
	/**
	 * API
	 * 更新数据
	 * @param wcsStoreCategory
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateData(VicmobFansInfo vicmobFansInfo) {

		return super.update(vicmobFansInfo);
		
	}
	
	/**
	 * API
	 * 通过openId获取fans信息
	 * @param wcsStoreCategory
	 * @return
	 */
	public VicmobFansInfo getFindList(VicmobFansInfo vicmobFansInfo){
		
		VicmobFansInfo VicmobFansInfoNew = vicmobFansInfoDao.getFindList(vicmobFansInfo);
		return VicmobFansInfoNew;
	}
	
	
}