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
import com.jeeplus.modules.lazyearn.entity.AutoControl;
import com.jeeplus.modules.lazyearn.service.AutoControlService;
import com.jeeplus.modules.sys.utils.MySqlUtils;


/**
 * 懒赚账户管理Controller
 * @author Stephen	
 * @version 2017-9-27
 */
@Controller
@RequestMapping(value = "${adminPath}/lazyearn/AutoControl")
public class AutoControlController extends BaseController {
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
		return "modules/lazyearn/lazyEarnManager/myControlList";
	}
	
	/**
	 * 分页查询账号信息
	 * @param autoAccount
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findList")
	@ResponseBody
	public Page<AutoControl> findList(AutoControl autoControl,Model model, HttpServletRequest request,
			HttpServletResponse response){
		//autoAccount.setDeleteFlag(false);//未删除的
		String title = request.getParameter("searchFund");//搜索时带的关键字
		System.out.println("account----------"+title);
		String str = MySqlUtils.specialStr(title);
		System.out.println("account111----------"+str);
		autoControl.setMaindevices(str.trim());
		autoControl.setPartdevices(str.trim());
		Page<AutoControl> page = autoControlService.findPage(new Page<AutoControl>(request, response), autoControl);
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
	public int save(AutoControl autoControl, Model model, HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		System.out.println("controlId------" + autoControl.getControlId());
		System.out.println("maindevices---" + autoControl.getMaindevices());
		System.out.println("partdevices---" + autoControl.getPartdevices());
	    if(autoControl.getControlId()!=null&&autoControl.getControlId() != 0) {
	    	result = autoControlService.saveData(autoControl);
			System.out.println("result--" + result);
			return result;
	    }else {
	    	if(autoControlService.isExist(autoControl.getPartdevices())==0) {
				 try {
						result = autoControlService.saveData(autoControl);
						System.out.println("result--" + result);
						return result;
					 } catch (Exception e) {
						// TODO: handle exception
						return -1;
					 } 
		   }	
	    }
		return -2;
	
	}
	
	
	/**
	 * 单个删除的方法
	 * @param accountId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public int delete(Integer controlId, Model model, HttpServletRequest request , HttpServletResponse response){
		int result;
		AutoControl autoControl = new AutoControl();
		System.out.println(controlId);
		autoControl.setControlId(controlId);
		result = autoControlService.delcontrolId(autoControl);
		return result;
	}
	
	/**
	 * 批量删除的方法
	 * @param accountId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteAll")
	@ResponseBody
	public int deleteAll(Integer controlId, Model model, HttpServletRequest request , HttpServletResponse response){
		String[] delitems = request.getParameter("delitems").split(",");
		int result = 0;
		for(int i =0;i<delitems.length;i++){
			AutoControl autoControl = new AutoControl();
			autoControl.setControlId(Integer.parseInt(delitems[i]));
		
			result = autoControlService.delcontrolId(autoControl);
		}
		return result;
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
	public AutoControl edit(AutoControl autoControl, Integer controlId, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("autoAccount----ID:" + controlId);
		if (controlId != null) {
			autoControl.setControlId(controlId);
			autoControl = autoControlService.get(autoControl);
		}
		return autoControl;
	}
	/**
	 * 判断设备号是否存在
	 * @param autoAccount
	 * @param accountId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "isExist")
	@ResponseBody
	public Boolean isExist(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String devices=request.getParameter("devices");//搜索时带的关键字
		System.out.println("devices======="+devices );
		if (devices != null) {
		if(autoControlService.isExist(devices)>0) {
			return true;
		}else {
			return false;
		}
		}
		return false;
	}
}