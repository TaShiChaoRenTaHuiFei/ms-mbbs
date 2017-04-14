package com.mingsoft.bbs.entity;


import java.util.ArrayList;
import java.util.List;

import com.mingsoft.basic.entity.CategoryEntity;

/**
 * mbbs用户组实体，继承CategoryEntity
 * @Package com.mingsoft.bbs.entity
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月23日<br/>
 * 历史修订：<br/>
 */
public class PeopleGroupEntity extends CategoryEntity{
	
	/**
	 * 用户组对应的积分类型的范围列表
	 */
	private List<PeopleGroupScoreEntity> peopleGroupScoreList = new ArrayList<PeopleGroupScoreEntity>();
	
	
	
	public List<PeopleGroupScoreEntity> getPeopleGroupScoreList() {
		return peopleGroupScoreList;
	}

	public void setPeopleGroupScoreList(List<PeopleGroupScoreEntity> peopleGroupScoreList) {
		this.peopleGroupScoreList = peopleGroupScoreList;
	}

	
	
	
}
