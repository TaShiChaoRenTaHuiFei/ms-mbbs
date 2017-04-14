package com.mingsoft.bbs.parser.impl;

import java.util.List;

import com.mingsoft.bbs.entity.ModeratorEntity;
import com.mingsoft.parser.IParser;

/**
 * 
 *<p>
 * <b>铭飞-BBS论坛平台</b> 
 *</p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇铭飞科技有限公司
 * </p>
 * 
 * @author 李书宇
 * 
 * @version 140-000-000
 * 
 *          <p>
 *          版权所有 铭飞科技
 *          </p>
 *
 *	        @ClassName: ModeratorNameParser
 *
 *			@Description: 版主描述标签
 *
 *          <p>
 *          Comments:  继承的类 || 实现的接口
 *          </p>
 * 
 *          <p>
 *          Creatr Date:2015-5-15 下午6:53:57
 *          </p>
 * 
 *          <p>
 *          Modification history:暂无
 *          </p>
 *          
 *          版主名称{ms:field.moderator/}
 */
public class ModeratorNameParser extends IParser{


	/**
	 * 版主名称{ms:field.moderator/}
	 */
	private final static String MODERATOR_NAME = "\\{ms:field.moderator/\\}";
	
	private List<ModeratorEntity> moderator;
	
	
	/**
	 * 构造标签的属性
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public ModeratorNameParser(String htmlContent,List<ModeratorEntity> moderator){
		super.htmlCotent = htmlContent;
		this.moderator = moderator;
	}
	
	
	@Override
	public String parse() {
		super.newCotent = moderator.get(0).getModeratorPeople().getPeopleName();
		htmlCotent =  super.replaceAll(MODERATOR_NAME);
		return htmlCotent;
	}
}
