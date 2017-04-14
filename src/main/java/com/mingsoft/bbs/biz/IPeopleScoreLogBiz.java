package com.mingsoft.bbs.biz;

import java.util.List;

import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.bbs.bean.PeopleScoreBean;
import com.mingsoft.bbs.entity.PeopleScoreLogEntity;

/**
 * 用户积分变更日志业务层接口
 * @Package com.mingsoft.bbs.biz
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月03日<br/>
 * 历史修订：<br/>
 */
public interface IPeopleScoreLogBiz extends IBaseBiz {
	
	/**
	 * 根据用户id查询用户的积分变更日志列表
	 * @param peopleId 用户id
	 * @return 积分变更日志列表
	 */
	List<PeopleScoreLogEntity> queryByPeopleId(int peopleId);
	
	/**
	 * 根据用户id查询用户每种积分类型对应的变更记录
	 * @param peopleId 用户id
	 * @return
	 */
	List<PeopleScoreBean> queryByPeopleIdGroupByScoreTypeId(int peopleScoreLogPeopleId);
	
	/**
	 * 批量保存用户的积分变更日志
	 * @param peopleId 用户id
	 * @param appId 应用id
	 * @param forumId 板块id
	 */
	void saveBatch(int peopleId,int appId,int forumId,String functionMethod);
}
