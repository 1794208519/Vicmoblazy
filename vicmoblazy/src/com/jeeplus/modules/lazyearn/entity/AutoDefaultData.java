package com.jeeplus.modules.lazyearn.entity;
import com.jeeplus.common.persistence.DataEntity;
/**
 * 
 * @author Stephen
 * @version 2017-9-27
 *
 */
public class AutoDefaultData extends DataEntity<AutoDefaultData>{
	
	private static final long serialVersionUID = 1L;
	
	private Integer defaultDataId;		// 主键ID
	private String wxNearbyNum;	//微信附近人自定义数据
	private String wxFriendNum;//微信一键加好友自定义数据
	private String wxGroupfriendNum; //微信一键加群友自定义数据
	private String wxPublicNum; //公众号自定义数据
	private String wxDriftbottleNum; //微信漂流瓶自定义数据
	private String wxCircleNum; // 微信朋友圈自定义数据
	private String wxNearbyContent; // 微信附近人打招呼
	private String wxPublicIndex; //公众号从第几个添加
	private String wxPublicfriendNum; //公众号推荐人数
	private String wxDriftbottleContent; //漂流瓶内容
	private String wxCircleContent; //朋友圈评论内容
	private String wxAkeyContent; //
	private String devices; //设备号
	private Boolean deleteFlag;//逻辑删除（1：删除状态）
	public AutoDefaultData() {
		super();
	}
	public AutoDefaultData(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public Integer getDefaultDataId() {
		return defaultDataId;
	}
	public void setDefaultDataId(Integer defaultDataId) {
		this.defaultDataId = defaultDataId;
	}
	public String getWxNearbyNum() {
		return wxNearbyNum;
	}
	public void setWxNearbyNum(String wxNearbyNum) {
		this.wxNearbyNum = wxNearbyNum;
	}
	public String getWxFriendNum() {
		return wxFriendNum;
	}
	public void setWxFriendNum(String wxFriendNum) {
		this.wxFriendNum = wxFriendNum;
	}
	public String getWxGroupfriendNum() {
		return wxGroupfriendNum;
	}
	public void setWxGroupfriendNum(String wxGroupfriendNum) {
		this.wxGroupfriendNum = wxGroupfriendNum;
	}
	public String getWxPublicNum() {
		return wxPublicNum;
	}
	public void setWxPublicNum(String wxPublicNum) {
		this.wxPublicNum = wxPublicNum;
	}
	public String getWxDriftbottleNum() {
		return wxDriftbottleNum;
	}
	public void setWxDriftbottleNum(String wxDriftbottleNum) {
		this.wxDriftbottleNum = wxDriftbottleNum;
	}
	public String getWxCircleNum() {
		return wxCircleNum;
	}
	public void setWxCircleNum(String wxCircleNum) {
		this.wxCircleNum = wxCircleNum;
	}
	public String getWxNearbyContent() {
		return wxNearbyContent;
	}
	public void setWxNearbyContent(String wxNearbyContent) {
		this.wxNearbyContent = wxNearbyContent;
	}
	public String getWxPublicIndex() {
		return wxPublicIndex;
	}
	public void setWxPublicIndex(String wxPublicIndex) {
		this.wxPublicIndex = wxPublicIndex;
	}
	public String getWxPublicfriendNum() {
		return wxPublicfriendNum;
	}
	public void setWxPublicfriendNum(String wxPublicfriendNum) {
		this.wxPublicfriendNum = wxPublicfriendNum;
	}
	public String getWxDriftbottleContent() {
		return wxDriftbottleContent;
	}
	public void setWxDriftbottleContent(String wxDriftbottleContent) {
		this.wxDriftbottleContent = wxDriftbottleContent;
	}
	public String getWxCircleContent() {
		return wxCircleContent;
	}
	public void setWxCircleContent(String wxCircleContent) {
		this.wxCircleContent = wxCircleContent;
	}
	public String getWxAkeyContent() {
		return wxAkeyContent;
	}
	public void setWxAkeyContent(String wxAkeyContent) {
		this.wxAkeyContent = wxAkeyContent;
	}
	public String getDevices() {
		return devices;
	}
	public void setDevices(String devices) {
		this.devices = devices;
	}
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	
}