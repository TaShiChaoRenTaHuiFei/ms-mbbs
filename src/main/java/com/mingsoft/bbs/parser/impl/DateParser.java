package com.mingsoft.bbs.parser.impl;

import java.util.Date;

import com.mingsoft.parser.IParser;
import com.mingsoft.parser.IParserRegexConstant;

/**
 * 帖子发布时间(单标签)
 * {ms:field.date fmt=yyyy-mm-dd hh:mm:ss/}
 * @author 杨新远
 * 技术支持：景德镇铭飞科技
 * 官网：www.ming-soft.com
 */
public class DateParser extends IParser {
	/**
	 * 帖子发布时间(单标签)标签
	 */
	private final static String SUBJECT_DATE="\\{ms:field.date\\s{0,}(fmt=(.*?))?/}";
	
	/**
	 * 构造标签的属性
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public DateParser(String htmlContent,Date newContent){
		super.htmlCotent = htmlContent;
		super.newCotent = date(newContent, htmlContent);
	}
	
	@Override
	public String parse() {
		// TODO Auto-generated method stub
		return super.replaceAll(SUBJECT_DATE);
	}
	
	/**
	 * 将时间格式转换成字符串型并改变时间的显示格式
	 * @param date 数据库中的时间
	 * @param htmlCotent 读取时间格式的HTML代码
	 * @return 时间
	 */
	private String date(Date date,String htmlCotent){
		//从HTML代码中取出时间的显示格式，默认为yyyy-MM-dd hh:mm:ss形式
		String typeDate = IParserRegexConstant.REGEX_DATE;
		String fmt = parseFirst(htmlCotent,SUBJECT_DATE, 2);
		if(fmt!= null){
			typeDate = fmt;
		}
		//将时间转换成String型
		String srtDate = IParserRegexConstant.REGEX_DATE_ERRO;
		if(date != null){
			try{
				java.text.SimpleDateFormat forDate = new java.text.SimpleDateFormat(typeDate);
				srtDate = forDate.format(date);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return srtDate;
	}
}
