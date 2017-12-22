package com.jeeplus.modules.lazyearn.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.rest.EmailUtils;
import com.jeeplus.modules.lazyearn.rest.User;
import com.jeeplus.modules.lazyearn.rest.UserDao;
import com.jeeplus.modules.lazyearn.rest.UserDaoImpl;
import com.jeeplus.modules.lazyearn.service.AutoAccountService;
import com.jeeplus.modules.sys.utils.MySqlUtils;

import java.io.IOException;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  

/**
 * 懒赚账户管理Controller
 * @author Stephen	
 * @version 2017-9-27
 */
@Controller
@RequestMapping(value = "${adminPath}/lazyearn/Resetting")
public class ResttingController extends BaseController {
	
	/**
	 * 跳转账号管理List列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "jumpList")
	public String list(HttpServletRequest request, Model model) {
		return "modules/lazyearn/resetting";
	}
	
}  
	
