package com.mingsoft.bbs.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import net.mingsoft.bank.biz.IScoreBiz;
import net.mingsoft.bank.entity.ScoreEntity;
import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.basic.constant.e.CookieConstEnum;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.bbs.biz.IForumFunctionScoreBiz;
import com.mingsoft.bbs.biz.IFunctionBiz;
import com.mingsoft.bbs.biz.IGroupFunctionBiz;
import com.mingsoft.bbs.biz.IPeopleGroupBiz;
import com.mingsoft.bbs.biz.IPeopleGroupScoreBiz;
import com.mingsoft.bbs.entity.ForumFunctionScoreEntity;
import com.mingsoft.bbs.entity.FunctionEntity;
import com.mingsoft.bbs.entity.GroupFunctionEntity;
import com.mingsoft.bbs.entity.PeopleGroupEntity;
import com.mingsoft.bbs.entity.PeopleGroupScoreEntity;
import com.mingsoft.util.StringUtil;

/**
 * mbbs 用户组管理控制层
 * @Package com.mingsoft.bbs.action
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月04日<br/>
 * 历史修订：<br/>
 */
@Controller("bbsPeopleGroup")
@RequestMapping("/${managerPath}/bbs/people/group")
public class PeopleGroupAction extends BaseAction{
	
	
	/**
	 * 业务层的注入
	 */
	@Autowired
	private ICategoryBiz categoryBiz;
	
	/**
	 * 注入bbs权限功能业务层
	 */
	@Autowired
	private IFunctionBiz functionBiz;
	
	/**
	 * 注入积分类型业务层
	 */
	@Autowired
	private IScoreBiz bankScoreBiz;
	
	/**
	 * 组功能关系绑定业务层
	 */
	@Autowired
	private IGroupFunctionBiz groupFunctionBiz;
	
	/**
	 * 对应的板块功能积分奖励规则
	 */
	@Autowired
	private IForumFunctionScoreBiz forumFunctionScoreBiz;
	
	/**
	 * 用户组业务层
	 */
	@Autowired
	private IPeopleGroupBiz peopleGroupBiz;
	
	/**
	 * 用户组业务层
	 */
	@Autowired
	private IPeopleGroupScoreBiz peopleScoreGroupBiz;
	
	/**
	 * 用户组列表页面
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		int appId = this.getAppId(request);
		//保存cookie值
		this.setCookie(request, response, CookieConstEnum.BACK_COOKIE,"/manager/bbs/people/group/list.do");
		int modelId = this.getModelCodeId(request, com.mingsoft.bbs.constant.ModelCode.BBS_PEOPLE_CATEGORY);
		//获取用户分组的模块id
		List<PeopleGroupEntity> peopleGroupList = peopleGroupBiz.queryByAppIdAndModelId(appId, modelId,null,false);
		List<ScoreEntity> bankScoreList = null;//bankScoreBiz.queryPageByAppId(appId, null,"bs_id",false);
		//
		//查询该站点下所有的bbs对应的功能列表
		model.addAttribute("peopleGroupList", peopleGroupList);
		model.addAttribute("bankScoreList", bankScoreList);
		return view("/bbs/peoplegroup/people_group_list");
	}
	
	/**
	 * 用户组新增页面
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		int appId = this.getAppId(request);
		//查询功能列表
		List<FunctionEntity> funcList = this.functionBiz.queryByAppId(appId);
		//查询积分类型列表
		List<ScoreEntity> bankScoreList = null;//this.bankScoreBiz.queryPageByAppId(appId, null,"bs_id",false);
		int modelId = this.getModelCodeId(request, com.mingsoft.bbs.constant.ModelCode.BBS_PEOPLE_CATEGORY);
		PeopleGroupEntity lastPeopleGroup = this.peopleGroupBiz.getLastOrFirstPeopleGroup(appId,  true);
		//获取上一个等级的用户组
		model.addAttribute("funcList", funcList);
		model.addAttribute("bankScoreList", bankScoreList);
		model.addAttribute("appId", appId);
		model.addAttribute("modelId", modelId);
		model.addAttribute("lastPeopleGroup", lastPeopleGroup);
		//当前用户组等级
		int peopleGroupLevel = 1;
		if(lastPeopleGroup!=null){
			peopleGroupLevel = lastPeopleGroup.getCategorySort()+1;
		}
		model.addAttribute("categorySort",peopleGroupLevel);
		return view("/bbs/peoplegroup/people_group");
	}
	
	
	/**
	 * 用户组编辑页面
	 * @param groupId 用户组id 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/{groupId}/edit")
	public String edit(@PathVariable int groupId, HttpServletRequest request,HttpServletResponse response,ModelMap model){
		int appId = this.getAppId(request);
		//查询用户组功能列表
		List<GroupFunctionEntity> groupFunctionList = groupFunctionBiz.queryByGroupIdAndForumId(groupId,0);
		//查询用户组的对应功能的积分奖励规则
		List<ForumFunctionScoreEntity> forumScoreList = forumFunctionScoreBiz.queryByGroupIdAndForumId(groupId,0);
		//查询用户组的不同积分类型的约束范围
		List<PeopleGroupScoreEntity> peopleGroupScoreList = peopleScoreGroupBiz.queryByPeopleGroupId(groupId);
		//查询功能列表
		List<FunctionEntity> funcList = this.functionBiz.queryByAppId(appId);
		//查询积分类型列表
		List<ScoreEntity> bankScoreList = null;//this.bankScoreBiz.queryPageByAppId(appId, null,"bs_id",false);
		model.addAttribute("funcList", funcList);
		model.addAttribute("bankScoreList", bankScoreList);
		model.addAttribute("appId", appId);
		model.addAttribute("groupFunctionList", groupFunctionList);
		model.addAttribute("forumScoreList", forumScoreList);
		model.addAttribute("peopleGroupScoreList", peopleGroupScoreList);
		int modelId =this.getModelCodeId(request, com.mingsoft.bbs.constant.ModelCode.BBS_PEOPLE_CATEGORY);
		model.addAttribute("modelId", modelId);
		CategoryEntity category = (CategoryEntity)this.categoryBiz.getEntity(groupId);
		model.addAttribute("categorySort", category.getCategorySort());
		model.addAttribute("category", category);
		PeopleGroupEntity lastPeopleGroup = this.peopleGroupBiz.getPrePeopleGroup(groupId,appId);
		model.addAttribute("groupId", groupId);
		model.addAttribute("lastPeopleGroup", lastPeopleGroup);
		return view("/bbs/peoplegroup/people_group");
	}
	
	
	/**
	 * 保存用户分组信息
	 * @param category 用户组
	 * @param request
	 * @param response
	 */
	@RequestMapping("/save")
	public void save(@ModelAttribute CategoryEntity category,HttpServletRequest request,HttpServletResponse response){
		//应用id
		int appId = this.getAppId(request);
		//模块id
		int modelId = this.getModelCodeId(request, com.mingsoft.bbs.constant.ModelCode.BBS_PEOPLE_CATEGORY);
		//获取版主类型与板块关联关系数据
		String groupFunctionJson = request.getParameter("groupFunctionJson");
		List<GroupFunctionEntity> groupFunctionList = new ArrayList<GroupFunctionEntity>();
		groupFunctionList = JSONArray.parseArray(groupFunctionJson, GroupFunctionEntity.class);	
		category.setCategoryAppId(appId);
		//获取版主类型与板块关联关系数据
		String peopleGroupScoreJson = request.getParameter("peopleGroupScoreJson");
		List<PeopleGroupScoreEntity> peopleGroupScoreList = new ArrayList<PeopleGroupScoreEntity>();
		peopleGroupScoreList = JSONArray.parseArray(peopleGroupScoreJson, PeopleGroupScoreEntity.class);	
		
		//获取版主类型与板块关联关系数据
		String  forumFunctionScoreJson= request.getParameter("forumFunctionScoreJson");
		List<ForumFunctionScoreEntity>  forumFunctionScoreList= new ArrayList<ForumFunctionScoreEntity>();
		forumFunctionScoreList = JSONArray.parseArray(forumFunctionScoreJson, ForumFunctionScoreEntity.class);	
		PeopleGroupEntity lastPeopleGroup = this.peopleGroupBiz.getLastOrFirstPeopleGroup(appId, true);
		int peopleGroupLevel = 1;
		if(lastPeopleGroup!=null){
			peopleGroupLevel = lastPeopleGroup.getCategorySort()+1;
		}
		category.setCategorySort(peopleGroupLevel);
		peopleGroupBiz.savePeopleGroupInfo(category, peopleGroupScoreList, groupFunctionList, forumFunctionScoreList);
		this.outJson(response,null, true);
	}
	
