package com.mingsoft.bbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.bbs.entity.GroupFunctionEntity;

/**
 * mbbs功能与版主分类关联持久化层接口
 * @Package com.mingsoft.bbs.dao
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月08日<br/>
 * 历史修订：<br/>
 */
public interface IGroupFunctionDao extends IBaseDao{
	
	/**
	 * 根据版块id查询各组对应的功能列表
	 * @param forumId 版块id
	 * @return 各版主类型对应的功能列表
	 */
	List<GroupFunctionEntity> queryByForumIdOrGroupId(@Param("forumId")Integer forumId,@Param("groupId")Integer groupId);
	
	
	
	/**
	 * 根据板块id删除版主类型与功能关联实体
	 * @param forumId 板块id
	 */
	void deleteByForumIdAndGroupId(@Param("forumId")int forumId,@Param("groupId")int groupId);
	
	/**
	 * 根据版主类型以及板块id和功能id查询版主与功能关联实体
	 * @param moderatorTypeId 版主类型id
	 * @param forumId  板块id
	 * @param functionId 功能id
	 * @return 版主与功能关联实体
	 */
	GroupFunctionEntity getByFunctionIdAndGroupId(@Param("groupId")int groupId, @Param("forumId")int forumId,@Param("functionId") int functionId);
	
	/**
	 *  根据版主类型以及板块id和功能方法名查询版主与功能关联实体
	 * @param moderatorTypeId 版主类型id
	 * @param forumId 板块id
	 * @param functionMethod 功能方法名
	 * @return 版主与功能关联实体
	 */
	GroupFunctionEntity getByFunctionMethodAndGroupId(@Param("groupId")int groupId, @Param("forumId")int forumId,@Param("functionMethod") String  functionMethod);
	
	
	/**
	 * 根据版块id查询各组对应的功能列表
	 * @param forumId 版块id
	 * @return 各版主类型对应的功能列表
	 */
	List<GroupFunctionEntity> queryByForumIdAndFunctionMethod(@Param("forumId")Integer forumId,@Param("functionMethod")String functionMethod);
	
	/**
	 * 根据组id和板块id删除
	 */
	void deleteByGroupIdAndForumId(@Param("groupId")int groupId, @Param("forumId")int forumId);
}
