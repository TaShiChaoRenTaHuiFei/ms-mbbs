package com.mingsoft.bbs.constant.e;

import com.mingsoft.base.constant.e.BaseEnum;


/**
 * mbbs帖子对应的枚举类型
 * @Package com.mingsoft.bbs.constant.e
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月22日<br/>
 * 历史修订：<br/>
 */
public enum SubjectEnum implements BaseEnum {
	/**
	 * 显示
	 */
	DISPLAY("0"),
	
	/**
	 * 不显示
	 */
	HIDE("1"),
	
	;

	
	SubjectEnum(Object code) {
		this.code = code;
	}
	
	private Object code;
	
	
	@Override
	public int toInt() {
		return Integer.valueOf(code+"");
	}

}
