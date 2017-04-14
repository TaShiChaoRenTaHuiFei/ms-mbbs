package com.mingsoft.bbs.action.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mingsoft.base.action.BaseAction;
import com.mingsoft.base.entity.ListJson;
import com.mingsoft.bbs.biz.ISubjectCommentBiz;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.util.PageUtil;

import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.comment.biz.ICommentBiz;
import net.mingsoft.comment.entity.CommentEntity;

/**
 * mbbs帖子评论模块
 * @author qxb51
 * @version
 * 
 */
@Controller("webBbsCommentAction")
@RequestMapping("/mbbs/subject")
public class CommentAction extends BaseAction{

	/**
	 * 注入帖子评论业务层
	 */
	@Autowired
	private ISubjectCommentBiz subjectCommentBiz;
	
	@Autowired
	private ICommentBiz commentBiz;
	
	/**
	 * 根据帖子basicId查询帖子评论信息
	 * @param comment
	 * @param request
	 * @param response
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute CommentEntity comment, HttpServletRequest request,HttpServletResponse response) {
		BasicUtil.startPage(BasicUtil.getPageNo(), BasicUtil.getPageSzie(), true);
		List commentList = commentBiz.query(comment);
		PageInfo pageInfo= BasicUtil.endPage(commentList);
		this.outJson(response, JSONObject.toJSONStringWithDateFormat(commentList, "yyyy-MM-dd HH:mm:ss"));
	}
}
