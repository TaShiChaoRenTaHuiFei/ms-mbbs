package com.mingsoft.bbs.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.bbs.biz.IForumFunctionScoreBiz;
import com.mingsoft.bbs.dao.IForumFunctionScoreDao;
import com.mingsoft.bbs.entity.ForumFunctionScoreEntity;


/**
 * 不同板块的不同功能的积分奖励规则实体业务层实现类
 * @Package com.mingsoft.bbs.biz.impl
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月03日<br/>
 * 历史修订：<br/>
 */
@Service("forumFunctionScoreBiz")
public class ForumFunctionScoreBizImpl extends BaseBizImpl implements IForumFunctionScoreBiz {
	
	
	/**
	 * 注入不同板块的不同功能的积分奖励规则的持久化曾
	 */
	@Autowired
	private IForumFunctionScoreDao forumFunctionScoreDao;
	
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return forumFunctionScoreDao;
	}

	@Override
	public List<ForumFunctionScoreEntity> queryByGroupIdAndForumId(int groupId,int forumId) {
		// TODO Auto-generated method stub
		return forumFunctionScoreDao.queryList(forumId, groupId,null);
	}

	@Override
	public List<ForumFunctionScoreEntity> queryByGroupIdAndForumIdAndFunctionId(int groupId, int forumId,
			int functionId) {
		// TODO Auto-generated method stub
		return forumFunctionScoreDao.queryList(forumId, groupId, functionId);
	}

}
