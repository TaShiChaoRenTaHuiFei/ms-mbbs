package com.mingsoft.bbs.entity;


import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.people.entity.PeopleEntity;

/**
 * 
 *  <p>
 * <b>铭飞-BBS论坛平台</b>
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇铭飞科技有限公司
 * </p>
 * 
 * @author guoph
 * 
 * @version 300-001-001
 * 
 *          <p>
 *          版权所有 铭飞科技
 *          </p>
 *
 *         @ClassName: PeopleCategoryEntity
 *
 * 		@Description: TODO 用户绑定栏目的实体类
 *
 *          <p>
 *          Comments:  继承BasicEntity类
 *          </p>
 * 
 *          <p>
 *          Creatr Date:2015-4-16 上午9:19:04
 *          </p>
 * 
 *          <p>
 *          Modification history:
 *          </p>
 
 */
public class ModeratorEntity extends BaseEntity {
	
	/**
	 * 用户id
	 */
	private int moderatorPeopleId;

	/**
	 * 分类id
	 */
	private int moderatorForumId;
	
	/**
	 * 版主类型id
	 */
	private int moderatorTypeId;
	

	
	/**
	 * 版主实体
	 */
	private PeopleEntity moderatorPeople;
	
	/**
	 * 版主类型实体
	 */
	private CategoryEntity moderatorType;

	
	
	public int getModeratorPeopleId() {
		return moderatorPeopleId;
	}

	public void setModeratorPeopleId(int moderatorPeopleId) {
		this.moderatorPeopleId = moderatorPeopleId;
	}

	public int getModeratorForumId() {
		return moderatorForumId;
	}

	public void setModeratorForumId(int moderatorForumId) {
		this.moderatorForumId = moderatorForumId;
	}

	public int getModeratorTypeId() {
		return moderatorTypeId;
	}

	public void setModeratorTypeId(int moderatorTypeId) {
		this.moderatorTypeId = moderatorTypeId;
	}

	

	

	public PeopleEntity getModeratorPeople() {
		return moderatorPeople;
	}

	public void setModeratorPeople(PeopleEntity moderatorPeople) {
		this.moderatorPeople = moderatorPeople;
	}

	public CategoryEntity getModeratorType() {
		return moderatorType;
	}

	public void setModeratorType(CategoryEntity moderatorType) {
		this.moderatorType = moderatorType;
	}
	
	
	
}
