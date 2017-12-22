package com.jeeplus.modules.sys.web;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.entity.VicmobUser;
import com.jeeplus.modules.sys.service.VicmobUserProductsService;
import com.jeeplus.modules.sys.service.VicmobUserService;
import com.jeeplus.modules.tools.utils.CommUtil;

import net.sf.json.JSONObject;

/**
 * 用户信息接口
 * 
 * @author Jeezy
 *
 *         2017-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/agent/userInfo")
public class VicmobUserController extends BaseController {

	@Autowired
	private VicmobUserService vicmobUserService;

	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public Integer saveOrUpdate(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("=======================进入用户信息接口=====================");
		/**
		 * 获取传入参数
		 */
		// 定义变量
		Integer count = 0;
		InputStream inStream;
		JSONObject paramJson;
		JSONObject paramJaonForOut = new JSONObject();
		// 接收参数
		inStream = request.getInputStream();
		paramJson = CommUtil.loadString(inStream);

		try {
			count = vicmobUserService.saveUserInfo(paramJson);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
}
