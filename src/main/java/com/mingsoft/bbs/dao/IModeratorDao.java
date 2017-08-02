package com.mingsoft.bbs.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.bbs.entity.ModeratorEntity;

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
 *         @ClassName: IPeopleCategoryDao
 *
 * 		@Description: TODO 用户绑定栏目的持久化层
 *
 *          <p>
 *          Comments:  继承IBasicDao类 
 *          </p>
 * 
 *          <p>
 *          Creatr Date:2015-4-16 上午9:45:56
 *          </p>
 * 
 *          <p>
 *          Modification history:
 *          </p>
 */
public interface IModeratorDao extends IBaseDao {
	
	
	/**
	 * 根据板块id和版主id集合删除版主信息
	 * @param forumId 板块id
	 * @param ids 版主id集合
	 */
	void deleteByForumId(@Param("forumId")int forumId,@Param("ids")String[] ids);
	
	
	
	
	/**
	 * 根据peopleName和板块id获取版主信息
	 * @param forumId 板块id
	 * @param peopleId 会员Id
	 * @return  版主实体
	 */
	ModeratorEntity getByPeopleId(@Param("forumId")int forumId,@Param("peopleId")int peopleId);
	
	/**
	 * 查询站点下指定板块所有版主信息
	 * @param appId 站点id
	 * @param forumId板块id
	 * @return 该板块下所有版主集合
	 */
	List<ModeratorEntity>  queryByForumId(@Param("appId")int appId,@Param("forumId")int forumId);
	
	
	/**
	 * 根据用户判断该是否对该板块有操作权限
	 * @param appid 站点id
	 * @param forumIds 板块数组
	 * @param peopleName 会员帐号
	 * @return  版主实体
	 */
	ModeratorEntity getByPeopleIdAndForumIds(@Param("appId")int appId,@Param("forumIds")int[] forumIds,@Param("peopleId")int peopleId); 
}
