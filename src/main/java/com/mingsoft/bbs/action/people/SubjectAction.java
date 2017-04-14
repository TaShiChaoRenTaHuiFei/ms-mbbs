package com.mingsoft.bbs.action.people;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.base.entity.ListJson;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.bbs.action.BaseAction;
import com.mingsoft.bbs.biz.ISubjectBiz;
import com.mingsoft.bbs.entity.SubjectEntity;
import com.mingsoft.bbs.constant.Const;
import com.mingsoft.bbs.constant.ModelCode;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;

/**
 * MBbs系统，会员管理中心
 * 
 * @author 铭飞开发团队
 * @version 版本号：100-000-000<br/>
 *          创建日期：2016年3月31日<br/>
 *          历史修订：<br/>
 */
@Controller("peopleSubject")
@RequestMapping("/people/mbbs/subject")
public class SubjectAction extends BaseAction {

	/**
	 * 业务层的注入
	 */
	@Autowired
	private ISubjectBiz subjectBiz;

	/**
	 * 用户发布的帖子记录列表
	 * <dt><span class="strong">返回</span></dt><br/>
	 * {count:记录数量,<br/>
	 * list:[{basicId:帖子编号，basicTitlte:标题}]}
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(HttpServletRequest request, HttpServletResponse response) {
		// 得到站点id
		int appId = this.getAppId(request);
		PeopleEntity people = (PeopleEntity) this.getPeopleBySession(request);
		// 从session中得到用户id
		int peopleId = people.getPeopleId();
		// 当前页数
		int pageNo = this.getPageNo(request);
		// 总页数
		int pageSize = this.getPageSize(request);
		// 通过站点id和用户id得到帖子总数
		int count = subjectBiz.countByPeopleId(appId, peopleId);
		// 得到分页对象
		PageUtil page = new PageUtil(pageNo, pageSize, count, getUrl(request) + "");
		List<SubjectEntity> list = subjectBiz.queryByPeopleId(appId, peopleId, page);
		ListJson json = new ListJson(count, list);
		this.outJson(response, JSONObject.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 发帖
	 * 
	 * @param rand_code
	 *            验证码
	 * @param subject
	 *            <i>subject参数包含字段信息参考：</i><br/>
	 *            basicTitle 标题 6到70个字<br/>
	 *            subjectContent 内容<br/>
	 *            basicCategoryId 论坛版本id 一级<br/>
	 *            subjectForumFirstId 论坛版本id 一级<br/>
	 *            subjectForumSecondId 论坛版本id 二级<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 * 
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@ModelAttribute SubjectEntity subject, HttpServletRequest request, HttpServletResponse response) {
		PeopleEntity people = (PeopleEntity) this.getPeopleBySession(request);

		// 验证码验证 验证码不为null 或 验证码不相等
		if (!this.checkRandCode(request)) {
			this.outJson(response, null, false, this.getResString("err.error", this.getResString("rand.code")));
			return;
		}

		// 用户没有选择栏目，提示请选择
		if (subject.getBasicCategoryId() == 0) {
			this.outJson(response, null, false,
					this.getResString("err.empty", this.getResString("category.title", Const.RESOURCES)));
			return;
		}
		// 用户没有填写帖子标题，提示用户填写
		if (StringUtil.isBlank(subject.getBasicTitle())) {
			this.outJson(response, null, false,
					this.getResString("err.empty", this.getResString("basic.title", Const.RESOURCES)));
			return;
		}
		// 用户没有填写帖子内容，提示用户帖子内容
		if (StringUtil.isBlank(subject.getSubjectContent())) {
			this.outJson(response, null, false,
					this.getResString("err.empty", this.getResString("subject.content", Const.RESOURCES)));
			return;
		}
		// 当帖子标题>=70个字的时候，输出错误
		if (!StringUtil.checkLength(subject.getBasicTitle(), 6, 70)) {
			this.outJson(response, null, false,
					getResString("err.length", this.getResString("basic.title", Const.RESOURCES), "6", "70"));
			return;
		}

		int appId = this.getAppId(request);
		int modelId = this.getModelCodeId(request, ModelCode.BBS_SUBJECT);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		subject.setBasicDateTime(time);// 保存时间
		subject.setBasicModelId(modelId);// 保存模块id
		subject.setBasicAppId(appId);// 保存站点id
		subject.setBasicPeopleId(people.getPeopleId());// 保存用户id
		subjectBiz.saveSubject(subject);// 保存对象
		this.outJson(response, null, true, this.getResString("success", this.getResString("save")));
	}

	/**
	 * 读取帖子信息
	 * 
	 * @param basicId
	 *            帖子编号 <i>subject参数包含字段信息参考：</i><br/>
	 *            basicTitle 标题 6到70个字<br/>
	 *            subjectContent 内容<br/>
	 *            basicCategoryId 论坛版本id<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {basicTitle:标题,subjectContent:帖子内容,basicCategoryId:板块分类}  
	 */
	@RequestMapping(value = "/{basicId}/detail", method = RequestMethod.POST)
	@ResponseBody
	public void edit(@PathVariable int basicId, HttpServletRequest request, HttpServletResponse response) {
		// 主题对象
		SubjectEntity subject = (SubjectEntity) subjectBiz.getEntity(basicId);
		if(subject.getBasicPeopleId() == this.getPeopleBySession(request).getPeopleId()) {
			this.outJson(response, subject);
		} 
	}

