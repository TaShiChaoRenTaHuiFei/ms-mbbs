package com.mingsoft.bbs.entity;

import java.util.Date;
import java.util.List;

import com.mingsoft.basic.entity.BasicEntity;
import com.mingsoft.people.entity.PeopleUserEntity;
import com.mingsoft.util.StringUtil;

/**
 * 
 * <p>
 * <b>铭飞-BBS论坛平台</b> 
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇铭飞科技有限公司
 * </p>
 * 
 * @author  杨新远
 * 
 * @version 140-000-000
 * 
 *          <p>
 *          版权所有 铭飞科技
 *          </p>
 *
 *	        @ClassName: SubjectEntity
 *
 *			@Description: 帖子实体类
 *
 *          <p>
 *          Comments:  继承BasicEntity（基础类）
 *          </p>
 * 
 *          <p>
 *          Creatr Date:2015-3-24
 *          </p>
 * 
 *          <p>
 *          Modification history:暂无
 *          </p>
 */
public class SubjectEntity extends BasicEntity{

	/**
	 * 关联basic（基本类）中的ID
	 */
	private int subjectBasicId;

	/**
	 * 帖子的内容
	 */
	private String subjectContent;
	
	/**
	 * 帖子是否显示
	 */
	private int subjectDisplay;
	
	/**
	 * 栏目实体
	 */
	private ForumEntity subjectForum;

	/**
	 * 帖子的一级板块id（最多支持三级）
	 */
	private int subjectForumFirstId;

	
	
	/**
	 * 帖子的二级板块id（最多支持三级）
	 */
	private int subjectForumSecondId;
	
	/**
	 * 最后评论人的用户名
	 */
	private String subjectLastCommentPeopleName;
	
	/**
	 * 最后回复评论时间，临时属性，表结构不存在此字段（为确保查找效率调整已帖子表结构中）
	 */
	private Date subjectLastCommentTime;
	
	
	/**
	 * 用户详细实体
	 */
	private PeopleUserEntity subjectPeopleUser;
	
	
	/**
	 * 每条帖子的评论总数,临时属性，表结构不存在此字段（为确保查找效率调整已帖子表结构中）
	 */
	private int subjectTotalComment;
	
	
	

	public int getSubjectBasicId() {
		return subjectBasicId;
	}

	public String getSubjectContent() {
		return subjectContent;
	}

	public int getSubjectDisplay() {
		return subjectDisplay;
	}

	public ForumEntity getSubjectForum() {
		return subjectForum;
	}

	public int getSubjectForumFirstId() {
		return subjectForumFirstId;
	}

	public int getSubjectForumSecondId() {
		return subjectForumSecondId;
	}

	public String getSubjectLastCommentPeopleName() {
		return subjectLastCommentPeopleName;
	}

	public Date getSubjectLastCommentTime() {
		return subjectLastCommentTime;
	}

	public PeopleUserEntity getSubjectPeopleUser() {
		return subjectPeopleUser;
	}

	public int getSubjectTotalComment() {
		return subjectTotalComment;
	}


	public void setSubjectBasicId(int subjectBasicId) {
		this.subjectBasicId = subjectBasicId;
	}

	public void setSubjectContent(String subjectContent) {
		this.subjectContent = subjectContent;
	}

	public void setSubjectDisplay(int subjectDisplay) {
		this.subjectDisplay = subjectDisplay;
	}

	public void setSubjectForum(ForumEntity subjectForum) {
		this.subjectForum = subjectForum;
	}

	public void setSubjectForumFirstId(int subjectForumFirstId) {
		this.subjectForumFirstId = subjectForumFirstId;
	}

	public void setSubjectForumSecondId(int subjectForumSecondId) {
		this.subjectForumSecondId = subjectForumSecondId;
	}

	public void setSubjectLastCommentPeopleName(String subjectLastCommentPeopleName) {
		this.subjectLastCommentPeopleName = subjectLastCommentPeopleName;
	}

	public void setSubjectLastCommentTime(Date subjectLastCommentTime) {
		this.subjectLastCommentTime = subjectLastCommentTime;
		if(subjectLastCommentTime==null) {
			this.subjectLastCommentTime = this.getBasicDateTime();
		}
		
	}

	public void setSubjectPeopleUser(PeopleUserEntity subjectPeopleUser) {
		this.subjectPeopleUser = subjectPeopleUser;
	}

	public void setSubjectTotalComment(int subjectTotalComment) {
		this.subjectTotalComment = subjectTotalComment;
	}
	
}
