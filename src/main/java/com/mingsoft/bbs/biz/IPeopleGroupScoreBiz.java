package com.mingsoft.bbs.biz;

import java.util.List;

import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.bbs.entity.PeopleGroupScoreEntity;

/**
 * 用户组与积分类型（与com.mingsoft.bank.entity.BankScoreEntity）的业务层接口
 * @Package com.mingsoft.bbs.entity
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月04日<br/>
 * 历史修订：<br/>
 */
public interface IPeopleGroupScoreBiz extends IBaseBiz {
	
	/**
	 * 根据用户组id查询出不同积分类型的对应的积分类型
	 * @param peopleGroupId  用户组id
	 * @return 不同积分类型的对应的积分类型
	 */
	List<PeopleGroupScoreEntity> queryByPeopleGroupId(int peopleGroupId);
	
	
}
