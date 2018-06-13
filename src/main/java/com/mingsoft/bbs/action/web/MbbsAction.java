/**
 * 
 */
package com.mingsoft.bbs.action.web;

import static com.mingsoft.bbs.constant.Const.DETAIL;
import static com.mingsoft.bbs.constant.Const.LIST;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.basic.entity.BasicEntity;
import com.mingsoft.basic.parser.IGeneralParser;
import com.mingsoft.bbs.biz.IForumBiz;
import com.mingsoft.bbs.biz.ISubjectBiz;
import com.mingsoft.bbs.constant.Const;
import com.mingsoft.bbs.constant.ModelCode;
import com.mingsoft.bbs.constant.e.SubjectEnum;
import com.mingsoft.bbs.entity.ForumEntity;
import com.mingsoft.bbs.entity.SubjectEntity;
import com.mingsoft.bbs.parser.BbsParser;
import com.mingsoft.mdiy.biz.IPageBiz;
import com.mingsoft.mdiy.entity.PageEntity;
import com.mingsoft.parser.IParser;
import com.mingsoft.people.entity.PeopleUserEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;

import net.mingsoft.basic.util.BasicUtil;

/**
 * 铭飞MBbs论坛系统
 * 
 * @author 铭飞开发团队
 * @version 版本号：100-000-000<br/>
 *          创建日期：2016年4月1日<br/>
 *          历史修订：<br/>
 */
@Controller("mbbsAction")
@RequestMapping("/mbbs")
@Scope("prototype")
public class MbbsAction extends com.mingsoft.bbs.action.BaseAction {

	/**
	 * 论坛解析器
	 */
	@Autowired
	private BbsParser bbsParser;

	/**
	 * 栏目逻辑层注入
	 */
	@Autowired
	private IForumBiz forumBiz;

	/**
	 * 模板模型
	 */
	@Autowired
	private IPageBiz pageBiz;

	/**
	 * 帖子业务层的注入
	 */
	@Autowired
	private ISubjectBiz subjectBiz;

