package com.mingsoft.bbs.action.people;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.bbs.action.BaseAction;
import com.mingsoft.bbs.biz.IFunctionBiz;
import com.mingsoft.bbs.biz.IModeratorBiz;
import com.mingsoft.bbs.biz.IPeopleGroupBiz;
import com.mingsoft.bbs.entity.FunctionEntity;
import com.mingsoft.bbs.entity.ModeratorEntity;
import com.mingsoft.bbs.entity.PeopleGroupEntity;
import com.mingsoft.people.entity.PeopleEntity;

/**
 * mbbs 用户权限
 * @Package com.mingsoft.bbs.action
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月15日<br/>
 * 历史修订：<br/>
 */
@Controller("webFunction")
@RequestMapping("/people/bbs/function")
public class FunctionAction extends BaseAction{
	
	
	/**
	 * 用户分组业务层
	 */
	@Autowired
	private IPeopleGroupBiz peopleGroupBiz;
	
	/**
	 * 版主管理业务层
	 */
	@Autowired
	private IModeratorBiz moderatorBiz;
	
	/**
	 * 功能管理业务层
	 */
	@Autowired
	private IFunctionBiz functionBiz;
	
	/**
	 * 根据板块id查询该用户的权限列表
	 * @param forumId 板块id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{forumId}/list")
	public void list(@PathVariable int forumId,HttpServletRequest request,HttpServletResponse response){
		//从session中获取用户Id
		PeopleEntity people = (PeopleEntity) this.getPeopleBySession(request);
		if(people!=null){
			//获取应用id
			int appId = this.getAppId(request);
			PeopleGroupEntity peopleGroup = peopleGroupBiz.getPeopleGroup(people.getPeopleId(), appId);
			if(peopleGroup!=null){
				int peopleGroupId = peopleGroup.getCategoryId();
				//根据组id查询该用户组的功能权限
				//判断当前用户是否为该板块的版主
				ModeratorEntity moderator = this.moderatorBiz.getModeratorByPeopleId(appId, forumId, people.getPeopleId());
				int moderatorTypeId = 0;
				if(moderator!=null){
					moderatorTypeId = moderator.getModeratorTypeId();
				}
				List<FunctionEntity> functionList = this.functionBiz.queryByGroupIdAndModeratorTypeId(appId, peopleGroupId, moderatorTypeId,forumId);
				this.outJson(response, JSONObject.toJSONString(functionList));
				return;
			}
		}
		this.outJson(response, false);
	}
}
