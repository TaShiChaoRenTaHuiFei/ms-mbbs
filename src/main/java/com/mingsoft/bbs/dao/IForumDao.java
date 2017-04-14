package com.mingsoft.bbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.bbs.entity.ForumEntity;
/**
 * 
 * 
 * <p>
 * <b>铭飞-BBS论坛平台</b> 
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇铭飞科技有限公司
 * </p>
 * 
 * @author  杨新远
 * 
 * @version 140-000-000
 * 
 *          <p>
 *          版权所有 铭飞科技
 *          </p>
 *
 *	        @ClassName: IForumDao
 *
 *			@Description: 首页数据显示数据层
 *
 *          <p>
 *          Comments:  继承IBasicDao
 *          </p>
 * 
 *          <p>
 *          Creatr Date:2015-3-26
 *          </p>
 * 
 *          <p>
 *          Modification history:暂无
 *          </p>
 */
public interface IForumDao extends IBaseDao{
	
	/**
	 * 查询该站点和模块下该父板块id的子栏目
	 * @param categoryId　当前栏目id
	 * @param categoryCategoryId 父栏目ID
	 * @param appid 站点Id
	 * @param modelId 模块id
	 * @return 板块集合
	 */
	public List<ForumEntity> queryForum(@Param("categoryId")Integer categoryId,@Param("categoryCategoryId")Integer categoryCategoryId,@Param("appId")int appid,@Param("modelId")int modelId);

	/**
	 * 统计板块数据,如昨日发帖量,今日发帖量,会员数
	 * @param categoryId 版块编号
	 * @return 0:今日帖子总数 1:昨日帖子总数 2:用户总数
	 */
	public List statistics(@Param("categoryId")Integer categoryId,@Param("appId")int appid,@Param("modelId")int modelId);
	
	/**
	 * 根据栏目编号活取板块信息
	 * @param categoryId 栏目编号
	 * @return 板块信息
	 */
	public ForumEntity  getByForumId(@Param("categoryId")Integer categoryId,@Param("appId")int appid,@Param("modelId")int modelId);
	
	/**
	 * 更新板块的统计信息
	 * @param forum 板块实体
	 */
	void updateStatisticsInfo(ForumEntity forum);
}
