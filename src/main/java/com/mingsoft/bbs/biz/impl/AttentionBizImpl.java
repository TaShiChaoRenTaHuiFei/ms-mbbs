/**
 * 
 */
package com.mingsoft.bbs.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.mingsoft.attention.entity.BasicAttentionEntity;
import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import net.mingsoft.attention.constant.e.AttentionTypeEnum;
import com.mingsoft.bbs.biz.IAttentionBiz;
import com.mingsoft.bbs.dao.IAttentionDao;
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
 * @Description: 帖子关注业务层
 *
 * <p>
 * Create Date:2015-6-18 上午1:53:32
 * </p>
 * 
 * <p>
 * Modification history:
 * </p>
 */
@Service("bbsAttentionBiz")
public class AttentionBizImpl extends BaseBizImpl implements IAttentionBiz{
	
	/**
	 * 注入帖子关注持久化层
	 */
	@Resource(name="bbsAttentionDao")
	private IAttentionDao attentionDao;
	
	/**
	 * 关联dao
	 */
	@Override
	protected IBaseDao getDao() {
		return this.attentionDao;
	}
	
	
	@Override
	public List<BasicAttentionEntity> queryPageAndSbujectByPeopleIdAndAppId(
			int peopleId, int appId, int attentionType,	PageUtil page) {
		return this.attentionDao.queryPageAndSbujectByPeopleIdAndAppId(peopleId, appId, attentionType, page);
	}

	@Override
	public int countByPeople(int peopleId, int appId,
			AttentionTypeEnum attentionType, int modelId) {
		return this.attentionDao.countByPeople(peopleId, appId, attentionType.toInt(), modelId);
	}
	

	
	
}
