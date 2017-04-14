package com.mingsoft.bbs.action.people;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mingsoft.basic.constant.ModelCode;
import com.mingsoft.base.entity.ListJson;
import com.mingsoft.bbs.action.BaseAction;
import com.mingsoft.bbs.biz.ISubjectBiz;
import com.mingsoft.bbs.biz.ISubjectCommentBiz;
import com.mingsoft.bbs.constant.e.SubjectCommentTypeEnum;
import com.mingsoft.bbs.entity.SubjectEntity;
import net.mingsoft.comment.entity.CommentEntity;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;

/**
 * MBbs系统－会员管理中心－评论模块
 * 
 * @author 铭飞开发团队
 * @version 版本号：100-000-000<br/>
 *          创建日期：2016年4月1日<br/>
 *          历史修订：<br/>
 */
@Controller("bbsComment")
@RequestMapping("/people/mbbs/comment")
public class SubjectCommentAction extends BaseAction {

	/**
	 * 帖子业务注入
	 */
	@Autowired
	private ISubjectBiz subjectBiz;
	/**
	 * 评论的处理业务注入
	 */
	@Autowired
	private ISubjectCommentBiz subjectCommentBiz;

	/**
	 * 用户删除评论
	 * 
	 * @param commentIds
	 *            评论编号 多个编号用逗号隔开 例如:1,2,3,4
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(@ModelAttribute SubjectEntity subject, HttpServletRequest request,
			HttpServletResponse response) {

		// 根据subjectId获取帖子实体
		SubjectEntity _subject = (SubjectEntity) subjectBiz.getEntity(subject.getBasicId());
		// 判断评论实体是否为空，如果为空，则返回
		if (_subject == null) {
			this.outJson(response, com.mingsoft.bbs.constant.ModelCode.BBS_SUBJECT, false,
					this.getResString("err.not.exist", this.getResString("subject")));
			return;
		}
		// 获取评论id集合
		String[] ids = request.getParameterValues("commentIds");
		// 判断勾选框的值是否为空
		if (!StringUtil.isBlank(ids) && StringUtil.isIntegers(ids)) {
			// 删除多条评论
			subjectCommentBiz.delete(StringUtil.stringsToInts(ids), this.getPeopleBySession(request).getPeopleId());
			// 返回json数据，结束
			this.outJson(response, null, true, this.getResString("ok"));
			return;
		} else {
			this.outJson(response, null, false, this.getResString("fail", this.getResString("remove")));
		}
	}

	/**
	 * 用户评论列表
	 * <dt><span class="strong">返回</span></dt><br/>
	 * {count:"错误编码",<br/>
	 * list:{"count":1,"list":[{"commentBasicId":被评论信息编号,"commentCommentId":父评论编号,"commentId":评论编号,"commentPoints":评论分数,"commentTime":"时间"}]} 
	 * }
	 */
	@RequestMapping(value="/list", method = RequestMethod.POST)
	@ResponseBody
	public void list(HttpServletRequest request, HttpServletResponse response) {
		// 获取站点id
		int appId = this.getAppId(request);
		// 获取当前页面的页码参数，将当前页码转化成整型值
		int pageNo = this.getPageNo(request);
		// 获取每页的条数，将默认每页数转化成整型值
		int pageSize = this.getPageSize(request);
		// 获取people的session
		PeopleEntity people = (PeopleEntity) this.getPeopleBySession(request);
		// 获取当前用户id
		int peopleId = people.getPeopleId();
		// 获取评论条数
		int count = subjectCommentBiz.countByPeopleId(peopleId, appId);
		// 分页
		PageUtil page = new PageUtil(pageNo, pageSize, count, getUrl(request) + "");
		// 查询评论列表，带分页
		List<CommentEntity> list = subjectCommentBiz.queryByPeopleId(appId, peopleId, page, "COMMENT_TIME", false);
		ListJson json = new ListJson(count, list);
		this.outJson(response, JSONObject.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss"));
	}


	/**
	 * 用户提交评论
	 * @param rand_code
	 *            验证码

	 * @param comment
	 *            <i>comment参数包含字段信息参考：</i><br/>
	 *            commentContent 评论内容<br/>
	 *            commentBasicId 评论帖子编号<br/>
	 *            commentCommentId 评论的父评论编号<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@ModelAttribute CommentEntity comment, HttpServletRequest request, HttpServletResponse response) {
		// 验证码验证 验证码不为null 或 验证码不相等
		if (!this.checkRandCode(request)) {
			this.outJson(response, null, false, this.getResString("err.error", this.getResString("rand.code")));
			return;
		}

		// 通过session获取当前用户实体
		PeopleEntity people = (PeopleEntity) this.getPeopleBySession(request);
		// 判断评论实体是否为空，评论内容是否为空；
		if (comment != null && StringUtil.isBlank(comment.getCommentContent())) {
			this.outJson(response, null, false,
					this.getResString("err.empty", this.getResString("subject.comment.content")));
			return;
		}

		// 判断帖子实体是否存在
		SubjectEntity subject = (SubjectEntity) this.subjectBiz.getEntity(comment.getCommentBasicId());
		if (subject == null) {
			this.outJson(response, com.mingsoft.bbs.constant.ModelCode.BBS_COMMENT, false, this.getResString(
					"err.not.exist", this.getResString("subject", com.mingsoft.bbs.constant.Const.RESOURCES)));
			return;
		}
		// 如果是保存子评论，判断父评论是否存在
		if (comment.getCommentCommentId() > 0) {
			if (subjectCommentBiz.getEntity(comment.getCommentCommentId()) == null) {
				this.outJson(response, com.mingsoft.bbs.constant.ModelCode.BBS_COMMENT, false,
						this.getResString("err.not.exist",
								this.getResString("comment.comment", com.mingsoft.bbs.constant.Const.RESOURCES)));
				return;
			}
		}

		comment.setCommentPeopleId(people.getPeopleId());
		comment.setCommentAppId(this.getAppId(request));
		comment.setCommentTime(new Date());
		this.subjectCommentBiz.saveComment(comment);
		this.outJson(response, net.mingsoft.comment.constant.ModelCode.COMMENT, true, null,
				JSONObject.toJSONStringWithDateFormat(comment, "yyyy-MM-dd HH:mm:ss"));
	}

}
