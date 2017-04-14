package com.mingsoft.bbs.aop;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.base.constant.Const;
import com.mingsoft.bbs.biz.IForumBiz;
import com.mingsoft.bbs.entity.SubjectEntity;
import com.mingsoft.util.StringUtil;
import com.mingsoft.util.proxy.Proxy;

/**
 * @author 王天培
 * @version 版本号：100-000-000<br/>
 *          创建日期：2016年3月16日<br/>
 *          历史修订：<br/>
 */
@Component
@Aspect
public class SubjectMailAop extends BaseAop {

	/**
	 * 注入板块业务层
	 */
	@Autowired
	private IForumBiz forumBiz;

	/**
	 * 保存帖子
	 */
	@Pointcut("execution(* com.mingsoft.bbs.action.people.SubjectAction.save(..))")
	public void save() {
	}

	/**
	 * 保存帖子的切面
	 * 
	 * @param jp
	 */
	@After("save()")
	public void save(JoinPoint jp) {
		ShiroHttpServletRequest request = this.getType(jp, ShiroHttpServletRequest.class);
		if (!StringUtil.isBlank(this.getCode(request))) {
			SubjectEntity subject = this.getType(jp, SubjectEntity.class);
			Map<String, String> params = new HashMap<String, String>();
			Map<String, String> content = new HashMap<String, String>();
			content.put("subjectTitle", subject.getBasicTitle());
			content.put("subjectId", subject.getBasicId() + "");
			
			params.put("modelCode", this.encryptByAES(this.getAppId(request), this.getCode(request)));
			params.put("content",JSONObject.toJSONString(content));
			if (subject != null) {
				Proxy.post(this.getApp(request).getAppHostUrl() + "/mail/send.do", null, params, Const.UTF8);
			}
		}
	}

}
