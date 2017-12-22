package com.jeeplus.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.entity.VicmobPayAccount;
import com.jeeplus.modules.sys.service.VicmobPayAccountService;
import com.jeeplus.modules.sys.utils.ConfigurationFileHelper;




@Controller
@RequestMapping(value = "${adminPath}/mina/payAccount")
public class VicmobPayAccountController extends BaseController {
	
	@Autowired
	private VicmobPayAccountService vicmobPayAccountService;
	

	/**
	 * 跳转至支付页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "jump")
	public String jump(Model model, HttpServletRequest request , HttpServletResponse response) {
		HttpSession session = request.getSession();
		Integer minaId = Integer.parseInt(String.valueOf(session.getAttribute("minaId")));
		VicmobPayAccount vicmobPayAccount = new VicmobPayAccount();
		vicmobPayAccount.setMinaId(minaId);
		vicmobPayAccount = vicmobPayAccountService.get(vicmobPayAccount);
		
		String saveCertificatePath = ConfigurationFileHelper.getSaveCertificatePath();
		
		model.addAttribute("vicmobPayAccount", vicmobPayAccount);
		model.addAttribute("saveCertPath", saveCertificatePath);
		return "modules/sys/sysPayAccount";
	}
	
	
	@RequestMapping(value = "save")
	@ResponseBody
	public int save(Model model, HttpServletRequest request , HttpServletResponse response, VicmobPayAccount vicmobPayAccount) {
		try {
			if(vicmobPayAccount.getWeChatPayId() != null) {
				return vicmobPayAccountService.updateData(vicmobPayAccount);
			} else {
				return vicmobPayAccountService.saveData(vicmobPayAccount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
}