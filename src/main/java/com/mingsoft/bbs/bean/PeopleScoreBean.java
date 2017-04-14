package com.mingsoft.bbs.bean;

/**
 * 用户各积分类型的积分使用情况
 * @Package com.mingsoft.bbs.bean
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月03日<br/>
 * 历史修订：<br/>
 */
public class PeopleScoreBean {
	
	/**
	 * 用户id
	 */
	private int peopleScorePeopelId;
	
	/**
	 * 用户已用积分
	 */
	private double peopleScoreUsedScore;
	
	/**
	 * 用户总积分
	 */
	private double peopleScoreTotalScore;
	
	/**
	 * 用户关联的积分类型id
	 */
	private int peopleScoreBankScoreId;
	
	/**
	 * 积分类型对应的标题
	 */
	private String peopleScoreBankScoreTitle;

	public int getPeopleScorePeopelId() {
		return peopleScorePeopelId;
	}

	public void setPeopleScorePeopelId(int peopleScorePeopelId) {
		this.peopleScorePeopelId = peopleScorePeopelId;
	}

	public double getPeopleScoreUsedScore() {
		return peopleScoreUsedScore;
	}

	public void setPeopleScoreUsedScore(double peopleScoreUsedScore) {
		this.peopleScoreUsedScore = peopleScoreUsedScore;
	}

	public double getPeopleScoreTotalScore() {
		return peopleScoreTotalScore;
	}

	public void setPeopleScoreTotalScore(double peopleScoreTotalScore) {
		this.peopleScoreTotalScore = peopleScoreTotalScore;
	}

	public int getPeopleScoreBankScoreId() {
		return peopleScoreBankScoreId;
	}

	public void setPeopleScoreBankScoreId(int peopleScoreBankScoreId) {
		this.peopleScoreBankScoreId = peopleScoreBankScoreId;
	}

	public String getPeopleScoreBankScoreTitle() {
		return peopleScoreBankScoreTitle;
	}

	public void setPeopleScoreBankScoreTitle(String peopleScoreBankScoreTitle) {
		this.peopleScoreBankScoreTitle = peopleScoreBankScoreTitle;
	}
	
	
	
	
}
