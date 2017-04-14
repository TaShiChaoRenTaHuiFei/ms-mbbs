package com.mingsoft.bbs.constant.e;

import com.mingsoft.base.constant.e.BaseEnum;


/**
 * mbbs评论类型的枚举类
 * @Package com.mingsoft.bbs.constant.e
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月22日<br/>
 * 历史修订：<br/>
 */
public enum SubjectCommentTypeEnum  implements BaseEnum {
	/**
	 * 评论显示
	 */
	DISPLAY("0"),
	/**
	 *匿名 
	 */
	INCOGNITO("1"),
	/**
	 * 公开
	 */
	PUBLIC("2"),
	
	/**
	 * 评论不显示
	 */
	HIDE("3")
	;

	SubjectCommentTypeEnum(Object code) {
		this.code = code;
	}
	
	private Object code;
	
	
	@Override
	public int toInt() {
		return Integer.valueOf(code+"");
	}

}
