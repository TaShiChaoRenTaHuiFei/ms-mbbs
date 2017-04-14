package com.mingsoft.bbs.parser.impl;

import com.mingsoft.parser.IParser;
/**
 * 	<p>
 *	  <b>铭飞-BBS论坛平台</b>
 *	</p>
 *
 *	标签：{ms:field.total.today/}
 *	@version 300-001-001
 * 	@ClassName: SubjectTodayCountParser
 * 	@Description: 论坛今天的发帖总数
 * 	@author 刘跃卫 
 * 
 *	<p>
 *		Creatr Date:2015-4-22 下午5:25:48
 *	</p>
 * 
 * 	<p>
 *		Modification history:
 *	</p>
 */
public class TotalTodayParser extends IParser{
	
	/**
	 * 今天的发帖总数标签
	 */
	private final static String TODAY_COUNT="\\{ms:field.total.today/\\}";
	
	
	/**
	 * 构造标签的属性
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public TotalTodayParser(String htmlContent,String newContent){
		super.htmlCotent = htmlContent;
		super.newCotent = newContent;
	}

	/**
	 * 标签替换
	 */
	@Override
	public String parse() {
		return super.replaceAll(TODAY_COUNT);
	}

}
