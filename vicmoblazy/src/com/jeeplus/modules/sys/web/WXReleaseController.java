package com.jeeplus.modules.sys.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.mp.aes.AesException;
import org.jeewx.api.mp.aes.WXBizMsgCrypt;
import org.jeewx.api.third.JwThirdAPI;
import org.jeewx.api.third.model.ApiComponentToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.jeeplus.modules.sys.entity.WeixinOpenAccount;
import com.jeeplus.modules.sys.service.WeixinOpenAccountService;
import com.jeeplus.modules.sys.utils.ConfigurationFileHelper;

/**
 * 微信公众账号第三方平台全网发布
 * @author Stephen
 * @version 2017-7-2
 */

@Controller
@RequestMapping(value = "${adminPath}/sys/releasewx")
public class WXReleaseController {

	/**
	 * 微信全网发布第三方平台账号信息
	 */
	private final static String COMPONENT_APPID = ConfigurationFileHelper.getComponentAppid();
	private final String COMPONENT_APPSECRET = ConfigurationFileHelper.getComponentAppsecret();
	private final static String COMPONENT_ENCODINGAESKEY = ConfigurationFileHelper.getComponentEncodingaeskey();
	private final static String COMPONENT_TOKEN = ConfigurationFileHelper.getComponentToken();
	
	
	@Autowired
	private WeixinOpenAccountService weixinOpenAccountService;
	private static String path = ConfigurationFileHelper.getServiceUrl();

	/**
	 * 授权事件接收
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws AesException
	 * @throws DocumentException
	 */
	@RequestMapping(value = "/event/authorize")
	public void acceptAuthorizeEvent(HttpServletRequest request, HttpServletResponse response)
			throws IOException, AesException, DocumentException {
		// LogUtil.info("微信第三方平台---------微信推送Ticket消息10分钟一次-----------"+
		// DataUtils.getDataString(DataUtils.yyyymmddhhmmss));
		processAuthorizeEvent(request);
		output(response, "success"); // 输出响应的内容。
	}

	/**
	 * 处理授权事件的推送
	 * 
	 * @param request
	 * @throws IOException
	 * @throws AesException
	 * @throws DocumentException
	 */
	public void processAuthorizeEvent(HttpServletRequest request) throws IOException, DocumentException, AesException {
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		String signature = request.getParameter("signature");
		String msgSignature = request.getParameter("msg_signature");

		if (!StringUtils.isNotBlank(msgSignature))
			return;// 微信推送给第三方开放平台的消息一定是加过密的，无消息加密无法解密消息
		boolean isValid = checkSignature(COMPONENT_TOKEN, signature, timestamp, nonce);
		if (isValid) {
			StringBuilder sb = new StringBuilder();
			BufferedReader in = request.getReader();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			String xml = sb.toString();
			// LogUtil.info("第三方平台全网发布-----------------------原始 Xml="+xml);
			String encodingAesKey = COMPONENT_ENCODINGAESKEY;// 第三方平台组件加密密钥
			// String appId = getAuthorizerAppidFromXml(xml);//
			// 此时加密的xml数据中ToUserName是非加密的，解析xml获取即可
			// LogUtil.info("第三方平台全网发布-------------appid----------getAuthorizerAppidFromXml(xml)-----------appId="+appId);
			WXBizMsgCrypt pc = new WXBizMsgCrypt(COMPONENT_TOKEN, encodingAesKey, COMPONENT_APPID);
			xml = pc.decryptMsg(msgSignature, timestamp, nonce, xml);
			// LogUtil.info("第三方平台全网发布-----------------------解密后 Xml="+xml);
			processAuthorizationEvent(xml);
		}
	}

