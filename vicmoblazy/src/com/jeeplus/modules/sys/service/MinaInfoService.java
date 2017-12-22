
package com.jeeplus.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.MinaInfoDao;
import com.jeeplus.modules.sys.entity.MinaInfo;
import com.jeeplus.modules.sys.entity.User;


/**
 * 授权小程序的Service
 * @author Stephen
 * @version 2017-07-02
 */
@Service
@Transactional(readOnly = true)
public class MinaInfoService extends CrudService<MinaInfoDao,MinaInfo> {
		
    @Autowired
	private MinaInfoDao minaInfoDao;
	 
	public MinaInfo get(String id) {
		return super.get(id);
	}
	
	public List<MinaInfo> findList(MinaInfo minaInfo) {
		return super.findList(minaInfo);
	}
	
	@Transactional(readOnly = false)
	public Integer saveMinaInfo(MinaInfo minaInfo) {
		boolean isNewRecord = true;
		if (minaInfo.getMinaid() != null){
			isNewRecord = false;		
		}
		minaInfo.setIsNewRecord(isNewRecord);
		super.saveCustomId(minaInfo);
		return minaInfo.getMinaid();
	}
	
	@Transactional(readOnly = false)
	public void delete(MinaInfo minaInfo) {
		super.delete(minaInfo);
	}
	
	//逻辑删除
	@Transactional(readOnly = false)
	public void logicDelete(Integer minaid) {
		minaInfoDao.logicDelete(minaid);
	}
	
	public MinaInfo findEntityById(Integer minaid){
		MinaInfo minaInfo = minaInfoDao.findEntityById(minaid);
		return minaInfo;
	}
	
	//通过appid获取记录
	public List<MinaInfo> findMinaByAppid(String appid){
		List<MinaInfo> minaInfoList = minaInfoDao.findMinaByAppid(appid);
		return minaInfoList;
	}
	
	//通过appid获取记录
	public List<MinaInfo> findMinaByUserid(Integer userid){
		List<MinaInfo> minaInfoList = minaInfoDao.findMinaByUserid(userid);
		return minaInfoList;
	}
		
	//通过用户userid获取记录
	public User findUserById(Integer userid){
		User user = minaInfoDao.findUserById(userid);
		return user;
	}	
	
	/**
	 * API
	 * 通过minaId获取记录
	 * @param minaId
	 * @return
	 */
	public MinaInfo getMessageByminaId(Integer minaId){
		
		MinaInfo minaInfo = minaInfoDao.getMessageByminaId(minaId);
		return minaInfo;
	}
}