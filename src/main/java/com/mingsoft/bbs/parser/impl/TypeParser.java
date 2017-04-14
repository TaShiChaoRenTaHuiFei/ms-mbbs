/**
 * 
 */
package com.mingsoft.bbs.parser.impl;

import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.bbs.constant.Const;
import com.mingsoft.parser.IParser;
import com.mingsoft.util.StringUtil;

/**
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
 * Comments: 当前类别＆版块解析器 {ms:field.type*}
 * </p>
 * 
 * <p>
 * Create Date:2015-4-30
 * </p>
 * 
 * <p>
 * Modification history:
 * </p>
 */
public class TypeParser extends IParser{

	/**
	 * 版块连接
	 */
	private final static String LINK="\\{ms:field.typelink(.*)?/\\}";
	

	/**
	 * 版块编号{ms:field.typeid/}
	 */
	private final static String ID="\\{ms:field.typeid(.*)?/\\}";
	
	/**
	 * 版块名
	 */
	private final static String TITLE="\\{ms:field.typetitle(.*)?/\\}";
	
	/**
	 * 版块头像{ms:field.typeicon/}
	 */
	private final static String ICON = "\\{ms:field.typeicon(.*)?/\\}";
	
	/**
	 * 版块描述{ms:field.typedescrip/}
	 */
	private final static String DESCRIP = "\\{ms:field.typedescrip(.*)?/\\}";
	
	/**
	 * 分类实体
	 */
	private CategoryEntity category;
	
	/**
	 * 父分类实体
	 */
	private CategoryEntity categoryCategory;
	
	
	private String link;
	
	/**
	 * 获取类型，当type=top时则是获取父级栏目的相关信息
	 */
	private  final static String TYPE = "type";
	
	/**
	 * type值
	 */
	private final static String TYPE_TOP = "top";
	
	/**
	 * 构造标签的属性
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public TypeParser(String htmlContent,CategoryEntity category,String link){
		super.htmlCotent = htmlContent;
		this.category = category;
		this.link = link;
	}
	
	/**
	 * 构造标签的属性
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 * @param category 分类信息
	 * @param link 分类连接地址
	 * @param categoryCategory 分类的父分类信息
	 */
	public TypeParser(String htmlContent,CategoryEntity category,String link,CategoryEntity categoryCategory){
		super.htmlCotent = htmlContent;
		//
		this.category = category;
		//父分类
		this.categoryCategory = categoryCategory;
		this.link = link;
	}
	
	
	/* (non-Javadoc)
	 * @see com.mingsoft.parser.IParser#parse()
	 */
	@Override
	public String parse() {
		// TODO Auto-generated method stub
		//获取板块id标签中的type值
		String temp  = super.getProperty(ID).get(TYPE);
		//默认取当前板块id
		super.newCotent = category.getCategoryId()+"";
		//如果type=top 且父板块存在则取父板块的id
		if(!StringUtil.isBlank(temp) && temp.equalsIgnoreCase(TYPE_TOP) && categoryCategory!=null){
			super.newCotent = categoryCategory.getCategoryId()+"";
		}
		//替换板块id标签
		htmlCotent =  super.replaceAll(ID);
		//获取板块标题标签中的type值
		temp  = super.getProperty(TITLE).get(TYPE);
		//默认取当前板块标题
		super.newCotent = category.getCategoryTitle()+"";
		//如果type=top 且父板块存在则取父板块标题
		if(!StringUtil.isBlank(temp) && temp.equalsIgnoreCase(TYPE_TOP) && categoryCategory!=null){
			super.newCotent = categoryCategory.getCategoryTitle()+"";
		}
		//替换板块标题标签
		htmlCotent =  super.replaceAll(TITLE);
		
		//获取板块链接地址标签中的type值
		temp  = super.getProperty(LINK).get(TYPE);
		//默认是当前板块链接地址
		super.newCotent =  StringUtil.buildPath(link,category.getCategoryId(),Const.LIST+DO_SUFFIX).substring(1); 
		//如果type=top 且父板块存在则取父板块链接地址
		if(!StringUtil.isBlank(temp) && temp.equalsIgnoreCase(TYPE_TOP) && categoryCategory!=null){
			super.newCotent =  StringUtil.buildPath(link,categoryCategory.getCategoryId(),Const.LIST+DO_SUFFIX).substring(1); 
		}
		//替换板块链接地址标签
		htmlCotent =  super.replaceAll(LINK);	
		
		//获取板块描述标签中的type值
		temp  = super.getProperty(DESCRIP).get(TYPE);
		//默认是当前板块描述
		super.newCotent = category.getCategoryDescription();
		//如果type=top 且父板块存在则取父板块描述
		if(!StringUtil.isBlank(temp) && temp.equalsIgnoreCase(TYPE_TOP) && categoryCategory!=null){
			super.newCotent =  categoryCategory.getCategoryDescription();
		}
		//替换板块描述标签
		htmlCotent =  super.replaceAll(DESCRIP);
		
		//获取板块缩略图标签中的type值
		temp  = super.getProperty(ICON).get(TYPE);
		//默认是当前板块缩略图
		super.newCotent = category.getCategorySmallImg();
		//如果type=top 且父板块存在则取父板块缩略图
		if(!StringUtil.isBlank(temp) && temp.equalsIgnoreCase(TYPE_TOP) && categoryCategory!=null){
			super.newCotent =  categoryCategory.getCategorySmallImg();
		}
		htmlCotent =  super.replaceAll(ICON);
		return htmlCotent;
	}

}