	/**
	 * 删除用户分组信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response){
		String [] ids = request.getParameterValues("ids");
		if(!StringUtil.isBlank(ids)){
			if(StringUtil.isIntegers(ids)){
				int[] _ids = StringUtil.stringsToInts(ids);
				peopleGroupBiz.delete(_ids);
				this.outJson(response,null, true);
				return;
			}
		}
		this.outJson(response,null, false);
	}
	
	/**
	 * 对用户组信息进行更新处理
	 * @param groupId
	 * @param category
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{groupId}/update")
	public void update(@PathVariable int groupId,@ModelAttribute CategoryEntity category,HttpServletRequest request,HttpServletResponse response){
		if(groupId<=0){
			this.outJson(response,null, false);
			return;
		}
		category.setCategoryId(groupId);
		category.setCategoryAppId(this.getAppId(request));
		//保存用户分类
		int appId = this.getAppId(request);
		//获取版类型与板块关联关系数据
		String groupFunctionJson = request.getParameter("groupFunctionJson");
		List<GroupFunctionEntity> groupFunctionList = new ArrayList<GroupFunctionEntity>();
		groupFunctionList = JSONArray.parseArray(groupFunctionJson, GroupFunctionEntity.class);	
		category.setCategoryAppId(appId);
		//获取版主类型与板块关联关系数据
		String peopleGroupScoreJson = request.getParameter("peopleGroupScoreJson");
		List<PeopleGroupScoreEntity> peopleGroupScoreList = new ArrayList<PeopleGroupScoreEntity>();
		peopleGroupScoreList = JSONArray.parseArray(peopleGroupScoreJson, PeopleGroupScoreEntity.class);	
				
		//获取版主类型与板块关联关系数据
		String  forumFunctionScoreJson= request.getParameter("forumFunctionScoreJson");
		List<ForumFunctionScoreEntity>  forumFunctionScoreList= new ArrayList<ForumFunctionScoreEntity>();
		forumFunctionScoreList = JSONArray.parseArray(forumFunctionScoreJson, ForumFunctionScoreEntity.class);	
		this.peopleGroupBiz.updatePeopleGroupInfo(category, peopleGroupScoreList, groupFunctionList, forumFunctionScoreList);
		this.outJson(response,null, true);
	}
}
