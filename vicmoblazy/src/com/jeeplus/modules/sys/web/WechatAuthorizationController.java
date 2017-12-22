/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.dom4j.DocumentException;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.mp.aes.AesException;
import org.jeewx.api.report.datastatistics.useranalysis.JwUserAnalysisAPI;
import org.jeewx.api.report.datastatistics.useranalysis.model.UserAnalysisRtnInfo;
import org.jeewx.api.third.JwThirdAPI;
import org.jeewx.api.third.model.ApiAuthorizerToken;
import org.jeewx.api.third.model.ApiAuthorizerTokenRet;
import org.jeewx.api.third.model.ApiComponentToken;
import org.jeewx.api.third.model.ApiGetAuthorizer;
import org.jeewx.api.third.model.ApiGetAuthorizerRet;
import org.jeewx.api.third.model.ApiGetAuthorizerRetAuthorizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.entity.MinaInfo;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.WeixinOpenAccount;
import com.jeeplus.modules.sys.service.MinaInfoService;
import com.jeeplus.modules.sys.service.WeixinOpenAccountService;
import com.jeeplus.modules.sys.utils.ConfigurationFileHelper;

import net.sf.json.JSONObject;





/**
 * 小程序授权Controller
 * @author Stephen
 * @version 2017-7-2
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/authorization")
public class WechatAuthorizationController extends BaseController {
	 /**
     * 微信第三方平台账号信息
     */
    private final static String COMPONENT_APPID = ConfigurationFileHelper.getComponentAppid();
    private final static String COMPONENT_APPSECRET = ConfigurationFileHelper.getComponentAppsecret();
    private final static String COMPONENT_ENCODINGAESKEY = ConfigurationFileHelper.getComponentEncodingaeskey();
    private final static String COMPONENT_TOKEN = ConfigurationFileHelper.getComponentToken();
    
    private static String path = ConfigurationFileHelper.getServiceUrl();
    @Autowired
    private WeixinOpenAccountService weixinOpenAccountService;
    @Autowired
	private MinaInfoService minaInfoService;
	
	@ModelAttribute
	public MinaInfo get(@RequestParam(required=false) String id) {
		MinaInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = minaInfoService.get(id);
		}
		if (entity == null){
			entity = new MinaInfo();
		}
		return entity;
	}
	
	/**
	 * 微信小程序授权
	 */
	@RequestMapping(value = "authorize")
	public String authorize(HttpServletRequest request) {	

  	    HttpSession session =  request.getSession();
        String userid = (String)session.getAttribute("userId");
        
		ApiComponentToken apiComponentToken = new ApiComponentToken();
        apiComponentToken.setComponent_appid(COMPONENT_APPID);
        apiComponentToken.setComponent_appsecret(COMPONENT_APPSECRET);
        WeixinOpenAccount entity = getWeixinOpenAccount(COMPONENT_APPID);
        apiComponentToken.setComponent_verify_ticket(entity.getTicket());
        String component_access_token = entity.getComponentAccessToken();
        Integer component_access_token_endtime = entity.getComponentAccessTokenEndtime();
        String url = null;
        try {
        	
        	//判断component_access_token有没有过期
        	if(((int)((System.currentTimeMillis())/1000)>=component_access_token_endtime)){
        		component_access_token = JwThirdAPI.getAccessToken(apiComponentToken);
        		Integer currentTime = (int)((System.currentTimeMillis())/1000+7200);
                entity.setComponentAccessTokenEndtime(currentTime);
                entity.setComponentAccessToken(component_access_token);
                weixinOpenAccountService.saveWeixinOpenAccount(entity);
        	}
            
            //预授权码
        	String preAuthCode = JwThirdAPI.getPreAuthCode(COMPONENT_APPID, component_access_token);     
            url = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid="+COMPONENT_APPID+"&pre_auth_code="+preAuthCode+
            	  "&redirect_uri="+path+"/VicmobMINA/a/sys/authorization/authorCallback?userid="+userid;
        } catch (WexinReqException e) {
            e.printStackTrace();
        } 

        return "redirect:" + url;
	}
	
	 @RequestMapping(value = "authorCallback")
	 public String authorCallback(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, AesException, DocumentException {
		 	  String authorization_code = request.getParameter("auth_code");		 	 
	    	  //获取用户账号信息
	    	  String userid = request.getParameter("userid");
	    	  HttpSession session =  request.getSession();
	    	  Integer groupid = (Integer)session.getAttribute("groupid");
	    	  String loginname = (String)session.getAttribute("loginname");
	    	  String password = (String)session.getAttribute("pwds");
	    	  Integer minaid = null;
	    	  
	    	  ApiComponentToken apiComponentToken = new ApiComponentToken();  
	          apiComponentToken.setComponent_appid(COMPONENT_APPID);  
	          apiComponentToken.setComponent_appsecret(COMPONENT_APPSECRET);  
	          WeixinOpenAccount  entity = getWeixinOpenAccount(COMPONENT_APPID);  
	          apiComponentToken.setComponent_verify_ticket(entity.getTicket());
	          String component_access_token = entity.getComponentAccessToken();
	          Integer component_access_token_endtime = entity.getComponentAccessTokenEndtime();
	          try {
	        	  if(((int)((System.currentTimeMillis())/1000)>=component_access_token_endtime)){
	          			component_access_token = JwThirdAPI.getAccessToken(apiComponentToken);
	          			Integer currentTime = (int)((System.currentTimeMillis())/1000+7200);
	          			entity.setComponentAccessTokenEndtime(currentTime);
	          			entity.setComponentAccessToken(component_access_token);
	          			weixinOpenAccountService.saveWeixinOpenAccount(entity);
	        	  } 
	        	  
	        //解析获取授权小程序信息并保存	  
	          net.sf.json.JSONObject MinaInfoJson = JwThirdAPI.getApiQueryAuthInfo(COMPONENT_APPID, authorization_code, component_access_token);
	          net.sf.json.JSONObject infoJson = MinaInfoJson.getJSONObject("authorization_info"); 
	          String authorizerAppid = infoJson.getString("authorizer_appid");
	          String authorizer_access_token = infoJson.getString("authorizer_access_token");	        
	          String authorizer_refresh_token = infoJson.getString("authorizer_refresh_token");
	          
	          ApiGetAuthorizer apiGetAuthorizer =new ApiGetAuthorizer();
	          apiGetAuthorizer.setComponent_appid(COMPONENT_APPID);
	          apiGetAuthorizer.setAuthorizer_appid(authorizerAppid);
	          ApiGetAuthorizerRet userInfo = JwThirdAPI.apiGetAuthorizerInfo(apiGetAuthorizer,component_access_token);
	          ApiGetAuthorizerRetAuthorizer authorizer_info = userInfo.getAuthorizer_info();
	          
	          MinaInfo minaInfo= new MinaInfo();
	          minaInfo.setUserid(Integer.parseInt(userid));
	          minaInfo.setToken(COMPONENT_TOKEN);
	          minaInfo.setEncodingaeskey(COMPONENT_ENCODINGAESKEY);
	          minaInfo.setAppid(authorizerAppid);
	          minaInfo.setNickName(authorizer_info.getNick_name());
	          minaInfo.setHeadImg(authorizer_info.getHead_img());
	          minaInfo.setUserName(authorizer_info.getUser_name());
	          minaInfo.setServiceType(authorizer_info.getService_type_info().getId().toString());
	          minaInfo.setVerifyType(authorizer_info.getVerify_type_info().getId().toString());
	          minaInfo.setLoginname(loginname);
	          minaInfo.setPassword(password);
	          minaInfo.setAuthorizerAccessToken(authorizer_access_token);
	          Integer authorizerAccessEndTime = (int)((System.currentTimeMillis())/1000+3600*2);//authorizer_accessToken过期时间
	          Integer authorizerRefreshTokenEndTime = (int)((System.currentTimeMillis())/1000+3600*24*7);//authorizer_refreshToken过期时间	         
	          minaInfo.setAuthorizerAccessTokenEndTime(authorizerAccessEndTime);
	          minaInfo.setAuthorizerRefreshToken(authorizer_refresh_token);
	          minaInfo.setAuthorizerRefreshTokenEndTime(authorizerRefreshTokenEndTime);
	          minaInfo.setStatus(true);	         
	          
	          /*
	           * 一个系统账号只能绑定一个小程序账号，相反一个小程序也只能绑定一个系统账号，一旦系统账号绑定一个小程序账号，
	           * 这个账号将永远只能绑定这一个小程序账号，且只能跟新这个小程序账号
	           */
	          
	          //判断当前账户是否绑定了小程序
	          List<MinaInfo> minaInfoList = minaInfoService.findMinaByUserid(Integer.parseInt(userid));
	          if(minaInfoList!=null && minaInfoList.size()>0){//如果当前账号绑定了小程序则不能绑定其他小程序账号
	        	  if(!(minaInfoList.get(0).getAppid()).equals(authorizerAppid)){
	        		  //当前授权小程序不是绑已定的小程序
		        	  model.addAttribute("minaBindMessage", minaInfoList.get(0).getNickName());
		        	  return "modules/sys/sysAuthorization";  
	        	  }	        	  	        	  
	        	  
	          }
	        	          
	          //通过授权小程序的appid查找是否已被授权（是否存在这条数据）
	          List<MinaInfo> minaList = minaInfoService.findMinaByAppid(authorizerAppid);
	          if(minaList.size()>0 && minaList!=null){//存在则跟新这个小程序账号信息
	        	  minaInfo.setMinaid(minaList.get(0).getMinaid());	        	  
	        	  //再判断存在的小程序是否是当前系统账号的，
	        	  	  //1.如果是当前系统账号的就更新这个小程序账号信息.2.如果不是当前系统账号的则提示该小程序账号已被另一系统账户授权，并将另一系统账户的该小程序状态变为正常显示
	        	  if(!(String.valueOf(minaList.get(0).getUserid())).equals(userid)){	
	        		  User user = minaInfoService.findUserById(minaList.get(0).getUserid());
	        		  minaInfo.setUserid(minaList.get(0).getUserid());	
	        		  model.addAttribute("minaMessage", user.getLoginName());
	        	  }
	        	    	
	          }
	          //保存授权小程序账号
        	  minaid = minaInfoService.saveMinaInfo(minaInfo);
        	  
	          } catch (WexinReqException e) {
	              e.printStackTrace();
	          } 	          	          
	          return "modules/sys/sysAuthorization";
	    }
		
 /**
  * 获取授权账号信息
  * @param component_appid
  * @return
  */
		WeixinOpenAccount getWeixinOpenAccount(String componentAppid) {
			WeixinOpenAccount weixinOpenAccount = new WeixinOpenAccount();
			weixinOpenAccount.setComponentAppid(componentAppid);
			List<WeixinOpenAccount> weixinOpenAccountList = weixinOpenAccountService.findList(weixinOpenAccount);
			if (weixinOpenAccountList != null && weixinOpenAccountList.size() != 0) {
				weixinOpenAccount = weixinOpenAccountList.get(0);
			}
			return weixinOpenAccount;
		}
	  	 
	 /**
	  * 限制每个账号只能有授权一个小程序
	  * @param userid
	  * @return
	  */
	 @RequestMapping(value ="authorizeCount",method = RequestMethod.POST) 
	 @ResponseBody
	public Boolean authorizeCount(HttpServletRequest request){
		 HttpSession session =  request.getSession();
	     String userid = (String)session.getAttribute("userId");
	     //如果是超级管理员可以授权多个小程序
	     if(userid.equals("1")){
	    	 return true;
			}
	     MinaInfo minaInfo = new MinaInfo();
	     minaInfo.setUserid(Integer.parseInt(userid));
	     List<MinaInfo> MinaInfoList = minaInfoService.findList(minaInfo);
	     if(MinaInfoList.size()>=1){
	    	 
	        return false;
	       }
		return true;
	 }
	 
	 /**
	  * 其他页面返回主页页面
	  */
	@RequestMapping(value ="goHome") 
	public String goHome(HttpServletRequest request){

		 return "modules/sys/sysIndex";
	 }
	 	
	/**
	 * 跳转到授权小程序下的管理页面
	 * @throws WexinReqException 
	 * @throws Exception 
	 */
	@RequestMapping(value ="management",method = RequestMethod.POST)
	public String management(HttpServletRequest request,HttpServletResponse response) throws WexinReqException {
			HttpSession session =  request.getSession();
			session.setAttribute("minaId", request.getParameter("minaid"));	        	        
	        MinaInfo minaInfo = minaInfoService.findEntityById(Integer.parseInt(request.getParameter("minaid")));
	        session.setAttribute("mina_nickname", minaInfo.getNickName());
	        session.setAttribute("mina_headImg", minaInfo.getHeadImg());
	        session.setAttribute("mina_appId", minaInfo.getAppid());
	        session.setAttribute("mina_verify_type", minaInfo.getVerifyType());
	        
			return "modules/sys/sysIndex";
		}

	
	/**
	 * 授权小程序查询列表供页面展示数据
	 */
	@RequestMapping(value = "findList",method = RequestMethod.POST)
	@ResponseBody
	public List<MinaInfo> findMinaList(HttpServletRequest request) {
		String userid = request.getParameter("userid");
		String nickName = request.getParameter("nickName");
		MinaInfo minaInfo = new MinaInfo();
		if(!userid.equals("1")){
			minaInfo.setUserid(Integer.parseInt(userid));
		}		
		minaInfo.setNickName(nickName);
		List<MinaInfo> minaInfoList = minaInfoService.findList(minaInfo);
		return minaInfoList;
	}
	
	/**
	 * 通过主键minaid获取小程序
	 */
	@RequestMapping(value = "findMina",method = RequestMethod.POST)
	@ResponseBody
	public MinaInfo findMina(HttpServletRequest request) {
		MinaInfo minaInfo = minaInfoService.findEntityById(Integer.parseInt(request.getParameter("minaId")));

		return minaInfo;
	}
	
	/**
	 * 绑定用户小程序的APPSECRET
	 */
	@RequestMapping(value = "bindMinaAppsecret",method = RequestMethod.POST)
	@ResponseBody
	public Integer bindMinaAppsecret(HttpServletRequest request) {
		MinaInfo minaInfo = minaInfoService.findEntityById(Integer.parseInt(request.getParameter("minaId")));
		minaInfo.setAppsecret(request.getParameter("appsecret"));
		Integer minaid = minaInfoService.saveMinaInfo(minaInfo);
		return minaid;
	}
	
	/**
	 * 删除授权小程序
	 */
	@RequestMapping(value = "delete",method = RequestMethod.POST)
	public String delete(MinaInfo minaInfo, RedirectAttributes redirectAttributes) {
		minaInfoService.delete(minaInfo);
		addMessage(redirectAttributes, "删除授权小程序成功");
		return "redirect:"+Global.getAdminPath()+"/wedding/MinaInfo/?repage";
	}
	
	/**
	 * 批量删除授权小程序
	 */
	@RequestMapping(value = "deleteAll",method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteAll(String ids) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			if(id!=null&&!"".equals(id)){
				minaInfoService.logicDelete(Integer.parseInt(id));
			}
		}
		
		return 1;
	}
	
	/**
	 * 获取授权小程序的access_token
	 * @throws WexinReqException 
	 */
	public String findAuthorizerAccessToken(String minaid) throws WexinReqException{
		//获取数据库中授权时产生的授权小程序authorizer_access_token
	    MinaInfo minaInfo = minaInfoService.findEntityById(Integer.parseInt(minaid));
	    //该条记录不存在则结束方法运行
	    if(minaInfo == null || "".equals(minaInfo)){
	    	return null;
	    }
	    
	    // 当前时间戳
	 	long nowTime = (System.currentTimeMillis()) / 1000;
		// 授权公众号刷新令牌未到期(authorizer_refresh_token未过期)
		if (minaInfo.getAuthorizerRefreshTokenEndTime() > nowTime) {
			// 授权公众号令牌未到期(authorizer_access_token未过期)
			if (minaInfo.getAuthorizerAccessTokenEndTime() > nowTime) {
				String jsapiTicket = minaInfo.getJsapiTicket();
				if(jsapiTicket !=null && ! jsapiTicket.equals("null") && ! jsapiTicket.isEmpty() && ! jsapiTicket.equals("") ){					
					//未过期就返回当前数据库中的authorizer_access_token
					return minaInfo.getAuthorizerAccessToken();					
				}
			}
			// 过期了,重新获取			
			String new_authorizer_access_token = saveAccessToken(minaInfo,COMPONENT_APPID, nowTime);
			if(new_authorizer_access_token !=null ){
				
				return new_authorizer_access_token;
			}
		}else if(minaInfo.getAuthorizerAccessTokenEndTime() < nowTime){// 到期的话给当前时间加一天(由于只存7天的机制，实际情况下，每获取一次accessToken,刷新时间就存一次。理论上时间永远够用)
			//(但是不排除7天内未使用accessToken的情况，而且，刷新时间的机制是第一次存7天，然后30天，90天)若还不行，只能重新授权。
			 long new_authorizer_refresh_tokenEndTime=nowTime+24*3600;
			 /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			 String re_StrTime = sdf.format(new Date(Totime * 1000));*/
			 //更新数据库刷新时间
			minaInfo.setAuthorizerRefreshTokenEndTime((int)new_authorizer_refresh_tokenEndTime);
			Integer id = minaInfoService.saveMinaInfo(minaInfo);
			//存成功,在执行获取getAccessToken的方法
			if (id>0) {
				findAuthorizerAccessToken(minaInfo.getMinaid().toString());
			}else{
				return null;
			}
			
		}
		return null;
		
	}
	
	/**
	 * 设置authorizer_access_token、jsapi_ticket共用一个字段2小时authorizer_access_tokenEndTime，
	 * 同时更新。
	 * 
	 * @param authorizer_appid
	 * @param authorizer_refresh_token
	 * @param component_appid
	 * @param nowTime
	 * @return
	 * @throws WexinReqException
	 */
	private String saveAccessToken(MinaInfo minaInfo,String component_appid,long nowTime) throws WexinReqException {
		ApiAuthorizerToken apiAuthorizerToken = new ApiAuthorizerToken();
		apiAuthorizerToken.setAuthorizer_appid(minaInfo.getAppid());
		apiAuthorizerToken.setAuthorizer_refresh_token(minaInfo.getAuthorizerRefreshToken());
		apiAuthorizerToken.setComponent_appid(component_appid);
		
		// 获取component_access_token
		 ApiComponentToken apiComponentToken = new ApiComponentToken();  
         apiComponentToken.setComponent_appid(COMPONENT_APPID);  
         apiComponentToken.setComponent_appsecret(COMPONENT_APPSECRET);  
         WeixinOpenAccount  entity = getWeixinOpenAccount(COMPONENT_APPID);  
         apiComponentToken.setComponent_verify_ticket(entity.getTicket());
         String component_access_token = entity.getComponentAccessToken();
         Integer component_access_token_endtime = entity.getComponentAccessTokenEndtime();
         //判断component_access_token有没有过期
       	  if(((int)((System.currentTimeMillis())/1000)>=component_access_token_endtime)){
         			component_access_token = JwThirdAPI.getAccessToken(apiComponentToken);
         			Integer currentTime = (int)((System.currentTimeMillis())/1000+7200);
         			entity.setComponentAccessTokenEndtime(currentTime);
         			entity.setComponentAccessToken(component_access_token);
         			weixinOpenAccountService.saveWeixinOpenAccount(entity);
       	  } 
		// 获取new_authorizer_access_token、new_authorizer_refresh_token
		ApiAuthorizerTokenRet apiAuthorizerTokenRet = JwThirdAPI.apiAuthorizerToken(apiAuthorizerToken,component_access_token);
		String new_authorizer_access_token = apiAuthorizerTokenRet.getAuthorizer_access_token();
		String new_authorizer_refresh_token = apiAuthorizerTokenRet.getAuthorizer_refresh_token();
		// 获取jsapi_ticket
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="
				+ new_authorizer_access_token;
		String ticktresult = WxstoreUtils.httpRequest(requestUrl,"GET", null).toString();

		JSONObject jsonresult = JSONObject.fromObject(ticktresult);
		String jsapi_ticket = jsonresult.getString("ticket");
		// 到期时间设置
		long new_authorizer_access_tokenEndTime = nowTime + 7200;
		// 刷新 token authorizer_refresh_token到期时间设置（refresh_token拥有较长的有效期（7天、30天、60天、90天）微信文档说的不清不楚，目前就按7天存吧）
		long new_authorizer_refresh_tokenEndTime = nowTime + 3600*24*7;
		
		// 更新到数据库
		minaInfo.setJsapiTicket(jsapi_ticket);
		minaInfo.setAuthorizerAccessToken(new_authorizer_access_token);
		minaInfo.setAuthorizerAccessTokenEndTime((int)new_authorizer_access_tokenEndTime);
		minaInfo.setAuthorizerRefreshToken(new_authorizer_refresh_token);
		minaInfo.setAuthorizerRefreshTokenEndTime((int)new_authorizer_refresh_tokenEndTime);
		Integer id = minaInfoService.saveMinaInfo(minaInfo);
		
		if (id>0) {
			return new_authorizer_access_token;
		}
		return null;
	}
	
}