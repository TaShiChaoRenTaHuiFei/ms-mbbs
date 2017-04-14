/**
 * 
 */
package com.mingsoft.bbs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.bbs.constant.Const;
import com.mingsoft.bbs.constant.ModelCode;

/**
 * 
 * 
 *  
 * <p>
 * <b>铭飞科技</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * @author killfen
 * 
 * <p>
 * Comments:
 * </p>
 * 
 * <p>
 * Create Date:2015-3-24
 * </p>
 * 
 * <p>
 * Modification history:
 * </p>
 */ 
public class BaseAction extends com.mingsoft.people.action.BaseAction{
	/**
	 * 业务层的注入
	 */
	@Autowired
	private ICategoryBiz categoryBiz;
	/**
	 * 读取当前应用版主类型
	 * @param request
	 * @return
	 */
	public List<CategoryEntity> queryModerator(HttpServletRequest request) {
//		ModelCode.BBS_MODERATOR;
		int appId=this.getAppId(request);
		//获取版主类型模块编号
		int modelIds= this.getModelCodeId(request, ModelCode.BBS_MODERATOR);
		//查询版主类型
		 List<CategoryEntity> categorys = categoryBiz.queryByAppIdOrModelId(appId, modelIds);
		return categorys;
	}
	
	/**
	 * 读取当前帖子类型
	 * @param request
	 * @return
	 */
	public List<CategoryEntity>  querySubjectType(HttpServletRequest request) {
//		ModelCode.BBS_SUBJECT_TYPE;
		int appId=this.getAppId(request);
		//获取帖子类型模块编号
		int modelIds= this.getModelCodeId(request, ModelCode.BBS_SUBJECT_TYPE);
		//查询帖子类型
		 List<CategoryEntity> categorys = categoryBiz.queryByAppIdOrModelId(appId, modelIds);
		return categorys;
	}
	
	/**
	 * 读取国际化资源文件
	 * 
	 * @param key
	 *            ，键值
	 * @return字符串
	 */
	protected String getResString(String key) {
		return super.getResString(key, Const.RESOURCES);
	}
	
}
