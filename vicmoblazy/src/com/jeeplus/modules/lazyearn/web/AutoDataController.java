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
import com.jeeplus.modules.lazyearn.entity.AutoData;
import com.jeeplus.modules.lazyearn.service.AutoDataService;
import com.jeeplus.modules.sys.utils.MySqlUtils;


/**
 * 群控系统携带的参数Controller
 * @author Stephen	
 * @version 2017-9-27
 */
@Controller

@RequestMapping(value = "${adminPath}/lazyearn/AutoData")
public class AutoDataController extends BaseController {
	@Autowired
	private AutoDataService autoDataService;
	/**
	 * 跳转账号管理List列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "jumpList")
	public String list(HttpServletRequest request, Model model) {
		return "modules/lazyearn/lazyEarnManager/myAddressList";
	}
	/**	 * 申请删除地点(用)
	 * @param request
	 * @param autoData
	 */
	@RequestMapping(value ="deleteAddress")
	@ResponseBody
	public AjaxJson deleteAddress(HttpServletRequest request,AutoData autoData) {	
		AjaxJson ajax = new AjaxJson();
		try {
			String dataId = request.getParameter("dataId");
			autoData.setDataId(Integer.parseInt(dataId));
			autoDataService.delete(autoData);
			System.out.println("111");
			ajax.setSuccess(true);
		} catch (Exception e) {	
			System.out.println("222");
			ajax.setSuccess(false);
			ajax.setMsg(e.getMessage());
		}		
		return ajax;
	}
	
	@RequestMapping(value ="find")
	@ResponseBody
	public List<AutoData> find(HttpServletRequest request,AutoData autoData) {	
			String dataId = request.getParameter("dataId");
			autoData.setDataId(Integer.parseInt(dataId));
			List<AutoData> list = autoDataService.findList(autoData);		
		return list;
	
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
	public Page<AutoData> findList(AutoData autoData, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String title = request.getParameter("searchFund");// 搜索时带的关键字
		String maindevices = request.getParameter("maindevices");// 搜索时带的关键字
		System.out.println("account----------" + title);
		System.out.println("account----------" + maindevices);
		String str = MySqlUtils.specialStr(title.trim());
		System.out.println("account111----------" + str);
		if(maindevices != null&& !maindevices.equals("")&& !maindevices.equals("null")&&!maindevices.equals("0")) {			
			String str1 = MySqlUtils.specialStr(maindevices);
			System.out.println("vicCodeText111----------selectedDevice" + str1);
			autoData.setMaindevices(maindevices);
		}else {
			autoData.setMaindevices(null);
		}
		autoData.setAddress(str);
		autoData.setDevices(str);
		
		Page<AutoData> page = autoDataService.findPage(new Page<AutoData>(request, response), autoData);
		
		return page;
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
	public int save(AutoData autoData, Model model, HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		System.out.println("dataId------" + request.getParameter("dataId"));
		System.out.println("address---" + request.getParameter("address"));
		System.out.println("lat---" + request.getParameter("lat"));
		System.out.println("lng---" + request.getParameter("lng"));
		System.out.println("lac---" + request.getParameter("lac"));
		System.out.println("devices---" + request.getParameter("devices"));
		autoData.setDevices(request.getParameter("devices"));
		autoData.setLatitude(request.getParameter("lat"));
		autoData.setLongitude(request.getParameter("lng"));
		autoData.setAddress(request.getParameter("address"));
		try {
			result = autoDataService.saveData(autoData);
			System.out.println("result--" + result);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
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
	public AutoData edit(AutoData autoData, Integer dataId, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("autoAccount----ID:" + dataId);
		if (dataId != null) {
			autoData.setDataId(dataId);
			autoData = autoDataService.get(autoData);
		}
		return autoData;
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
	public int deleteAll(Integer dataId, Model model, HttpServletRequest request, HttpServletResponse response) {
		String[] delitems = request.getParameter("delitems").split(",");
		int result = 0;
		for (int i = 0; i < delitems.length; i++) {
			AutoData autoData = new AutoData();
			autoData.setDataId(Integer.parseInt(delitems[i]));
			result=autoDataService.deletedataId(autoData);
			
		}
		return result;
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
	public int delete(Integer dataId, Model model, HttpServletRequest request, HttpServletResponse response) {
		int result;
		AutoData autoData = new AutoData();
		autoData.setDataId(dataId);
		result = autoDataService.deletedataId(autoData); 
		return result;
	}
	

}