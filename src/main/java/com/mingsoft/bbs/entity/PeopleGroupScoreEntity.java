package com.mingsoft.bbs.entity;


import com.mingsoft.base.entity.BaseEntity;

/**
 * 用户组与积分类型（与com.mingsoft.bank.entity.BankScoreEntity）的的关联实体
 * @Package com.mingsoft.bbs.entity
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月23日<br/>
 * 历史修订：<br/>
 */
public class PeopleGroupScoreEntity extends BaseEntity{
	
	/**
	 * 关联的分类id
	 */
	private int peopleGroupScoreGroupId;
	
	
	
	
	/**
	 * 用户组的最小积分
	 */
	private double peopleGroupScoreMinScore;
	
	
	/**
	 * 用户组的最大积分数
	 */
	private double peopleGroupScoreMaxScore;

	
	/**
	 * 对应的积分类型id与bankScore中的id绑定
	 */
	private int peopleGroupScoreBankScoreId;
	
	
	
	/**
	 * 关联的积分类型的名称
	 */
	private String peopleGroupScoreBankScoreTitle;
	
	

	public int getPeopleGroupScoreGroupId() {
		return peopleGroupScoreGroupId;
	}


	public void setPeopleGroupScoreGroupId(int peopleGroupScoreGroupId) {
		this.peopleGroupScoreGroupId = peopleGroupScoreGroupId;
	}

	public double getPeopleGroupScoreMinScore() {
		return peopleGroupScoreMinScore;
	}


	public void getPeopleGroupScoreMinScore(double peopleGroupScoreMinScore) {
		this.peopleGroupScoreMinScore = peopleGroupScoreMinScore;
	}


	


	public double getPeopleGroupScoreMaxScore() {
		return peopleGroupScoreMaxScore;
	}


	public void setPeopleGroupScoreMaxScore(double peopleGroupScoreMaxScore) {
		this.peopleGroupScoreMaxScore = peopleGroupScoreMaxScore;
	}


	public void setPeopleGroupScoreMinScore(double peopleGroupScoreMinScore) {
		this.peopleGroupScoreMinScore = peopleGroupScoreMinScore;
	}


	public int getPeopleGroupScoreBankScoreId() {
		return peopleGroupScoreBankScoreId;
	}


	public void setPeopleGroupScoreBankScoreId(int peopleGroupScoreBankScoreId) {
		this.peopleGroupScoreBankScoreId = peopleGroupScoreBankScoreId;
	}


	


	public String getPeopleGroupScoreBankScoreTitle() {
		return peopleGroupScoreBankScoreTitle;
	}


	public void setPeopleGroupScoreBankScoreTitle(String peopleGroupScoreBankScoreTitle) {
		this.peopleGroupScoreBankScoreTitle = peopleGroupScoreBankScoreTitle;
	}
}
