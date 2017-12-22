package com.jeeplus.modules.lazyearn.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.lazyearn.entity.AutoChatHistory;
import com.jeeplus.modules.lazyearn.entity.AutoDevices;
import com.jeeplus.modules.lazyearn.service.AutoChatHistoryService;
import com.jeeplus.modules.lazyearn.service.AutoDevicesService;

@Controller
@RequestMapping(value = "${adminPath}/lazyearn/AutoChatHistory")

public class AutoChatHistoryController extends BaseController{
	@Autowired
	AutoChatHistoryService autoChatHistoryService;
	@RequestMapping(value = "jumpList")
	public ModelAndView list(HttpServletRequest request, Model model) {
		String nickname=request.getParameter("nickname").trim().replace(" ", "");
		System.out.println("========"+nickname+"========");
		return new ModelAndView("modules/lazyearn/lazyEarnManager/chatHistoryList","nickname",nickname);
	}
	
	/**
	 * 分页查询白名单信息
	 * 
	 * @param autoAccount
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findList")
	@ResponseBody
	public Page<AutoChatHistory> findList(AutoChatHistory autoChatHistory, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String title = request.getParameter("searchFund");// 搜索时带的关键字
		System.out.println("title----------" + title);
		autoChatHistory.setChatContents(title);
		Page<AutoChatHistory> page = autoChatHistoryService.findPage(new Page<AutoChatHistory>(request, response), autoChatHistory);
		return page;
	}
	/**
	 * 获得聊天好友
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findChatFriendsList")
	@ResponseBody
	public List<String> findChatFriendsList(HttpServletRequest request,HttpServletResponse response) {
		String weChatId = request.getParameter("weChatId");
	    return autoChatHistoryService.findChatFriendsList(weChatId);
	}
	/**
	 * 获得聊天记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "searchChatDetails")
	@ResponseBody
	public List<String> searchChatDetails(HttpServletRequest request,HttpServletResponse response) {
		String weChatId = request.getParameter("weChatId");
		String weChatFriends = request.getParameter("weChatFriends");
		String weChatdate = request.getParameter("weChatdate");
		return autoChatHistoryService.searchChatDetails(weChatId,weChatFriends,weChatdate);
	}
	/**
	 * 获得最近时间
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getRecentlyTime")
	@ResponseBody
	public String getRecentlyTime(HttpServletRequest request,HttpServletResponse response) {
		String weChatId = request.getParameter("weChatId");
		String weChatFriends = request.getParameter("weChatFriends");
		return autoChatHistoryService.getRecentlyTime(weChatId,weChatFriends);
	}
	/**
	 * 获得磁盘上图片
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="showImg")
	public void ShowImg(HttpServletRequest request,HttpServletResponse response) throws IOException{
	       String imgFile = request.getParameter("imgFile"); //文件名
	       String path= "D:\\users";//这里是存放图片的文件夹地址
	       if("img_".equals(imgFile.substring(0, 4))) {
	    	   path = "D:\\pictures";
	       }
	       FileInputStream fileIs=null;
	       try {
	        fileIs = new FileInputStream(path+"/"+imgFile);
	       } catch (Exception e) {
	         System.out.println("系统找不到图像文件："+path+"/"+imgFile);        
	         return;
	       }
	       int i=fileIs.available(); //得到文件大小   
	       byte data[]=new byte[i];   
	       fileIs.read(data);  //读数据   
	       response.setContentType("image/*"); //设置返回的文件类型   
	       OutputStream outStream=response.getOutputStream(); //得到向客户端输出二进制数据的对象   
	       outStream.write(data);  //输出数据      
	       outStream.flush();  
	       outStream.close();   
	       fileIs.close();   
	   }
}
