package com.jeeplus.modules.sys.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jeeplus.modules.sys.entity.VicmobFansInfo;
import com.jeeplus.modules.sys.entity.VicmobPayAccount;
import com.jeeplus.modules.sys.service.VicmobFansInfoService;
import com.jeeplus.modules.sys.service.VicmobPayAccountService;
import com.jeeplus.modules.tools.utils.CommUtil;
import com.jeeplus.modules.tools.utils.WxCommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 微信小程序支付接口
 * 
 * @author YeJR
 * 
 *         payType 0=支付给单独的商家， 调用此接口需传入appid mch_id api_key 三个参数
 *         1=支付给unicalId对应的商家， 调用此接口只需传入unicalId， 此商家的其他参数要提前设置好
 *
 */
@RestController
@RequestMapping(value = "/weChatPay")
public class WeChatPay {

	@Autowired
	private VicmobPayAccountService vicmobPayAccountService;

	@Autowired
	private VicmobFansInfoService vicmobFansInfoService;

	/**
	 * 用户支付给商家
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/customerToMerchant.do", method = RequestMethod.POST)
	public void customerToMerchant(HttpServletRequest request, HttpServletResponse response) {
		try {
			/** 微信下单接口 **/
			String orderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

			InputStream iStream = request.getInputStream();
			JSONObject paramJson = CommUtil.loadString(iStream);
			/******************************* 获取post提交值 *******************************/
			// 小程序ID
			String minaId = paramJson.getString("minaId");
			// 粉丝ID
			String fansId = paramJson.getString("fansId");
			// 通知地址
			String notify_url = paramJson.getString("notify_url");
			// 商品描述
			String body = paramJson.getString("body");
			// 商户订单号
			String out_trade_no = paramJson.getString("out_trade_no");
			// 总金额
			String total_fee = (int) Float.parseFloat(paramJson.getString("total_fee")) + "";
			// 0:支付给统一商家 1:支付给minaId对应的商家
			String payType = paramJson.getString("payType");

			/******************************* 获取post提交值 *******************************/
			// 随机字符串
			String nonce_str = WxCommonUtil.createNoncestr();
			// 终端IP
			String spbill_create_ip = request.getRemoteAddr();
			// 交易类型
			String trade_type = "JSAPI";

			// 小程序appid
			String appid = "";
			// 商户号
			String mch_id = "";
			// API安全密钥
			String api_key = "";
			// 用户在当前appId下的唯一标识
			String openid = "";
			// 签名
			String sign = "";

			// 针对不同的payType 获取支付参数
			if (payType.equals("0")) {
				appid = ConfigurationFileHelper.getPayAppId();
				mch_id = ConfigurationFileHelper.getPayMchId();
				api_key = ConfigurationFileHelper.getPayApiKey();
			} else {
				VicmobPayAccount vicmobPayAccount = new VicmobPayAccount();
				vicmobPayAccount.setMinaId(Integer.parseInt(minaId));
				vicmobPayAccount.setState(true);
				if (vicmobPayAccountService.get(vicmobPayAccount) != null) {
					vicmobPayAccount = vicmobPayAccountService.get(vicmobPayAccount);
					appid = vicmobPayAccount.getAppId();
					mch_id = vicmobPayAccount.getMchId();
					api_key = vicmobPayAccount.getApiKey();
				}
			}

			// 获取用户openId
			VicmobFansInfo vicmobFansInfo = new VicmobFansInfo();
			vicmobFansInfo.setMinaId(Integer.parseInt(minaId));
			vicmobFansInfo.setFansId(Integer.parseInt(fansId));
			if (vicmobFansInfoService.get(vicmobFansInfo) != null) {
				vicmobFansInfo = vicmobFansInfoService.get(vicmobFansInfo);
				openid = vicmobFansInfo.getOpenId();
			}

			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			parameters.put("appid", appid);
			parameters.put("mch_id", mch_id);
			parameters.put("nonce_str", nonce_str);
			parameters.put("body", body);
			parameters.put("out_trade_no", out_trade_no);
			parameters.put("total_fee", total_fee);
			parameters.put("spbill_create_ip", spbill_create_ip);
			parameters.put("notify_url", notify_url);
			parameters.put("trade_type", trade_type);
			parameters.put("openid", openid);

