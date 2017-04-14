package com.mingsoft.bbs.biz;

import java.util.List;


import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.bbs.entity.GroupFunctionEntity;

/**
 * mbbs功能与版主分类关联实体业务层接口
 * @Package com.mingsoft.bbs.entity
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月08日<br/>
 * 历史修订：<br/>
 */
public interface IGroupFunctionBiz extends IBaseBiz {
	
	/**
	 * 根据版块id查询各版主类型对应的功能列表
	 * @param forumId 版块id
	 * @return 各版主类型对应的功能列表
	 */
	List<GroupFunctionEntity> queryByForumId(int forumId);
	
	/**
	 * 根据版主类型id查询关联功能列表
	 * @param groupId  版主类型id
	 * @return 关联功能列表
	 */
	List<GroupFunctionEntity> queryByGroupId(int groupId);
	
	/**
	 * 批量保存功能与版主分类关联实体
	 * @param moderatorFunctionList  功能与版主分类关联实体
	 * @param forumId 板块id
	 * @param groupId 组id
	 */
	public void saveBatch(List<GroupFunctionEntity>  moderatorFunctionList,int forumId,int groupId);
	
	
	/**
	 * 根据版主类型以及板块id和功能id查询版主与功能关联实体
	 * @param moderatorTypeId 版主类型id
	 * @param forumId  板块id
	 * @param functionId 功能id
	 * @return 版主与功能关联实体
	 */
	public GroupFunctionEntity getByFunctionAndGroupId(int groupId,int forumId,int functionId);
	
	/**
	 *  根据版主类型以及板块id和功能方法名查询版主与功能关联实体
	 * @param moderatorTypeId 版主类型id
	 * @param forumId 板块id
	 * @param functionMethod 功能方法名
	 * @return 版主与功能关联实体
	 */
	GroupFunctionEntity getByFunctionMethodAndGroupId(int groupId,int forumId,String  functionMethod);
	
	/**
	 * 根据用户组id和板块id查询关联功能列表
	 * @param groupId  用户组id
	 * @param forumId 板块id
	 * @return 关联功能列表
	 */
	List<GroupFunctionEntity> queryByGroupIdAndForumId(int groupId,int forumId);
	
	/**
	 * 根据用户组id和功能方法名查询关联功能列表
	 * @param groupId  用户组id
	 * @param functionMethod 功能方法名
	 * @return 关联功能列表
	 */
	List<GroupFunctionEntity> queryByForumIdAndFunctionMethod(int groupId,String functionMethod);
	
	/**
	 * 根据用户id判断该用户是否拥有某项功能权限
	 * @param peopleId 用户id
	 * @param forumId 板块id
	 * @param appId 应用id
	 * @param functionMethod 功能名称方法
	 * @return false :没有权限  true :拥有权限
	 */
	boolean isAuth(int peopleId,int forumId,int appId,String functionMethod);
}
