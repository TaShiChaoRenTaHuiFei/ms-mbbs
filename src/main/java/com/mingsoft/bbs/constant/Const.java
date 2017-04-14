package com.mingsoft.bbs.constant;

import java.util.ResourceBundle;

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
 *         <p>
 *         Comments:论坛常用配置
 *         </p>
 * 
 *         <p>
 *         Create Date:2015-4-28
 *         </p>
 * 
 *         <p>
 *         Modification history:
 *         </p>
 */
public interface Const {

	/**
	 * 默认模板文件名称,板块页面\内容页面\发布页面\搜索页面\动态后缀\静态后缀
	 */
	String LIST = "list", DETAIL = "detail", MBBS = "mbbs";

	public final static ResourceBundle RESOURCES = ResourceBundle.getBundle("com.mingsoft.bbs.resources.resources");

	/**
	 * bbs消息模板
	 */
	public final static ResourceBundle MESSAGE_RESOURCES = ResourceBundle
			.getBundle("com.mingsoft.bbs.resources.bbs_message");;
}