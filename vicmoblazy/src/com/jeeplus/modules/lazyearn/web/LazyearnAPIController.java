package com.jeeplus.modules.lazyearn.web;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.authc.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.HTMLSpiritUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.AutoChatHistory;
import com.jeeplus.modules.lazyearn.entity.AutoCity;
import com.jeeplus.modules.lazyearn.entity.AutoControl;
import com.jeeplus.modules.lazyearn.entity.AutoData;
import com.jeeplus.modules.lazyearn.entity.AutoDefaultData;
import com.jeeplus.modules.lazyearn.entity.AutoDevices;
import com.jeeplus.modules.lazyearn.entity.VicCodeText;
import com.jeeplus.modules.lazyearn.service.AutoAccountService;
import com.jeeplus.modules.lazyearn.service.AutoChatHistoryService;
import com.jeeplus.modules.lazyearn.service.AutoCityService;
import com.jeeplus.modules.lazyearn.service.AutoControlService;
import com.jeeplus.modules.lazyearn.service.AutoDataService;
import com.jeeplus.modules.lazyearn.service.AutoDefaultDataService;
import com.jeeplus.modules.lazyearn.service.AutoDevicesService;
import com.jeeplus.modules.lazyearn.service.VicCodeTextService;
import com.jeeplus.modules.sys.utils.ConfigurationFileHelper;
/**
 * 懒赚接口Controller
 * @author Stephen	
 * @version 2017-9-27
 */
@Controller
@RequestMapping(value = "lazyearn-api")
public class LazyearnAPIController extends BaseController {
	@Autowired
	private AutoDataService autoDataService;
	@Autowired
	private AutoAccountService autoAccountService;
	@Autowired
	private AutoCityService autoCityService;
	@Autowired
	private VicCodeTextService vicCodeTextService;
	@Autowired
	private AutoDefaultDataService autoDefaultDataService;
	@Autowired
	private AutoDevicesService autoDevicesService;
	@Autowired
	private AutoControlService autoControlService;
	@Autowired
	private AutoChatHistoryService autoChatHistoryService;
	
	private String savePicPath = ConfigurationFileHelper.getSavePicPath();
	
	/**
	 * 1.申请删除地点(用)
	 * @param request
	 * @param dataId
	 */
	@RequestMapping(value ="autoData/deleteAddress")
	@ResponseBody
	public AjaxJson deleteAddress(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String dataId = request.getParameter("dataId");
		if("".equals(dataId)||!isNumeric(dataId)||dataId==null){
			ajax.setSuccess(false);
			ajax.setMsg("操作失败");
			ajax.setErrorCode("0");
			return ajax;
		}
		AutoData autoData = new AutoData();
		autoData.setDataId(Integer.parseInt(dataId));
		autoDataService.delete(autoData);
		return ajax;
	}
	/**
	 * 2.添加模拟地点(用)
	 * @param request
	 * @param devices
	 */
	@RequestMapping(value ="autoData/insertAddress")
	@ResponseBody
	public AjaxJson insertAddress(HttpServletRequest request) {	
		String address = request.getParameter("address");
		String hello = request.getParameter("hello");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String devices = request.getParameter("devices");
		String lac = request.getParameter("lac");
		String cid = request.getParameter("cid");
		String mnc = request.getParameter("mnc");
		
		AjaxJson ajax = new AjaxJson();
		if(!isNumeric(lac)||!isNumeric(cid)||!isNumeric(mnc)){
			ajax.setSuccess(false);
			ajax.setMsg("传参有误");
			ajax.setErrorCode("0");
			return ajax;
		}
		AutoData autoData = new AutoData();
		autoData.setDevices(devices);
		autoData.setAddress(address);
		autoData.setHello(hello);
		autoData.setLongitude(longitude);
		autoData.setLatitude(latitude);
		autoData.setLac(Integer.parseInt(lac));
		autoData.setCid(Integer.parseInt(cid));
		autoData.setMnc(Integer.parseInt(mnc));	
		Integer id =  autoDataService.saveAutoData(autoData);
		if(id>0){
			ajax.setMsg("product successfully created");		
			return ajax;
		}
		ajax.setSuccess(false);
		ajax.setMsg("操作失败");
		ajax.setErrorCode("0");		
		return ajax;
	}
	
