package com.mingsoft.bbs.parser.impl;

import com.mingsoft.parser.IParser;

/**
 * mbbs 解析用户发帖总数标签
 * @Package com.mingsoft.bbs.parser.impl;
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月15日<br/>
 * 历史修订：<br/>
 */
public class TotalPeopleSubjectParser  extends IParser{
	/**
	 *
	 */
	private final static String PEOPLE_SUBJECT_COUNT="\\{ms:field.total.peoplesubject/\\}";
	
	
	/**
	 * 构造标签的属性
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public TotalPeopleSubjectParser(String htmlContent,String newContent){
		super.htmlCotent = htmlContent;
		super.newCotent = newContent;
	}
	
	
	@Override
	public String parse() {
		return super.replaceAll(PEOPLE_SUBJECT_COUNT);
	}
}
