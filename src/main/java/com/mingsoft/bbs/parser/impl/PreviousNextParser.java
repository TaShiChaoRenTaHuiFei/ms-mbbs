package com.mingsoft.bbs.parser.impl;

import com.mingsoft.basic.entity.BasicEntity;
import com.mingsoft.bbs.constant.Const;
import com.mingsoft.parser.IParser;
import com.mingsoft.util.StringUtil;

/**
 * 
 * 
 * 
 * 
 * <p>
 * <b>铭飞科技</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * @author killfen
 * 
 * <p>
 * Comments:帖子上一页下一页标签
 * </p>
 * 
 * <p>
 * Create Date:2015-4-28
 * </p>
 * 
 * <p>
 * Modification history:
 * </p>
 */
public class PreviousNextParser extends IParser{

	/**
	 * 上一篇连接
	 */
	private final static String PREVIOUS_LINK="\\{ms:field.prelink/\\}";
	
	/**
	 * 上一篇标题
	 */
	private final static String PREVIOUS_TITLE="\\{ms:field.pretitle/\\}";
	/**
	 * 上一篇连接
	 */
	private final static String NEXT_LINK="\\{ms:field.nextlink/\\}";
	
	/**
	 * 上一篇标题
	 */
	private final static String NEXT_TITLE="\\{ms:field.nexttitle/\\}";
	
	/**
	 * 上一篇
	 */
	private BasicEntity previous;
	
	/**
	 * 下一篇
	 */
	private BasicEntity next;
	
	/**
	 * 应用主机地址
	 */
	private String appUrl;
	


	/**
	 * 构造标签的属性
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 * @param previous上一篇主题
	 * @param next下一篇主题
	 *  @param appUrl应用主机地址
	 */
	public PreviousNextParser(String htmlContent,BasicEntity previous,BasicEntity next,String appUrl){
		super.htmlCotent = htmlContent;
		this.previous = previous;
		this.next = next;
		this.appUrl = appUrl;
	}
	

	@Override
	public String parse() {
		if (this.previous != null) {
			// 替换上一篇文章链接
			super.htmlCotent = replaceAll(StringUtil.buildPath(appUrl,this.previous.getBasicId(),Const.DETAIL+DO_SUFFIX).substring(1),PREVIOUS_LINK);
			// 替换上一篇文章标题
			super.htmlCotent = replaceAll(this.previous.getBasicTitle(),PREVIOUS_TITLE);
		} else {
			// 替换上一篇文章链接
			super.htmlCotent = replaceAll("#",PREVIOUS_LINK);
			// 替换上一篇文章标题
			super.htmlCotent = replaceAll("",PREVIOUS_TITLE);
		}
		if (this.next != null) {
			// 替换上一篇文章链接
			super.htmlCotent = replaceAll(StringUtil.buildPath(appUrl,this.next.getBasicId(),Const.DETAIL+DO_SUFFIX).substring(1),NEXT_LINK);
			// 替换上一篇文章标题
			super.htmlCotent = replaceAll(this.next.getBasicTitle(),NEXT_TITLE);
		} else {
			// 替换上一篇文章链接
			super.htmlCotent = replaceAll("#", NEXT_LINK);
			// 替换上一篇文章标题
			super.htmlCotent = replaceAll("", NEXT_TITLE);
		}
		return super.htmlCotent ;
	}



}
