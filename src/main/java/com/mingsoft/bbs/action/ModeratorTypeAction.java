package com.mingsoft.bbs.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.bbs.biz.IFunctionBiz;
import com.mingsoft.bbs.entity.FunctionEntity;
import com.mingsoft.util.StringUtil;

/**
 * mbbs版主类型控制层
 * @Package com.mingsoft.bbs.action
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月15日<br/>
 * 历史修订：<br/>
 */
@Controller("bbsModeratorType")
@RequestMapping("/${managerPath}/bbs/moderator/type")
public class ModeratorTypeAction extends BaseAction {
	
	/**
	 * 注入bbs权限功能业务层
	 */
	@Autowired
	private IFunctionBiz functionBiz;
	
	/**
	 * 业务层的注入
	 */
	@Autowired
	private ICategoryBiz categoryBiz;
	
	/**
	 * 版主类型列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request,HttpServletResponse response,ModelMap model) {
		int modelId = this.getModelCodeId(request, com.mingsoft.bbs.constant.ModelCode.BBS_MODERATOR);
		//
		int appId = this.getAppId(request);
		String categoryCategoryId = request.getParameter("categoryCategoryId");//提供展开效果使用
		//查询版主类型列表
		List<CategoryEntity> moderatorTypeList = this.categoryBiz.queryByAppIdOrModelId(appId, modelId); 
		//查询该站点下所有的bbs对应的功能列表
		List<FunctionEntity> functionList = functionBiz.queryByAppId(appId);
		model.addAttribute("functionList", functionList);
		//版主类型
		model.addAttribute("categoryCategoryId", categoryCategoryId);
		model.addAttribute("categoryJson", JSONArray.toJSONString(moderatorTypeList));
		model.addAttribute("modelId",request.getParameter("modelId"));
		return view("/bbs/moderatortype/moderator_type_list");
	}
	
	/**
	 * 加载添加版主类型页面
	 * @param request 请求对象
	 * @return 添加栏目页面
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request,ModelMap model) {
		String categoryCategoryId = request.getParameter("categoryCategoryId"); 
		CategoryEntity category = new CategoryEntity();
		if (!StringUtil.isBlank(categoryCategoryId)) {
			category.setCategoryCategoryId(Integer.parseInt(categoryCategoryId));	
		}
		category.setCategoryManagerId(this.getManagerId(request));
		category.setCategoryModelId(this.getModelCodeId(request));
		List<CategoryEntity> list = categoryBiz.queryChilds(category);
		
		String listJsonString = JSONObject.toJSON(list).toString();
		
		request.setAttribute("categoryCategoryId", categoryCategoryId); //提供给子栏目使用
		request.setAttribute("listCategory", listJsonString);
		request.setAttribute("modelId",request.getParameter("modelId"));
		return view("/bbs/moderatortype/moderator_type");
	}
	
	
	/**
	 * 加载栏目更新页面
	 * @param categoryId 栏目id
	 * @param request 请求对象
	 * @return 栏目更新页面地址
	 */
	@RequestMapping("/{categoryId}/edit")
	public String edit(@PathVariable int categoryId, HttpServletRequest request) {
		// 站点ID
		List<CategoryEntity> list = new ArrayList<CategoryEntity>();
		
		// 判断管理员权限,查询其管理的栏目集合
		CategoryEntity category = new CategoryEntity();
		category.setCategoryManagerId(this.getManagerId(request));
		category.setCategoryModelId(this.getModelCodeId(request));
		list = categoryBiz.queryChilds(category);
		
		CategoryEntity column = (CategoryEntity) categoryBiz.getEntity(categoryId);
		request.setAttribute("category", column);
		// 获取父栏目对象
		if (column.getCategoryCategoryId() != 0) {
			CategoryEntity columnSuper = (CategoryEntity) categoryBiz.getEntity(column.getCategoryCategoryId());
			request.setAttribute("columnSuper", columnSuper);
		}

		String listJsonString = JSONObject.toJSON(list).toString();
		request.setAttribute("listCategory", listJsonString);
		request.setAttribute("modelId",request.getParameter("modelId"));
		return view("/bbs/moderatortype/moderator_type");
	}
}
