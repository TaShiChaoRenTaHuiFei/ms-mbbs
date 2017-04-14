package com.mingsoft.bbs.action.people;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.bbs.action.BaseAction;
import com.mingsoft.bbs.biz.IModeratorBiz;
import com.mingsoft.bbs.entity.ModeratorEntity;


/**
 * 
 *  <p>
 * <b>铭飞-BBS论坛平台</b>
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇铭飞科技有限公司
 * </p>
 * 
 * @author guoph
 * 
 * @version 300-001-001
 * 
 *          <p>
 *          版权所有 铭飞科技
 *          </p>
 *
 *         @ClassName: ModeratorAction
 *
 * 		@Description: TODO people.action版主的业务处理层
 *
 *          <p>
 *          Comments:  继承BaseAction
 *          </p>
 * 
 *          <p>
 *          Creatr Date:2015-4-16 下午2:32:29
 *          </p>
 * 
 *          <p>
 *          Modification history:
 *          </p>
 */
@Controller("peopleModerator")
@RequestMapping("/people/bbs/moderator")
public class ModeratorAction extends BaseAction{
	
	/**
	 * 注入用户和类别绑定的业务逻辑层
	 */
	@Autowired
	private  IModeratorBiz moderatorBiz;

	/**
	 * 根据用户帐号和板块判断是否是版主
	 * @param categoryId  版块id
	 * @param request  
	 * @param response
	 * @return  关联的版主实体
	 */
	@RequestMapping("/{forumId}/isModerator")
	public void isModerator(@PathVariable int forumId,HttpServletRequest request,HttpServletResponse response){
		//根据session获取当前的用户实体
		PeopleEntity people = (PeopleEntity) this.getPeopleBySession(request);
		int appId = this.getAppId(request);
		//判断用户是否登录,如果没有登录,直接返回
		if(people==null){
			this.outJson(response, false);
			return ;
		}
		
		//通过当前的站点id，当前的用户帐号,查询当前用户是否为版主
		ModeratorEntity moderator = moderatorBiz.getModeratorByPeopleId(appId, forumId, people.getPeopleId());
		//判断查询到的实体集合长度，如果不为0，则返回是版主，否则不是版主
		if(moderator==null){
			this.outJson(response, false);
			return ;
		}
		//查询到版主信息，返回json数据
		this.outJson(response, null, true, null,  JSONObject.toJSONString(moderator));
	}
}
