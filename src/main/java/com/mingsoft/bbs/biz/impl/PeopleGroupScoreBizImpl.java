package com.mingsoft.bbs.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.bbs.biz.IPeopleGroupScoreBiz;
import com.mingsoft.bbs.dao.IPeopleGroupScoreDao;
import com.mingsoft.bbs.entity.PeopleGroupScoreEntity;

/**
 * 用户组与积分类型（与com.mingsoft.bank.entity.BankScoreEntity）的业务处理层
 * @Package com.mingsoft.bbs.entity
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月04日<br/>
 * 历史修订：<br/>
 */
@Service("peopleGroupScoreBiz")
public class PeopleGroupScoreBizImpl extends BaseBizImpl implements IPeopleGroupScoreBiz {
	
	
	/**
	 * 注入用户收货地址持久层
	 */
	@Autowired
	private IPeopleGroupScoreDao peopleGroupScoreDao;
	
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return peopleGroupScoreDao;
	}

	@Override
	public List<PeopleGroupScoreEntity> queryByPeopleGroupId(int peopleGroupId) {
		// TODO Auto-generated method stub
		return peopleGroupScoreDao.queryByPeopleGroupId(peopleGroupId);
	}

}
