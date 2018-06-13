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
import com.mingsoft.basic.biz.IBasicBiz;
import com.mingsoft.basic.entity.BasicEntity;
import com.mingsoft.bbs.biz.IForumBiz;
import net.mingsoft.comment.entity.CommentEntity;
import com.mingsoft.people.biz.IPeopleBiz;
import com.mingsoft.people.constant.e.PeopleEnum;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.util.StringUtil;
import com.mingsoft.util.proxy.Proxy;

import cn.hutool.http.HttpUtil;

/**
 * @author 王天培
 * @version 版本号：100-000-000<br/>
 *          创建日期：2016年3月16日<br/>
 *          历史修订：<br/>
 */
@Component
@Aspect
public class SubjectCommentMailAop extends BaseAop {

	
	@Autowired
	private IPeopleBiz peopleBiz;
	
	@Autowired
	private IBasicBiz basicBiz;
	/**
	 * 保存帖子
	 */
	@Pointcut("execution(* com.mingsoft.bbs.action.people.SubjectCommentAction.save(..))")
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
			CommentEntity comment = this.getType(jp, CommentEntity.class);
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, String> content = new HashMap<String, String>(); 
			content.put("commentContent", comment.getCommentContent());
			content.put("subjectId", comment.getCommentBasicId() + "");
			BasicEntity basic = basicBiz.getBasic(comment.getCommentBasicId());
			PeopleEntity people = (PeopleEntity)peopleBiz.getEntity(basic.getBasicPeopleId());
			content.put("subjectId", comment.getCommentBasicId() + "");
			content.put("subjectTitle", basic.getBasicTitle() + "");
			content.put("userName", people.getPeopleUser().getPuNickname());
			params.put("thrid", "sendcloud"); //使用第三方平台发送，确保用户能收到
			params.put("modelCode", this.encryptByAES(this.getAppId(request), this.getCode(request)));
			
			if (people.getPeopleMailCheck() == PeopleEnum.MAIL_CHECK.toInt()) {
				params.put("receive", people.getPeopleMail());
			}
			params.put("content", JSONObject.toJSONString(content));
			
			if (comment != null) {
				HttpUtil.post(this.getApp(request).getAppHostUrl() + "/mail/send.do",  params);

			}
		}
	}

}
