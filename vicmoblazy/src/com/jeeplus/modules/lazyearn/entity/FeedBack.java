package com.jeeplus.modules.lazyearn.entity;
import java.sql.Date;

import com.jeeplus.common.persistence.DataEntity;
/**
 * 
 * 懒赚账户实体 
 * @author Stephen
 * @version 2017-9-27
 *
 */
public class FeedBack extends DataEntity<FeedBack>{
	
	private static final long serialVersionUID = 1L;
	
	private Integer feedbackId;// 主键ID
	private String name;	//账户名
	private String title;// 主题
	private String content; // 内容
	private String createtime; //创建时间
	private Date updatetime;//更新时间
	private String software;//软件
	private Boolean deleteflag;//是否删除（1：删除状态）
	public FeedBack() {
		super();
	}
	public FeedBack(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public Integer getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getSoftware() {
		return software;
	}
	public void setSoftware(String software) {
		this.software = software;
	}
	public Boolean getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(Boolean deleteflag) {
		this.deleteflag = deleteflag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}