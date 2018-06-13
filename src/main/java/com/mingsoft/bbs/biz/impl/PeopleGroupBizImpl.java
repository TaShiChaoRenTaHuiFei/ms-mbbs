package com.mingsoft.bbs.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.basic.biz.IModelBiz;
import com.mingsoft.basic.biz.impl.CategoryBizImpl;
import com.mingsoft.basic.dao.ICategoryDao;
import com.mingsoft.basic.dao.IModelDao;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.bbs.bean.PeopleScoreBean;
import com.mingsoft.bbs.biz.IPeopleGroupBiz;
import com.mingsoft.bbs.dao.IForumFunctionScoreDao;
import com.mingsoft.bbs.dao.IGroupFunctionDao;
import com.mingsoft.bbs.dao.IPeopleGroupDao;
import com.mingsoft.bbs.dao.IPeopleGroupScoreDao;
import com.mingsoft.bbs.dao.IPeopleScoreLogDao;
import com.mingsoft.bbs.entity.ForumFunctionScoreEntity;
import com.mingsoft.bbs.entity.GroupFunctionEntity;
import com.mingsoft.bbs.entity.PeopleGroupEntity;
import com.mingsoft.bbs.entity.PeopleGroupScoreEntity;

/**
 * 
 *用户组业务处理层
 * @author 史爱华
 * @version 
 * 版本号：【100-000-000】
 * 创建日期：2015年12月03日 
 * 历史修订：
 */
@Service("peopleGroupBiz")
public class PeopleGroupBizImpl  extends CategoryBizImpl implements IPeopleGroupBiz{

	/**
	 * 注入用户组持久层
	 */
	@Autowired
	private ICategoryDao categoryDao;
	
