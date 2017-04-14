package com.mingsoft.bbs.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.bbs.entity.PeopleGroupEntity;

/**
 * 用户与分类的关联持久化层接口
 * @Package com.mingsoft.people.dao
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月23日<br/>
 * 历史修订：<br/>
 */
public interface IPeopleGroupDao  extends IBaseDao  {
	
	/**
	 * 根据应用id和模块查询用户分组列表
	 * @param appId 应用id
	 * @param modelId  模块id
	 * @param orderBy 依据排序字段
	 * @param order false 升序 ，true 倒序
	 * @return
	 */
	public List<PeopleGroupEntity> queryByAppIdAndModelId(@Param("appId")int appId,@Param("modelId")int modelId,@Param("orderBy")String orderBy,@Param("order")boolean order);
	
	/**
	 * 根据用户组id查询用户分组信息
	 * @param categoryId
	 * @return
	 */
	public PeopleGroupEntity getPeopleGroup(@Param("groupId")int groupId);
	
	/**
	 * 获取最后一个或第一个用户组
	 * @param appId 应用id
	 * @param modelId 模块id
	 * @param isLast 是否是领取最后一个true表示领取最后一个 ，false:表示领取第一个用户组信息
	 * @return  最后一个或第一个用户组
	 */
	public Integer getLastOrFirstPeopleGroupId(@Param("appId")int appId,@Param("modelId")int modelId,@Param("isLast")boolean isLast);
	
	/**
	 * 批量删除用户组
	 * @param groupIds 用户组id集合
	 */
	public void delete(@Param("groupIds")int[] groupIds);
	
	/**
	 * 查询上一个用户等级实体或下一个用户等级实体
	 * @param groupId  
	 * @param appId 应用id
	 * @param modelId 模块id
	 * @param isNext true:表示获取下一个，false获取上一个
	 */
	public Integer getNextOrPrePeopleGroupId(@Param("groupId")int groupId,@Param("appId")int appId,@Param("modelId")int modelId,@Param("isNext")boolean isNext);
	
	
}
