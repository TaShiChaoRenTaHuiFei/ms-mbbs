package com.mingsoft.bbs.action.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.basic.action.BaseAction;
import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.bbs.constant.ModelCode;

/**
 * mbbs供前端页面对板块的数据
 * 
 * @Package com.mingsoft.bbs.action.web
 * @author 郭鹏辉
 * @version 版本号：<br/>
 *          创建日期：@date 2015年03月26日<br/>
 *          历史修订：<br/>
 */
@Controller("bbsWebForum")
@RequestMapping("/mbbs/forum")
public class ForumAction extends BaseAction {
	/**
	 * 业务层的注入
	 */
	@Autowired
	private ICategoryBiz categoryBiz;

	/**
	 * 板块在前端页面列表显示
	 * <dt><span class="strong">返回</span></dt><br/>
	 * [<br/>
	 * {<br/>
	 * "categoryDateTime": "2015-07-26 10:07:12",<br/>
	 * "categoryDescription": "板块描述",<br/>
	 * "categoryId": 板块编号,<br/>
	 * "categorySmallImg": "板块缩略图",<br/>
	 * "categorySort": 板块排序,<br/>
	 * "categoryTitle": "板块标题"<br/>
	 * }<br/>
	 * ]<br/>
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public void list(HttpServletRequest request, HttpServletResponse response) {
		// 获取站点ID
		int appId = this.getAppId(request);
		// 获取模块ID
		int modeId = this.getModelCodeId(request, ModelCode.BBS_CATEGORY);
		// 同过站点ID和模块ID获取栏目列表
		List<CategoryEntity> list = categoryBiz.queryByAppIdOrModelId(appId, modeId);
		this.outJson(response, JSONObject.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 通过categoryId查询category实体并以json的形式给前端
	 * 
	 * @param categoryId
	 *            版块id
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {<br/>
	 *            "categoryDateTime": "2015-07-26 10:07:12",<br/>
	 *            "categoryDescription": "板块描述",<br/>
	 *            "categoryId": 板块编号,<br/>
	 *            "categorySmallImg": "板块缩略图",<br/>
	 *            "categorySort": 板块排序,<br/>
	 *            "categoryTitle": "板块标题"<br/>
	 *            }<br/>
	 */
	@RequestMapping("/{categoryId}/getByForumId")
	public void getByForumId(@PathVariable int categoryId, HttpServletRequest request, HttpServletResponse response) {
		CategoryEntity categoryEntity = (CategoryEntity)categoryBiz.getEntity(categoryId);
		this.outJson(response, JSONObject.toJSONStringWithDateFormat(categoryEntity, "yyyy-MM-dd HH:mm:ss"));
	}
}
