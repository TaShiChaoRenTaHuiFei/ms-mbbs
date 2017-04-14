package com.mingsoft.bbs.biz;

import java.util.List;

import com.mingsoft.basic.biz.IBasicBiz;
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
 *         @ClassName: IPeopleCategoryBiz
 *
 * 		@Description: TODO 用户绑定栏目的业务逻辑层
 *
 *          <p>
 *          Comments:  继承IBasicBiz类
 *          </p>
 * 
 *          <p>
 *          Creatr Date:2015-4-16 上午9:55:49
 *          </p>
 * 
 *          <p>
 *          Modification history:
 *          </p>
 */
public interface IModeratorBiz extends  IBasicBiz{
	
	
	
	
	
	/**
	 * 根据板块id和多个版主id删除版主信息
	 * @param forumId 板块id
	 * @param ids 版主id集合
	 */
	public void deleteByForumId(int forumId,String[] ids);
	
	/**
	 * 根据会员帐号和板块id获取版主信息
	 * @param forumId 板块id
	 * @param peopleId 会员id
	 * @return 版主实体
	 */
	public ModeratorEntity getEntity(int forumId,int peopleId);
	
	/**
	 * 根据站点id和板块id查询版主关联的实体集合
	 * @param appId   站点id
	 * @param forumId 板块id
	 * @return  版主关联的实体集合
	 */
	public List<ModeratorEntity> queryByForumId(int appId,int forumId);
	
	/**
	 * 获取版主实体根据用户id
	 * @param appId 应用id
	 * @param forumId 板块id
	 * @param peopleId 用户id
	 * @return 版主实体
	 */
	public ModeratorEntity getModeratorByPeopleId(int appId,int forumId,int peopleId);
}
