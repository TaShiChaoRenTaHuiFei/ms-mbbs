package com.mingsoft.bbs.action.web;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingsoft.base.action.BaseAction;
import com.mingsoft.basic.entity.BasicEntity;
import com.mingsoft.bbs.biz.ISubjectBiz;
import com.mingsoft.bbs.entity.SubjectEntity;

import net.mingsoft.comment.entity.CommentEntity;

/**
 * mbbs帖子详情模块
 * @author qxb51
 *
 */

@Controller("webBbsSubjectAction")
@RequestMapping("/mbbs/subject")
public class SubjectAction extends BaseAction{

	@Autowired
	private ISubjectBiz subjectBiz;
	
	/**
	 * 根据subjectBasicId查询模板帖子的内容
	 * @param subject
	 * @param request
	 * @param response
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public void detail(@ModelAttribute SubjectEntity subject, HttpServletRequest request,HttpServletResponse response) {
		SubjectEntity detail = (SubjectEntity)subjectBiz.getSubject(subject.getSubjectBasicId());
		this.outJson(response, detail.getSubjectContent());
	}
}
