package com.mingsoft.bbs.parser.impl;

import com.mingsoft.parser.IParser;

/**
 * 	<p>
 *	  <b>铭飞-BBS论坛平台</b>
 *	</p>
 *	标签：{ms:field.total.people/}
 *	@version 300-001-001
 * 	@ClassName: TotalPeopleParser
 * 	@Description: 统计会员总数标签
 * 	@author 刘跃卫 
 *	<p>
 *		Creatr Date:2015-4-26 上午1:30:57
 *	</p>
 * 
 * 	<p>
 *		Modification history:
 *	</p>
 */
public class TotalPeopleParser extends IParser{

	/**
	 * 统计会员总数标签
	 */
	private final static String TOTAL_PEOPLE="\\{ms:field.total.people/\\}";
	
	
	/**
	 * 构造标签的属性
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public TotalPeopleParser(String htmlContent,String newContent){
		super.htmlCotent = htmlContent;
		super.newCotent = newContent;
	}

	/**
	 * 标签替换
	 */
	@Override
	public String parse() {
		return super.replaceAll(TOTAL_PEOPLE);
	}

}
