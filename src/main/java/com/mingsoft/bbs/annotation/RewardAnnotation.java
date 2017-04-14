package com.mingsoft.bbs.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 奖励标记，注意提供aop拦截使用 
 * @author killfen
 * @version 
 * 版本号：【100-000-000】
 * 创建日期：2015年12月11日
 * 历史修订：
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RewardAnnotation {
	 String description() default "";
}