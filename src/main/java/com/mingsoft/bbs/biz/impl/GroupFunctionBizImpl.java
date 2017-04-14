package com.mingsoft.bbs.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.bbs.biz.IGroupFunctionBiz;
import com.mingsoft.bbs.biz.IModeratorBiz;
import com.mingsoft.bbs.biz.IPeopleGroupBiz;
import com.mingsoft.bbs.dao.IFunctionDao;
import com.mingsoft.bbs.dao.IGroupFunctionDao;
import com.mingsoft.bbs.entity.FunctionEntity;
import com.mingsoft.bbs.entity.GroupFunctionEntity;
import com.mingsoft.bbs.entity.ModeratorEntity;
import com.mingsoft.bbs.entity.PeopleGroupEntity;

/**
 * mbbs对应的功能业务层实现类
 * @Package com.mingsoft.bbs.biz.impl
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月08日<br/>
 * 历史修订：<br/>
 */
@Service("groupFunctionBiz")
public class GroupFunctionBizImpl extends BaseBizImpl implements IGroupFunctionBiz {
	
	
	/**
	 * 用户组业务层
	 */
	@Autowired
	private IPeopleGroupBiz peopleGroupBiz;
	
	/**
	 * 功能管理业务层
	 */
	@Autowired
	private IFunctionDao functionDao;
	
	
	/**
	 * 注入功能持久化
	 */
	@Autowired
	private IGroupFunctionDao groupFunctionDao;
	
	@Autowired
	private IModeratorBiz moderatorBiz;
	
	@Override
	public List<GroupFunctionEntity> queryByForumId(int forumId){
		// TODO Auto-generated method stub
		return groupFunctionDao.queryByForumIdOrGroupId(forumId,null);
	}

	

	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return groupFunctionDao;
	}

	@Override
	public void saveBatch(List<GroupFunctionEntity> moderatorFunctionList, int forumId,int groupId) {
		groupFunctionDao.deleteByForumIdAndGroupId(forumId,groupId);
		if(moderatorFunctionList!=null && moderatorFunctionList.size()>0){
			this.saveBatch(moderatorFunctionList);
		}
		
	}

	@Override
	public GroupFunctionEntity getByFunctionAndGroupId(int groupId, int forumId, int functionId) {
		// TODO Auto-generated method stub
		return groupFunctionDao.getByFunctionIdAndGroupId(groupId, forumId, functionId);
	}

	@Override
	public GroupFunctionEntity getByFunctionMethodAndGroupId(int groupId, int forumId,
			String functionMethod) {
		// TODO Auto-generated method stub
		return groupFunctionDao.getByFunctionMethodAndGroupId(groupId, forumId, functionMethod);
	}

	@Override
	public List<GroupFunctionEntity> queryByGroupId(int groupId) {
		// TODO Auto-generated method stub
		return groupFunctionDao.queryByForumIdOrGroupId(null, groupId);
	}



	@Override
	public List<GroupFunctionEntity> queryByGroupIdAndForumId(int groupId, int forumId) {
		// TODO Auto-generated method stub
		return groupFunctionDao.queryByForumIdOrGroupId(forumId, groupId);
	}



	@Override
	public boolean isAuth(int peopleId, int forumId, int appId, String functionMethod) {
		//获取用户所在的用户组
		PeopleGroupEntity peopleGroup = peopleGroupBiz.getPeopleGroup(peopleId, appId);
		FunctionEntity function = functionDao.getByFunctionMethod(appId,functionMethod);
		if(function==null){
			return true;
		}
		//判断该用户组是否拥有发帖权限
		GroupFunctionEntity groupFunctionEntity =this.getByFunctionAndGroupId(peopleGroup.getCategoryId(), forumId, function.getFunctionId());
		if(groupFunctionEntity!=null){
			return true;
		}else{
			//判断该用户是否是该板块的版主
			ModeratorEntity moderate = moderatorBiz.getModeratorByPeopleId(appId, forumId, peopleId);
			if(moderate!=null){
				//判断该版主类型是否存在权限
				groupFunctionEntity =this.getByFunctionAndGroupId(moderate.getModeratorTypeId(), 0, function.getFunctionId());
				if(groupFunctionEntity!=null){
					return true;
				}
			}
		}
		return false;
	}



	@Override
	public List<GroupFunctionEntity> queryByForumIdAndFunctionMethod(int forumId, String functionMethod) {
		// TODO Auto-generated method stub
		return this.groupFunctionDao.queryByForumIdAndFunctionMethod(forumId, functionMethod);
	}

	
	
	

}
