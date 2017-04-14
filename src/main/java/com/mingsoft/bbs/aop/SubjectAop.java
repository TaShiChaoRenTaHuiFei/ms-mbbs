package com.mingsoft.bbs.aop;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.bbs.biz.IForumBiz;
import com.mingsoft.bbs.biz.ISubjectBiz;
import com.mingsoft.bbs.constant.ModelCode;
import com.mingsoft.bbs.constant.e.SubjectEnum;
import com.mingsoft.bbs.entity.ForumEntity;
import com.mingsoft.bbs.entity.SubjectEntity;

import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.basic.util.SpringUtil;
import net.mingsoft.message.constant.e.MessageTypeEnum;

/**
 * 帖子切面
 * 
 * @author 史爱华
 * @version 版本号：100-000-000<br/>
 *          创建日期：2015-11-21<br/>
 *          历史修订：<br/>
 */
@Component
// 首先初始化切面类
@Aspect
// 声明为切面类，底层使用动态代理实现AOP
public class SubjectAop extends BaseAop {

	/**
	 * 注入板块业务层
	 */
	@Autowired
	private IForumBiz forumBiz;

	/**
	 * 注入帖子业务层
	 */
	@Autowired
	private ISubjectBiz subjectBiz;

	/**
	 * 分类业务层
	 */
	@Autowired
	private ICategoryBiz categoryBiz;

	/**
	 * 保存帖子
	 */
	@Pointcut("execution(* com.mingsoft.bbs.biz.impl.SubjectBizImpl.saveSubject(..))")
	public void saveSubject() {
	}

	/**
	 * 帖子被删除 删除指的是帖子被管理员设置成在前端页面不显示,不是真正的数据上的删除)
	 */
	@Pointcut("execution(* com.mingsoft.bbs.biz.impl.SubjectBizImpl.updateSubjectDisplay(..))")
	public void delete() {

	}


	/**
	 * 帖子被置顶
	 */
	@Pointcut("execution(* com.mingsoft.bbs.biz.impl.SubjectBizImpl.updateSort(..))")
	public void updateSort() {

	}

	/**
	 * 保存帖子的切面
	 * 
	 * @param jp
	 */
	@After("saveSubject()")
	public void saveSubject(JoinPoint jp) {
		SubjectEntity subject = this.getType(jp, SubjectEntity.class);
		if (subject != null) {
			ForumEntity forum = forumBiz.getByForumId(subject.getBasicCategoryId(), subject.getBasicAppId());
			if (forum != null) {
				forum.setForumCommentCount(forum.getForumCommentCount() + 1);
				forum.setForumLastSubjectTime(new Date());
				forumBiz.updateStatisticsInfo(forum);
			}
		}
	}

	/**
	 * 更新帖子的切面
	 * 
	 * @param jp
	 */
	@After("delete()")
	public void update(JoinPoint jp) {
		Object[] obj = jp.getArgs();
		// 获取切的方法名
		if (obj[0] instanceof Integer) {
			Integer subjectId = (Integer) obj[0];
			// 根据帖子id查找帖子
			SubjectEntity subject = (SubjectEntity) subjectBiz.getEntity(subjectId);
			if (subject != null) {
				Integer peopleId = (Integer) obj[2];
				// 帖子被设置成隐藏不显示
				if (subject != null && subject.getSubjectDisplay() == SubjectEnum.HIDE.toInt()) {
					this.sendMessage(0, subject.getBasicPeopleId(), peopleId, subject, "delete.subject",
							MessageTypeEnum.SYSTEM, null);
				}
			}
		}
	}


	

	/**
	 * 
	 * @param jp
	 */
	@After("updateSort()")
	public void updateSort(JoinPoint jp) {
		Object[] obj = jp.getArgs();
		if (obj[0] instanceof Integer) {
			// 根据帖子id查找帖子
			SubjectEntity subject = (SubjectEntity) subjectBiz.getEntity((Integer) obj[0]);
			if (subject != null) {
				Integer peopleId = (Integer) obj[4];
				if (peopleId == null) {
					return;
				}
				if (subject.getBasicSort() > 0) {
					this.sendMessage(0, subject.getBasicPeopleId(), peopleId, subject, "update.sort",
							MessageTypeEnum.SYSTEM, null);
					return;
				}
				if (subject.getBasicSort() == 0) {
					this.sendMessage(0, subject.getBasicPeopleId(), peopleId, subject, "delete.sort",
							MessageTypeEnum.SYSTEM, null);
				}
			}
		}
	}
	@Pointcut("execution(* com.mingsoft.bbs.action.SubjectAction.edit(..))")
	public void edit() { 
		
	}
	@Around("edit()")
	public Object addOrEdit(ProceedingJoinPoint pjp) throws Throwable {
		
		int mallTypemodelId = BasicUtil.getModelCodeId(ModelCode.BBS_SUBJECT_TYPE);
		List<CategoryEntity> mallTypeList = this.categoryBiz.queryByAppIdOrModelId(BasicUtil.getAppId(),mallTypemodelId);
		HttpServletRequest request = SpringUtil.getRequest();
		Object obj = pjp.proceed();
		request.setAttribute("subjectTypes", mallTypeList);
		return obj;
	}
}
