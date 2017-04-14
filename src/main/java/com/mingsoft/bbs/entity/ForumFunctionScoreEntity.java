package com.mingsoft.bbs.entity;

import com.mingsoft.base.entity.BaseEntity;


/**
 * 不同板块的不同功能的积分奖励规则实体
 * @Package com.mingsoft.bbs.entity
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月03日<br/>
 * 历史修订：<br/>
 */
public class ForumFunctionScoreEntity extends BaseEntity{
	
	/**
	 * 关联的板块id
	 */
	private int forumFunctionScoreForumId;
	
	/**
	 * 关联的功能id
	 */
	private int forumFunctionScoreFunctionId;
	
	/**
	 * 关联的组id
	 */
	private int forumFunctionScoreGroupId;
	
	/**
	 * 关联的bank中的积分类型id
	 */
	private int forumFunctionScoreBankScoreId;
	
	/**
	 * 对应增加的积分数量 .正数表示增加积分，负数表示减少积分
	 */
	private double forumFunctionScoreScoreNum;

	
	public int getForumFunctionScoreForumId() {
		return forumFunctionScoreForumId;
	}

	public void setForumFunctionScoreForumId(int forumFunctionScoreForumId) {
		this.forumFunctionScoreForumId = forumFunctionScoreForumId;
	}

	public int getForumFunctionScoreFunctionId() {
		return forumFunctionScoreFunctionId;
	}

	public void setForumFunctionScoreFunctionId(int forumFunctionScoreFunctionId) {
		this.forumFunctionScoreFunctionId = forumFunctionScoreFunctionId;
	}

	public int getForumFunctionScoreGroupId() {
		return forumFunctionScoreGroupId;
	}

	public void setForumFunctionScoreGroupId(int forumFunctionScoreGroupId) {
		this.forumFunctionScoreGroupId = forumFunctionScoreGroupId;
	}

	public int getForumFunctionScoreBankScoreId() {
		return forumFunctionScoreBankScoreId;
	}

	public void setForumFunctionScoreBankScoreId(int forumFunctionScoreBankScoreId) {
		this.forumFunctionScoreBankScoreId = forumFunctionScoreBankScoreId;
	}

	public double getForumFunctionScoreScoreNum() {
		return forumFunctionScoreScoreNum;
	}

	public void setForumFunctionScoreScoreNum(double forumFunctionScoreScoreNum) {
		this.forumFunctionScoreScoreNum = forumFunctionScoreScoreNum;
	}
	
	
	
}
