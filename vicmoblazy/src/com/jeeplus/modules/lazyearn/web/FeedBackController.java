package com.jeeplus.modules.lazyearn.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

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
import com.jeeplus.modules.lazyearn.entity.AutoDefaultData;
import com.jeeplus.modules.lazyearn.entity.FeedBack;
import com.jeeplus.modules.lazyearn.service.AutoAccountService;
import com.jeeplus.modules.lazyearn.service.AutoControlService;
import com.jeeplus.modules.lazyearn.service.FeedBackService;
import com.jeeplus.modules.sys.utils.MySqlUtils;

/**
 * 反馈管理Controller
 * 
 * @author Stephen
 * @version 2017-9-27
 */
@Controller
@RequestMapping(value = "${adminPath}/lazyearn/feedback")
public class FeedBackController extends BaseController {
	@Autowired
	private FeedBackService feedBackService;
	
	/**
	 * 跳转账号管理List列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "jumpList")
	public String list(HttpServletRequest request, Model model) {
		return "modules/lazyearn/feedBackList";
	}

	/**
	 * 分页查询账号信息
	 * 
	 * @param autoAccount
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findList")
	@ResponseBody
	public Page<FeedBack> findList(FeedBack feedBack, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		feedBack.setDeleteflag(false);// 未删除的
		String title = request.getParameter("searchFund");// 搜索时带的关键字
		System.out.println("account----------" + title);
		String str = MySqlUtils.specialStr(title.trim());
		System.out.println("account111----------" + str);
		feedBack.setTitle(str);
		feedBack.setContent(str);
		Page<FeedBack> page = feedBackService.findPage(new Page<FeedBack>(request, response), feedBack);
		return page;
	}

	/**
	 * 单个删除的方法
	 * 
	 * @param accountId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public int delete(Integer feedbackId, Model model, HttpServletRequest request, HttpServletResponse response) {
		int result;
		FeedBack feedback = new FeedBack();
		System.out.println(feedbackId+"sss");
		feedback.setFeedbackId(feedbackId);
		feedback.setDeleteflag(true);
		result = feedBackService.saveData(feedback);
		return result;
	}

	/**
	 * 批量删除的方法
	 * 
	 * @param accountId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteAll")
	@ResponseBody
	public int deleteAll(Model model, HttpServletRequest request, HttpServletResponse response) {
		String[] delitems = request.getParameter("delitems").split(",");
		System.out.println(request.getParameter("delitems"));
		int result = 0;
		for (int i = 0; i < delitems.length; i++) {
			FeedBack feedback = new FeedBack();
			feedback.setFeedbackId(Integer.parseInt(delitems[i]));
			feedback.setDeleteflag(true);
			result = feedBackService.saveData(feedback);
		}
		return result;
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
	public int save(FeedBack feedBack, Model model, HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		feedBack.setDeleteflag(false);
		String content=request.getParameter("content");
		System.out.println(content);
		try {
			feedBack.setContent(content);
			result = feedBackService.saveData(feedBack);
			System.out.println("result--" + result);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	
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
	public FeedBack edit(FeedBack feedBack, Integer feedbackId, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("feedBackId----ID:" + feedbackId);
		if (feedbackId != null) {
			feedBack.setFeedbackId(feedbackId);
			feedBack = feedBackService.get(feedBack);
			
		}
		return feedBack;
	}
	/**
	 * 跳转酷虎反馈form表单
	 * 
	 * @param feedBack
	 * @param feedBackId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "jumpForm")
	public String jumpForm(FeedBack feedBack, Integer feedbackId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		if (feedbackId != null) {
			feedBack.setFeedbackId(feedbackId);
			FeedBack feedBack1 = feedBackService.get(feedBack);
			model.addAttribute("feedBack", feedBack1);
		}
		
		return "modules/lazyearn/feedBackForm";
	}
}