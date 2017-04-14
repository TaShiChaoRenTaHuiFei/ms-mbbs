package com.mingsoft.bbs.parser.impl;

import com.mingsoft.parser.IParser;

/**
 * 	<p>
 *	  <b>铭飞-BBS论坛平台</b>
 *	</p>
 *	标签：{ms:field.newpeople/}
 *	@version 300-001-001
 * 	@ClassName: NewPeopleParser
 * 	@Description: 获取论坛新注册用户标签
 * 	@author 刘跃卫 
 *	<p>
 *		Creatr Date:2015-4-26 上午3:31:29
 *	</p>
 * 
 * 	<p>
 *		Modification history:
 *	</p>
 */
public class NewPeopleParser extends IParser{

	/**
	 * 帖子点击量
	 */
	private final static String NEW_PEOPLE="\\{ms:field.newpeople/\\}";
	
	/**
	 * 构造标签的属性
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public NewPeopleParser(String htmlContent,String newContent){
		super.htmlCotent = htmlContent;
		super.newCotent = newContent;
	}
	
	@Override
	public String parse() {
		return super.replaceAll(NEW_PEOPLE);
	}
}
