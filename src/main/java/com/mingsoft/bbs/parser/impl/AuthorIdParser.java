package com.mingsoft.bbs.parser.impl;

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
 *	        @ClassName: SubjectAuthorIconParser
 *
 *			@Description: TODO类的描述
 *
 *          <p>
 *          Comments:  继承IParser
 *          </p>
 * 
 *          <p>
 *          Creatr Date:2015-5-6 下午4:14:57
 *          </p>
 * 
 *          <p>
 *          Modification history:暂无
 *          </p>
 *          
 */
public class AuthorIdParser extends IParser{


	
	/**
	 * 发帖用户头像标签
	 */ 
	private final static String SUBJECT_ID_FIELD="\\{ms:field.author.id/\\}";
	
	/**
	 * 构造标签的属性
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public AuthorIdParser(String htmlContent,String newContent){
		super.htmlCotent = htmlContent;
		super.newCotent = newContent;
	}
	
	
	@Override
	public String parse() {
		return super.replaceAll(SUBJECT_ID_FIELD);
	}
}
