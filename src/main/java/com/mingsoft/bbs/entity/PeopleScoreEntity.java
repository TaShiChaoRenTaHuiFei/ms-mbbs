package com.mingsoft.bbs.entity;

import com.mingsoft.base.entity.BaseEntity;

/**
 * mbbs对用户不同积分的类型统计实体，继承BaseEntity
 * @Package com.mingsoft.bbs.entity
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月21日<br/>
 * 历史修订：<br/>
 */
public class PeopleScoreEntity  extends BaseEntity{
	
	/**
	 * 关联的用户id
	 */
	private int peopleScorePeopleId;
	
	/**
	 * 关联的积分类型id
	 */
	private int peopleScoreBankScoreId;
	
	/**
	 * 对应关联的积分类型的总积分
	 */
	private int peopleScoreBankScoreCount;
	
	/**
	 * 对应关联积分类型的已用的总积分
	 */
	private int peopleScoreUsedBankScoreCount;

	public int getPeopleScorePeopleId() {
		return peopleScorePeopleId;
	}

	public void setPeopleScorePeopleId(int peopleScorePeopleId) {
		this.peopleScorePeopleId = peopleScorePeopleId;
	}

	public int getPeopleScoreBankScoreId() {
		return peopleScoreBankScoreId;
	}

	public void setPeopleScoreBankScoreId(int peopleScoreBankScoreId) {
		this.peopleScoreBankScoreId = peopleScoreBankScoreId;
	}

	public int getPeopleScoreBankScoreCount() {
		return peopleScoreBankScoreCount;
	}

	public void setPeopleScoreBankScoreCount(int peopleScoreBankScoreCount) {
		this.peopleScoreBankScoreCount = peopleScoreBankScoreCount;
	}

	public int getPeopleScoreUsedBankScoreCount() {
		return peopleScoreUsedBankScoreCount;
	}

	public void setPeopleScoreUsedBankScoreCount(int peopleScoreUsedBankScoreCount) {
		this.peopleScoreUsedBankScoreCount = peopleScoreUsedBankScoreCount;
	}
	
	
}
