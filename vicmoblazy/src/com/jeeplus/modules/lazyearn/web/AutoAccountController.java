package com.jeeplus.modules.lazyearn.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.service.AutoAccountService;
import com.jeeplus.modules.lazyearn.service.AutoControlService;
import com.jeeplus.modules.sys.utils.MySqlUtils;

/**
 * 懒赚账户管理Controller
 * 
 * @author Stephen
 * @version 2017-9-27
 */
@Controller
@RequestMapping(value = "${adminPath}/lazyearn/AutoAccount")
public class AutoAccountController extends BaseController {
	@Autowired
	private AutoAccountService autoAccountService;
	@Autowired
	private AutoControlService autoControlService;
	/**
	 * 跳转账号管理List列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "jumpList")
	public String list(HttpServletRequest request, Model model) {
		return "modules/lazyearn/lazyEarnManager/myAccountList";
	}

	/**
	 * 分页查询账号信息
	 * 
	 * @param autoAccount
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findList")
	@ResponseBody
	public Page<AutoAccount> findList(AutoAccount autoAccount, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		autoAccount.setDeleteFlag(false);// 未删除的
		String title = request.getParameter("searchFund");// 搜索时带的关键字
		String maindevices = request.getParameter("maindevices");// 搜索时带的关键字
		System.out.println("account----------" + title);
		String str = MySqlUtils.specialStr(title.trim());
		System.out.println("account111----------" + str);
		autoAccount.setAccount(str);
		autoAccount.setDevices(str);
		if(maindevices != null&& !maindevices.equals("")&& !maindevices.equals("null")&&!maindevices.equals("0")) {			
			String str1 = MySqlUtils.specialStr(maindevices);
			System.out.println("vicCodeText111----------selectedDevice" + str1);
			autoAccount.setMaindevices(maindevices);
		}else {
			autoAccount.setMaindevices(null);
		}
		autoAccount.setNickname(str);
		Page<AutoAccount> page = autoAccountService.findPage(new Page<AutoAccount>(request, response), autoAccount);
		return page;
	}

	/**
	 * 单个删除的方法
	 * 
	 * @param accountId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public int delete(Integer accountId, Model model, HttpServletRequest request, HttpServletResponse response) {
		int result;
		AutoAccount autoAccount = new AutoAccount();
		autoAccount.setAccountId(accountId);
		autoAccount.setDeleteFlag(true);
		result = autoAccountService.saveData(autoAccount);
		return result;
	}

	/**
	 * 批量删除的方法
	 * 
	 * @param accountId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteAll")
	@ResponseBody
	public int deleteAll(Integer accountId, Model model, HttpServletRequest request, HttpServletResponse response) {
		String[] delitems = request.getParameter("delitems").split(",");
		int result = 0;
		for (int i = 0; i < delitems.length; i++) {
			AutoAccount autoAccount = new AutoAccount();
			autoAccount.setAccountId(Integer.parseInt(delitems[i]));
			autoAccount.setDeleteFlag(true);
			result = autoAccountService.saveData(autoAccount);
		}
		return result;
	}


	/**
	 * 编辑或保存的方法
	 * 
	 * @param autoAccount
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public int save(AutoAccount autoAccount, Model model, HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		System.out.println("accountId------" + autoAccount.getAccountId());
		System.out.println("account---" + autoAccount.getAccount());
		System.out.println("password---" + autoAccount.getPassword());
		
		System.out.println("nickname---" + autoAccount.getNickname());
		autoAccount.setDeleteFlag(false);
		
		if(autoAccount.getDevices()!=null && !autoAccount.getDevices().equals("")&& !autoAccount.getDevices().equals("null")&&!autoAccount.getDevices().equals("0") && autoControlService.isExist(autoAccount.getDevices())==0 ) {
			System.out.println("devices---" + autoAccount.getDevices());
			return -2;
		}else {
		try {
			result = autoAccountService.saveData(autoAccount);
			System.out.println("result--" + result);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
		}

	}

	/**
	 * 编辑form表单
	 * @param autoAccount
	 * @param accountId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "edit")
	@ResponseBody
	public AutoAccount edit(AutoAccount autoAccount, Integer accountId, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("autoAccount----ID:" + accountId);
		if (accountId != null) {
			autoAccount.setAccountId(accountId);
			autoAccount = autoAccountService.get(autoAccount);
		}
		return autoAccount;
	}

}