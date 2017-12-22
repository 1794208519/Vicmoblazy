package com.jeeplus.modules.sys.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.VicmobUserDao;
import com.jeeplus.modules.sys.dao.VicmobUserProductsDao;
import com.jeeplus.modules.sys.entity.VicmobUser;
import com.jeeplus.modules.sys.entity.VicmobUserProducts;
import com.jeeplus.modules.sys.utils.ConfigurationFileHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户表Service
 * 
 * @author Jeezy
 * @version 2017-11-04
 */
@Service
@Transactional(readOnly = true)
public class VicmobUserService extends CrudService<VicmobUserDao, VicmobUser> {
	private static String path = ConfigurationFileHelper.getServiceUrl();

	@Autowired
	private VicmobUserDao vicmobUserDao;
	@Autowired
	private VicmobUserProductsDao vicmobUserProductsDao;

	/**
	 * 编辑用户信息（权限，即：用户使用功能版本）
	 * 
	 * @param paramJson
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = false)
	public Integer saveUserInfo(JSONObject paramJson) throws ParseException {
		int flag;
		int count = 0;
		/**
		 * 处理用户信息
		 * 
		 * 新增成功：返回flag为1，更新成功：返回flag为2
		 */
		// 保存用户信息
		flag = addOrUpdateUser(paramJson);
		// 创建实体
		VicmobUser vicmobUser = new VicmobUser();
		VicmobUserProducts vicUserPro = new VicmobUserProducts();
		// 取出 用户产品权限信息
		JSONArray userAuthoritys = (JSONArray) paramJson.get("userAuthoritys");
		String loginName = paramJson.get("loginName").toString();
		String endTime = paramJson.get("endTime").toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 如果已经插入或更新用户信息成功
		if (flag == 1 || flag == 2) {
			vicmobUser.setLoginName(loginName);
			VicmobUser userIdInfo = vicmobUserDao.get(vicmobUser);// 取出userId
			vicUserPro.setUserId(userIdInfo.getUserId());

			// 先清空userId下所有的产品
			vicmobUserProductsDao.delete(vicUserPro);

			// 通过userId插入所有最新的产品信息，伪update
			for (int i = 0; i < userAuthoritys.size(); i++) {
				Object o = userAuthoritys.get(i);
				JSONObject jsonObject = JSONObject.fromObject(o);
				vicUserPro.setStartTime(sdf.parse(jsonObject.getString("startTime")));
				vicUserPro.setEndTime(sdf.parse(jsonObject.getString("endTime")));
				vicUserPro.setUpdateTime(new Date());
				vicUserPro.setVersionNum(jsonObject.getString("moduleNumber"));
				vicUserPro.setVersionLevel(Integer.parseInt(jsonObject.getString("subModuleNumber")));
				vicUserPro.setStatus(Integer.parseInt(jsonObject.getString("status")));
				count = vicmobUserProductsDao.insert(vicUserPro);
				if (count == 0) {
					throw new RuntimeException("用户产品录入异常");
				}
			}
			flag = 1;
			return flag;
		} else {
			System.err.println("插入用户信息失败！");
			return 0;
		}
	}

	public Integer addOrUpdateUser(JSONObject paramJson) throws ParseException {
		int flag;

		// 获取Json中的参数
		String groupId = paramJson.getString("groupId").toString();
		String loginName = (String) paramJson.get("loginName");// 用户名
		String passWord = (String) paramJson.get("passWord");// 用户名
		String name = (String) paramJson.get("name");// 姓名
		String phone = (String) paramJson.get("phone");// 电话
		String company = (String) paramJson.get("company");// 公司名
		String address = (String) paramJson.get("address");// 地址
		String createDate = (String) paramJson.get("createDate");// 地址
		String startTime = paramJson.get("startTime").toString();// 开始时间
		String endTime = paramJson.get("endTime").toString();// 结束时间
		String delFlag = paramJson.get("delFlag").toString();// 逻辑删除标志
		String loginFlag = paramJson.get("loginFlag").toString();// 逻辑删除标志

		// 判断某loginName下的用户是否存在
		VicmobUser vicmobUser = new VicmobUser();
		vicmobUser.setLoginName(loginName);
		VicmobUser userLoginName = vicmobUserDao.get(vicmobUser);
		// 转换时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null == userLoginName) {
			vicmobUser.setGroupId(Integer.parseInt(groupId));
			vicmobUser.setLoginName(loginName);
			passWord = SystemService.entryptPassword(passWord);
			vicmobUser.setPassWord(passWord);
			vicmobUser.setName(name);
			vicmobUser.setPhone(phone);
			vicmobUser.setCompany(company);
			vicmobUser.setAddress(address);
			vicmobUser.setJoinTime(sdf.parse(createDate));
			vicmobUser.setStartTime(sdf.parse(startTime));
			vicmobUser.setEndTime(sdf.parse(endTime));
			vicmobUser.setDelFlag(delFlag);
			vicmobUser.setLoginFlag(loginFlag);
			vicmobUserDao.insert(vicmobUser);
			flag = 1;
			return flag;
		} else {
			vicmobUser.setGroupId(Integer.parseInt(groupId));
			vicmobUser.setLoginName(loginName);
			passWord = SystemService.entryptPassword(passWord);
			vicmobUser.setPassWord(passWord);
			vicmobUser.setName(name);
			vicmobUser.setPhone(phone);
			vicmobUser.setCompany(company);
			vicmobUser.setAddress(address);
			vicmobUser.setStartTime(sdf.parse(startTime));
			vicmobUser.setEndTime(sdf.parse(endTime));
			vicmobUser.setDelFlag(delFlag);
			vicmobUser.setLoginFlag(loginFlag);
			vicmobUserDao.update(vicmobUser);
			flag = 2;
			return flag;
		}

	}
}
