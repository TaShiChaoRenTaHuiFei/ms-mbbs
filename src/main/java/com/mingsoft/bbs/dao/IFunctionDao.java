package com.mingsoft.bbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.bbs.entity.FunctionEntity;

/**
 * mbbs功能实体持久化层接口
 * @Package com.mingsoft.bbs.dao
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月08日<br/>
 * 历史修订：<br/>
 */
public interface IFunctionDao  extends IBaseDao{
	
	/**
	 * 根据应用id查询功能列表
	 * @param appId 应用id
	 * @return 功能列表
	 */
	List<FunctionEntity> queryByAppId(@Param("appId")int appId);
	
	/**
	 * 批量删除数据
	 * @param appId 应用id
	 * @param functionIds 功能id集合
	 */
	void delete(@Param("appId")int appId,@Param("functionIds")int[] functionIds);
	
	/**
	 * 根据应用id和功能的方法名查询实体
	 * @param appId 应用id
	 * @param functionMethod 功能的方法名
	 * @return 功能实体
	 */
	FunctionEntity getByFunctionMethod(@Param("appId")int appId,@Param("functionMethod")String functionMethod);
	
	/**
	 * 根据组id和版主id查询功能列表
	 * @param appId 应用id
	 * @param groupId 组id
	 * @param moderatorTypeId 版主类型id
	 * @param forumId 板块id
	 * @return 功能列表
	 */
	List<FunctionEntity> queryByGroupIdAndModeratorTypeId(@Param("appId")int appId,@Param("groupId")int groupId,@Param("moderatorTypeId")int moderatorTypeId,@Param("forumId")int forumId);
}
