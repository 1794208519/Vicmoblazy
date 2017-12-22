package com.jeeplus.modules.lazyearn.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.lazyearn.dao.AutoCityDao;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.AutoCity;
import com.jeeplus.modules.lazyearn.service.AutoCityService;
import com.jeeplus.modules.lazyearn.service.AutoControlService;
import com.jeeplus.modules.sys.utils.MySqlUtils;

/**
 * 选择城市生成号码Controller
 * @author Stephen	
 * @version 2017-9-27
 */
@Controller
@RequestMapping(value = "${adminPath}/lazyearn/AutoCity")
public class AutoCityController extends BaseController {
	@Autowired
	private AutoCityService autoCityService;
	
	/**
	 * 城市管理List列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "jumpList")
	public String list(HttpServletRequest request, Model model) {
		return "modules/lazyearn/lazyEarnManager/myCityManagerList";
	}

	/**
	 * 分页查询账号信息
	 * @param autoCity
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findList")
	@ResponseBody
	public Page<AutoCity> findList(AutoCity autoCity,Model model, HttpServletRequest request,
			HttpServletResponse response){
		autoCity.setDeleteFlag(false);//未删除的
		String title = request.getParameter("searchFund");//搜索时带的关键字
		String selectedDevice = request.getParameter("maindevices");//选择的设备号
		System.out.println("city----------"+title);
		String str = MySqlUtils.specialStr(title);
		System.out.println("city111----------"+str);
		System.out.println("selectedDevice:"+selectedDevice);
		if(selectedDevice != null&& !selectedDevice.equals("")&& !selectedDevice.equals("null")&&!selectedDevice.equals("0")) {			
			String str1 = MySqlUtils.specialStr(selectedDevice);
			System.out.println("vicCodeText111----------selectedDevice" + str1);
			autoCity.setMaindevices(str1);
		}else {
			autoCity.setMaindevices(null);
		}
		autoCity.setCity(str);
		autoCity.setDevices(str);
		autoCity.setProvince(str);
		autoCity.setOperator(str);
		Page<AutoCity> page = autoCityService.findPage(new Page<AutoCity>(request, response), autoCity);
		return page;
	}
	
	
	/**
	 *  单个删除的方法
	 * @param cityId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public int delete(Integer cityId, Model model, HttpServletRequest request, HttpServletResponse response) {
		int result;
		AutoCity autoCity = new AutoCity();
		autoCity.setCityId(cityId);
		autoCity.setDeleteFlag(true);
		result = autoCityService.saveData(autoCity);
		return result;
	}
	
	/**
	 * 批量删除的方法
	 * @param cityId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteAll")
	@ResponseBody
	public int deleteAll(Integer cityId, Model model, HttpServletRequest request, HttpServletResponse response) {
		String[] delitems = request.getParameter("delitems").split(",");
		int result = 0;
		for (int i = 0; i < delitems.length; i++) {
			AutoCity autoCity = new AutoCity();
			autoCity.setCityId(Integer.parseInt(delitems[i]));
			autoCity.setDeleteFlag(true);
			result = autoCityService.saveData(autoCity);
			
		}
		return result;
	}
	
	/**
	 * 编辑或保存的方法
	 * @param autoCity
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public int save(AutoCity autoCity, Model model, HttpServletRequest request, HttpServletResponse response) {
		String city=request.getParameter("city");
		String province=request.getParameter("province");
		String operator=request.getParameter("operator");
		System.out.println("devices---" + autoCity.getDevices());
		int result = 0;
		System.out.println("CityId------" + autoCity.getCityId());
		System.out.println("city---" + city);
		System.out.println("operator---" + operator);
		System.out.println("devices---" + autoCity.getDevices());
		autoCity.setDeleteFlag(false);
		autoCity.setCity(city);
		autoCity.setProvince(province);
		if (autoCity.getDevices() != null&& !autoCity.getDevices().equals("")&& !autoCity.getDevices().equals("null")&&!autoCity.getDevices().equals("0")) {
//			autoCity.setDevices(autoCity.getDevices());
			if(autoCityService.isExist(autoCity.getDevices())>0) {
				return -2;
			}
		}else {
			autoCity.setDevices(null);
		}
		
		try {
			result = autoCityService.saveData(autoCity);
			System.out.println("result--" + result);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}

	}
	
	/**
	 * 编辑form表单
	 * @param autoCity
	 * @param cityId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "edit")
	@ResponseBody
	public AutoCity edit(AutoCity autoCity, Integer cityId, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("autoCity----ID:" + cityId);
		if (cityId != null) {
			autoCity.setCityId(cityId);
			autoCity = autoCityService.get(autoCity);
		}
		return autoCity;
	}

}