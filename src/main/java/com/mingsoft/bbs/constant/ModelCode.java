package com.mingsoft.bbs.constant;

import com.mingsoft.base.constant.e.BaseEnum;

/**
 * 
 * 
 * 
 * <p>
 * <b>铭飞科技J2EE基础框架</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2013 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇铭飞科技有限公司
 * </p>
 * 
 * @author killfen
 * 
 * @version 100-000-000
 * 
 *          <p>
 *          版权所有
 *          </p>
 * 
 *          <p>
 *          Comments: 模块编号说明：八位整型数据<br/>
 *          项目编号(2位)+模块编号(2位)+功能编号(2位)+子功能编号(2位)<br/>
 *          如：01(微信项目编号)01(微页面模块编号)01(模版管理编号)01(添加模版)<br/>
 *          若为：01010100则代表整个模块管理功能模块<br/>
 *          </p>
 * 
 *          <p>
 *          Create Date:2013-9-4
 *          </p>
 * 
 *          <p>
 *          Modification history:暂无
 *          </p>
 */
public enum ModelCode implements BaseEnum{

	
	/**
	 * bbs板块列表
	 */
	BBS_CATEGORY("14990000"),
	
	/**
	 * bbs帖子类型,如，置顶，精华、热门等。方便后台自定义
	 */
	BBS_SUBJECT_TYPE("14990100"),
	
	/**
	 * bbs版主类型，如果版主、小版主
	 */
	BBS_MODERATOR("14990200"),
	
	/**
	 * bbs帖子列表
	 */
	BBS_SUBJECT("14980000"),
	
	/**
	 * bbs用户分组
	 */
	BBS_PEOPLE_CATEGORY("14990300"),
	
	/**
	 * bbs评论
	 */
	BBS_COMMENT("14990400");
	
	ModelCode(String code) {
		this.code = code;
	}

	private String code;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return code;
	}

	public int toInt() {
		// TODO Auto-generated method stub
		return Integer.parseInt(code);
	}
}
