package com.mingsoft.bbs.entity;

import java.util.Date;

import com.mingsoft.base.entity.BaseEntity;

/**
 * 用户积分变更日志
 * @Package com.mingsoft.bbs.entity
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月03日<br/>
 * 历史修订：<br/>
 */
public class PeopleScoreLogEntity  extends BaseEntity{
	
	/**
	 *日志自增长id
	 */
	private int peopleScoreLogId;
	
	/**
	 * 关联用户id
	 */
	private int peopleScoreLogPeopleId;
	
	/**
	 * 关联操作的板块id
	 */
	private int peopleScoreLogForumId;
	
	/**
	 * 关联的功能id
	 */
	private int peopleScoreLogFunctionId;
	
	/**
	 * 变更的积分类型
	 */
	private int peopleScoreLogBankScoreId;
	
	/**
	 * 变更的积分数量
	 */
	private double peopleScoreLogScoreNum;
	
	/**
	 * 积分变更时间
	 */
	private Date peopleScoreTime = new Date();
	
	
	
	public int getPeopleScoreLogId() {
		return peopleScoreLogId;
	}

	public void setPeopleScoreLogId(int peopleScoreLogId) {
		this.peopleScoreLogId = peopleScoreLogId;
	}

	public int getPeopleScoreLogPeopleId() {
		return peopleScoreLogPeopleId;
	}

	public void setPeopleScoreLogPeopleId(int peopleScoreLogPeopleId) {
		this.peopleScoreLogPeopleId = peopleScoreLogPeopleId;
	}

	public int getPeopleScoreLogForumId() {
		return peopleScoreLogForumId;
	}

	public void setPeopleScoreLogForumId(int peopleScoreLogForumId) {
		this.peopleScoreLogForumId = peopleScoreLogForumId;
	}

	public int getPeopleScoreLogFunctionId() {
		return peopleScoreLogFunctionId;
	}

	public void setPeopleScoreLogFunctionId(int peopleScoreLogFunctionId) {
		this.peopleScoreLogFunctionId = peopleScoreLogFunctionId;
	}

	public int getPeopleScoreLogBankScoreId() {
		return peopleScoreLogBankScoreId;
	}

	public void setPeopleScoreLogBankScoreId(int peopleScoreLogBankScoreId) {
		this.peopleScoreLogBankScoreId = peopleScoreLogBankScoreId;
	}

	public double getPeopleScoreLogScoreNum() {
		return peopleScoreLogScoreNum;
	}

	public void setPeopleScoreLogScoreNum(double peopleScoreLogScoreNum) {
		this.peopleScoreLogScoreNum = peopleScoreLogScoreNum;
	}

	public Date getPeopleScoreTime() {
		return peopleScoreTime;
	}

	public void setPeopleScoreTime(Date peopleScoreTime) {
		this.peopleScoreTime = peopleScoreTime;
	}
	
	
	
}