	/**
	 * 3.查询模拟地点(用)
	 * 说明：查询设备号为空 或者设备号为所传设备号
	 * @param request
	 * @param devices
	 */
	@RequestMapping(value ="autoData/selectAddress")
	@ResponseBody
	public AjaxJson selectAddress(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String devices = request.getParameter("devices");
		List<AutoData> list = null;
		list = autoDataService.findAutoDataByDevices(devices);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("auto_data", list);
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 4.查询设备地址(用)
	 * @param request
	 * @param devices
	 */
	@RequestMapping(value ="autoData/selectDevicesAddress")
	@ResponseBody
	public AjaxJson selectDevicesAddress(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String devices = request.getParameter("devices");
		List<AutoData> list = null;
		if(devices==null){
			ajax.setSuccess(false);
			ajax.setMsg("请传入参数");
			ajax.setErrorCode("0");
			return ajax;
		}
		AutoData autoData = new AutoData();
		autoData.setDevices(devices);
		list = autoDataService.findList(autoData);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("auto_data", list);
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 5.更新地址(用)
	 * 请求参数说明根据id更新设备号。
	 * @param request
	 * @param devices,dataId
	 */
	@RequestMapping(value ="autoData/updateAddress")
	@ResponseBody
	public AjaxJson pdateAddress(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String dataId = request.getParameter("dataId");
		String devices = request.getParameter("devices");
		if("".equals(dataId)||!isNumeric(dataId)||dataId==null||devices==null){
			ajax.setSuccess(false);
			ajax.setMsg("传参错误");
			ajax.setErrorCode("0");
			return ajax;
		}
				
		AutoData autoData = new AutoData();
		autoData.setDataId(Integer.parseInt(dataId));
		autoData.setDevices(devices);
		Integer id = autoDataService.saveAutoData(autoData);
		if(id>0){
			return ajax;	
		}
		ajax.setSuccess(false);
		ajax.setMsg("操作失败");
		ajax.setErrorCode("0");
		return ajax;
	}
	
	/**
	 *6.根据id删除城市管理数据记录(用)
	 * @param request
	 * @param cityId
	 */
	@RequestMapping(value ="autoCity/DeleteCityOperator")
	@ResponseBody
	public AjaxJson DeleteCityOperator(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String cityId = request.getParameter("cityId");
		if("".equals(cityId)||!isNumeric(cityId)||cityId==null){
			ajax.setSuccess(false);
			ajax.setMsg("操作失败");
			ajax.setErrorCode("0");
			return ajax;
		}
		AutoCity autoCity = new AutoCity();
		autoCity.setCityId(Integer.parseInt(cityId));;
		autoCityService.delete(autoCity);
		return ajax;
	}
	
	/**
	 *7.懒懒申请删除智能回复批量导入的数据(用)
	 * @param request
	 * @param keyword,text
	 */
	@RequestMapping(value ="vicCodeText/deleteIntelligent")
	@ResponseBody
	public AjaxJson deleteIntelligent(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String keyword  = request.getParameter("keyword");
		String text  = request.getParameter("text");
		String codeTextId = request.getParameter("codeTextId");
		
		if(keyword==null||text==null || codeTextId == null){
			ajax.setSuccess(false);
			ajax.setMsg("操作失败");
			ajax.setErrorCode("0");
			return ajax;
		}
		VicCodeText vicCodeText = new VicCodeText();
		vicCodeText.setCodeTextId(Integer.parseInt(codeTextId));
		vicCodeText.setKeyword(keyword);
		vicCodeText.setText(text);
		Integer idDelete = vicCodeTextService.deleteByCodeTextId(vicCodeText);
//		Integer idDelete = vicCodeTextService.deleteByKeword(vicCodeText);
		if(idDelete>0){
			
		}else if(idDelete==0){
			ajax.setMsg("无对应数据可操作");
		}else{
			ajax.setSuccess(false);
			ajax.setMsg("操作失败");
			ajax.setErrorCode("0");
		}
		return ajax;
	}
	
	/**
	 *8.查询智能回复批量导入的数据(用)
	 * @param request
	 * @param VicCodeText
	 */
	@RequestMapping(value ="vicCodeText/selectIntelligent")
	@ResponseBody
	public AjaxJson selectIntelligent(HttpServletRequest request,VicCodeText vicCodeText) {
		String keyword = request.getParameter("keyword");
		String text = request.getParameter("text");
		String devices = request.getParameter("devices");
		String pager = request.getParameter("pager");
		String row = request.getParameter("row");
		if (keyword!=null&&keyword!="null") {
			try {
				keyword=new String(keyword.getBytes("iso-8859-1"), "utf-8");
				System.out.println(keyword.length());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if (pager==null||"".equals(pager)) {
			pager="0";
		}
		if (row==null||"".equals(row)) {
			row="20";
		}
		AjaxJson ajax = new AjaxJson();
		vicCodeText.setKeyword(keyword);
		vicCodeText.setText(text);
		vicCodeText.setDevices(devices);
		vicCodeText.setPager(Integer.parseInt(pager)*Integer.parseInt(row));
		vicCodeText.setRow(Integer.parseInt(row));
		List<VicCodeText> list=new ArrayList<VicCodeText>();
		if(keyword!=null&&keyword.length()>=2){
			list = vicCodeTextService.findlikeList(vicCodeText);
		}else{
			list = vicCodeTextService.findList(vicCodeText);
		}
		
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("vic_codetext", list);
		ajax.setBody(body);
		return ajax;
	}
	/**
	 *8.查询智能回复模糊查询
	 * @param request
	 * @param VicCodeText
	 */
	@RequestMapping(value ="vicCodeText/selectlikeIntelligent")
	@ResponseBody
	public AjaxJson selectlikeIntelligent(HttpServletRequest request,VicCodeText vicCodeText) {
		String keyword = request.getParameter("keyword");
		String text = request.getParameter("text");
		String devices = request.getParameter("devices");
		String pager = request.getParameter("pager");
		String row = request.getParameter("row");
		if (keyword!=null&&keyword!="null") {
			try {
				keyword=new String(keyword.getBytes("iso-8859-1"), "utf-8");
				System.out.println(keyword.length());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if (pager==null||"".equals(pager)) {
			pager="0";
		}
		if (row==null||"".equals(row)) {
			row="20";
		}
		AjaxJson ajax = new AjaxJson();
		vicCodeText.setKeyword(keyword);
		vicCodeText.setText(text);
		vicCodeText.setDevices(devices);
		vicCodeText.setPager(Integer.parseInt(pager)*Integer.parseInt(row));
		vicCodeText.setRow(Integer.parseInt(row));
		List<VicCodeText> list=new ArrayList<VicCodeText>();
			list = vicCodeTextService.findlikeList(vicCodeText);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("vic_codetext", list);
		ajax.setBody(body);
		return ajax;
	}
	/**
	 *8.查询智能回复精确查询
	 * @param request
	 * @param VicCodeText
	 */
	@RequestMapping(value ="vicCodeText/selectnolikeIntelligent")
	@ResponseBody
	public AjaxJson selectnolikeIntelligent(HttpServletRequest request,VicCodeText vicCodeText) {
		String keyword = request.getParameter("keyword");
		String text = request.getParameter("text");
		String devices = request.getParameter("devices");
		String pager = request.getParameter("pager");
		String row = request.getParameter("row");
		if (keyword!=null&&keyword!="null") {
			try {
				keyword=new String(keyword.getBytes("iso-8859-1"), "utf-8");
				System.out.println(keyword.length());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if (pager==null||"".equals(pager)) {
			pager="0";
		}
		if (row==null||"".equals(row)) {
			row="20";
		}
		AjaxJson ajax = new AjaxJson();
		vicCodeText.setKeyword(keyword);
		vicCodeText.setText(text);
		vicCodeText.setDevices(devices);
		vicCodeText.setPager(Integer.parseInt(pager)*Integer.parseInt(row));
		vicCodeText.setRow(Integer.parseInt(row));
		List<VicCodeText> list=new ArrayList<VicCodeText>();
			list = vicCodeTextService.findList(vicCodeText);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("vic_codetext", list);
		ajax.setBody(body);
		return ajax;
	}
	/**
	 * 9.赚赚选择城市运营商(用)
	 * @param request
	 * @param devices
	 */
	@RequestMapping(value ="autoCity/earnSelectCityOperators")
	@ResponseBody
	public AjaxJson earnSelectCityOperators(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String devices = request.getParameter("devices");
		List<AutoCity> list = null;
		if(devices==null){
			ajax.setSuccess(false);
			ajax.setMsg("传参有误");
			ajax.setErrorCode("0");
			return ajax;
		}
		AutoCity autoCity = new AutoCity();
		autoCity.setDevices(devices);
		list = autoCityService.findList(autoCity);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("auto_city", list);
		ajax.setBody(body);
		return ajax;
	}	

	/**
	 * 10.赚赚选择城市运营商(用)
	 * 说明：查询设备号为空 或者设备号为所传设备号
	 * @param request
	 * @param devices
	 */
	@RequestMapping(value ="autoCity/selectCityOperator")
	@ResponseBody
	public AjaxJson selectCityOperator(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String devices = request.getParameter("devices");
		List<AutoCity> list = autoCityService.findAutoCityByDevices(devices);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("auto_city", list);
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 11. 插入城市运营商(用）
	 * @param request
	 * @param city,operator
	 */
	@RequestMapping(value ="autoCity/insertCityOperator")
	@ResponseBody
	public AjaxJson insertCityOperator(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String city = request.getParameter("city");
		String operator = request.getParameter("operator");
		if("".equals(city)||city==null||"".equals(operator)||operator==null){
			ajax.setSuccess(false);
			ajax.setMsg("请传入参数");
			ajax.setErrorCode("0");
			return ajax;
		}
		AutoCity autoCity = new AutoCity();
		autoCity.setCity(city);
		autoCity.setOperator(operator);
		Integer id = autoCityService.saveAutoCity(autoCity);
		if(id>0){
			return ajax;	
		}
		ajax.setSuccess(false);
		ajax.setMsg("操作失败");
		ajax.setErrorCode("0");
		return ajax;
	}	
	
	/**
	 * 12.更新城市运营商(用)
	 * 说明：根据主键ID更新设备号
	 * @param request
	 * @param cityId,devices
	 */
	@RequestMapping(value ="autoCity/updateCityOperator")
	@ResponseBody
	public AjaxJson UpdateCityOperator(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String cityId = request.getParameter("cityId");
		String devices = request.getParameter("devices");
		if("".equals(cityId)||!isNumeric(cityId)||cityId==null||devices==null){
			ajax.setSuccess(false);
			ajax.setMsg("传参错误");
			ajax.setErrorCode("0");
			return ajax;
		}
				
		AutoCity autoCity = new AutoCity();
		autoCity.setCityId(Integer.parseInt(cityId));
		autoCity.setDevices(devices);
		Integer id = autoCityService.saveAutoCity(autoCity);
		if(id>0){
			return ajax;	
		}
		ajax.setSuccess(false);
		ajax.setMsg("操作失败");
		ajax.setErrorCode("0");
		return ajax;
	}		
	
	/**
	 * 13.插入数据中心数据(用)
	 * @param request
	 * @param AutoDefaultData
	 */
	@RequestMapping(value ="autoDefaultData/insertDefaultData")
	@ResponseBody
	public AjaxJson insertDefaultData(HttpServletRequest request) {	
		String devices = request.getParameter("devices");
		String wx_nearby_num = request.getParameter("wx_nearby_num");
		String wx_friend_num = request.getParameter("wx_friend_num");
		String wx_groupfriend_num = request.getParameter("wx_groupfriend_num");
		String wx_public_num = request.getParameter("wx_public_num");
		String wx_driftbottle_num = request.getParameter("wx_driftbottle_num");
		String wx_circle_num = request.getParameter("wx_circle_num");
		String wx_nearby_content = request.getParameter("wx_nearby_content");
		String wx_public_index = request.getParameter("wx_public_index");
		String wx_publicfriend_num = request.getParameter("wx_publicfriend_num");
		String wx_driftbottle_content = request.getParameter("wx_driftbottle_content");
		String wx_circle_content = request.getParameter("wx_circle_content");
		String wx_akey_content = request.getParameter("wx_akey_content");
		/*String qq_nearby_num = request.getParameter("qq_nearby_num");
		String qq_friend_num = request.getParameter("qq_friend_num");
		String qq_groupfriend_num = request.getParameter("qq_groupfriend_num");
		String qq_nearby_content = request.getParameter("qq_nearby_content");
		String qq_akey_content = request.getParameter("qq_akey_content");*/
		AutoDefaultData autoDefaultData = new AutoDefaultData();
		autoDefaultData.setWxNearbyNum(wx_nearby_num);
		autoDefaultData.setWxFriendNum(wx_friend_num);
		autoDefaultData.setWxGroupfriendNum(wx_groupfriend_num);
		autoDefaultData.setWxPublicNum(wx_public_num);
		autoDefaultData.setWxDriftbottleNum(wx_driftbottle_num);
		autoDefaultData.setWxCircleNum(wx_circle_num);
		autoDefaultData.setWxNearbyContent(wx_nearby_content);
		autoDefaultData.setWxPublicIndex(wx_public_index);
		autoDefaultData.setWxPublicfriendNum(wx_publicfriend_num);
		autoDefaultData.setWxDriftbottleContent(wx_driftbottle_content);
		autoDefaultData.setWxCircleContent(wx_circle_content);
		autoDefaultData.setWxAkeyContent(wx_akey_content);
		/*autoDefaultData.setQqNearbyNum(qq_nearby_num);		
		autoDefaultData.setQqFriendNum(qq_friend_num);
		autoDefaultData.setQqGroupfriendNum(qq_groupfriend_num);
		autoDefaultData.setQqNearbyContent(qq_nearby_content);
		autoDefaultData.setQqAkeyContent(qq_akey_content);*/
		AjaxJson ajax = new AjaxJson();
		if("".equals(devices)||devices==null){
			ajax.setSuccess(false);
			ajax.setMsg("传参错误");
			ajax.setErrorCode("0");
			return ajax;
		}
		List<AutoDefaultData> list = new ArrayList<AutoDefaultData>();
		autoDefaultData.setDevices(devices);
		list = autoDefaultDataService.findList(autoDefaultData);
		//插入与更新一体，每个设备号有且只有一条数据，没数据插入，有数据更新。
		try {
			if(list!=null && list.size()>=1){
				autoDefaultData.setDefaultDataId(list.get(0).getDefaultDataId());
				autoDefaultDataService.saveAutoDefaultData(autoDefaultData);
			}else if(list!=null && list.size()==0){
				autoDefaultDataService.saveAutoDefaultData(autoDefaultData);
			}else{
				ajax.setSuccess(false);
				ajax.setMsg("操作失败");
				ajax.setErrorCode("0");
			}
		} catch (Exception e) {
			ajax.setSuccess(false);
			ajax.setMsg("传参有误");
			ajax.setErrorCode("0");
			//throw new RuntimeException("传参有误");
		}
		return ajax;
	}	
	
	/**
	 * 14. 查询qq微信数据中心数据(用)
	 * @param request
	 * @param AutoDefaultData
	 */
	@RequestMapping(value ="autoDefaultData/selectDefaultData")
	@ResponseBody
	public AjaxJson selectDefaultData(HttpServletRequest request,AutoDefaultData autoDefaultData) {	
		AjaxJson ajax = new AjaxJson();
		String devices  = request.getParameter("devices");	
		if("".equals(devices)||devices==null){
			ajax.setSuccess(false);
			ajax.setMsg("传入参数有误");
			ajax.setErrorCode("0");
			return ajax;
		}
		List<AutoDefaultData> list = new ArrayList<AutoDefaultData>();
		autoDefaultData.setDevices(devices);
		list = autoDefaultDataService.findList(autoDefaultData);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("auto_default_data", list);
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 *15.懒懒添加智能回复批量导入的数据(用)
	 *说明：插入与更新一体，每个设备号有且只有一条数据，没数据插入，有数据更新。
	 * @param request
	 * @param keyword,text
	 */
	@RequestMapping(value ="vicCodeText/insertIntelligent")
	@ResponseBody
	public AjaxJson insertIntelligent(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String keyword  = request.getParameter("keyword");
		String text  = request.getParameter("text");
		String creatime  = request.getParameter("creatime");
		String type  = request.getParameter("type");		
		String devices  = request.getParameter("devices");		
		if("".equals(devices)||devices==null||"".equals(keyword)||keyword==null||text==null||creatime==null||type==null){
			ajax.setSuccess(false);
			ajax.setMsg("传入参数有误");
			ajax.setErrorCode("0");
			return ajax;
		}
		VicCodeText vicCodeText = new VicCodeText();
		vicCodeText.setKeyword(keyword);
		vicCodeText.setText(text);
		vicCodeText.setCreatime(creatime);
		vicCodeText.setType(type);
		vicCodeText.setDevices(devices);
		//插入与更新一体，每个设备号有且只有一条数据，没数据插入，有数据更新。
		//插入，每个设备号最多对应五条数据。
		VicCodeText codeText = new VicCodeText();
		codeText.setDevices(devices);
		codeText.setKeyword(keyword);
		codeText.setPager(0);
		codeText.setRow(5);
		List<VicCodeText> list = vicCodeTextService.findList(codeText);
		int id =0;
		if(list!=null && list.size()>=0 && list.size() < 5){
//			vicCodeText.setCodeTextId(list.get(0).getCodeTextId());
			id = vicCodeTextService.saveVicCodeText(vicCodeText);
		}else{
			ajax.setSuccess(false);
			ajax.setMsg("操作失败");
			ajax.setErrorCode("0");
		}
		if(id>0){
			return ajax;
		}
		ajax.setSuccess(false);
		ajax.setMsg("操作失败");
		ajax.setErrorCode("0");
		return ajax;
	}
	
	/**
	 * 16.获取所有账户和锁定设备地址
	 * @param request
	 * @param devices
	 */
	@RequestMapping(value ="autoAccount/selectAccount")
	@ResponseBody
	public AjaxJson selectAccount(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String devices = request.getParameter("devices");
		List<AutoAccount> list = autoAccountService.findAutoAccountByDevices(devices);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("auto_account", list);
		ajax.setBody(body);
		return ajax;
	}
	/**
	 * 17.获取该设备的所有账户
	 * @param request
	 * @param devices
	 */
	@RequestMapping(value ="autoAccount/findAccountByDevices")
	@ResponseBody
	public AjaxJson findAccountByDevices(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String devices = request.getParameter("devices");
		List<AutoAccount> list = null;
		if(devices==null){
			ajax.setSuccess(false);
			ajax.setMsg("请传入参数");
			ajax.setErrorCode("0");
			return ajax;
		}
		AutoAccount autoAccount = new AutoAccount();
		autoAccount.setDevices(devices);
		list = autoAccountService.findList(autoAccount);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("auto_account", list);
		ajax.setBody(body);
		return ajax;
	}
	/**
	 * 18.添加账户
	 * @param request
	 * @param account,password
	 */
	@RequestMapping(value ="autoAccount/addAccountByAccount")
	@ResponseBody
	public AjaxJson addAccountByAccount(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		if(account==null || "".equals(account)||password==null || "".equals(password)){
			ajax.setSuccess(false);
			ajax.setMsg("请传入参数");
			ajax.setErrorCode("0");
			return ajax;
		}
		AutoAccount autoAccount = new AutoAccount();
		autoAccount.setAccount(account);
		autoAccount.setPassword(password);
		int id  = autoAccountService.saveAutoAccount(autoAccount);
		if(id>0){
			ajax.setMsg("添加账户成功");
			return ajax;
		}
		ajax.setSuccess(false);
		ajax.setMsg("操作失败");
		ajax.setErrorCode("0");
		return ajax;
	}
	
	/**
	 * 19.更新账户
	 * @param request
	 * @param accountId,devices
	 */
	@RequestMapping(value ="autoAccount/updateAccount")
	@ResponseBody
	public AjaxJson updateAccount(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String accountId = request.getParameter("accountId");
		String devices = request.getParameter("devices");
		if("".equals(accountId)||!isNumeric(accountId)||accountId==null||devices==null){
			ajax.setSuccess(false);
			ajax.setMsg("传参错误");
			ajax.setErrorCode("0");
			return ajax;
		}
				
		AutoAccount autoAccount = new AutoAccount();
		autoAccount.setAccountId(Integer.parseInt(accountId));
		autoAccount.setDevices(devices);
		Integer id = autoAccountService.saveAutoAccount(autoAccount);
		if(id>0){
			return ajax;	
		}
		ajax.setSuccess(false);
		ajax.setMsg("操作失败");
		ajax.setErrorCode("0");
		return ajax;
	}
	/**
	 * 20.删除账户
	 * 说明：根据账户主键ID删除账户
	 * @param request
	 * @param cityId,devices
	 */
	@RequestMapping(value ="autoAccount/deleteAccount")
	@ResponseBody
	public AjaxJson deleteAccount(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String accountId = request.getParameter("accountId");
		if("".equals(accountId)||!isNumeric(accountId)||accountId==null){
			ajax.setSuccess(false);
			ajax.setMsg("传参错误");
			ajax.setErrorCode("0");
			return ajax;
		}
		
		AutoAccount autoAccount = new AutoAccount();
		autoAccount.setAccountId(Integer.parseInt(accountId));
		autoAccountService.delete(autoAccount);
		return ajax;
	}
	
	/**
	 * 21.添加白名单并返回这条记录
	 * 说明：同一个设备号数据库有数据就更新，没有就插入
	 * @param request
	 * @param file，devices
	 */
	@RequestMapping(value ="autoDevices/addDevicesByFile")
	@ResponseBody
	public AjaxJson addDevicesByFile(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String file = request.getParameter("file");
		String devices = request.getParameter("devices");
		if(file==null || "".equals(file)||devices==null){
			ajax.setSuccess(false);
			ajax.setMsg("请传入参数");
			ajax.setErrorCode("0");
			return ajax;
		}
		AutoDevices device = new AutoDevices();	
		device.setDevices(devices);
		List<AutoDevices> list = autoDevicesService.findList(device);
		int id = 0;
		if(list!=null && list.size()>0){
			AutoDevices newDevice = new AutoDevices();	
			newDevice.setDevicesId(list.get(0).getDevicesId());
			newDevice.setFile(file);
			id = autoDevicesService.saveAutoDevices(newDevice);
		}else{		
			AutoDevices autoDevices = new AutoDevices();
			autoDevices.setFile(file);
			autoDevices.setDevices(devices);
			id  = autoDevicesService.saveAutoDevices(autoDevices);
		}
		
		if(id>0){
			AutoDevices device1 = autoDevicesService.get(String.valueOf(id));
			LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
			body.put("auto_devices", device1);
			ajax.setBody(body);
			return ajax;
		}
		ajax.setSuccess(false);
		ajax.setMsg("操作失败");
		ajax.setErrorCode("0");
		return ajax;
		
	}		
	/**
	 * 22.获取该设备的所有白名单
	 * @param request
	 * @param devices
	 */
	@RequestMapping(value ="autoDevices/findDevices")
	@ResponseBody
	public AjaxJson findAccountByBevices(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String devices = request.getParameter("devices");
		List<AutoDevices> list = null;
		if(devices==null){
			ajax.setSuccess(false);
			ajax.setMsg("请传入参数");
			ajax.setErrorCode("0");
			return ajax;
		}
		AutoDevices autoDevices = new AutoDevices();
		autoDevices.setDevices(devices);
		list = autoDevicesService.findList(autoDevices);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("auto_devices", list);
		ajax.setBody(body);
		return ajax;
	}
	
	/**
	 * 23.删除账户
	 * 说明：根据账户主键ID删除账户
	 * @param request
	 * @param devicesId
	 */
	@RequestMapping(value ="autoDevices/deleteDevices")
	@ResponseBody
	public AjaxJson deleteDevices(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String devicesId = request.getParameter("devicesId");
		if("".equals(devicesId)||!isNumeric(devicesId)||devicesId==null){
			ajax.setSuccess(false);
			ajax.setMsg("传参错误");
			ajax.setErrorCode("0");
			return ajax;
		}
		
		AutoDevices autoDevices = new AutoDevices();
		autoDevices.setDevicesId(Integer.parseInt(devicesId));
		autoDevicesService.delete(autoDevices);
		return ajax;
	}
	/**
	 * 24.更新白名单文件状态值
	 * 说明：传入参数改变改表里所有记录的状态值
	 * @param request
	 * @param status
	 */
	@RequestMapping(value ="autoDevices/updateAllStatus")
	@ResponseBody
	public AjaxJson updateAllStatus(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String status = request.getParameter("status");
		if("".equals(status)||status==null){
			ajax.setSuccess(false);
			ajax.setMsg("传参错误");
			ajax.setErrorCode("0");
			return ajax;
		}
		autoDevicesService.updateAllStatus(status);
		return ajax;
	}
	/**
	 * 25.通过分控设备号查询主控设备号
	 * @param request
	 * @param partdevices
	 */
	@RequestMapping(value ="autoControl/findMainByPartdevices")
	@ResponseBody
	public AjaxJson findMainByPartdevices(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String partdevices = request.getParameter("partdevices");
		List<AutoControl> list = null;
		if("".equals(partdevices)||partdevices==null){
			ajax.setSuccess(false);
			ajax.setMsg("传参错误");
			ajax.setErrorCode("0");
			return ajax;
		}
		AutoControl autoControl = new AutoControl();
		autoControl.setPartdevices(partdevices);
		list = autoControlService.findList(autoControl);
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("auto_control", list);
		ajax.setBody(body);
		return ajax;
	}
	/**
	 * 26.添加分控设备号与主控设备号
	 * @param request
	 * @param partdevices,maindevices 
	 */
	@RequestMapping(value ="autoControl/insertData")
	@ResponseBody
	public AjaxJson insertData(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String partdevices = request.getParameter("partdevices");
		String maindevices = request.getParameter("maindevices");
		if("".equals(maindevices)||maindevices==null||"".equals(partdevices)||partdevices==null){
			ajax.setSuccess(false);
			ajax.setMsg("传参错误");
			ajax.setErrorCode("0");
			return ajax;
		}
		AutoControl control = new AutoControl();
		control.setPartdevices(partdevices);
		List<AutoControl> list = autoControlService.findList(control);
		int id = 0;
		if(list!=null && list.size()>0){
			AutoControl autoControl = new AutoControl();
			autoControl.setControlId(list.get(0).getControlId());
			autoControl.setPartdevices(partdevices);
			autoControl.setMaindevices(maindevices);
			id = autoControlService.saveAutoControl(autoControl);
		}else{		
			AutoControl autoControl = new AutoControl();
			autoControl.setPartdevices(partdevices);
			autoControl.setMaindevices(maindevices);
			id = autoControlService.saveAutoControl(autoControl);
		}	
		if(id>0){
			ajax.setMsg("添加数据成功");
			return ajax;
		}
		ajax.setSuccess(false);
		ajax.setMsg("操作失败");
		ajax.setErrorCode("0");
		return ajax;
	}
	
	/**
	 * 27.更新智能回复数据(用)
	 * @param request
	 * @param codeTextId,keyword,text
	 */
	@RequestMapping(value ="vicCodeText/updateVicCodeText")
	@ResponseBody
	public AjaxJson updateCodeText(HttpServletRequest request) {
		String codeTextId = request.getParameter("codeTextId");
		String keyword = request.getParameter("keyword");
		String text = request.getParameter("text");
		AjaxJson ajax = new AjaxJson();
		if("".equals(codeTextId)||codeTextId==null||!isNumeric(codeTextId)||
			"".equals(keyword)||keyword==null||"".equals(text)||keyword==text){
			ajax.setSuccess(false);
			ajax.setMsg("传参错误");
			ajax.setErrorCode("0");
			return ajax;
		}
		VicCodeText vicCodeText = new VicCodeText();
		vicCodeText.setCodeTextId(Integer.parseInt(codeTextId));
		vicCodeText.setKeyword(keyword);
		vicCodeText.setText(text);
		int id = vicCodeTextService.saveVicCodeText(vicCodeText);
		if(id>0){
			return ajax;
		}
		ajax.setSuccess(false);
		ajax.setMsg("操作失败");
		ajax.setErrorCode("0");
		return ajax;
	}
	/**
	 * 28. 插入聊天记录(用）
	 * @param request
	 * @param deviceId,chatPeople,chatFriends,chatTime,chatFlag,chatContents
	 */
	@RequestMapping(value ="autoChatHistory/insertChatHistory")
	@ResponseBody
	public AjaxJson insertChatHistory(HttpServletRequest request) {	
		AjaxJson ajax = new AjaxJson();
		String deviceId = request.getParameter("deviceId");
		String chatPeople = request.getParameter("liaotianzhe");
		String chatFriends = request.getParameter("liaotianhaoyou");
		String chatTime = request.getParameter("liaotianshijian");
		String chatFlag = request.getParameter("liaotianflag");
		String chatContents = request.getParameter("liaotianneirong");
		System.out.println("deviceId: "+deviceId);
		System.out.println("chatPeople: "+chatPeople);
		System.out.println("chatFriends: "+chatFriends);
		System.out.println("chatTime: "+chatTime);
		System.out.println("chatFlag: "+chatFlag);
		System.out.println("chatContents: "+chatContents);
		if(
				"".equals(deviceId)||deviceId==null||
				"".equals(chatPeople)||chatPeople==null||
				"".equals(chatFriends)||chatFriends==null||
				"".equals(chatTime)||chatTime==null||
				"".equals(chatFlag)||chatFlag==null||
				"".equals(chatContents)||chatContents==null){
			ajax.setSuccess(false);
			ajax.setMsg("请传入参数");
			ajax.setErrorCode("0");
			return ajax;
		}
		Integer allnum = autoChatHistoryService.findAllNum(deviceId);
		Integer minChaId = autoChatHistoryService.findMinChaId(deviceId);
		
		
		AutoChatHistory autoChatHistory = new AutoChatHistory();
		autoChatHistory.setDeviceId(deviceId);
		autoChatHistory.setChatPeople(HTMLSpiritUtils.delHTMLTag(chatPeople));
		autoChatHistory.setChatFriends(HTMLSpiritUtils.delHTMLTag(chatFriends));
		autoChatHistory.setChatTime(chatTime);
		autoChatHistory.setChatFlag(HTMLSpiritUtils.delHTMLTag(chatFlag));
		autoChatHistory.setChatContents(chatContents);
		Integer id = -1;
		if(allnum>=5000) {
			autoChatHistoryService.deleteMinCha(minChaId);
			//id = autoChatHistoryService.updataChatHistory(autoChatHistory);
		}
		id = autoChatHistoryService.insertChatHistory(autoChatHistory);
		if(id>0){
			return ajax;	
		}
		ajax.setSuccess(false);
		ajax.setMsg("操作失败");
		ajax.setErrorCode("0");
		return ajax;
	}
	/**
	 * 29. 插入聊天记录图片(用）
	 * @param request
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value ="autoChatHistory/insertChatImg")
	@ResponseBody
	public void insertChatImg(HttpServletRequest request) {	
		System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
		String chatContents = request.getParameter("picName");
		String files = request.getParameter("files");
		System.out.println("deviceId: "+chatContents);
		System.out.println("file: "+files);
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       // response.setContentType("text/html;charset=utf-8");
        // step1,创建一个DiskFileItemFactory对象
        // 为解析器提供解析时的缺省的配置。
        DiskFileItemFactory dfif = new DiskFileItemFactory();
        // step2,创建一个解析器
        ServletFileUpload sfu = new ServletFileUpload(dfif);
        sfu.setHeaderEncoding("utf-8");
        // step3,使用解析器解析
        try {
            // FileItem对象封装了一个表单域当中的所有的
            // 数据。

            List<FileItem> items = sfu.parseRequest(request);
           System.out.println(items);
            String value = "";
            for (int i = 0; i < items.size(); i++) {
                FileItem item = items.get(i);
                String name = item.getFieldName();
                if ("picName".equals(name)) {
                    // 是一个普通的表单域
                    value = item.getString("UTF-8");
                    System.out.println(name + ":" + value);
                } else if("file".equals(name)){
               	 
                	String path = "D:/users/";
                	if("img_".equals(value.substring(0, 4))) {
                		path = "D:/pictures/";
                	}
                    // 获得上传文件的名称
                    String fileName = value;
                    File file = new File(path + fileName);
                    item.write(file);
                }
            }
 
        } catch (Exception e) {
        	System.out.println("Exception: " + e);
            e.printStackTrace();
        }
	}	

	
	/** 
	 * 判断字符串是否为数字 
	 */  
	public static boolean isNumeric(String str){  		
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    if(str==null||"".equals(str)){
	    	return false;
	    }
	    Matcher isNum = pattern.matcher(str);  
	    return isNum.matches();    
	 } 

}