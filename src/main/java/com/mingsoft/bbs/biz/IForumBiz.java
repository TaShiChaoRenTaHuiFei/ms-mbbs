package com.mingsoft.bbs.biz;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.bbs.bean.StatisticsBean;
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
 *	        @ClassName: IForumBiz
 *
 *			@Description: BBS首页显示业务层
 *
 *          <p>
 *          Comments:   继承了IBasicBiz
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
public interface IForumBiz extends ICategoryBiz {
	
	/**
	 * 读取板块信息,
	 * @param categoryId 当前栏目id，可选
	 * @param categoryCategoryId 存在表示获取子栏目,可选
	 * @param appId 应用编号
	 * @param modelId 模块编号
	 * @return bbs下的栏目实体
	 */
	public List<ForumEntity> queryForum(Integer categoryId,Integer categoryCategoryId,int appId,int modelId);
	
	
	/**
	 * 统计板块数据,如昨日发帖量,今日发帖量,会员数
	 * @param categoryId 版块编号
	 * @return 0:今日帖子总数 1:昨日帖子总数 2:用户总数
	 */
	public StatisticsBean statistics(Integer categoryId,int appId,int modelId);
	
	/**
	 * 根据栏目编号活取板块信息
	 * @param categoryId 栏目编号
	 * @return 板块信息
	 */
	public ForumEntity  getByForumId(int categoryId,int appid);
	
	/**
	 * 查询当前板块的同级板块
	 * @param categoryId 分类id
	 * @param appid 应用id
	 * @return 子板块列表
	 */
	public List<ForumEntity> querySibling(int categoryId,int appid);
	
	/**
	 * 更新板块的统计信息
	 * @param forum 板块实体
	 */
	public void updateStatisticsInfo(ForumEntity forum);
	
	/**
	 * 保存板块信息
	 * @param forum 板块实体
	 */
	public void saveForum(ForumEntity forum);
}
