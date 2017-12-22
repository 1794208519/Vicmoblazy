package com.jeeplus.modules.lazyearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.lazyearn.dao.AutoAccountDao;
import com.jeeplus.modules.lazyearn.dao.AutoChatHistoryDao;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.AutoChatHistory;
import com.jeeplus.modules.lazyearn.entity.AutoDevices;
@Service
@Transactional(readOnly = false)
public class AutoChatHistoryService extends CrudService<AutoChatHistoryDao, AutoChatHistory>{
	@Autowired
	private AutoChatHistoryDao autoChatHistoryDao;
	@Transactional(readOnly = false)
	public Integer insertChatHistory(AutoChatHistory autoChatHistory){
		return autoChatHistoryDao.insertChatHistory(autoChatHistory);
	}
	@Transactional(readOnly = false)
	public Integer updataChatHistory(AutoChatHistory autoChatHistory){
		return autoChatHistoryDao.updataChatHistory(autoChatHistory);
	}
	
	public List<AutoChatHistory> findList(AutoChatHistory autoChatHistory) {
		return super.findList(autoChatHistory);
	}
	public List<String> findChatFriendsList(String weChatId){
		return autoChatHistoryDao.findChatFriendsList(weChatId);
	}
	public String getRecentlyTime(String weChatId,String weChatFriends){
		return autoChatHistoryDao.getRecentlyTime(weChatId,weChatFriends);
	}
	public List<String> searchChatDetails(String weChatId,String weChatFriends,String weChatdate){
		return autoChatHistoryDao.searchChatDetails(weChatId,weChatFriends,weChatdate);
	}
	public Integer findAllNum(String deviceId){
		return autoChatHistoryDao.findAllNum(deviceId);
	}
	public Integer findMinChaId(String deviceId) {
		return autoChatHistoryDao.findMinChaId(deviceId);
	}
	public Integer deleteMinCha(Integer minChaId) {
		return autoChatHistoryDao.deleteMinCha(minChaId);
	}
}
