package com.mingsoft.bbs.biz;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.bbs.entity.FunctionEntity;

/**
 * mbbs对应的功能业务层接口
 * @Package com.mingsoft.bbs.biz
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月08日<br/>
 * 历史修订：<br/>
 */
public interface IFunctionBiz extends IBaseBiz{
	/**
	 * 根据应用id查询功能列表
	 * @param appId 应用id
	 * @return 功能列表
	 */
	List<FunctionEntity> queryByAppId(int appId);
	
	/**
	 * 批量删除数据
	 * @param appId 应用id
	 * @param functionIds 功能id集合
	 */
	void delete(int appId,int[] functionIds);
	
	
	/**
	 * 根据应用id和功能的方法名查询实体
	 * @param appId 应用id
	 * @param functionMethod 功能的方法名
	 * @return 功能实体
	 */
	FunctionEntity getByFunctionMethod(int appId,String functionMethod);
	
	/**
	 * 根据组id和版主id查询功能列表
	 * @param appId 应用id
	 * @param groupId 组id
	 * @param moderatorTypeId 版主类型id
	 * @return 功能列表
	 */
	List<FunctionEntity> queryByGroupIdAndModeratorTypeId(int appId,int groupId,int moderatorTypeId,int forumId);
}
