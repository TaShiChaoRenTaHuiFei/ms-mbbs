package com.mingsoft.bbs.parser.impl;

import com.mingsoft.parser.IParser;

/**
 * 	<p>
 *	  <b>铭飞-BBS论坛平台</b>
 *	</p>
 *	标签：{ms:field.total.subject/}
 *	@version 300-001-001
 * 	@ClassName: TotalSubjectParser
 * 	@Description: 该站点下所有帖子总数标签
 * 	@author 刘跃卫 
 *	<p>
 *		Creatr Date:2015-4-26 上午5:24:49
 *	</p>
 * 
 * 	<p>
 *		Modification history:
 *	</p>
 */
public class TotalSubjectParser extends IParser {

	/**
	 * 该站点下所有帖子总数标签
	 */
	private final static String TOTAL_SUBJECT="\\{ms:field.total.subject/\\}";
	
	/**
	 * 构造标签的属性
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public TotalSubjectParser(String htmlContent,String newContent){
		super.htmlCotent = htmlContent;
		super.newCotent = newContent;
	}
	@Override
	public String parse() {
		// TODO Auto-generated method stub
		return super.replaceAll(TOTAL_SUBJECT);
	}
}
