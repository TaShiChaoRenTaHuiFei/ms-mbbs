package com.mingsoft.bbs.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.bbs.constant.ModelCode;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.basic.biz.IModelBiz;
import com.mingsoft.basic.biz.impl.CategoryBizImpl;
import com.mingsoft.basic.dao.ICategoryDao;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.bbs.bean.StatisticsBean;
import com.mingsoft.bbs.biz.IForumBiz;
import com.mingsoft.bbs.dao.IForumDao;
import com.mingsoft.bbs.entity.ForumEntity;
/**
 * 
 * 
 * <p>
 * <b>铭飞-BBS论坛平台</b> 
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇铭飞科技有限公司
 * </p>
 * 
 * @author  杨新远
 * 
 * @version 140-000-000
 * 
 *          <p>
 *          版权所有 铭飞科技
 *          </p>
 *
 *	        @ClassName: ForumBizImpl
 *
 *			@Description: BBS首页显示业务层的实现类
 *
 *          <p>
 *          Comments:  继承BasicBizImpl，实现IForumBiz
 *          </p>
 * 
 *          <p>
 *          Creatr Date:2015-3-26
 *          </p>
 * 
 *          <p>
 *          Modification history:暂无
 *          </p>
 */
@Service("IForumBiz")
public class ForumBizImpl extends CategoryBizImpl implements IForumBiz {
	
	/**
	 * 栏目持久化层注入
	 */
	@Autowired
	private IForumDao forumDao;
	
	/**
	 * 模块
	 */
	@Autowired
	private IModelBiz modelBiz;
	
	
	

	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return forumDao;
	}
	
	@Override
	public List<ForumEntity> queryForum(Integer categoryId,Integer categoryCategoryId, int appId, int modelId) {
		// TODO Auto-generated method stub
		return forumDao.queryForum(categoryId,categoryCategoryId,appId,modelId);
	}
	
	@Override
	public StatisticsBean statistics(Integer categoryId,int appId,int modelId) {
		// TODO Auto-generated method stub
		List<Map> list =forumDao.statistics(categoryId,appId,modelId);
		StatisticsBean sb = new StatisticsBean();
		
		sb.setTotal(list.get(0).get("total")+""); //总帖数
		sb.setToday(list.get(1).get("total")+""); //今日总帖
		sb.setYestoday(list.get(2).get("total")+""); // 昨日总贴
		sb.setPeople(list.get(3).get("total")+"");//总人数
		return sb;
	}
	
	@Override
	public ForumEntity getByForumId(int categoryId, int appid) {
		// TODO Auto-generated method stub
		ModelEntity model = modelBiz.getEntityByModelCode(ModelCode.BBS_CATEGORY);
		return forumDao.getByForumId(categoryId, appid,model.getModelId() );
	}

	@Override
	public List<ForumEntity> querySibling(int categoryId,int appId) {
		// TODO Auto-generated method stub
		ForumEntity forum= this.getByForumId(categoryId,appId);
		List<ForumEntity> list = new ArrayList<ForumEntity>();
		if(forum!=null){
			
			list = forumDao.queryForum(categoryId, forum.getCategoryCategoryId(), appId, forum.getCategoryModelId());
		}
		return list;
	}

	@Override
	public void updateStatisticsInfo(ForumEntity forum) {
		//保存本板块的帖子总数
		this.forumDao.updateStatisticsInfo(forum);
		if(forum.getForumId()>0){
			ForumEntity parentForum =(ForumEntity) this.getEntity(forum.getCategoryCategoryId());
			//判断是否存在二级板块
			if(parentForum!=null){
				//存在则对二级板块的帖子数量进行增加
				forum.setForumId(parentForum.getCategoryId());
				forum.setForumCommentCount(parentForum.getForumCommentCount()+1);
				forum.setForumLastSubjectTime(new Date());;
				this.forumDao.updateStatisticsInfo(forum);
				if(parentForum.getCategoryCategoryId()>0){
					ForumEntity parentParentForum = (ForumEntity) this.getEntity(parentForum.getCategoryCategoryId());
					if(parentParentForum!=null){
						//对一级板块的数量进行增加
						forum.setForumId(parentParentForum.getCategoryId());
						forum.setForumCommentCount(parentParentForum.getForumCommentCount()+1);
						forum.setForumLastSubjectTime(new Date());
						this.forumDao.updateStatisticsInfo(forum);
					}
				}
			}
			return;
		}
		
	}

	@Override
	public void saveForum(ForumEntity forum) {
		//forumDao
		this.saveCategoryEntity(forum);
		forum.setForumId(forum.getCategoryId());
		forumDao.saveEntity(forum);
	}
	
	
}
