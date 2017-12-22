package com.jeeplus.common.utils;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.db.BaseSQLHelper;
import com.jeeplus.modules.tools.utils.WxCommonUtil;

import net.sf.json.JSONObject;

/** 
 * @author YeJR
 * @date 创建时间：2017年10月11日 下午1:11:28 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */



public class WxInfoUtil {
	
	/**
	 * 获取最新的access_token
	 * @param minaid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String attainAccessToken(int minaid) {
		BaseSQLHelper sqlHelper = new BaseSQLHelper();
		String querySql = "select * from vicmob_mina_authorization where minaid = " + minaid + " AND status = 1";
		List<Object> queryResult = sqlHelper.query(querySql);
		String accessToken = "";
		// 当前时间戳
		long nowTime = (System.currentTimeMillis()) / 1000;
		long futureTime = nowTime + 7200;
		
		if (!queryResult.isEmpty() && queryResult != null) {
			Map<String, Object> queryMap = (Map<String, Object>) queryResult.get(0);
			String appid = String.valueOf(queryMap.get("appid"));
			String appsecret = String.valueOf(queryMap.get("appsecret"));
			
			String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appid +"&secret=" + appsecret;
			if (queryMap.get("authorizer_access_token") != null && !(String.valueOf(queryMap.get("authorizer_access_token"))).equals("") && !(String.valueOf(queryMap.get("authorizer_access_token"))).equals("null")) {
				long accessTokenEndTime = Long.parseLong(String.valueOf(queryMap.get("authorizer_access_tokenEndTime")));
				accessToken = String.valueOf(queryMap.get("authorizer_access_token"));
				if (accessTokenEndTime > nowTime) {
					return accessToken;
				} else {
					String tookenResult = WxCommonUtil.httpsRequestString(url, "GET", null);
					JSONObject jsonresult = JSONObject.fromObject(tookenResult);
					accessToken = jsonresult.getString("access_token");
				}
			} else {
				String tookenResult = WxCommonUtil.httpsRequestString(url, "GET", null);
				JSONObject jsonresult = JSONObject.fromObject(tookenResult);
				accessToken = jsonresult.getString("access_token");
			}
			String upSql = "update vicmob_mina_authorization set authorizer_access_token = '"+ accessToken +"' , authorizer_access_tokenEndTime = "+ futureTime +" where minaid = " + minaid;
			boolean isSuccess = sqlHelper.update(upSql);
			if (isSuccess) {
				return accessToken;
			}
		}
		return accessToken;
	}
	
	@SuppressWarnings("unchecked")
	public static String attainOpenId(int minaid, int fansId) {
		String openId = "";
		BaseSQLHelper sqlHelper = new BaseSQLHelper();
		String querySql = "select * from vicmob_fans where minaid = " + minaid + " AND fansId = " + fansId;
		List<Object> queryResult = sqlHelper.query(querySql);
		if (!queryResult.isEmpty() && queryResult != null) {
			Map<String, Object> queryMap = (Map<String, Object>) queryResult.get(0);
			openId = String.valueOf(queryMap.get("openId"));
		}
		return openId;
	}
	
	/**
	 * 微餐饮小程序支付订单时，更新prepayId到数据库
	 * @param minaid
	 * @param orderId
	 * @param prepayId
	 * @return
	 */
	public static String updatePrepayIdToOrder(int minaId, int orderId,String prepayId) {
		
		BaseSQLHelper sqlHelper = new BaseSQLHelper();
		String querySql = "update wcs_order set prepayId='"+prepayId+"' where minaId = " + minaId + " AND orderId = " + orderId;
		boolean queryResult=false;
		queryResult = sqlHelper.update(querySql);
		
		if (queryResult) {//更新成功
			return "1";
		}
		return "0";
	}
	/**
	 * 微餐饮小程序支付订单时，更新prepayId到数据库
	 * @param minaid
	 * @param orderId
	 * @param prepayId
	 * @return
	 */
	public static String updatePrepayIdToOrderForWos(int minaId, int orderId,String prepayId) {
		
		BaseSQLHelper sqlHelper = new BaseSQLHelper();
		String querySql = "update wos_order set prepayId='"+prepayId+"' where minaId = " + minaId + " AND orderId = " + orderId;
		boolean queryResult=false;
		queryResult = sqlHelper.update(querySql);
		
		if (queryResult) {//更新成功
			return "1";
		}
		return "0";
	}
	/**
	 * 微餐饮小程序支付订单时，更新formId到数据库
	 * @param minaid
	 * @param orderId
	 * @param formId
	 * @return
	 */
	public static String updateFormIdToOrder(int minaId, int orderId,String formId) {
		
		BaseSQLHelper sqlHelper = new BaseSQLHelper();
		String querySql = "update wcs_order set formId='"+formId+"' where minaId = " + minaId + " AND orderId = " + orderId;
		boolean queryResult=false;
		queryResult = sqlHelper.update(querySql);
		
		if (queryResult) {//更新成功
			return "1";
		}
		return "0";
	}
	/**
	 * 微餐饮小程序支付订单时，更新formId到数据库
	 * @param minaid
	 * @param orderId
	 * @param formId
	 * @return
	 */
	public static  String findFormIdFromOrder(int orderId) {
		String formId="";
		BaseSQLHelper sqlHelper = new BaseSQLHelper();
		String querySql = "select formId from wcs_order where  orderId = " + orderId;
		
		 List<Object> queryResult = sqlHelper.query(querySql);
			if (!queryResult.isEmpty() && queryResult != null) {
				Map<String, Object> queryMap = (Map<String, Object>) queryResult.get(0);
				formId = String.valueOf(queryMap.get("formId"));
			}
		
		return formId;
	}
	/**
	 * 微餐饮小程序支付订单时，更新formId到数据库
	 * @param minaid
	 * @param orderId
	 * @param formId
	 * @return
	 */
	public static  String findFormIdFromWosOrder(int orderId) {
		String formId="";
		BaseSQLHelper sqlHelper = new BaseSQLHelper();
		String querySql = "select formId from wos_order where  orderId = " + orderId;
		
		List<Object> queryResult = sqlHelper.query(querySql);
		if (!queryResult.isEmpty() && queryResult != null) {
			Map<String, Object> queryMap = (Map<String, Object>) queryResult.get(0);
			formId = String.valueOf(queryMap.get("formId"));
		}
		
		return formId;
	}
	/**
	 * 微餐饮小程序支付订单时，更新formId到数据库
	 * @param minaid
	 * @param orderId
	 * @param formId
	 * @return
	 */
	public static  String findPrepayIdFromOrder(int orderId) {
		String prepayId="";
		BaseSQLHelper sqlHelper = new BaseSQLHelper();
		String querySql = "select prepayId from wcs_order where  orderId = " + orderId;
		
		List<Object> queryResult = sqlHelper.query(querySql);
		if (!queryResult.isEmpty() && queryResult != null) {
			Map<String, Object> queryMap = (Map<String, Object>) queryResult.get(0);
			prepayId = String.valueOf(queryMap.get("prepayId"));
		}
		
		return prepayId;
	}
	/**
	 * 微餐饮小程序支付订单时，更新formId到数据库
	 * @param minaid
	 * @param orderId
	 * @param formId
	 * @return
	 */
	public static  String findPrepayIdFromWosOrder(int orderId) {
		String prepayId="";
		BaseSQLHelper sqlHelper = new BaseSQLHelper();
		String querySql = "select prepayId from wos_order where  orderId = " + orderId;
		
		List<Object> queryResult = sqlHelper.query(querySql);
		if (!queryResult.isEmpty() && queryResult != null) {
			Map<String, Object> queryMap = (Map<String, Object>) queryResult.get(0);
			prepayId = String.valueOf(queryMap.get("prepayId"));
		}
		
		return prepayId;
	}
	
	
}
