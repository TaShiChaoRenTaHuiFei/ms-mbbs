package com.mingsoft.bbs.entity;

import com.mingsoft.base.entity.BaseEntity;

/**
 * mbbs对用户所发帖的类型统计实体，继承BaseEntity
 * @Package com.mingsoft.bbs.entity
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月21日<br/>
 * 历史修订：<br/>
 */
public class PeopleSubjectTypeEntity extends BaseEntity{
	
	/**
	 * 关联的用户id
	 */
	private int peopleSubjectTypePeopleId;
	
	/**
	 * 关联的帖子类型（如精华）id
	 */
	private int peopleSubjectTypeSubjectTypeId;
	
	/**
	 * 对应的帖子类型的帖子总数。（如精华帖的总数）
	 */
	private int peopleSubjectTypeCount;
	
	

	public int getPeopleSubjectTypePeopleId() {
		return peopleSubjectTypePeopleId;
	}

	public void setPeopleSubjectTypePeopleId(int peopleSubjectTypePeopleId) {
		this.peopleSubjectTypePeopleId = peopleSubjectTypePeopleId;
	}

	public int getPeopleSubjectTypeSubjectTypeId() {
		return peopleSubjectTypeSubjectTypeId;
	}

	public void setPeopleSubjectTypeSubjectTypeId(int peopleSubjectTypeSubjectTypeId) {
		this.peopleSubjectTypeSubjectTypeId = peopleSubjectTypeSubjectTypeId;
	}

	public int getPeopleSubjectTypeCount() {
		return peopleSubjectTypeCount;
	}

	public void setPeopleSubjectTypeCount(int peopleSubjectTypeCount) {
		this.peopleSubjectTypeCount = peopleSubjectTypeCount;
	}
	
	
	
}
