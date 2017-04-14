package com.mingsoft.bbs.entity;

import com.mingsoft.base.entity.BaseEntity;


/**
 * mbbs对应的功能表
 * @Package com.mingsoft.bbs.entity
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月08日<br/>
 * 历史修订：<br/>
 */
public class FunctionEntity extends BaseEntity{
	
	/**
	 * 功能自增长id
	 */
	private int functionId;
	
	/**
	 * 功能对应的应用id
	 */
	private int functionAppId;
	
	/**
	 * 功能对应的名称
	 */
	private String functionTitle;
	
	/**
	 * 功能对应的方法名
	 */
	private String functionMethod;

	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	public int getFunctionAppId() {
		return functionAppId;
	}

	public void setFunctionAppId(int functionAppId) {
		this.functionAppId = functionAppId;
	}

	public String getFunctionTitle() {
		return functionTitle;
	}

	public void setFunctionTitle(String functionTitle) {
		this.functionTitle = functionTitle;
	}

	public String getFunctionMethod() {
		return functionMethod;
	}

	public void setFunctionMethod(String functionMethod) {
		this.functionMethod = functionMethod;
	}
	
	
}
