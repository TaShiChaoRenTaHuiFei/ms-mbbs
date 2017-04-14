package com.mingsoft.bbs.action;

import java.sql.Timestamp;
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
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.basic.constant.e.CookieConstEnum;
import com.mingsoft.basic.constant.e.SessionConstEnum;
import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.basic.entity.ManagerSessionEntity;
import com.mingsoft.bbs.biz.IForumBiz;
import com.mingsoft.bbs.biz.IFunctionBiz;
import com.mingsoft.bbs.entity.ForumEntity;
import com.mingsoft.bbs.entity.FunctionEntity;
import com.mingsoft.util.StringUtil;


/**
 * mbbs 板块管理控制层
 * @Package com.mingsoft.bbs.action
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月15日<br/>
 * 历史修订：<br/>
 */
@Controller("bbsManagerForum")
@RequestMapping("/${managerPath}/bbs/forum")
public class ForumAction  extends BaseAction {
	
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
	
	@Autowired
	private IForumBiz forumBiz;
	
	/**
	 * 栏目首页面列表显示
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return 栏目列表页面地址
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request,HttpServletResponse response,ModelMap model) {
		String modelId = request.getParameter("modelId"); 
		String categoryCategoryId = request.getParameter("categoryCategoryId");//提供展开效果使用
		this.setSession(request, SessionConstEnum.MANAGER_MODEL_CODE, modelId);
		//获取登录的session
		ManagerSessionEntity managerSession = (ManagerSessionEntity) getManagerBySession(request);
		//传入一个实体，提供查询条件
		CategoryEntity category  = new CategoryEntity();
		category.setCategoryModelId(Integer.parseInt(modelId));
		AppEntity app = this.getApp(request);
		//查询指定的appId下的分类
		category.setCategoryAppId(app.getAppId());
		//判断是否为该网站总管理员，如果是管理员查询分类时则可以不受管理员限制，即可以查看所有的分类
		if(managerSession.getManagerId()!=app.getAppManagerId()){
			category.setCategoryManagerId(managerSession.getManagerId());
		}
		List list = categoryBiz.queryByPageList(category, null, "category_id", true);
		//保存cookie值
		this.setCookie(request, response, CookieConstEnum.BACK_COOKIE,"//manager/bbs/forum/forum_list.do");
		model.addAttribute("categoryCategoryId", categoryCategoryId);
		model.addAttribute("categoryJson", JSONArray.toJSONString(list));
		model.addAttribute("modelId",request.getParameter("modelId"));
		
		//获取bbs版主分类的模块id
		category.setCategoryModelId(this.getModelCodeId(request, com.mingsoft.bbs.constant.ModelCode.BBS_MODERATOR));
		//获取所有的版主类型列表
		List moderatorTypeList= categoryBiz.queryByPageList(category, null, "category_id", true);
		model.addAttribute("moderatorTypeList", moderatorTypeList);
		//获取用户分组的模块id
		category.setCategoryModelId(this.getModelCodeId(request, com.mingsoft.bbs.constant.ModelCode.BBS_PEOPLE_CATEGORY));
		List peopleCategoryList = categoryBiz.queryByPageList(category, null, "category_id", true);
		//查询该站点下所有的bbs对应的功能列表
		List<FunctionEntity> functionList = functionBiz.queryByAppId(app.getAppId());
		model.addAttribute("functionList", functionList);
		//版主类型
		model.addAttribute("moderatorTypes",moderatorTypeList);
		model.addAttribute("peopleCategoryList", peopleCategoryList);
		return view("/bbs/forum/forum_list");
	}
	
	
	/**
	 * 加载添加栏目页面
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
		request.setAttribute("modelTitle",request.getParameter("modelTitle"));
		request.setAttribute("modelId",request.getParameter("modelId"));
		return view("/bbs/forum/forum_form");
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
		request.setAttribute("modelTitle", 	request.getParameter("modelTitle"));
		request.setAttribute("modelId",request.getParameter("modelId"));
		return view("/bbs/forum/forum_form");
	}
	
	/**
	 * 添加板块
	 * @param forum 板块对象
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	@RequestMapping("/save")
	public void save(@ModelAttribute ForumEntity forum,
			HttpServletRequest request, HttpServletResponse response) {
		//获取登录的session
		ManagerSessionEntity managerSession = (ManagerSessionEntity) getManagerBySession(request);
		forum.setCategoryManagerId(managerSession.getManagerId());
		forum.setCategoryDateTime(new Timestamp(System.currentTimeMillis()));
		forum.setCategoryAppId(this.getAppId(request));
		forum.setCategoryModelId(this.getModelCodeId(request, com.mingsoft.bbs.constant.ModelCode.BBS_CATEGORY));
		forumBiz.saveForum(forum);
		
		this.outJson(response,com.mingsoft.bbs.constant.ModelCode.BBS_CATEGORY, true, null,String.valueOf(forum.getCategoryId()));
	}
	
	/**
	 * 添加板块
	 * @param forum 板块对象
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	@RequestMapping("/update")
	public void update(@ModelAttribute ForumEntity forum,
			HttpServletRequest request, HttpServletResponse response) {
		CategoryEntity categoryEntity = categoryBiz.getCategory(forum.getCategoryId());
		ForumEntity _forum = forumBiz.getByForumId(forum.getCategoryId(),this.getAppId(request));
		if (_forum.getForumId() ==0) {
			forum.setForumId(forum.getCategoryId());
			forumBiz.saveEntity(forum);
		} 
		categoryBiz.updateCategoryEntity(forum);
		
		this.outJson(response,com.mingsoft.bbs.constant.ModelCode.BBS_CATEGORY, true, null,String.valueOf(forum.getCategoryId()));
	}
}
