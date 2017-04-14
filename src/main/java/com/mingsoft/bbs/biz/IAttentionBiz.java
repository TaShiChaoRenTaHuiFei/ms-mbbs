/**
 * 
 */
package com.mingsoft.bbs.biz;

import java.util.List;

import net.mingsoft.attention.entity.BasicAttentionEntity;
import com.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.attention.constant.e.AttentionTypeEnum;
import com.mingsoft.util.PageUtil;

/**
 * <p>
 * <b>铭飞-科技</b>
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2015 - 2016
 * </p>
 * 
 * @author 石马人山
 * 
 * @version 300-001-001
 * 
 * @Description: bbs帖子关注模块业务层
 *
 * <p>
 * Create Date:2015-6-17 下午10:49:56
 * </p>
 * 
 * <p>
 * Modification history:
 * </p>
 */
public interface IAttentionBiz extends IBaseBiz{
	
	/**
	 * 根据用户ID和APPID查询该用户的帖子关注列表(带分页)
	 * @param peopleId 用户ID
	 * @param appId 应用ID
	 * @param attentionType 类型
	 * 			1：收藏
	 * 			2：顶
	 * @param page 分页
	 * @return 用户关注列表
	 */
	public List<BasicAttentionEntity> queryPageAndSbujectByPeopleIdAndAppId(int peopleId,int appId,int attentionType,PageUtil page);

	
	/**
	 * 根据用户ID和APPID查询用户关注内容数量
	 * @param peopleId 用户ID
	 * @param appId 应用ID
	 * @param modelId 模块编号
	 * @param attentionType 类型
	 * @return 数量
	 */
	public int countByPeople(int peopleId,int appId,AttentionTypeEnum attentionType,int modelId);
	
}
