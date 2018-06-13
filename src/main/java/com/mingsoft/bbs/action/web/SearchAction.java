package com.mingsoft.bbs.action.web;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.bbs.action.BaseAction;
import com.mingsoft.bbs.biz.ISubjectBiz;
import com.mingsoft.bbs.constant.Const;
import com.mingsoft.bbs.constant.e.SubjectEnum;
import com.mingsoft.bbs.entity.SubjectEntity;
import com.mingsoft.bbs.parser.BbsParser;
import com.mingsoft.mdiy.biz.ISearchBiz;
import com.mingsoft.mdiy.entity.SearchEntity;
import com.mingsoft.parser.IParserRegexConstant;
import com.mingsoft.mdiy.parser.ListParser;
import com.mingsoft.util.FileUtil;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;

/**
 * 铭飞MBbs 搜索模块
 * @author 铭飞开发团队
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2016年4月6日<br/>
 * 历史修订：<br/>
 */
@Controller(value = "webBbsSearchAction")
@RequestMapping("/mbbs")
public class SearchAction extends BaseAction {

	/**
	 * 注入搜索业务层
	 */
	@Autowired
	private ISearchBiz searchBiz;

	/**
	 * 注入帖子业务层
	 */
	@Autowired
	private ISubjectBiz subjectBiz;

	/**
	 * mbbs解析器
	 */
	@Autowired
	private BbsParser bbsParser;

	/**
	 * 对论坛的的搜索模板进行解析
	 * 
	 * @param request
	 * @param searchId
	 *            搜索模板id
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/{searchId}/search")
	@ResponseBody
	public void search(HttpServletRequest request, @PathVariable int searchId, HttpServletResponse response) {
		// 获取系统模版存放物理路径
		String tmpPath = getRealPath(request, IParserRegexConstant.REGEX_SAVE_TEMPLATE);
		AppEntity app = this.getApp(request);
		// 获取模版名称
		String tmpName = app.getAppStyle();
		int appId = app.getAppId();

		int pageNo = this.getPageNo(request);

		Map<String, String[]> field = new HashMap<String, String[]>();
		field = request.getParameterMap(); // 读取请求字段
		// 获取对应搜索模型
		SearchEntity search = (SearchEntity) searchBiz.getById(searchId);
		if (search != null) {
			String webSiteTmpPath = "";
			if (isMobileDevice(request) && !StringUtil.isBlank(app.getAppMobileStyle())) {
				// 根据站点id组装站点信息路径 格式：templets／站点ID/模版风格
				webSiteTmpPath = tmpPath + File.separator + app.getAppId() + File.separator + tmpName + File.separator
						+ app.getAppMobileStyle();
			} else {
				webSiteTmpPath = tmpPath + File.separator + app.getAppId() + File.separator + tmpName;
			}
			// 读取模板内容
			String htmlContent = FileUtil.readFile(webSiteTmpPath + File.separator + search.getSearchTemplets());
			// 分页连接地址
			String pageUrl = app.getAppHostUrl() + File.separator + Const.MBBS + File.separator + searchId + File.separator
					+ "search.do";

			// 读取列表标签中中的
			Map<String, String> property = com.mingsoft.bbs.parser.impl.ListParser.listProperty(htmlContent, true);

			// 自定义字段集合
			Map<String, String> searchMap = new HashMap<String, String>();
			// 遍历取字段集合
			for (Entry<String, String[]> entry : field.entrySet()) {
				if (entry != null) {
					String value = entry.getValue()[0];
					if (StringUtil.isBlank(value)) {
						continue;
					}
					String key = entry.getKey();
					if (request.getMethod().equals(RequestMethod.GET)) { // 如果是get方法需要将请求地址参数转吗
						value = StringUtil.isoToUTF8(value);
					}
					// 若为文章字段，则保存至文章字段集合；否则保存至自定义字段集合
					if (!StringUtil.isBlank(value)) {
						searchMap.put(key, value);
					}
					htmlContent = htmlContent.replaceAll("\\{ms:search." + key + "/\\}", value);

				}
			}
			int count = this.subjectBiz.getCountByMapForSearch(appId, searchMap, SubjectEnum.DISPLAY);
			// 默认显示的数量为20条
			int size = count;
			// 列表每页显示的数量
			if (StringUtil.string2Int(property.get(ListParser.LIST_SIZE)) > 0) {
				size = StringUtil.string2Int(property.get(ListParser.LIST_SIZE));
			}
			searchMap.put("pageNo", null);
			pageUrl = StringUtil.buildUrl(pageUrl, searchMap);
			PageUtil page = new PageUtil(pageNo, size, count, pageUrl);
			// 依据排序字段
			String orderBy = request.getParameter("orderby");
			// 排序方式
			String order = request.getParameter("order");
			// 默认是降序
			boolean _order = false;

			if (!StringUtil.isBlank(order) && order.equals("asc")) {
				_order = true;
			}
			List<SubjectEntity> subjectList = this.subjectBiz.queryByMapForSearch(appId, page, searchMap, orderBy,
					_order, SubjectEnum.DISPLAY);

			Map map = new HashMap();
			map.put(BbsParser.SUBJECT_SEARCH_LIST, subjectList);
			map.put(BbsParser.PAGE, page);
			// 移动端与pc端分离
			if (isMobileDevice(request) && !StringUtil.isBlank(app.getAppMobileStyle())) {
				htmlContent = bbsParser.parse(htmlContent, app, subjectList, map);
			} else {
				// 对模板内容进行解析
				htmlContent = bbsParser.parse(htmlContent, app, subjectList, map);
			}
			this.outString(response, htmlContent);
		}
	}
}
