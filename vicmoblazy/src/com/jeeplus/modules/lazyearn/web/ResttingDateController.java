package com.jeeplus.modules.lazyearn.web;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.lazyearn.dao.ResttingDateDao;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.ResttingDate;
import com.jeeplus.modules.sys.rest.EmailUtils;
import com.jeeplus.modules.lazyearn.service.ResttingDateService;
import com.jeeplus.modules.sys.dao.UserDao;


/**
 * 懒赚账户管理Controller
 * @author Stephen	
 * @version 2017-9-27
 */
@Controller
@RequestMapping(value = "${adminPath}/lazyearn/resetdate")
public class ResttingDateController extends BaseController {
	@Autowired
	private ResttingDateService resttingDateService;
	
	/**
	 * 跳转账号管理List列表
	 * 
	 * @param request
	 * @return
	 */


//	  private static final long serialVersionUID = 1L;  
//	  
//	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
//	      
//	        UserDao userDao = UserDaoImpl.getInstance();  
//	        User user = userDao.findUserByNameOrEmail(username);  
//	        if (user == null) {  
//	            request.setAttribute("errorMsg", username + "，不存在！");  
//	            request.getRequestDispatcher("/forgotPwdUI").forward(request, response);  
//	            return;  
//	        }  
//	          
//	        // 发送重新设置密码的链接  
//	        EmailUtils.sendResetPasswordEmail(user);  
//	          
//	        request.setAttribute("sendMailMsg", "您的申请已提交成功，请查看您的"+user.getEmail()+"邮箱。");  
//	          
//	        request.getRequestDispatcher("/WEB-INF/pages/forgotPwdSuccess.jsp").forward(request, response);  
//	    }  
//	  
//	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
//	        doGet(request, response);  
//	    } 
//	    
		@RequestMapping(value = "jumpList")
		public String list(ResttingDate resttingDate,Model model, HttpServletRequest request,
				HttpServletResponse response) { 
			   String username = request.getParameter("username");  
		       String useraddress = request.getParameter("useraddress");  
		       System.out.println(username);
		       System.out.println(useraddress);
		       resttingDate.setLogin_name(username);
		       resttingDate.setEmail(useraddress);
		       List<ResttingDate> resttingDates= resttingDateService.findList(resttingDate);
		       System.out.println(resttingDates.size()+"");
		       if(resttingDates.size()>0) {
		       EmailUtils.sendResetPasswordEmail(resttingDate);  
			          
			        request.setAttribute("sendMailMsg", "您的申请已提交成功，请查看您的"+resttingDate.getEmail()+"邮箱。");  
//			          
//			        request.getRequestDispatcher("/WEB-INF/pages/forgotPwdSuccess.jsp").forward(request, response);  
		       }
			return "";
		        }
	}  
	
