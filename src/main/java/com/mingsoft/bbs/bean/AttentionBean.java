package com.mingsoft.bbs.bean;

import java.sql.Timestamp;
import java.util.Date;


/**
 * <p>
 * <b>铭飞-科技</b>
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * @author 石马人山
 * 
 * @version 300-001-001
 * 
 * @Description: 帖子的关注实体
 *
 * <p>
 * Creatr Date:2015-6-17 上午11:45:03
 * </p>
 * 
 * <p>
 * Modification history:
 * </p>
 */
public class AttentionBean{

	/**
	 * 基本实体自增长编号
	 */
	private int basicId;
	
	/**
	 *标题
	 *长度:200 
	 */
	private String basicTitle;

	/**
	 * 点击次数
	 */
	private int basicHit;
	
	/**
	 * 描述
	 * 长度:1500
	 */
	private String basicDescription;
	
	/**
	 * 缩略图
	 * 长度:200
	 */
	private String basicThumbnails;

	/**
	 * 更新时间
	 */
	private Date basicUpdateTime;

	/**
	 * 发布时间
	 */
	private Timestamp basicDateTime;

	/**
	 * 帖子的应用id
	 */
	private int basicAppId;
	
	/**
	 *所属分类编号
	 */
	private int basicCategoryId=0;
	
	/**
	 * 帖子的作者id
	 */
	private int basicPeopleId;
	
	
	
	
	/**
	 * 收藏的自增长id
	 */
	private int basicAttentionId;
	
	/**
	 * 关联的用户id
	 */
	private int basicAttentionPeopleId;
	
	/**
	 * 收藏的类型id：
	 * 		1：收藏
	 * 		2：顶
	 */
	private int basicAttentionType;
	
	/**
	 * 该收藏创建的时间
	 */
	private Date basicAttentionTime;
	
	
	
	
	/**
	 * 帖子属性是否显示
	 */
	private int subjectDisplay;
	
	/**
	 * 帖子关联的版块id
	 */
	private int categoryId;
	
	/**
	 * 所属板块名称
	 */
	private String categoryTitle;
	
	/**
	 * 帖子今日的评论总数
	 */
	private int totalComment;
	
	/**
	 * 帖子最后的评论总数
	 */
	private int lastCommentTime;

	public int getBasicPeopleId() {
		return basicPeopleId;
	}

	public void setBasicPeopleId(int basicPeopleId) {
		this.basicPeopleId = basicPeopleId;
	}

	public int getBasicId() {
		return basicId;
	}

	public void setBasicId(int basicId) {
		this.basicId = basicId;
	}

	public String getBasicTitle() {
		return basicTitle;
	}

	public void setBasicTitle(String basicTitle) {
		this.basicTitle = basicTitle;
	}

	public int getBasicHit() {
		return basicHit;
	}

	public void setBasicHit(int basicHit) {
		this.basicHit = basicHit;
	}

	public String getBasicDescription() {
		return basicDescription;
	}

	public void setBasicDescription(String basicDescription) {
		this.basicDescription = basicDescription;
	}

	public String getBasicThumbnails() {
		return basicThumbnails;
	}

	public void setBasicThumbnails(String basicThumbnails) {
		this.basicThumbnails = basicThumbnails;
	}

	public Date getBasicUpdateTime() {
		return basicUpdateTime;
	}

	public void setBasicUpdateTime(Date basicUpdateTime) {
		this.basicUpdateTime = basicUpdateTime;
	}

	public Timestamp getBasicDateTime() {
		return basicDateTime;
	}

	public void setBasicDateTime(Timestamp basicDateTime) {
		this.basicDateTime = basicDateTime;
	}

	public int getBasicAppId() {
		return basicAppId;
	}

	public void setBasicAppId(int basicAppId) {
		this.basicAppId = basicAppId;
	}

	public int getBasicCategoryId() {
		return basicCategoryId;
	}

	public void setBasicCategoryId(int basicCategoryId) {
		this.basicCategoryId = basicCategoryId;
	}

	public int getBasicAttentionId() {
		return basicAttentionId;
	}

	public void setBasicAttentionId(int basicAttentionId) {
		this.basicAttentionId = basicAttentionId;
	}

	public int getBasicAttentionPeopleId() {
		return basicAttentionPeopleId;
	}

	public void setBasicAttentionPeopleId(int basicAttentionPeopleId) {
		this.basicAttentionPeopleId = basicAttentionPeopleId;
	}

	public int getBasicAttentionType() {
		return basicAttentionType;
	}

	public void setBasicAttentionType(int basicAttentionType) {
		this.basicAttentionType = basicAttentionType;
	}

	public Date getBasicAttentionTime() {
		return basicAttentionTime;
	}

	public void setBasicAttentionTime(Date basicAttentionTime) {
		this.basicAttentionTime = basicAttentionTime;
	}

	public int getSubjectDisplay() {
		return subjectDisplay;
	}

	public void setSubjectDisplay(int subjectDisplay) {
		this.subjectDisplay = subjectDisplay;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public int getTotalComment() {
		return totalComment;
	}

	public void setTotalComment(int totalComment) {
		this.totalComment = totalComment;
	}

	public int getLastCommentTime() {
		return lastCommentTime;
	}

	public void setLastCommentTime(int lastCommentTime) {
		this.lastCommentTime = lastCommentTime;
	}
	
	
}