			// 使用MD5进行签名，编码必须为UTF-8
			sign = WxCommonUtil.createSignMD5("UTF-8", parameters, api_key);

			parameters.put("sign", sign);

			// 生成xml结构的数据，用于统一下单请求的xml请求数据
			String requestXML = WxCommonUtil.getRequestAllXml(parameters);
			// https访问微信接口
			String wx_result = WxCommonUtil.httpsRequestString(orderUrl, "POST", requestXML);

			// 解析微信返回的信息，以Map形式存储便于取值
			Map<String, String> wx_map = WxCommonUtil.doXMLParse(wx_result);
			String return_code = wx_map.get("return_code");
			String result_code = wx_map.get("result_code");

			if (return_code.equals("SUCCESS") && result_code.equals("SUCCESS")) {
				// 预支付交易会话标识
				String prepay_id = wx_map.get("prepay_id");

				// 返回支付参数
				SortedMap<Object, Object> params = new TreeMap<Object, Object>();

				params.put("timeStamp", new Date().getTime() + ""); // 时间戳
				params.put("nonceStr", WxCommonUtil.createNoncestr()); // 随机字符串
				params.put("package", "prepay_id=" + prepay_id); // 格式必须为prepay_id=***
				params.put("signType", "MD5"); // 签名的方式必须是MD5
				params.put("appId", appid); // 签名的方式必须是MD5

				// 生成返回给页面的签名
				String paySign = WxCommonUtil.createSignMD5("UTF-8", params, api_key);

				params.put("paySign", paySign); // 支付签名

				String json = JSONArray.fromObject(params).toString();

				output(response, json);
			}
		} catch (Exception e) {
			// 返回支付参数
			SortedMap<Object, Object> params = new TreeMap<Object, Object>();
			params.put("return_code", "error");
			params.put("message", e.getMessage());
			String json = JSONArray.fromObject(params).toString();
			output(response, json);
			e.printStackTrace();
		}
	}

	/**
	 * 查询消费者给商家付款的订单在微信那边存不存在
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody // 返回json
	@RequestMapping(value = "/customerPayedOrderQuery.do", method = RequestMethod.POST)
	public void customerPayedOrderQuery(HttpServletRequest request, HttpServletResponse response) {

		try {
			String orderQueryUrl = "https://api.mch.weixin.qq.com/pay/orderquery";

			InputStream iStream = request.getInputStream();
			JSONObject paramJson = CommUtil.loadString(iStream);

			/******************************* 获取post提交值 *******************************/
			// 小程序ID
			String minaId = paramJson.getString("minaId");
			// 商户订单号
			String out_trade_no = paramJson.getString("out_trade_no");
			// 0:支付给统一商家 1:支付给minaId对应的商家
			String payType = paramJson.getString("payType");
			/******************************* 获取post提交值 *******************************/
			// 随机字符串
			String nonce_str = WxCommonUtil.createNoncestr();

			// 小程序appid
			String appid = "";
			// 商户号
			String mch_id = "";
			// API安全密钥
			String api_key = "";
			// 签名
			String sign = "";

			// 针对不同的payType 获取支付参数
			if (payType.equals("0")) {
				appid = ConfigurationFileHelper.getPayAppId();
				mch_id = ConfigurationFileHelper.getPayMchId();
				api_key = ConfigurationFileHelper.getPayApiKey();
			} else {
				VicmobPayAccount vicmobPayAccount = new VicmobPayAccount();
				vicmobPayAccount.setMinaId(Integer.parseInt(minaId));
				vicmobPayAccount.setState(true);
				if (vicmobPayAccountService.get(vicmobPayAccount) != null) {
					vicmobPayAccount = vicmobPayAccountService.get(vicmobPayAccount);
					appid = vicmobPayAccount.getAppId();
					mch_id = vicmobPayAccount.getMchId();
					api_key = vicmobPayAccount.getApiKey();
				}
			}

			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			parameters.put("appid", appid);
			parameters.put("mch_id", mch_id);
			parameters.put("nonce_str", nonce_str);
			parameters.put("out_trade_no", out_trade_no);

			sign = WxCommonUtil.createSignMD5("UTF-8", parameters, api_key);
			parameters.put("sign", sign);

			// 生成查询订单的xml
			String requestXML = WxCommonUtil.getRequestAllXml(parameters);

			// 访问wx
			String wx_result = WxCommonUtil.httpsRequestString(orderQueryUrl, "POST", requestXML);
			Map<String, String> wx_map = WxCommonUtil.doXMLParse(wx_result);
			String return_code = wx_map.get("return_code");
			String result_code = wx_map.get("result_code");
			if (return_code.equals("SUCCESS") && result_code.equals("SUCCESS")) {
				// 付款人openId
				String openid = wx_map.get("openid");
				// SUCCESS—支付成功 REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭 REVOKED—已撤销（刷卡支付）
				// USERPAYING--用户支付中 PAYERROR--支付失败(其他原因，如银行返回失败)
				String trade_state = wx_map.get("trade_state");
				// 订单金额
				String total_fee = wx_map.get("total_fee");
				// 商户订单号
				String outTradeNo = wx_map.get("out_trade_no");
				// 支付完成时间
				String time_end = wx_map.get("time_end");
				// 获取fansId
				int fansId = 0;
				VicmobFansInfo vicmobFansInfo = new VicmobFansInfo();
				vicmobFansInfo.setMinaId(Integer.parseInt(minaId));
				vicmobFansInfo.setOpenId(openid);
				if (vicmobFansInfoService.get(vicmobFansInfo) != null) {
					vicmobFansInfo = vicmobFansInfoService.get(vicmobFansInfo);
					fansId = vicmobFansInfo.getFansId();
				}

				// 返回支付参数
				SortedMap<Object, Object> params = new TreeMap<Object, Object>();
				params.put("return_code", "success");
				params.put("fansId", fansId);
				params.put("trade_state", trade_state);
				params.put("total_fee", total_fee);
				params.put("out_trade_no", outTradeNo);
				params.put("time_end", time_end);

				String json = JSONArray.fromObject(params).toString();
				json = json.substring(1, json.length() - 1);
				output(response, json);
			}
		} catch (Exception e) {
			// 返回支付参数
			SortedMap<Object, Object> params = new TreeMap<Object, Object>();
			params.put("return_code", "error");
			params.put("message", e.getMessage());
			String json = JSONArray.fromObject(params).toString();
			json = json.substring(1, json.length() - 1);
			output(response, json);
			e.printStackTrace();
		}

	}

	/**
	 * 消费者退款(支付单号和退款单号一致)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody // 返回json
	@RequestMapping(value = "/customerRefund.do", method = RequestMethod.POST)
	public void customerRefund(HttpServletRequest request, HttpServletResponse response) {
		try {
			/** 微信退款接口 **/
			String refundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";
			InputStream iStream = request.getInputStream();
			JSONObject paramJson = CommUtil.loadString(iStream);
			System.out.println("======退款=======paramJson" + paramJson);
			/******************************* 获取post提交值 *******************************/
			// 小程序ID
			String minaId = paramJson.getString("minaId");
			// 商户订单号
			String out_trade_no = paramJson.getString("out_trade_no");
			// 总金额
			String total_fee = (int) Float.parseFloat(paramJson.getString("total_fee")) + "";
			// 退款金额
			String refund_fee = (int) Float.parseFloat(paramJson.getString("refund_fee")) + "";
			// 0:支付给统一商家 1:支付给minaId对应的商家
			String payType = paramJson.getString("payType");
			/******************************* 获取post提交值 *******************************/

			// 随机字符串
			String nonce_str = WxCommonUtil.createNoncestr();
			// 商户退款单号（与订单号一致）
			String out_refund_no = out_trade_no;

			// 小程序appid
			String appid = "";
			// 商户号
			String mch_id = "";
			// API安全密钥
			String api_key = "";
			// 证书地址，使用的服务器的实际路径，如：/opt/tomcat/apiclient_cert.p12
			String certificatePath = "";
			// 操作员
			String op_user_id = "";
			// 签名
			String sign = "";

			// 针对不同的payType 获取支付参数
			if (payType.equals("0")) {
				appid = ConfigurationFileHelper.getPayAppId();
				mch_id = ConfigurationFileHelper.getPayMchId();
				api_key = ConfigurationFileHelper.getPayApiKey();
				certificatePath = ConfigurationFileHelper.getPayCertificatePath();
			} else {
				VicmobPayAccount vicmobPayAccount = new VicmobPayAccount();
				vicmobPayAccount.setMinaId(Integer.parseInt(minaId));
				vicmobPayAccount.setState(true);
				if (vicmobPayAccountService.get(vicmobPayAccount) != null) {
					vicmobPayAccount = vicmobPayAccountService.get(vicmobPayAccount);
					appid = vicmobPayAccount.getAppId();
					mch_id = vicmobPayAccount.getMchId();
					api_key = vicmobPayAccount.getApiKey();
					certificatePath = vicmobPayAccount.getCertificatePath();
				}
			}
			// 操作员一般默认商户号
			op_user_id = mch_id;

			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			parameters.put("appid", appid);
			parameters.put("mch_id", mch_id);
			parameters.put("op_user_id", op_user_id);
			parameters.put("nonce_str", nonce_str);
			parameters.put("out_trade_no", out_trade_no);
			parameters.put("out_refund_no", out_refund_no);
			parameters.put("total_fee", total_fee);
			parameters.put("refund_fee", refund_fee);

			sign = WxCommonUtil.createSignMD5("UTF-8", parameters, api_key);
			parameters.put("sign", sign);

			// 生成用于退款的xml数据
			String requestXML = WxCommonUtil.getRequestAllXml(parameters);

			// 使用证书开始退款
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(new File(certificatePath));// 放退款证书的路径
			try {
				keyStore.load(instream, mch_id.toCharArray());
			} finally {
				instream.close();
			}

			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			try {
				HttpPost httpPost = new HttpPost(refundUrl);// 退款接口

				StringEntity reqEntity = new StringEntity(requestXML, "UTF-8");
				// 设置类型
				reqEntity.setContentType("application/x-www-form-urlencoded");
				httpPost.setEntity(reqEntity);
				CloseableHttpResponse chresponse = httpclient.execute(httpPost);
				try {
					String xmlStr = EntityUtils.toString(chresponse.getEntity(), "UTF-8");
					Map<String, String> wx_map = WxCommonUtil.doXMLParse(xmlStr);
					System.out.println("======退款=======wx_map" + wx_map);
					String  return_code = "FAIL";
					String  return_msg = "FAIL";
					
					// 返回结果
					String returnCode = wx_map.get("return_code");
					String returnMsg = wx_map.get("return_msg");
					if (returnCode.equals("SUCCESS") && returnMsg.equals("OK")) {
						String resultCode = wx_map.get("result_code");
						if (resultCode.equals("SUCCESS")) {
							return_code = "SUCCESS";
							return_msg = "OK";
						} else {
							return_msg = wx_map.get("err_code_des");
						}
					}
					
					// 返回支付参数
					SortedMap<Object, Object> params = new TreeMap<Object, Object>();
					params.put("minaId", minaId);
					params.put("return_code", return_code);// 退款状态
					params.put("return_msg", return_msg);// 返回信息

					String json = JSONArray.fromObject(params).toString();
					json = json.substring(1, json.length() - 1);
					output(response, json);

				} finally {
					chresponse.close();
				}
			} finally {
				httpclient.close();
			}
		} catch (Exception e) {
			// 返回支付参数
			SortedMap<Object, Object> params = new TreeMap<Object, Object>();
			params.put("return_code", "error");
			params.put("return_msg", e.getMessage());
			String json = JSONArray.fromObject(params).toString();
			json = json.substring(1, json.length() - 1);
			output(response, json);
			e.printStackTrace();
		}
	}

	/**
	 * 消费者退款(支付单号和退款单号不一致)
	 * 可以单个退款
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody // 返回json
	@RequestMapping(value = "/customerRefundSingle.do", method = RequestMethod.POST)
	public void customerRefundSingle(HttpServletRequest request, HttpServletResponse response) {
		try {
			/** 微信退款接口 **/
			String refundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";
			InputStream iStream = request.getInputStream();
			JSONObject paramJson = CommUtil.loadString(iStream);

			/******************************* 获取post提交值 *******************************/
			// 小程序ID
			String minaId = paramJson.getString("minaId");
			// 商户订单号
			String out_trade_no = paramJson.getString("out_trade_no");
			// 商户退款单号
			String out_refund_no = paramJson.getString("out_refund_no");
			// 总金额
			String total_fee = (int) Float.parseFloat(paramJson.getString("total_fee")) + "";
			// 退款金额
			String refund_fee = (int) Float.parseFloat(paramJson.getString("refund_fee")) + "";
			// 0:支付给统一商家 1:支付给minaId对应的商家
			String payType = paramJson.getString("payType");
			/******************************* 获取post提交值 *******************************/

			// 随机字符串
			String nonce_str = WxCommonUtil.createNoncestr();

			// 小程序appid
			String appid = "";
			// 商户号
			String mch_id = "";
			// API安全密钥
			String api_key = "";
			// 证书地址，使用的服务器的实际路径，如：/opt/tomcat/apiclient_cert.p12
			String certificatePath = "";
			// 操作员
			String op_user_id = "";
			// 签名
			String sign = "";

			// 针对不同的payType 获取支付参数
			if (payType.equals("0")) {
				appid = ConfigurationFileHelper.getPayAppId();
				mch_id = ConfigurationFileHelper.getPayMchId();
				api_key = ConfigurationFileHelper.getPayApiKey();
				certificatePath = ConfigurationFileHelper.getPayCertificatePath();
			} else {
				VicmobPayAccount vicmobPayAccount = new VicmobPayAccount();
				vicmobPayAccount.setMinaId(Integer.parseInt(minaId));
				vicmobPayAccount.setState(true);
				if (vicmobPayAccountService.get(vicmobPayAccount) != null) {
					vicmobPayAccount = vicmobPayAccountService.get(vicmobPayAccount);
					appid = vicmobPayAccount.getAppId();
					mch_id = vicmobPayAccount.getMchId();
					api_key = vicmobPayAccount.getApiKey();
					certificatePath = vicmobPayAccount.getCertificatePath();
				}
			}
			// 操作员一般默认商户号
			op_user_id = mch_id;

			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			parameters.put("appid", appid);
			parameters.put("mch_id", mch_id);
			parameters.put("op_user_id", op_user_id);
			parameters.put("nonce_str", nonce_str);
			parameters.put("out_trade_no", out_trade_no);
			parameters.put("out_refund_no", out_refund_no);
			parameters.put("total_fee", total_fee);
			parameters.put("refund_fee", refund_fee);

			sign = WxCommonUtil.createSignMD5("UTF-8", parameters, api_key);
			parameters.put("sign", sign);

			// 生成用于退款的xml数据
			String requestXML = WxCommonUtil.getRequestAllXml(parameters);

			// 使用证书开始退款
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(new File(certificatePath));// 放退款证书的路径
			try {
				keyStore.load(instream, mch_id.toCharArray());
			} finally {
				instream.close();
			}

			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			try {
				HttpPost httpPost = new HttpPost(refundUrl);// 退款接口

				StringEntity reqEntity = new StringEntity(requestXML, "UTF-8");
				// 设置类型
				reqEntity.setContentType("application/x-www-form-urlencoded");
				httpPost.setEntity(reqEntity);
				CloseableHttpResponse chresponse = httpclient.execute(httpPost);
				try {
					String xmlStr = EntityUtils.toString(chresponse.getEntity(), "UTF-8");
					Map<String, String> wx_map = WxCommonUtil.doXMLParse(xmlStr);
					// 返回结果
					String return_code = wx_map.get("return_code");
					String return_msg = wx_map.get("return_msg");

					// 返回支付参数
					SortedMap<Object, Object> params = new TreeMap<Object, Object>();
					params.put("minaId", minaId);
					params.put("return_code", return_code);// 退款状态
					params.put("return_msg", return_msg);// 返回信息

					String json = JSONArray.fromObject(params).toString();
					json = json.substring(1, json.length() - 1);
					output(response, json);

				} finally {
					chresponse.close();
				}
			} finally {
				httpclient.close();
			}
		} catch (Exception e) {
			// 返回支付参数
			SortedMap<Object, Object> params = new TreeMap<Object, Object>();
			params.put("return_code", "error");
			params.put("return_msg", e.getMessage());
			String json = JSONArray.fromObject(params).toString();
			json = json.substring(1, json.length() - 1);
			output(response, json);
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 数据输出
	 * 
	 * @param response
	 * @param returnValeue
	 */
	public void output(HttpServletResponse response, String returnValeue) {
		try {
			response.setContentType("text/plain");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");

			PrintWriter printWriter = response.getWriter();
			printWriter.write(returnValeue);
			printWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
