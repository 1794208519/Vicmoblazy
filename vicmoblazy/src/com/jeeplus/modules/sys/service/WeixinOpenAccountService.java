
package com.jeeplus.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.WeixinOpenAccountDao;
import com.jeeplus.modules.sys.entity.WeixinOpenAccount;


/**
 * 获取并保存微信推送的ticket的Service
 * @author Stephen
 * @version 2017-07-02
 */
@Service
@Transactional(readOnly = true)
public class WeixinOpenAccountService extends CrudService<WeixinOpenAccountDao,WeixinOpenAccount> {
		
	public WeixinOpenAccount get(String id) {
		return super.get(id);
	}
	/**
	 * API也调用了这个方法
	 */
	public List<WeixinOpenAccount> findList(WeixinOpenAccount weixinOpenAccount) {
		return super.findList(weixinOpenAccount);
	}
	
	@Transactional(readOnly = false)
	public Integer saveWeixinOpenAccount(WeixinOpenAccount weixinOpenAccount) {
		boolean isNewRecord = true;
		if (weixinOpenAccount.getTicketId() != null){
			isNewRecord = false;		
		}
		weixinOpenAccount.setIsNewRecord(isNewRecord);
		super.saveCustomId(weixinOpenAccount);
		return weixinOpenAccount.getTicketId();
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinOpenAccount WeixinOpenAccount) {
		super.delete(WeixinOpenAccount);
	}
	
}