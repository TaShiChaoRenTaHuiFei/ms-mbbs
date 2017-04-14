package com.mingsoft.bbs.entity;

import com.mingsoft.base.entity.BaseEntity;

/**
 * mbbs功能与用户组关联实体
 * @Package com.mingsoft.bbs.entity
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月08日<br/>
 * 历史修订：<br/>
 */
public class GroupFunctionEntity extends BaseEntity{
	/**
	 * 关联的功能id
	 */
	private int groupFunctionFunctionId;
	
	/**
	 * 关联的版主分类或用户组id
	 */
	private int groupFunctionGroupId;
	
	/**
	 * 关联的板块id
	 */
	private int groupFunctionForumId;

	public int getGroupFunctionFunctionId() {
		return groupFunctionFunctionId;
	}

	public void setGroupFunctionFunctionId(int groupFunctionFunctionId) {
		this.groupFunctionFunctionId = groupFunctionFunctionId;
	}
	
	

	public int getGroupFunctionGroupId() {
		return groupFunctionGroupId;
	}

	public void setGroupFunctionGroupId(int groupFunctionGroupId) {
		this.groupFunctionGroupId = groupFunctionGroupId;
	}

	public int getGroupFunctionForumId() {
		return groupFunctionForumId;
	}

	public void setGroupFunctionForumId(int groupFunctionForumId) {
		this.groupFunctionForumId = groupFunctionForumId;
	}
	
	

	
	
	
	
}
