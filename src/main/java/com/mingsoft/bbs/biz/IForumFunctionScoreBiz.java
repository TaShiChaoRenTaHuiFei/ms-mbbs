package com.mingsoft.bbs.biz;

import java.util.List;


import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.bbs.entity.ForumFunctionScoreEntity;

/**
 * 不同板块的不同功能的积分奖励规则实体接口
 * @Package com.mingsoft.bbs.biz
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月03日<br/>
 * 历史修订：<br/>
 */
public interface IForumFunctionScoreBiz extends IBaseBiz {
	/**
	 * 根据用户组id和查询不同功能对应的不同类型的积分的奖励规则
	 * @param groupId 用户组id
	 * @return 不同功能对应的不同类型的积分的奖励规则
	 */
	List<ForumFunctionScoreEntity> queryByGroupIdAndForumId(int groupId,int forumId);
	
	/**
	 * 根据组id和板块id以及功能id查询不同积分类型的积分奖励规则
	 * @param groupId 组id
	 * @param forumId 板块id
	 * @param functionId 功能id
	 * @return 查询不同积分类型的积分奖励规则
	 */
	List<ForumFunctionScoreEntity> queryByGroupIdAndForumIdAndFunctionId(int groupId,int forumId,int functionId);
}
