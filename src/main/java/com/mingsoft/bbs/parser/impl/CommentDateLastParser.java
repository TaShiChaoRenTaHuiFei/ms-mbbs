package com.mingsoft.bbs.parser.impl;

import java.util.Date;

import com.mingsoft.parser.IParser;
import com.mingsoft.parser.IParserRegexConstant;

/**
 * 	<p>
 *	  <b>铭飞-BBS论坛平台</b>
 *	</p>
 *	标签：{ms:field.date.comment.last fmt=yyyy-mm-dd hh:mm:ss/}
 *	@version 300-001-001
 * 	@ClassName: CommentLastTimeParser
 * 	@Description: 评论最后时间标签
 * 	@author 刘跃卫 
 *	<p>
 *		Creatr Date:2015-4-25 上午9:41:37
 *	</p>
 * 
 * 	<p>
 *		Modification history:
 *	</p>
 */
public class CommentDateLastParser extends IParser{

	/**
	 * 评论最后时间标签
	 */
	private final static String COMMENT_LAST_TIME="\\{ms:field.date.comment.last\\s{0,}(fmt=(.*?))?/}";
	
	/**
	 * 构造标签的属性
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public CommentDateLastParser(String htmlContent,Date newContent){
		super.htmlCotent = htmlContent;
		super.newCotent = date(newContent, htmlContent);
	}
	
	@Override
	public String parse() {
		// TODO Auto-generated method stub
		return super.replaceAll(COMMENT_LAST_TIME);
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
		String fmt = parseFirst(htmlCotent,COMMENT_LAST_TIME, 2);
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
