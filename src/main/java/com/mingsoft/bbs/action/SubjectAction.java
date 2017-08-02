package com.mingsoft.bbs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.constant.e.CookieConstEnum;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.bbs.biz.IForumBiz;
import com.mingsoft.bbs.biz.ISubjectBiz;
import com.mingsoft.bbs.constant.ModelCode;
import com.mingsoft.bbs.entity.SubjectEntity;
import com.mingsoft.util.PageUtil;

@Controller("bbsManagerSubject")
@RequestMapping("/${managerPath}/bbs/subject")
public class SubjectAction extends BaseAction {

	/**
	 * 帖子业务逻辑层
	 */
	@Autowired
	private ISubjectBiz subjectBiz;

	/**
	 * 帖子板块业务逻辑层
	 */
	@Autowired
	private IForumBiz forumBiz;
	
	@Autowired
	private ICategoryBiz categoryBiz;
	
	

	/**
	 * 查询帖子列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		// 获取站点id
		int appId = this.getAppId(request);
		// 获取当前页数
		int pageNo = this.getPageNo(request);
		// 获取一页显示条数
		int pageSize = this.getPageSize(request);
		// 板块id
		int forumId = this.getInt(request, "forumId", 0);
		// 关键字
		String keyWord = request.getParameter("keyWord");

		// 获取总条数
		int pageCount = subjectBiz.getCountByForumIdAndSubjectTypeId(appId, forumId, keyWord, null, null, null);

		String url = "/manager/bbs/subject/list.do?forumId=" + forumId + "&keyWord=" + (keyWord == null ? "" : keyWord);
		// 分页
		PageUtil page = new PageUtil(pageNo, pageSize, pageCount, getUrl(request) + url);

		// 获取分页实体
		List<SubjectEntity> subjectList = subjectBiz.queryByForumIdAndSubjectTypeId(appId, forumId, keyWord, page, null,
				null, null, false, null);
		// 获取板块列表
		List<CategoryEntity> forumList = forumBiz.queryByAppIdOrModelId(appId,
				this.getModelCodeId(request, ModelCode.BBS_CATEGORY));

		this.setCookie(request, response, CookieConstEnum.BACK_COOKIE,url+"&pageNo="+pageNo);
		model.addAttribute("subjectList", subjectList);
		model.addAttribute("page", page);
		model.addAttribute("forumList", forumList);
		return view("/bbs/subject/subject_list");
	}

	/**
	 * 删除帖子
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		int appId = this.getAppId(request);
		String[] ids = request.getParameterValues("ids");
		if (ids.length == 0 || ids == null) {
			this.outJson(response, ModelCode.BBS_SUBJECT, false);
			return;
		}
		// 删除多个帖子
		subjectBiz.delete(appId, ids);
		this.outJson(response, ModelCode.BBS_SUBJECT, true);
	}

	/**
	 * 编辑帖子
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(@ModelAttribute SubjectEntity subject, HttpServletRequest request,
			HttpServletResponse response) {
		SubjectEntity _subject = subjectBiz.getSubject(subject.getSubjectBasicId());
		
		request.setAttribute("subject", _subject);
		request.setAttribute("appId", this.getApp(request).getAppId());
		return view("/bbs/subject/subject_form");
	}

	/**
	 * 更新帖子
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/update")
	public void update(@ModelAttribute SubjectEntity subject, HttpServletRequest request,
			HttpServletResponse response) {
		subjectBiz.updateBasic(subject);
		this.outJson(response, true);
	}

}
