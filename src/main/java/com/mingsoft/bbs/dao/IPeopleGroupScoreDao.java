package com.mingsoft.bbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.bbs.entity.PeopleGroupScoreEntity;

/**
 * 用户组与积分类型（与com.mingsoft.bank.entity.BankScoreEntity）的持久化层接口
 * @Package com.mingsoft.bbs.entity
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月04日<br/>
 * 历史修订：<br/>
 */
public interface IPeopleGroupScoreDao extends IBaseDao{
	
	/**
	 * 根据用户组id查询出不同积分类型的对应的积分类型
	 * @param peopleGroupId
	 * @return
	 */
	List<PeopleGroupScoreEntity> queryByPeopleGroupId(@Param("peopleGroupId")int peopleGroupId);
	
	/**
	 * 根据用户组id和板块id删除用户组与积分绑定
	 * @param groupId  用户组id
	 * @param forumId·板块id
	 */
	public void deleteByGroupId(@Param("groupId")int groupId);
}
