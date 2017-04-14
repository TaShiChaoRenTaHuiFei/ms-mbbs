package com.mingsoft.bbs.exception;

/**
 * mbbs 没有权限的异常类
 * @Package com.mingsoft.bbs.exception
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月15日<br/>
 * 历史修订：<br/>
 */
public class AuthException extends RuntimeException {
	
	public AuthException() {
		super("no power");
	}
}