	/**
	 * 编辑
	 * 
	 * @param rand_code
	 *            验证码
	 * @param subject
	 *            <i>subject参数包含字段信息参考：</i><br/>
	 *            basicTitle 标题 6到70个字<br/>
	 *            subjectContent 内容<br/>
	 *            basicCategoryId 论坛版本id<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 */
	@RequestMapping(value = "/{basicId}/update", method = RequestMethod.POST)
	@ResponseBody
	public void update(@ModelAttribute SubjectEntity subject, @PathVariable int basicId, HttpServletRequest request,
			HttpServletResponse response) {
		PeopleEntity people = (PeopleEntity) this.getPeopleBySession(request);

		// 验证码验证 验证码不为null 或 验证码不相等
		if (!this.checkRandCode(request)) {
			this.outJson(response, null, false, this.getResString("err.error", this.getResString("rand.code")));
			return;
		}
		// 用户没有选择栏目，提示请选择
		if (subject.getBasicCategoryId() <= 0) {
			this.outJson(response, null, false,
					this.getResString("err.error", this.getResString("basic.categoryId", Const.RESOURCES)));
			return;
		}
		// 用户没有填写帖子标题，提示用户填写
		if (StringUtil.isBlank(subject.getBasicTitle())) {
			this.outJson(response, null, false,
					this.getResString("err.empty", this.getResString("basic.title", Const.RESOURCES)));
			return;
		}
		// 当帖子标题>=70个字的时候，输出错误
		if (!StringUtil.checkLength(subject.getBasicTitle(), 0, 70)) {
			this.outJson(response, null, false,
					getResString("err.length", this.getResString("basic.title", Const.RESOURCES), "0", "70"));
			return;
		}
		// 用户没有填写帖子内容，提示用户帖子内容
		if (StringUtil.isBlank(subject.getSubjectContent())) {
			this.outJson(response, null, false,
					this.getResString("err.empty", this.getResString("subject.content", Const.RESOURCES)));
			return;
		}

		// 根据帖子Id查询帖子实体,判断帖子实体是否存在
		SubjectEntity oldeSubject = (SubjectEntity) subjectBiz.getEntity(basicId);
		if (oldeSubject == null) {
			this.outJson(response, com.mingsoft.bbs.constant.ModelCode.BBS_SUBJECT, false, this.getResString(
					"err.not.exist", this.getResString("subject", com.mingsoft.bbs.constant.Const.RESOURCES)));
			return;
		}

		oldeSubject.setBasicTitle(subject.getBasicTitle());// 更改标题
		subject.setBasicUpdateTime(new Date());
		subjectBiz.updateBasic(subject);
		this.outJson(response, null, true, this.getResString("success"));
	}

	/**
	 * 删除帖子
	 * 
	 * @param subjectIds
	 *            集合
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		// 通过session获取当前用户实体
		PeopleEntity people = (PeopleEntity) this.getPeopleBySession(request);
		// 获取站点id
		int appId = this.getAppId(request);
		// 当basicId为空时，获取勾选框的值
		String[] ids = request.getParameterValues("subjectIds");
		// 判断勾选框的值是否为空
		if (StringUtil.isIntegers(ids)) {
			// 删除多条评论
			subjectBiz.deleteByPeopleId(appId, ids, people.getPeopleId());
			// 返回json数据，结束
			this.outJson(response, null, true, this.getResString("success"));
			return;
		} else {
			// 如果id集合为空，返回false，结束
			this.outJson(response, null, false, this.getResString("err.error", this.getResString("subject.id")));
		}
	}

}
