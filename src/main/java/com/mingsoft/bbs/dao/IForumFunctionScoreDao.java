package com.mingsoft.bbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.bbs.entity.ForumFunctionScoreEntity;

/**
 * 不同板块的不同功能的积分奖励规则实体持久化接口
 * @Package com.mingsoft.bbs.entity
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月03日<br/>
 * 历史修订：<br/>
 */
public interface IForumFunctionScoreDao extends IBaseDao{
	
	
	/**
	 * 根据板块id,用户组id查询不同功能对应的不同类型的积分的奖励规则
	 * @param forumId 板块id
	 * @param groupId 用户组id
	 * @param functionId 功能id
	 * @return 不同功能对应的不同类型的积分的奖励规则
	 */
	List<ForumFunctionScoreEntity> queryList(@Param("forumId")Integer forumId,@Param("groupId")Integer groupId,@Param("functionId")Integer functionId);
	
	/**
	 * 根据用户组id删除不同功能对应的不同类型的积分的奖励规则
	 * @param groupId 用户组id
	 */
	public void deleteByGroupId(@Param("groupId")Integer groupId);
}