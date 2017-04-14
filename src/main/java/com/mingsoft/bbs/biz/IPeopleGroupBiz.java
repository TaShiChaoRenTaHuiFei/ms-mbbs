package com.mingsoft.bbs.biz;

import java.util.List;
import java.util.Map;


import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.bbs.entity.ForumFunctionScoreEntity;
import com.mingsoft.bbs.entity.GroupFunctionEntity;
import com.mingsoft.bbs.entity.PeopleGroupEntity;
import com.mingsoft.bbs.entity.PeopleGroupScoreEntity;

/**
 * 
 * 用户组业务层接口
 * @author 史爱华
 * @version 
 * 版本号：【100-000-000】
 * 创建日期：2015年11月23日 
 * 历史修订：
 */
public interface IPeopleGroupBiz extends ICategoryBiz {
	
	/**
	 * 根据应用id和模块id查询用户分组列表
	 * @param appId  应用id
	 * @param modelId 模块id
	 * @param orderBy 依据排序字段
	 * @param order 排序方式
	 * @return 用户分组列表
	 */
	public List<PeopleGroupEntity> queryByAppIdAndModelId(int appId,int modelId,String orderBy,boolean order);
	
	/**
	 * 保存用户分组的信息
	 * @param groupId 用户组id
	 * @param peopleGroup 用户组关联的积分类型列表
	 * @param groupFunctionList 用户组绑定的功能列表
	 * @param forumFunctionScoreList 用户组绑定的积分奖励规则
	 */
	public void savePeopleGroupInfo(CategoryEntity category,List<PeopleGroupScoreEntity> peopleGroupList,List<GroupFunctionEntity> groupFunctionList,List<ForumFunctionScoreEntity> forumFunctionScoreList);
	
	/**
	 * 根据用户id获取该用户所在的用户组
	 * @param peopleId
	 * @return
	 */
	public Map getPeopleGroupInfo(int peopleId,int appId);
	
	/**
	 * 获取最后一个或第一个用户组
	 * @param appId 应用id
	 * @param modelId 模块id
	 * @param isLast 是否是领取最后一个true表示领取最后一个 ，false:表示领取第一个用户组信息
	 * @return  最后一个或第一个用户组
	 */
	public PeopleGroupEntity getLastOrFirstPeopleGroup(int appId,boolean isLast);
	
	/**
	 * 批量删除用户组
	 * @param groupIds 用户组id集合
	 */
	public void delete(int[] groupIds);
	
	
	/**
	 * 更新用户分组的信息
	 * @param groupId 用户组id
	 * @param peopleGroup 用户组关联的积分类型列表
	 * @param groupFunctionList 用户组绑定的功能列表
	 * @param forumFunctionScoreList 用户组绑定的积分奖励规则
	 */
	public void updatePeopleGroupInfo(CategoryEntity category,List<PeopleGroupScoreEntity> peopleGroupList,List<GroupFunctionEntity> groupFunctionList,List<ForumFunctionScoreEntity> forumFunctionScoreList);
	
	/**
	 * 查询上一个用户等级实体
	 * @param groupId  
	 * @param appId 应用id
	 * @param modelId 模块id
	 */
	public PeopleGroupEntity getNextPeopleGroup(int groupId,int appId);
	
	/**
	 * 查询上一个用户等级实体
	 * @param groupId  
	 * @param appId 应用id
	 * @param modelId 模块id
	 */
	public PeopleGroupEntity getPrePeopleGroup(int groupId,int appId);
	
	/**
	 * 获取用户所在的用户组
	 * @param peopleId 用户id
	 * @param appId 应用id
	 * @return 用户组实体
	 */
	public PeopleGroupEntity getPeopleGroup(int peopleId,int appId);
}