	/**
	 * 注入用户组持久还层
	 */
	@Autowired
	private IPeopleGroupDao peopleGroupDao;
	
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return categoryDao;
	}

	
	/**
	 * 注入用户组功能绑定持久化层
	 */
	@Autowired
	private IGroupFunctionDao groupFunctionDao;
	
	/**
	 * 注入功能积分奖励规则持久化层
	 */
	@Autowired
	private IForumFunctionScoreDao forumFunctionScoreDao;
	
	/**
	 * 注入功能积分奖励规则持久化层
	 */
	@Autowired
	private IPeopleGroupScoreDao peopleGroupScoreDao;
	
	/**
	 * 积分变更日志持久化曾
	 */
	@Autowired
	private IPeopleScoreLogDao peopleScoreLogDao;
	
	
	/**
	 * 模块管理持久化层
	 */
	@Autowired
	private IModelDao modelDao;
	
	

	
	@Override
	public void savePeopleGroupInfo(CategoryEntity category, List<PeopleGroupScoreEntity> peopleGroupScoreList,
			List<GroupFunctionEntity> groupFunctionList, List<ForumFunctionScoreEntity> forumFunctionScoreList) {
		categoryDao.saveEntity(category);
		//设置用户组不同积分类型的积分约束条件
		if(peopleGroupScoreList!=null && peopleGroupScoreList.size()>0){
			List<PeopleGroupScoreEntity> newPeopleGroupScoreList = new ArrayList<PeopleGroupScoreEntity>();
			for(int i=0;i<peopleGroupScoreList.size();i++){
				PeopleGroupScoreEntity peopleGroupScore = peopleGroupScoreList.get(i);
				peopleGroupScore.setPeopleGroupScoreGroupId(category.getCategoryId());
				newPeopleGroupScoreList.add(peopleGroupScore);
			}
			peopleGroupScoreDao.saveBatch(newPeopleGroupScoreList);
		}
		
		//设置用户组的功能权限
		if(groupFunctionList!=null && groupFunctionList.size()>0){
			List<GroupFunctionEntity> newGroupFunctionList = new ArrayList<GroupFunctionEntity>();
			for(int i=0;i<groupFunctionList.size();i++){
				GroupFunctionEntity groupFunction = groupFunctionList.get(i);
				groupFunction.setGroupFunctionGroupId(category.getCategoryId());
				newGroupFunctionList.add(groupFunction);
			}
			groupFunctionDao.saveBatch(newGroupFunctionList);
		}
		
		//设置功能操作的积分奖励规则
		if(forumFunctionScoreList!=null && forumFunctionScoreList.size()>0){
			List<ForumFunctionScoreEntity> newForumFunctionScoreList = new ArrayList<ForumFunctionScoreEntity>();
			for(int i=0;i<forumFunctionScoreList.size();i++){
				ForumFunctionScoreEntity forumFunctionScore = forumFunctionScoreList.get(i);
				//groupFunction.setGroupFunctionGroupId(category.getCategoryId());
				forumFunctionScore.setForumFunctionScoreGroupId(category.getCategoryId());
				newForumFunctionScoreList.add(forumFunctionScore);
			}
			forumFunctionScoreDao.saveBatch(newForumFunctionScoreList);
		}
	}

	@Override
	public List<PeopleGroupEntity> queryByAppIdAndModelId(int appId,int modelId,String orderBy,boolean order) {
		// TODO Auto-generated method stub
		return peopleGroupDao.queryByAppIdAndModelId(appId,modelId,orderBy,order);
	}
	
	
	
	
	@Override
	public Map getPeopleGroupInfo(int peopleId,int appId) {
		//查询用户的积分情况
		List<PeopleScoreBean> peopleScoreLogList = peopleScoreLogDao.queryByPeopleIdGroupByScoreTypeId(peopleId);
		Map<String,Object> peopleGroupMap = new HashMap<String,Object>();
		ModelEntity model = modelDao.getEntityByModelCode(com.mingsoft.bbs.constant.ModelCode.BBS_PEOPLE_CATEGORY.toString());
			//默认是需要第一个等级的
			PeopleGroupEntity peopleGroup = this.getLastOrFirstPeopleGroup(appId, false);
			peopleGroupMap.put("peopleGroup", peopleGroup);
			return peopleGroupMap;
	}

	@Override
	public PeopleGroupEntity getLastOrFirstPeopleGroup(int appId, boolean isLast) {
		ModelEntity model = modelDao.getEntityByModelCode(com.mingsoft.bbs.constant.ModelCode.BBS_PEOPLE_CATEGORY.toString());
		Integer peopleGroupId = this.peopleGroupDao.getLastOrFirstPeopleGroupId(appId, model.getModelId(), isLast);
		if(peopleGroupId==null){
			peopleGroupId=0;
		}
		return this.peopleGroupDao.getPeopleGroup(peopleGroupId);
	}

	@Override
	public void delete(int[] groupIds) {
		// TODO Auto-generated method stub
		peopleGroupDao.delete(groupIds);
	}

	@Override
	public void updatePeopleGroupInfo(CategoryEntity category, List<PeopleGroupScoreEntity> peopleGroupScoreList,
			List<GroupFunctionEntity> groupFunctionList, List<ForumFunctionScoreEntity> forumFunctionScoreList) {
		categoryDao.updateEntity(category);
		peopleGroupScoreDao.deleteByGroupId(category.getCategoryId());
		//设置用户组不同积分类型的积分约束条件
		if(peopleGroupScoreList!=null && peopleGroupScoreList.size()>0){
			peopleGroupScoreDao.saveBatch(peopleGroupScoreList);
		}
		
		groupFunctionDao.deleteByGroupIdAndForumId(category.getCategoryId(), 0);
		//设置用户组的功能权限
		if(groupFunctionList!=null && groupFunctionList.size()>0){
			groupFunctionDao.saveBatch(groupFunctionList);
		}
		forumFunctionScoreDao.deleteByGroupId(category.getCategoryId());
		//设置功能操作的积分奖励规则
		if(forumFunctionScoreList!=null && forumFunctionScoreList.size()>0){
			forumFunctionScoreDao.saveBatch(forumFunctionScoreList);
		}
		
	}

	@Override
	public PeopleGroupEntity getNextPeopleGroup(int groupId, int appId) {
		ModelEntity model = modelDao.getEntityByModelCode(com.mingsoft.bbs.constant.ModelCode.BBS_PEOPLE_CATEGORY.toString());
		Integer peopleGroupId =this.peopleGroupDao.getNextOrPrePeopleGroupId(groupId, appId, model.getModelId(), true);
		if(peopleGroupId==null){
			peopleGroupId=0;
		}
		return this.peopleGroupDao.getPeopleGroup(peopleGroupId);
	}

	@Override
	public PeopleGroupEntity getPrePeopleGroup(int groupId, int appId) {
		ModelEntity model = modelDao.getEntityByModelCode(com.mingsoft.bbs.constant.ModelCode.BBS_PEOPLE_CATEGORY.toString());
		Integer peopleGroupId =this.peopleGroupDao.getNextOrPrePeopleGroupId(groupId, appId, model.getModelId(), false);
		if(peopleGroupId==null){
			peopleGroupId=0;
		}
		return this.peopleGroupDao.getPeopleGroup(peopleGroupId);
	}

	@Override
	public PeopleGroupEntity getPeopleGroup(int peopleId, int appId) {
		Map map = this.getPeopleGroupInfo(peopleId, appId);
		if(map!=null){
			return (PeopleGroupEntity) map.get("peopleGroup");
		}
		return null;
	}
	
	

}
