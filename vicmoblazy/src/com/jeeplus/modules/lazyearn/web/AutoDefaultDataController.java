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
import com.jeeplus.modules.lazyearn.service.AutoControlService;
import com.jeeplus.modules.lazyearn.service.AutoDefaultDataService;
import com.jeeplus.modules.sys.utils.MySqlUtils;


/**
 * 群控系统携带的参数Controller
 * @author Stephen	
 * @version 2017-9-27
 */
@Controller
@RequestMapping(value = "${adminPath}/lazyearn/AutoWXDefaultData")
public class AutoDefaultDataController extends BaseController {
	@Autowired
	private AutoDefaultDataService autoDefaultDataService;
	@Autowired
	private AutoControlService autoControlService;
	/**
	 * 跳转数据交互中心List列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "jumpallList")
	public String allList(HttpServletRequest request, Model model) {
		return "modules/lazyearn/lazyEarnManager/DefaultData";
	}
	
	@RequestMapping(value = "jumpList")
	public String list(HttpServletRequest request, Model model) {
		return "modules/lazyearn/lazyEarnManager/wxDefaultDataList";
	}
	/**
	 * 分页查询微信数据信息
	 * 
	 * @param autoDefaultData
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findList")
	@ResponseBody
	public Page<AutoDefaultData> findList(AutoDefaultData autoDefaultData, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		autoDefaultData.setDeleteFlag(false);
		String title = request.getParameter("wxSearchFund");// 搜索时带的关键字
		System.out.println("defaultData----------" + title);
		String str = MySqlUtils.specialStr(title);
		System.out.println("defaultData111----------" + str);
		autoDefaultData.setDevices(str);
		Page<AutoDefaultData> page = autoDefaultDataService.findPage(new Page<AutoDefaultData>(request, response),
				autoDefaultData);
		System.out.println("___pageList---" + page.getList().size());
		//////////// 循环遍历将数据中心中关于招呼内容的参数用encode转中文//////////
		if (page.getList().size() > 0 && page.getList() != null) {
			for (int i = 0; i < page.getList().size(); i++) {
				AutoDefaultData autoDefaultData1 = page.getList().get(i);
				EncoderToChina(autoDefaultData1);
				page.getList().set(i, autoDefaultData1);
			}
		}
		return page;
	}
	
	/**
	 * 单个删除的方法
	 * 
	 * @param defaultDataId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public int delete(Integer defaultDataId, Model model, HttpServletRequest request, HttpServletResponse response) {
		int result;
		AutoDefaultData autoDefaultData = new AutoDefaultData();
		autoDefaultData.setDefaultDataId(defaultDataId);
		autoDefaultData.setDeleteFlag(true);
		result = autoDefaultDataService.saveData(autoDefaultData);
		return result;
	}

	/**
	 * 批量删除的方法
	 * 
	 * @param defaultDataId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteAll")
	@ResponseBody
	public int deleteAll(Integer defaultDataId, Model model, HttpServletRequest request, HttpServletResponse response) {
		String[] delitems = request.getParameter("delitems").split(",");
		int result = 0;
		for (int i = 0; i < delitems.length; i++) {
			AutoDefaultData autoDefaultData = new AutoDefaultData();
			autoDefaultData.setDefaultDataId(Integer.parseInt(delitems[i]));
			autoDefaultData.setDeleteFlag(true);
			result = autoDefaultDataService.saveData(autoDefaultData);
		}
		return result;
	}

	/**
	 * 编辑或保存数据中心
	 * 
	 * @param autoDefaultData
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public int save(AutoDefaultData autoDefaultData, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		int result = 0;
		System.out.println("DefaultDataId---" + autoDefaultData.getDefaultDataId());
		
		String device = request.getParameter("wxDevice");
		System.out.println("wxDevices---"+device);
		// 创建时间
		// Date now = new Date();
		// System.out.println("date---"+now);
		// autoDefaultData.setCreatime(stampToDate(now));
		// System.out.println("stampToDate(now)"+stampToDate(now));
		//////////// 循环遍历将数据中心中关于招呼内容的参数中文转encode上传到数据库//////////
		try {
			autoDefaultData.setDeleteFlag(false);
			autoDefaultData.setDevices(device);
			ChinaToEncoder(autoDefaultData);
			result = autoDefaultDataService.saveAllData(autoDefaultData);
			return result;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 跳转数据中心form表单
	 * 
	 * @param autoDefaultData
	 * @param codeId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "jumpForm")
	public String jumpForm(AutoDefaultData autoDefaultData, Integer codeId, Integer tab, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("DefaultDataId---" + codeId);
		System.out.println("tab------------" + tab);
		if (codeId != null) {
			autoDefaultData.setDefaultDataId(codeId);
			AutoDefaultData autoDefaultData1 = autoDefaultDataService.get(autoDefaultData);

			//////////// 循环遍历将数据中心中关于招呼内容的参数用encode转中文//////////
			EncoderToChina(autoDefaultData1);
			model.addAttribute("autoDefaultData", autoDefaultData1);
		}
		model.addAttribute("tab", tab);
		return "modules/lazyearn/lazyEarnManager/wxDefaultDataForm";
	}

	/**
	 * 单条数据为form表单赋值
	 * 
	 * @param autoDefaultData
	 * @param defaultDataId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "singleForm")
	@ResponseBody
	public AutoDefaultData singleForm(AutoDefaultData autoDefaultData, Integer defaultDataId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("defaultDataId");
		System.out.println("id---------" + id);
		System.out.println("scodeId---" + defaultDataId);
		AutoDefaultData autoDefaultData1 = new AutoDefaultData();
		if (defaultDataId != null) {
			autoDefaultData.setDefaultDataId(defaultDataId);
			autoDefaultData1 = autoDefaultDataService.get(autoDefaultData);
			//////////// 循环遍历将数据中心中关于招呼内容的参数用encode转中文//////////
			EncoderToChina(autoDefaultData1);
		}
		return autoDefaultData1;

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
	 * @param autoDefaultData
	 */
	public void EncoderToChina(AutoDefaultData autoDefaultData) {
		String wxNearbyContent = autoDefaultData.getWxNearbyContent().trim();
		String wxAkeyContent = autoDefaultData.getWxAkeyContent().trim();
		String wxCircleContent = autoDefaultData.getWxCircleContent().trim();
		String wxDriftbottleContent = autoDefaultData.getWxDriftbottleContent().trim();
		try {
			if (wxNearbyContent != null && !wxNearbyContent.equals("")) {
				autoDefaultData.setWxNearbyContent(URLDecoder.decode(wxNearbyContent, "UTF-8"));
			}
			if (wxAkeyContent != null && !wxAkeyContent.equals("")) {
				autoDefaultData.setWxAkeyContent(URLDecoder.decode(wxAkeyContent, "utf-8"));
			}
			if (wxCircleContent != null && !wxCircleContent.equals("")) {
				autoDefaultData.setWxCircleContent(URLDecoder.decode(wxCircleContent, "utf-8"));
			}
			if (wxDriftbottleContent != null && !wxDriftbottleContent.equals("")) {
				autoDefaultData.setWxDriftbottleContent(URLDecoder.decode(wxDriftbottleContent, "utf-8"));
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/****
	 * 中文转URL编码
	 * 
	 * @param autoDefaultData
	 */
	public void ChinaToEncoder(AutoDefaultData autoDefaultData) {
		String wxNearbyContent = autoDefaultData.getWxNearbyContent().trim();
		String wxAkeyContent = autoDefaultData.getWxAkeyContent().trim();
		String wxCircleContent = autoDefaultData.getWxCircleContent().trim();
		String wxDriftbottleContent = autoDefaultData.getWxDriftbottleContent().trim();
		try {
			    if (wxNearbyContent != null && !wxNearbyContent.equals("")) {
				    autoDefaultData.setWxNearbyContent(URLEncoder.encode(wxNearbyContent, "UTF-8"));
				if (wxAkeyContent != null && !wxAkeyContent.equals("")) {
					autoDefaultData.setWxAkeyContent(URLEncoder.encode(wxAkeyContent, "utf-8"));
				}
				if (wxCircleContent != null && !wxCircleContent.equals("")) {
					autoDefaultData.setWxCircleContent(URLEncoder.encode(wxCircleContent, "utf-8"));
				}
				if (wxDriftbottleContent != null && !wxDriftbottleContent.equals("")) {
					autoDefaultData.setWxDriftbottleContent(URLEncoder.encode(wxDriftbottleContent, "utf-8"));
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}