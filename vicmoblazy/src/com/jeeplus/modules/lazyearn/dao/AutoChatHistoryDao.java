package com.jeeplus.modules.lazyearn.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.lazyearn.entity.AutoChatHistory;
@MyBatisDao
public interface AutoChatHistoryDao extends CrudDao<AutoChatHistory>{
	Integer insertChatHistory(AutoChatHistory autoChatHistory);
	Integer updataChatHistory(AutoChatHistory autoChatHistory);
	List<String> findChatFriendsList(String weChatId);
	String getRecentlyTime(String weChatId,String weChatFriends);
	List<String> searchChatDetails(String weChatId,String weChatFriends,String weChatdate);
	Integer findAllNum(String deviceId);
	Integer findMinChaId(String deviceId);
	Integer deleteMinCha(Integer minChaId);
	
	
}
