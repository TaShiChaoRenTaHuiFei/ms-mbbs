package com.mingsoft.bbs.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.biz.impl.BasicBizImpl;
import com.mingsoft.basic.dao.ICategoryDao;
import com.mingsoft.basic.dao.IModelDao;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.bbs.biz.IModeratorBiz;
import com.mingsoft.bbs.dao.IModeratorDao;
import com.mingsoft.bbs.entity.ModeratorEntity;
import com.mingsoft.util.StringUtil;

/**
 * 
 *  <p>
 * <b>铭飞-BBS论坛平台</b>
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇铭飞科技有限公司
 * </p>
 * 
 * @author guoph
 * 
 * @version 300-001-001
 * 
 *          <p>
 *          版权所有 铭飞科技
 *          </p>
 *
 *         @ClassName: PeopleCategoryBizImpl
 *
 * 		@Description: TODO 用户绑定栏目的业务逻辑层实现
 *
 *          <p>
 *          Comments:  继承BasicBizImpl类 || 实现IPeopleCategoryBiz接口
 *          </p>
 * 
 *          <p>
 *          Creatr Date:2015-4-16 上午10:28:11
 *          </p>
 * 
 *          <p>
 *          Modification history:
 *          </p>
 
 */
@Service("bbsModeratorBizImpl")
public class ModeratorBizImpl  extends BasicBizImpl implements IModeratorBiz{

	/**
	 * 注入持久化
	 */
	@Autowired
	private IModeratorDao mbbsModeratorDao;
	
	/**
	 * 类别持久化层的注入
	 */
	@Autowired
	private ICategoryDao categoryDao;
	
	@Autowired
	private ICategoryBiz categoryBiz;
	
	/**
	 * 模块管理持久化层
	 */
	@Autowired
	private IModelDao modelDao;
	
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return mbbsModeratorDao;
	}

	@Override
	public void deleteByForumId(int forumId,String[] ids) {
		// TODO Auto-generated method stub
		mbbsModeratorDao.deleteByForumId(forumId, ids);
	}
	
	


	

	@Override
	public List<ModeratorEntity> queryByForumId(int appId, int forumId) {
		// TODO Auto-generated method stub
		return mbbsModeratorDao.queryByForumId(appId, forumId);
	}

	@Override
	public ModeratorEntity getEntity(int forumId, int peopleId) {
		// TODO Auto-generated method stub
		return mbbsModeratorDao.getByPeopleId(forumId, peopleId);
	}

	
	
	
	

	@Override
	public ModeratorEntity getModeratorByPeopleId(int appId, int forumId, int peopleId) {
		//获取父栏目集合
		String parserIds = "";//categoryDao.queryParentIds(forumId);
		ModelEntity model= modelDao.getEntityByModelCode( com.mingsoft.bbs.constant.ModelCode.BBS_CATEGORY.toString());
		//获取子分类集合
		int[] childrenIds = categoryBiz.queryChildrenCategoryIds(forumId,model.getModelId(),  model.getModelId());
		ModeratorEntity moderator =null;
		if (!StringUtil.isBlank(parserIds)) {
			String[] _ids = parserIds.split(",");
			moderator=mbbsModeratorDao.getByPeopleIdAndForumIds(appId,StringUtil.stringsToInts(_ids),peopleId);
		}else if(childrenIds.length != 0 || childrenIds!=null){
			moderator= mbbsModeratorDao.getByPeopleIdAndForumIds(appId,childrenIds,peopleId);
		}
		return moderator;
	}

	
}
