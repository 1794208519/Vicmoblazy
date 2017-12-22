
package com.jeeplus.modules.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.VicmobPayAccountDao;
import com.jeeplus.modules.sys.entity.VicmobPayAccount;


@Service
@Transactional(readOnly = true)
public class VicmobPayAccountService extends CrudService<VicmobPayAccountDao,VicmobPayAccount> {
	
	/**
	 * 查询支付账户信息
	 */
	public VicmobPayAccount get(VicmobPayAccount vicmobPayAccount) {
		return super.get(vicmobPayAccount);
	}

	/**
	 * 保存支付信息
	 * @param vicmobPayAccount
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveData(VicmobPayAccount vicmobPayAccount) {
		super.save(vicmobPayAccount);
		return vicmobPayAccount.getWeChatPayId();
	}

	/**
	 * 更新支付信息
	 * @param vicmobPayAccount
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateData(VicmobPayAccount vicmobPayAccount) {
		int result = super.update(vicmobPayAccount);
		if (result > 0) {
			return vicmobPayAccount.getWeChatPayId();
		}
		return -1;
	}
	
}