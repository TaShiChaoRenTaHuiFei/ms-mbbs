package com.mingsoft.bbs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.basic.biz.ICategoryBiz;

import com.mingsoft.basic.entity.CategoryEntity;

import com.mingsoft.bbs.biz.IForumBiz;
import com.mingsoft.bbs.biz.IModeratorBiz;
import com.mingsoft.bbs.constant.ModelCode;
import com.mingsoft.bbs.entity.ForumEntity;
import com.mingsoft.bbs.entity.ModeratorEntity;
import com.mingsoft.people.biz.IPeopleBiz;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.util.StringUtil;

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
 *         @ClassName: ManagerModeratorAction
 *
 * 		@Description: TODO 管理员对版主的管理的业务处理层
 *
 *          <p>
 *          Comments:  继承BaseAction类
 *          </p>
 * 
 *          <p>
 *          Creatr Date:2015-4-16 下午2:32:34
 *          </p>
 * 
 *          <p>
 *          Modification history:
 *          </p>
 */
@Controller("bbsManagerModerator")
@RequestMapping("/${managerPath}/bbs/moderator")
public class ModeratorAction extends BaseAction {
	
	
	/**
	 * 注入用户和类别绑定的业务逻辑层
	 */
	@Autowired
	private IModeratorBiz moderatorBiz;
	
	/**
	 * 注入用户基础业务层
	 */
	@Autowired
	private IPeopleBiz peopleBiz;
	
	/**
	 * 业务层的注入
	 */
	@Autowired
	private ICategoryBiz categoryBiz;
	
	/**
	 * 业务层的注入
	 */
	@Autowired
	private IForumBiz forumBiz;
	
	
	
	/**
	 * 版主列表
	 * @param forumId 板块id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{forumId}/query")
	public void query(@PathVariable int forumId,HttpServletRequest request,HttpServletResponse response){
		int appId = this.getAppId(request);
		//板块和版主列表
		List <ModeratorEntity> peopleList  = moderatorBiz.queryByForumId(appId,forumId);
		//板块和版主列表
		this.outJson(response, null, true,JSONObject.toJSONString(peopleList));
	}
	
	/**
	 * 版主信息
	 * @param categoryId 板块id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{forumId}/getModerator")
	public void getModerator(@PathVariable int forumId,HttpServletRequest request,HttpServletResponse response){
		int peopleId = this.getInt(request, "peopleId", 0);
		//板块和版主列表
		ModeratorEntity moderator  = moderatorBiz.getEntity(forumId,peopleId);
		//板块和版主列表
		this.outJson(response, null, true,JSONObject.toJSONString(moderator));
	}
	
	/**
	 * 保存版主
	 * @param categoryId 板块id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{forumId}/save")
	public void save(@PathVariable int forumId,HttpServletRequest request,HttpServletResponse response){
		//获取版主类型
		Integer moderatorTypeId = this.getInt(request,"moderatorTypeId");
		//获取版主帐号
		String peopleName = request.getParameter("moderatorName");
		int appId= this.getAppId(request);
		
		//获取得到的版主id
		ModeratorEntity moderator  = this.checkForm(appId, forumId, moderatorTypeId, peopleName, response,request);
		//通过会员账号查询会员
		PeopleEntity people = peopleBiz.getEntityByUserName(peopleName,appId);
		//判断会员是否存在
		if(people==null){
			this.outJson(response, null,false,this.getResString("err.not.exist",this.getResString("people", com.mingsoft.people.constant.Const.RESOURCES)));
			return;
		}
		//验证参数
		if(moderator !=null){
			this.outJson(response, null, false,this.getResString("err.exist",this.getResString("moderator", com.mingsoft.bbs.constant.Const.RESOURCES)));
			return;
		}else{
			//添加新的版主
			moderator= new ModeratorEntity();
		}
		
		
		
		//添加版主
		moderator.setModeratorPeopleId(people.getPeopleId());
		//添加新的版主类型
		moderator.setModeratorTypeId(moderatorTypeId);
		//添加板块id
		moderator.setModeratorForumId(forumId);
	
		moderatorBiz.saveEntity(moderator);
		this.outJson(response, null, true);
	}
	

	/**
	 * 验证会员帐号
	 * @param appId 站点id
	 * @param categoryId 板块id
	 * @param peopleName 会员帐号
	 * @return ModeratorEntity 版主关联实体
	 */
	public ModeratorEntity checkForm(int appId,int forumId,Integer moderatorTypeId,String peopleName,HttpServletResponse response,HttpServletRequest request){
		
		//板块id,版主类型id,版主帐号不能为null
		if(StringUtil.isBlank(moderatorTypeId) || StringUtil.isBlank(peopleName) ){
			this.outJson(response, null,false);
			return null;
		}
		PeopleEntity people  = this.peopleBiz.getEntityByUserName(peopleName, appId);
		//判断用户是否存在
		if(people==null){
			return null;
		}
		//查询该版主是否在该板块以及其父板块中存在版主(一人担任一个顶级板块下的版主)
		ModeratorEntity moderator =  moderatorBiz.getModeratorByPeopleId(appId, forumId, people.getPeopleId());
		//判断该会员是否担任某个顶级板块下某板块的版主
		if(moderator ==null){
			return null;
		}else{
			//添加新的版主类型
			moderator.setModeratorTypeId(moderatorTypeId);
		}
		return moderator;
	}
	
	 
	/**
	 * 更新版主
	 * @param categoryId 板块id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{forumId}/update")
	public void update(@PathVariable int forumId,HttpServletRequest request,HttpServletResponse response){
		//获取新版主帐号
		String moderatorName = request.getParameter("moderatorName");
		int appId = this.getAppId(request);
		PeopleEntity people  = this.peopleBiz.getEntityByUserName(moderatorName, appId);
		//判断用户是否存在
		if(people==null){
			this.outJson(response, null,false,this.getResString("err.not.exist",this.getResString("people", com.mingsoft.people.constant.Const.RESOURCES)));
			return;
		}
		//获取当前点击的版主
		ModeratorEntity moderator =  moderatorBiz.getModeratorByPeopleId(appId, forumId, people.getPeopleId());
		if(moderator==null){
			this.outJson(response, null, false,this.getResString("err.exist",this.getResString("moderator", com.mingsoft.bbs.constant.Const.RESOURCES)));
			return;
		}
		int moderatorTypeId = this.getInt(request, "moderatorTypeId");
		moderator.setModeratorTypeId(moderatorTypeId);
		moderatorBiz.updateEntity(moderator);
		this.outJson(response, null, true);
	}
	
	
	/**
	 * 删除多个版主
	 * @param categoryId 板块id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{forumId}/delete")
	public void delete(@PathVariable int forumId,HttpServletRequest request,HttpServletResponse response){
		String[] ids = request.getParameterValues("id");
		//获取板块
		ForumEntity forum = forumBiz.getByForumId(forumId, this.getAppId(request));
		//判断该板块是否是该站点下的
		if(forum != null){
			//删除多个版主
			moderatorBiz.deleteByForumId( forumId, ids);
			this.outJson(response, null, true);
		}else{
			this.outJson(response, null, false);
		}
	}
	
}
