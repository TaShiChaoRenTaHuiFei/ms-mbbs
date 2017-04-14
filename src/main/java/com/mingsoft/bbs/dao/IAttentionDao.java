/**
 * 
 */
package com.mingsoft.bbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import net.mingsoft.attention.entity.BasicAttentionEntity;
import com.mingsoft.base.dao.IBaseDao;
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
 * @Description: bbs关注模块持久化层
 *
 * <p>
 * Create Date:2015-6-17 下午11:01:58
 * </p>
 * 
 * <p>
 * Modification history:
 * </p>
 */
@Component("bbsAttentionDao")
public interface IAttentionDao extends IBaseDao {
	
	
	/**
	 * 根据用户ID和APPID查询用户关注内容数量
	 * @param peopleId 用户ID
	 * @param appId 应用ID
	 * @param attentionType 关注类型
	 * @return 数量
	 */
	public int countByPeople(@Param("peopleId")int peopleId,@Param("appId")int appId,@Param("attentionType") int attentionType,@Param("modelId") Integer modelId);

	/**
	 * 根据用户ID和APPID查询用户对bbs帖子的关注列表(带分页)
	 * @param peopleId 用户ID
	 * @param appId 应用ID
	 * @param page 分页
	 * @param attentionType 积分类型
	 * @return 用户关注列表
	 */
	public List<BasicAttentionEntity> queryPageAndSbujectByPeopleIdAndAppId(@Param("peopleId")int peopleId,@Param("appId")int appId,@Param("attentionType") int attentionType,@Param("page")PageUtil page);
	
}
