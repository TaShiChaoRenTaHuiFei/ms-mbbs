package com.mingsoft.bbs.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.bbs.biz.IGroupFunctionBiz;
import com.mingsoft.bbs.entity.GroupFunctionEntity;
import com.mingsoft.util.StringUtil;


/**
 * mbbs 版主和功能的关联关系控制层
 * @Package com.mingsoft.bbs.action
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月15日<br/>
 * 历史修订：<br/>
 */
@Controller("bbsGroupFunction")
@RequestMapping("/${managerPath}/bbs/group/function")
public class GroupFunctionAction  extends BaseAction {
	
	
	/**
	 * 注入版主类型与板块关联关系业务层
	 */
	@Autowired
	private IGroupFunctionBiz groupFunctionBiz;
	
	/**
	 * 保存版主类型与板块关联关系
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{forumId}/{groupId}/save")
	public void save(@PathVariable int forumId,@PathVariable int groupId,HttpServletRequest request,HttpServletResponse response){
		//获取版主类型与板块关联关系数据
		String groupFunctionJson = request.getParameter("groupFunctionJson");
		List<GroupFunctionEntity> groupFunctionList = new ArrayList<GroupFunctionEntity>();
		//如果数据为空则无法,进行保存
		if(StringUtil.isBlank(groupFunctionJson)){
			this.outJson(response, false);
			return;
		}
		groupFunctionList = JSONArray.parseArray(groupFunctionJson, GroupFunctionEntity.class);	
		groupFunctionBiz.saveBatch(groupFunctionList,forumId,groupId);
		this.outJson(response, true);
	}
	
	/**
	 * 根据板块id，查询各版主类型拥有的权限
	 * @param forumId 板块id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{forumId}/queryByForumId")
	public void queryByForumId(@PathVariable int forumId,HttpServletRequest request,HttpServletResponse response){
		List<GroupFunctionEntity> groupFunctionList = this.groupFunctionBiz.queryByForumId(forumId);
		this.outJson(response, JSONObject.toJSONString(groupFunctionList));
	}
	
	/**
	 * 根据板块id，查询各版主类型拥有的权限
	 * @param forumId 板块id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{groupId}/queryByGroupId")
	public void queryByGroupId(@PathVariable int groupId,HttpServletRequest request,HttpServletResponse response){
		List<GroupFunctionEntity> groupFunctionList = this.groupFunctionBiz.queryByGroupId(groupId);
		this.outJson(response, JSONObject.toJSONString(groupFunctionList));
	}
	
	
}
