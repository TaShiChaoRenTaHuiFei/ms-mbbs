package com.mingsoft.bbs.entity;

import java.util.Date;

import com.mingsoft.basic.entity.CategoryEntity;


/**
 * 
 * 
 * 
 * 
 * <p>
 * <b>铭飞科技</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * @author killfen
 * 
 * <p>
 * Comments:论坛板块
 * </p>
 * 
 * <p>
 * Create Date:2015-5-2
 * </p>
 * 
 * <p>
 * Modification history:
 * </p>
 */
public class ForumEntity extends CategoryEntity{
	
	
	/**
	 * 板块id
	 */
	private int forumId;
	
	/**
	 * 帖子总数
	 */
	private int forumSubjectCount;
	
	/**
	 * 最后发帖时间
	 */
	private Date forumLastSubjectTime;
	
	
	/**
	 * 今日发帖总数
	 */
	private int forumTodaySubjectCount;
	
	/**
	 * 昨日发帖总数
	 */
	private int forumYesterdaySubjectCount;
	
	/**
	 * 评论总数
	 */
	private int forumCommentCount;
	
	
	
	
	
	/**
	 * 帖子总数
	 */
	private int forumTotalSubject;
	
	
	/**
	 * 最后评论时间
	 */
	private Date forumLastCommentTime;
	
	
	
	public int getForumSubjectCount() {
		return forumSubjectCount;
	}
	
	
	public void setForumSubjectCount(int forumSubjectCount) {
		this.forumSubjectCount = forumSubjectCount;
	}
	public int getForumCommentCount() {
		return forumCommentCount;
	}
	
	public void setForumCommentCount(int forumCommentCount) {
		this.forumCommentCount = forumCommentCount;
	}
	
	public int getForumTodaySubjectCount() {
		return forumTodaySubjectCount;
	}
	
	public void setForumTodaySubjectCount(int forumTodaySubjectCount) {
		this.forumTodaySubjectCount = forumTodaySubjectCount;
	}
	
	public int getForumYesterdaySubjectCount() {
		return forumYesterdaySubjectCount;
	}
	
	public void setForumYesterdaySubjectCount(int forumYesterdaySubjectCount) {
		this.forumYesterdaySubjectCount = forumYesterdaySubjectCount;
	}
	
	
	public int getForumId() {
		return forumId;
	}
	
	public void setForumId(int forumId) {
		this.forumId = forumId;
	}
	
	public int getForumTotalSubject() {
		return forumTotalSubject;
	}
	
	public void setForumTotalSubject(int forumTotalSubject) {
		this.forumTotalSubject = forumTotalSubject;
	}
	
	public Date getForumLastSubjectTime() {
		return forumLastSubjectTime;
	}
	
	public void setForumLastSubjectTime(Date forumLastSubjectTime) {
		this.forumLastSubjectTime = forumLastSubjectTime;
	}
	public Date getForumLastCommentTime() {
		return forumLastCommentTime;
	}
	public void setForumLastCommentTime(Date forumLastCommentTime) {
		this.forumLastCommentTime = forumLastCommentTime;
	}
	
	
	
	
	
	
	
	
	
}
