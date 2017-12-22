package com.jeeplus.modules.lazyearn.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
import com.jeeplus.modules.lazyearn.entity.AutoControl;
import com.jeeplus.modules.lazyearn.entity.AutoDefaultData;
import com.jeeplus.modules.lazyearn.entity.AutoQQDefaultData;
import com.jeeplus.modules.lazyearn.service.AutoControlService;
import com.jeeplus.modules.lazyearn.service.AutoQQDefaultDataService;
import com.jeeplus.modules.sys.utils.MySqlUtils;

@Controller
@RequestMapping(value = "${adminPath}/lazyearn/AutoQQDefaultData")
public class AutoQQDefaultDataController extends BaseController {
	@Autowired
	private AutoQQDefaultDataService autoQQDefaultDataService;
	@Autowired
	private AutoControlService autoControlService;
	/**
	 * 跳转数据交互中心List列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "jumpList")
	public String list(HttpServletRequest request, Model model) {
		System.out.println("2121");
		return "modules/lazyearn/lazyEarnManager/qqDefaultDataList";
	}
	/**
	 * 分页查询qq数据信息
	 * 
	 * @param autoQQDefaultData
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findList")
	@ResponseBody
	public Page<AutoQQDefaultData> findList(AutoQQDefaultData autoQQDefaultData, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		autoQQDefaultData.setDeleteFlag(false);
		String title = request.getParameter("qqSearchFund");// 搜索时带的关键字
		System.out.println("qqdefaultData----------" + title);
		String str = MySqlUtils.specialStr(title);
		System.out.println("qqdefaultData111----------" + str);
		autoQQDefaultData.setDevices(str);
		Page<AutoQQDefaultData> page = autoQQDefaultDataService.findPage(new Page<AutoQQDefaultData>(request, response),
				autoQQDefaultData);
		System.out.println("___pageList---" + page.getList().size());
		//////////// 循环遍历将数据中心中关于招呼内容的参数用encode转中文//////////
		if (page.getList().size() > 0 && page.getList() != null) {
			for (int i = 0; i < page.getList().size(); i++) {
				AutoQQDefaultData autoQQDefaultData1 = page.getList().get(i);
				EncoderToChina(autoQQDefaultData1);
				page.getList().set(i, autoQQDefaultData1);
			}
		}
		return page;
	}
	
	/**
	 * 单个删除的方法
	 * 
	 * @param qqDefaultDataId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public int delete(Integer qqDefaultDataId, Model model, HttpServletRequest request, HttpServletResponse response) {
		int result;
		AutoQQDefaultData autoQQDefaultData = new AutoQQDefaultData();
		autoQQDefaultData.setQqDefaultDataId(qqDefaultDataId);
		autoQQDefaultData.setDeleteFlag(true);
		result = autoQQDefaultDataService.saveData(autoQQDefaultData);
		return result;
	}

	/**
	 * 批量删除的方法
	 * 
	 * @param qqDefaultDataId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteAll")
	@ResponseBody
	public int deleteAll(Integer qqDefaultDataId, Model model, HttpServletRequest request, HttpServletResponse response) {
		String[] delitems = request.getParameter("delitems").split(",");
		int result = 0;
		for (int i = 0; i < delitems.length; i++) {
			AutoQQDefaultData autoQQDefaultData = new AutoQQDefaultData();
			autoQQDefaultData.setQqDefaultDataId(Integer.parseInt(delitems[i]));
			autoQQDefaultData.setDeleteFlag(true);
			result = autoQQDefaultDataService.saveData(autoQQDefaultData);
		}
		return result;
	}

	/**
	 * 编辑或保存数据中心
	 * 
	 * @param autoQQDefaultData
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public int save(AutoQQDefaultData autoQQDefaultData, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		int result = 0;
		System.out.println("DefaultDataId---" + autoQQDefaultData.getQqDefaultDataId());
		String device = request.getParameter("qqDevices");
		System.out.println("qqDevices---"+device);
		// 创建时间
		// Date now = new Date();
		// System.out.println("date---"+now);
		// AutoQQDefaultData.setCreatime(stampToDate(now));
		// System.out.println("stampToDate(now)"+stampToDate(now));
		//////////// 循环遍历将数据中心中关于招呼内容的参数中文转encode上传到数据库//////////
		try {
			autoQQDefaultData.setDeleteFlag(false);
			autoQQDefaultData.setDevices(device);
			ChinaToEncoder(autoQQDefaultData);
			result = autoQQDefaultDataService.saveAllData(autoQQDefaultData);
			return result;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 跳转数据中心form表单
	 * 
	 * @param autoQQDefaultData
	 * @param codeId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "jumpForm")
	public String jumpForm(AutoQQDefaultData autoQQDefaultData, Integer codeId, Integer tab, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("DefaultDataId---" + codeId);
		System.out.println("tab------------" + tab);
		if (codeId != null) {
			autoQQDefaultData.setQqDefaultDataId(codeId);
			AutoQQDefaultData autoQQDefaultData1 = autoQQDefaultDataService.get(autoQQDefaultData);

			//////////// 循环遍历将数据中心中关于招呼内容的参数用encode转中文//////////
			EncoderToChina(autoQQDefaultData1);
			model.addAttribute("autoQQDefaultData", autoQQDefaultData1);
		}
		model.addAttribute("tab", tab);
		return "modules/lazyearn/lazyEarnManager/qqDefaultDataForm";
	}

	/**
	 * 单条数据为form表单赋值
	 * 
	 * @param autoQQDefaultData
	 * @param codeId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "singleForm")
	@ResponseBody
	public AutoQQDefaultData singleForm(AutoQQDefaultData autoQQDefaultData, Integer qqDefaultDataId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("qqDefaultDataId");
		System.out.println("id---------" + id);
		System.out.println("scodeId---" + qqDefaultDataId);
		AutoQQDefaultData autoQQDefaultData1 = new AutoQQDefaultData();
		if (qqDefaultDataId != null) {
			autoQQDefaultData.setQqDefaultDataId(qqDefaultDataId);
			autoQQDefaultData1 = autoQQDefaultDataService.get(autoQQDefaultData);
			//////////// 循环遍历将数据中心中关于招呼内容的参数用encode转中文//////////
			EncoderToChina(autoQQDefaultData1);
		}
		return autoQQDefaultData1;

	}
	/**
	 * 查找所有的主设备号
	 * 
	 * @param autoControl
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findMainDevices")
	@ResponseBody
	public List<AutoControl> findMainDevices(AutoControl autoControl, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		List<AutoControl> list = new ArrayList<AutoControl>();
		list = autoControlService.findList(autoControl);
		List<AutoControl> showList = new ArrayList<AutoControl>();
		//定义HashSet，用于去重
		HashSet<String> set = new HashSet<>();
		for(int i = 0;i<list.size();i++) {
			set.add(list.get(i).getMaindevices());
		}
		//遍历set，重新赋值
		for(String s : set) {
			System.out.println("maindevcies----"+s);
			AutoControl autoControl2 = new AutoControl();
			autoControl2.setMaindevices(s);
			showList.add(autoControl2);
		}
		return showList;
	}
	
	/****
	 * URL解码转中文
	 * 
	 * @param autoQQDefaultData
	 */
	public void EncoderToChina(AutoQQDefaultData autoQQDefaultData) {
		String qqNearbyContent = autoQQDefaultData.getQqNearbyContent().trim();
		String qqAkeyContent = autoQQDefaultData.getQqAkeyContent().trim();
		try {
			if (qqNearbyContent != null && !qqNearbyContent.equals("")) {
				autoQQDefaultData.setQqNearbyContent(URLDecoder.decode(qqNearbyContent, "utf-8"));
			}
			if (qqAkeyContent != null && !qqAkeyContent.equals("")) {
				autoQQDefaultData.setQqAkeyContent(URLDecoder.decode(qqAkeyContent, "utf-8"));
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/****
	 * 中文转URL编码
	 * 
	 * @param autoQQDefaultData
	 */
	public void ChinaToEncoder(AutoQQDefaultData autoQQDefaultData) {
		String qqNearbyContent = autoQQDefaultData.getQqNearbyContent().trim();
		String qqAkeyContent = autoQQDefaultData.getQqAkeyContent().trim();
		try {
			if (qqNearbyContent != null && !qqNearbyContent.equals("")) {
				autoQQDefaultData.setQqNearbyContent(URLEncoder.encode(qqNearbyContent, "utf-8"));
			}
			if (qqAkeyContent != null && !qqAkeyContent.equals("")) {
				autoQQDefaultData.setQqAkeyContent(URLEncoder.encode(qqAkeyContent, "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}