	/**
	 * 详情
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{basicId}/detail")
	@ResponseBody
	public void detail(@PathVariable int basicId, HttpServletRequest request, HttpServletResponse response) {
		AppEntity app = this.getApp(request);
		String listLinkPath = StringUtil.buildPath(app.getAppHostUrl(), Const.MBBS, basicId, DETAIL + IParser.DO_SUFFIX)
				.substring(1);
		// 获取当前页面的页码参数，将当前页码转化成整型值
		int curPageNo = this.getPageNo(request);
		// 主题对象
		SubjectEntity subject = (SubjectEntity) subjectBiz.getSubject(basicId);
		if (subject == null) {
			this.outString(response, this.getResString("err.not.exist", this.getResString("subject.content")));
			return;
		}
		// 判断帖子是否被设置为不显示，如果是则跳转无法查看的自定义页面
		if (subject.getSubjectDisplay() == SubjectEnum.HIDE.toInt()) {
			try {
				response.sendRedirect("/mbbs/noaceess.do");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		Map map = new HashMap();
		String html = readTemplate(Const.MBBS + File.separator + DETAIL + IParser.HTM_SUFFIX, request);
		//List<BasicEntity> list = subjectBiz.getPreviousAndNext(basicId);
		// 更新帖子点击量
		subjectBiz.updateHit(basicId, null);

		map.put(IGeneralParser.CUR_PAGE_NO, curPageNo);
		map.put(IGeneralParser.LIST_LINK_PATH, listLinkPath);
//		if (list.size() > 0) {
//			map.put(IGeneralParser.PREVIOUS, list.get(0));
//			if (list.size() > 1) {
//				map.put(IGeneralParser.NEXT, list.get(1));
//			}
//		}
		//map.put(BbsParser.POSTION, forumBiz.queryParent(this.getAppId(request), ModelCode.BBS_CATEGORY.toInt(),subject.getBasicCategoryId()));
		map.put(BbsParser.POSTION, "");
		map.put(IGeneralParser.REQUEST_PARAM, this.assemblyRequestMap(request));
		String content = bbsParser.parse(html, app, subject, map);
		this.outString(response, content);
	}

	/**
	 * mbbs自定义页面
	 * 
	 * @param key
	 *            自定义页面对应的模板名称
	 * @param req
	 * @param resp
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/{diy}")
	public void diy(@PathVariable(value = "diy") String diy, HttpServletRequest req, HttpServletResponse resp) {

//		 PeopleUserEntity peopleUserEntity = new PeopleUserEntity();
//		 peopleUserEntity.setPeopleId(8836);
//		 this.setPeopleBySession(req, peopleUserEntity); 

		AppEntity app = this.getApp(req);
		// 完整的项目模板路径
		String listLinkPath = StringUtil.buildPath(app.getAppHostUrl(), Const.MBBS, diy + IParser.DO_SUFFIX)
				.substring(1);

		// 获取当前页面的页码参数，将当前页码转化成整型值
		int curPageNo = this.getPageNo(req);
		// 获取每页的条数，将默认每页数转化成整型值
		Integer pageSize = this.getInt(req, "pageSize", 20);

		int recordCount = subjectBiz.countByForumId(app.getAppId(), 0, SubjectEnum.DISPLAY);
		PageUtil page = new PageUtil(curPageNo, pageSize, recordCount, listLinkPath);
		// ============================
		PageEntity _page = new PageEntity();
		_page.setPageAppId(BasicUtil.getAppId());
		_page.setPageKey(Const.MBBS + "/" + diy);
		PageEntity mte = (PageEntity)pageBiz.getEntity(_page);
		if (mte == null) {
			try {
				resp.sendRedirect("/error/404.do");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String html = this.readTemplate(mte.getPagePath(), req);
		// ====================
		Map map = new HashMap();
		map.put(IGeneralParser.LIST_LINK_PATH, listLinkPath);
		map.put(IGeneralParser.CUR_PAGE_NO, curPageNo);
		map.put(IGeneralParser.REQUEST_PARAM, this.assemblyRequestMap(req));
		html = bbsParser.parse(html, app, map, page);

		this.outString(resp, html);
	}

	/**
	 * 列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{categoryId}/list")
	@ResponseBody
	public void list(@PathVariable int categoryId, HttpServletRequest request, HttpServletResponse response) {
		AppEntity app = this.getApp(request);
		ForumEntity forum = forumBiz.getByForumId(categoryId, app.getAppId());
		if (forum != null) {
			// 获取当前页面的页码参数，将当前页码转化成整型值
			int curPageNo = this.getPageNo(request);
			// 获取每页的条数，将默认每页数转化成整型值
			int pageSize = this.getPageSize(request,20);
			// 依据排序字段
			String orderBy = request.getParameter("orderby");
			// 排序方式
			String order = request.getParameter("order");
			String subjectType = request.getParameter("subjectType");

			// 默认是降序
			boolean _order = false;
			if (!StringUtil.isBlank(order) && order.equals("asc")) {
				_order = true;
			}
			int recordCount = subjectBiz.getCountByForumIdAndSubjectTypeId(app.getAppId(), categoryId, null,
					subjectType, null, SubjectEnum.DISPLAY);
			// 完整的项目模板路径
			String listLinkPath = StringUtil
					.buildPath(app.getAppHostUrl(), Const.MBBS, categoryId, LIST + IParser.DO_SUFFIX).substring(1);
			Map wheremap = assemblyRequestMap(request);// 读取请求字段
			wheremap.put("pageNo", null);
			listLinkPath = StringUtil.buildUrl(listLinkPath, wheremap);
			PageUtil page = new PageUtil(curPageNo, pageSize, recordCount, listLinkPath);
			// 主题对象
			Map map = new HashMap();
			String html = this.readTemplate(Const.MBBS + File.separator + LIST + IParser.HTM_SUFFIX, request);
			// 查询当前板块下的帖子
			List<SubjectEntity> subjectList = this.subjectBiz.queryByForumIdAndSubjectTypeId(app.getAppId(), categoryId,
					null, page, subjectType, null, orderBy, _order, SubjectEnum.DISPLAY);
			map.put(BbsParser.MOBILE, null);
			map.put(IGeneralParser.CUR_PAGE_NO, curPageNo);
			map.put(IGeneralParser.LIST_LINK_PATH, listLinkPath);
			map.put(BbsParser.SUBJECT_SEARCH_LIST, subjectList);
			map.put(BbsParser.PAGE, page);
			map.put(IGeneralParser.REQUEST_PARAM, this.assemblyRequestMap(request));
//			map.put(BbsParser.POSTION, forumBiz.queryParent(this.getAppId(request), ModelCode.BBS_CATEGORY.toInt(),
//					forum.getCategoryId()));
			map.put(BbsParser.POSTION, "");
			String content = bbsParser.parse(html, app, forum, page, map);
			this.outString(response, content);
		}
	}

}