	/**
	 * 将微信每隔10min推送的Ticket保存起来
	 * @param xml
	 */
	void processAuthorizationEvent(String xml) {
		Document doc;
		try {
			doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement();
			String ticket = rootElt.elementText("ComponentVerifyTicket");
			if (StringUtils.isNotEmpty(ticket)) {
				// LogUtil.info("8、推送component_verify_ticket协议-----------ticket
				// = "+ticket);
				WeixinOpenAccount weixinOpenAccount = new WeixinOpenAccount();
				weixinOpenAccount.setTicket(ticket);
				weixinOpenAccount.setComponentAppid(COMPONENT_APPID);
				weixinOpenAccount.setComponentAppsecret(COMPONENT_APPSECRET);				
				weixinOpenAccount.setGetTicketTime(new Date());
				
				//判断数据库是否存在数据，若存在则更新这条数据，否则插入（注意：获取ticket的数据库有且只有一条）
				List<WeixinOpenAccount> weixinOpenAccountList = weixinOpenAccountService.findList(weixinOpenAccount);
				String component_access_token = "";
				if(weixinOpenAccountList !=null && weixinOpenAccountList.size() > 0){
					//有记录则更新这条数据
					WeixinOpenAccount entity = weixinOpenAccountList.get(0);
					weixinOpenAccount.setTicketId(entity.getTicketId());
					weixinOpenAccount.setComponentAccessToken(entity.getComponentAccessToken());
					weixinOpenAccount.setComponentAccessTokenEndtime(entity.getComponentAccessTokenEndtime());
				}else{
					//如果沒有記錄數據則通过ticket获取component_access_token
					ApiComponentToken apiComponentToken = new ApiComponentToken();
					apiComponentToken.setComponent_appid(COMPONENT_APPID);
					apiComponentToken.setComponent_appsecret(COMPONENT_APPSECRET);
					apiComponentToken.setComponent_verify_ticket(ticket);
					component_access_token = JwThirdAPI.getAccessToken(apiComponentToken);
					weixinOpenAccount.setComponentAccessToken(component_access_token);//将获取的component_access_token存储
					weixinOpenAccount.setComponentAccessTokenEndtime(((int)(System.currentTimeMillis())/1000)+7150);//component_access_token的到期时间（当前时间+2小时）
				}
				weixinOpenAccountService.saveWeixinOpenAccount(weixinOpenAccount);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/authorCallback")
	public String authorCallback(HttpServletRequest request, HttpServletResponse response)
			throws IOException, AesException, DocumentException {
		String authorizer_appid = request.getParameter("authorizer_appid");
		String authorizer_access_token = request.getParameter("authorizer_access_token");
		return "modules/VmMain/wcsIndex";
	}

	/**
	 * 一键授权功能
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws AesException
	 * @throws DocumentException
	 */
	@RequestMapping(value = "/goAuthor")
	public void goAuthor(HttpServletRequest request, HttpServletResponse response)
			throws IOException, AesException, DocumentException {

		ApiComponentToken apiComponentToken = new ApiComponentToken();
		apiComponentToken.setComponent_appid(COMPONENT_APPID);
		apiComponentToken.setComponent_appsecret(COMPONENT_APPSECRET);
		WeixinOpenAccount entity = getWeixinOpenAccount(COMPONENT_APPID);
		apiComponentToken.setComponent_verify_ticket(entity.getTicket());
		try {
			String component_access_token = JwThirdAPI.getAccessToken(apiComponentToken);
			// 预授权码
			String preAuthCode = JwThirdAPI.getPreAuthCode(COMPONENT_APPID, component_access_token);
			String url = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=" + COMPONENT_APPID
					+ "&pre_auth_code=" + preAuthCode + "&redirect_uri=" + path
					+ "/VicmobMINA/a/sys/releasewx/authorCallback";
			response.sendRedirect(url);
		} catch (WexinReqException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "{appid}/callback", method = RequestMethod.POST)
	public void acceptMessageAndEvent(HttpServletRequest request, HttpServletResponse response)
			throws IOException, AesException, DocumentException {
		String msgSignature = request.getParameter("msg_signature");
		// LogUtil.info("第三方平台全网发布-------------{appid}/callback-----------验证开始。。。。msg_signature="+msgSignature);
		if (!StringUtils.isNotBlank(msgSignature))
			return;// 微信推送给第三方开放平台的消息一定是加过密的，无消息加密无法解密消息

		StringBuilder sb = new StringBuilder();
		BufferedReader in = request.getReader();
		String line;
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		in.close();

		String xml = sb.toString();
		Document doc = DocumentHelper.parseText(xml);
		Element rootElt = doc.getRootElement();
		String fromUserName = rootElt.elementText("FromUserName");

		// 微信全网测试账号
		// if (StringUtils.equalsIgnoreCase(toUserName, APPID)) {
		// LogUtil.info("全网发布接入检测消息反馈开始---------------APPID="+ APPID
		// +"------------------------toUserName="+toUserName);
		checkWeixinAllNetworkCheck(request, response, xml);
		// }
	}

	/**
	 * 获取授权账号信息
	 * 
	 * @param appid
	 * @return
	 */
	WeixinOpenAccount getWeixinOpenAccount(String componentAppid) {
		WeixinOpenAccount weixinOpenAccount = new WeixinOpenAccount();
		weixinOpenAccount.setComponentAppid(COMPONENT_APPID);
		List<WeixinOpenAccount> weixinOpenAccountList = weixinOpenAccountService.findList(weixinOpenAccount);
		if (weixinOpenAccountList != null && weixinOpenAccountList.size() != 0) {
			weixinOpenAccount = weixinOpenAccountList.get(0);
		}
		return weixinOpenAccount;
	}

	/**
	 * 获取授权的Appid
	 * 
	 * @param xml
	 * @return
	 */
	String getAuthorizerAppidFromXml(String xml) {
		Document doc;
		try {
			doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement();
			String toUserName = rootElt.elementText("ToUserName");
			return toUserName;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void checkWeixinAllNetworkCheck(HttpServletRequest request, HttpServletResponse response, String xml)
			throws DocumentException, IOException, AesException {
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		String msgSignature = request.getParameter("msg_signature");
		WXBizMsgCrypt pc = new WXBizMsgCrypt(COMPONENT_TOKEN, COMPONENT_ENCODINGAESKEY, COMPONENT_APPID);
		xml = pc.decryptMsg(msgSignature, timestamp, nonce, xml);

		Document doc = DocumentHelper.parseText(xml);
		Element rootElt = doc.getRootElement();
		String msgType = rootElt.elementText("MsgType");
		String toUserName = rootElt.elementText("ToUserName");
		String fromUserName = rootElt.elementText("FromUserName");

		   
//      LogUtil.info("---全网发布接入检测--step.1-----------msgType="+msgType+"-----------------toUserName="+toUserName+"-----------------fromUserName="+fromUserName);  
//      LogUtil.info("---全网发布接入检测--step.2-----------xml="+xml);  
      if("event".equals(msgType)){  
//         LogUtil.info("---全网发布接入检测--step.3-----------事件消息--------");  
           String event = rootElt.elementText("Event");  
           replyEventMessage(request,response,event,toUserName,fromUserName);  
      }else if("text".equals(msgType)){  
//         LogUtil.info("---全网发布接入检测--step.3-----------文本消息--------");  
           String content = rootElt.elementText("Content");  
           processTextMessage(request,response,content,toUserName,fromUserName);  
      }  
  } 

	public void replyEventMessage(HttpServletRequest request, HttpServletResponse response, String event,
			String toUserName, String fromUserName) throws DocumentException, IOException {
		String content = event + "from_callback";
		// LogUtil.info("---全网发布接入检测------step.4-------事件回复消息 content="+content
		// + " toUserName="+toUserName+" fromUserName="+fromUserName);
		replyTextMessage(request, response, content, toUserName, fromUserName);
	}

	public void processTextMessage(HttpServletRequest request, HttpServletResponse response, String content,
			String toUserName, String fromUserName) throws IOException, DocumentException {
		if ("TESTCOMPONENT_MSG_TYPE_TEXT".equals(content)) {
			String returnContent = content + "_callback";
			replyTextMessage(request, response, returnContent, toUserName, fromUserName);
		} else if (StringUtils.startsWithIgnoreCase(content, "QUERY_AUTH_CODE")) {
			output(response, "");
			// 接下来客服API再回复一次消息
			replyApiTextMessage(request, response, content.split(":")[1], fromUserName);
		}
	}

	public void replyApiTextMessage(HttpServletRequest request, HttpServletResponse response, String auth_code,
			String fromUserName) throws DocumentException, IOException {
		String authorization_code = auth_code;
		// 得到微信授权成功的消息后，应该立刻进行处理！！相关信息只会在首次授权的时候推送过来
		// System.out.println("------step.1----使用客服消息接口回复粉丝----逻辑开始-------------------------");
		try {
			ApiComponentToken apiComponentToken = new ApiComponentToken();
			apiComponentToken.setComponent_appid(COMPONENT_APPID);
			apiComponentToken.setComponent_appsecret(COMPONENT_APPSECRET);
			WeixinOpenAccount entity = getWeixinOpenAccount(COMPONENT_APPID);
			apiComponentToken.setComponent_verify_ticket(entity.getTicket());
			String component_access_token = JwThirdAPI.getAccessToken(apiComponentToken);

			// System.out.println("------step.2----使用客服消息接口回复粉丝-------
			// component_access_token = "+component_access_token +
			// "---------authorization_code = "+authorization_code);
			net.sf.json.JSONObject authorizationInfoJson = JwThirdAPI.getApiQueryAuthInfo(COMPONENT_APPID,
					authorization_code, component_access_token);
			// System.out.println("------step.3----使用客服消息接口回复粉丝--------------
			// 获取authorizationInfoJson = "+authorizationInfoJson);
			net.sf.json.JSONObject infoJson = authorizationInfoJson.getJSONObject("authorization_info");
			String authorizer_access_token = infoJson.getString("authorizer_access_token");

			Map<String, Object> obj = new HashMap<String, Object>();
			Map<String, Object> msgMap = new HashMap<String, Object>();
			String msg = auth_code + "_from_api";
			msgMap.put("content", msg);

			obj.put("touser", fromUserName);
			obj.put("msgtype", "text");
			obj.put("text", msgMap);
			JwThirdAPI.sendMessage(obj, authorizer_access_token);
		} catch (WexinReqException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 验证是否过期
	 * 
	 * @param accessTokenExpires
	 * @return
	 */
	boolean isExpired(long accessTokenExpires) {
		return false;
	}

	/**
	 * 回复微信服务器"文本消息"
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @param toUserName
	 * @param fromUserName
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void replyTextMessage(HttpServletRequest request, HttpServletResponse response, String content,
			String toUserName, String fromUserName) throws DocumentException, IOException {
		Long createTime = Calendar.getInstance().getTimeInMillis() / 1000;
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
		sb.append("<CreateTime>" + createTime + "</CreateTime>");
		sb.append("<MsgType><![CDATA[text]]></MsgType>");
		sb.append("<Content><![CDATA[" + content + "]]></Content>");
		sb.append("</xml>");
		String replyMsg = sb.toString();

		String returnvaleue = "";
		try {
			WXBizMsgCrypt pc = new WXBizMsgCrypt(COMPONENT_TOKEN, COMPONENT_ENCODINGAESKEY, COMPONENT_APPID);
			returnvaleue = pc.encryptMsg(replyMsg, createTime.toString(), "vicmob");
			// System.out.println("------------------加密后的返回内容 returnvaleue：
			// "+returnvaleue);
		} catch (AesException e) {
			e.printStackTrace();
		}
		output(response, returnvaleue);
	}

	/*
	 * public static void main(String[] args) { Long createTime =
	 * Calendar.getInstance().getTimeInMillis() / 1000; String replyMsg =
	 * "LOCATIONfrom_callback";
	 * 
	 * String returnvaleue = ""; try { WXBizMsgCrypt pc = new
	 * WXBizMsgCrypt(COMPONENT_TOKEN, COMPONENT_ENCODINGAESKEY,
	 * COMPONENT_APPID); returnvaleue = pc.encryptMsg(replyMsg,
	 * createTime.toString(), "easemob"); System.out.println(returnvaleue); }
	 * catch (AesException e) { e.printStackTrace(); } }
	 */
	/**
	 * 工具类：回复微信服务器"文本消息"
	 * 
	 * @param response
	 * @param returnvaleue
	 */
	public void output(HttpServletResponse response, String returnvaleue) {
		try {
			PrintWriter pw = response.getWriter();
			pw.write(returnvaleue);

			// System.out.println("****************returnvaleue***************="+returnvaleue);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否加密
	 * 
	 * @param token
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
		System.out.println(
				"###token:" + token + ";signature:" + signature + ";timestamp:" + timestamp + "nonce:" + nonce);
		boolean flag = false;
		if (signature != null && !signature.equals("") && timestamp != null && !timestamp.equals("") && nonce != null
				&& !nonce.equals("")) {
			String sha1 = "";
			String[] ss = new String[] { token, timestamp, nonce };
			Arrays.sort(ss);
			for (String s : ss) {
				sha1 += s;
			}

			sha1 = AddSHA1.SHA1(sha1);

			if (sha1.equals(signature)) {
				flag = true;
			}
		}
		return flag;
	}
}

class AddSHA1 {
	public static String SHA1(String inStr) {
		MessageDigest md = null;
		String outStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1"); // 选择SHA-1，也可以选择MD5
			byte[] digest = md.digest(inStr.getBytes()); // 返回的是byet[]，要转化为String存储比较方便
			outStr = bytetoString(digest);
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		return outStr;
	}

	public static String bytetoString(byte[] digest) {
		String str = "";
		String tempStr = "";

		for (int i = 0; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			} else {
				str = str + tempStr;
			}
		}
		return str.toLowerCase();
	}
}