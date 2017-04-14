package com.mingsoft.bbs.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.bbs.biz.IFunctionBiz;
import com.mingsoft.bbs.dao.IFunctionDao;
import com.mingsoft.bbs.entity.FunctionEntity;

/**
 * mbbs对应的功能业务层实现类
 * @Package com.mingsoft.bbs.biz.impl
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月08日<br/>
 * 历史修订：<br/>
 */
@Service("functionBiz")
public class FunctionBizImpl extends BaseBizImpl implements IFunctionBiz {
	
	/**
	 * 注入功能持久化
	 */
	@Autowired
	private IFunctionDao functionDao;
	
	@Override
	public List<FunctionEntity> queryByAppId(int appId) {
		// TODO Auto-generated method stub
		return functionDao.queryByAppId(appId);
	}

	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return functionDao;
	}

	@Override
	public void delete(int appId, int[] functionIds) {
		// TODO Auto-generated method stub
		functionDao.delete(appId, functionIds);
	}

	@Override
	public FunctionEntity getByFunctionMethod(int appId, String functionMethod) {
		// TODO Auto-generated method stub
		return functionDao.getByFunctionMethod(appId, functionMethod);
	}

	@Override
	public List<FunctionEntity> queryByGroupIdAndModeratorTypeId(int appId, int groupId, int moderatorTypeId,int forumId) {
		// TODO Auto-generated method stub
		return functionDao.queryByGroupIdAndModeratorTypeId(appId, groupId, moderatorTypeId,forumId);
	}

}
