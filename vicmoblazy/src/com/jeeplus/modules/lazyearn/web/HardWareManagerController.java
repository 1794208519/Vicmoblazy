package com.jeeplus.modules.lazyearn.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.HdManager;
import com.jeeplus.modules.lazyearn.service.AutoChatHistoryService;
import com.jeeplus.modules.lazyearn.service.HdManagerService;

/**
 * 硬件管理
 * @author vicmob_yf002
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/lazyearn/HardWareManager")
public class HardWareManagerController {
	
	/**
	 * 硬件管理首页
	 * @param request
	 * @param model
	 * @return
	 */
	@Autowired
	HdManagerService hdManagerService;
	@RequestMapping(value = "jumpList")
	public String list(HttpServletRequest request, Model model) {
		return "modules/lazyearn/hdmanager/DataStatistics";
	}
	
	/**
	 * 获得聊天记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findNumByDate")
	@ResponseBody
	public List<HdManager> searchNum(HttpServletRequest request,HttpServletResponse response) {
		 List<HdManager> list=hdManagerService.findNumByDate();
		 System.out.println(list);
		return list;
	}
	
}
