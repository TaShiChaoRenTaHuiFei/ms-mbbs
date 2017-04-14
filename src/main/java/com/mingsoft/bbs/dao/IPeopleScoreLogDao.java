package com.mingsoft.bbs.dao;

import java.util.List;
import java.util.Map;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.bbs.bean.PeopleScoreBean;
import com.mingsoft.bbs.entity.PeopleScoreLogEntity;

/**
 * 用户积分变更持久化层接口
 * @Package com.mingsoft.bbs.dao
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月03日<br/>
 * 历史修订：<br/>
 */
public interface IPeopleScoreLogDao extends IBaseDao{
	
	/**
	 * 根据用户id查询用户的积分变更日志列表
	 * @param peopleId 用户id
	 * @return 积分变更日志列表
	 */
	List<PeopleScoreLogEntity> queryByPeopleId(int peopleScoreLogPeopleId);
	
	/**
	 * 根据用户id查询用户每种积分类型对应的变更记录
	 * @param peopleId 用户id
	 * @return
	 */
	List<PeopleScoreBean> queryByPeopleIdGroupByScoreTypeId(int peopleScoreLogPeopleId);
}
