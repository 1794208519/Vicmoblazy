package com.jeeplus.modules.lazyearn.web;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.AutoDevices;
import com.jeeplus.modules.lazyearn.service.AutoAccountService;
import com.jeeplus.modules.lazyearn.service.AutoDevicesService;
import com.jeeplus.modules.sys.utils.MySqlUtils;

@Controller
@RequestMapping(value = "${adminPath}/lazyearn/WhiteList")
public class WhiteListController extends BaseController{
	@Autowired
	private AutoDevicesService autoDevicesService;
	
	@RequestMapping(value = "jumpList")
	public String list(HttpServletRequest request, Model model) {
		return "modules/lazyearn/lazyEarnManager/whiteList";
	}
	/**
	 * 分页查询白名单信息
	 * 
	 * @param autoAccount
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findList")
	@ResponseBody
	public Page<AutoDevices> findList(AutoDevices autodevices, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String title = request.getParameter("searchFund");// 搜索时带的关键字
		String maindevices = request.getParameter("maindevices");// 搜索时带的关键字
		System.out.println("account----------" + title);
		String str = MySqlUtils.specialStr(title.trim());
		System.out.println("account111----------" + str);
		if(maindevices != null&& !maindevices.equals("")&& !maindevices.equals("null")&&!maindevices.equals("0")) {			
			String str1 = MySqlUtils.specialStr(maindevices);
			System.out.println("vicCodeText111----------selectedDevice" + str1);
			autodevices.setMaindevices(maindevices);
		}else {
			autodevices.setMaindevices(null);
		}
		autodevices.setDevices(str);
		autodevices.setFile(str);
		Page<AutoDevices> page = autoDevicesService.findPage(new Page<AutoDevices>(request, response), autodevices);
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
	@RequestMapping(value = "whiteListDelete")
	@ResponseBody
	public Integer whiteListDelete(Integer devicesId, Model model, HttpServletRequest request, HttpServletResponse response) {
		/*int result;
		AutoDevices autoAccount = new AutoDevices();
		autoAccount.setDevicesId(accountId);*/
	
		return autoDevicesService.whiteListDelete(devicesId+"");
	
	}
	@RequestMapping(value = "deleteAll")
	@ResponseBody
	public Integer deleteAll(String checkedListStr, Model model, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("HHHHHHH：");
		System.out.println("HHHHHHH："+request.getParameter("delitems"));
		String[] delitems = request.getParameter("delitems").split(",");
		
		System.out.println("HHHHHHH："+delitems.length);
		int result = 0;
		for (int i = 0; i < delitems.length; i++) {
			
			result = autoDevicesService.whiteListDelete(delitems[i]);
		}
		return result;
	}
	/**
	 * 编辑form表单
	 * @param AutoDevices
	 * @param devicesId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "edit")
	@ResponseBody
	public AutoDevices edit(AutoDevices autoDevices, Integer devicesId, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("autoAccount----ID:" + devicesId);
		if (devicesId != null) {
			autoDevices.setDevicesId(devicesId);
			autoDevices = autoDevicesService.get(autoDevices);
		}
		return autoDevices;
	}
	/**
	 * 编辑或保存的方法
	 * 
	 * @param AutoDevices
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public int save(AutoDevices autoDevices, Model model, HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		
		String file = request.getParameter("file");// 
		String devices = request.getParameter("devices");//
		autoDevices.setDevicesId(Integer.parseInt(request.getParameter("whiteId")));
		autoDevices.setFile(file);
		autoDevices.setDevices(devices);
		System.out.println("devicedId------" + request.getParameter("whiteId"));
		System.out.println("file---" + file);
		System.out.println("devices---" + devices);
			result = autoDevicesService.update(autoDevices);
			System.out.println("result--" + result);
			return result;

	}

}
