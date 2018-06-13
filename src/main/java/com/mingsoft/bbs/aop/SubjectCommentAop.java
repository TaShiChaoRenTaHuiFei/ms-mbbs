package com.mingsoft.bbs.aop;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mingsoft.bbs.biz.IForumBiz;
import com.mingsoft.bbs.biz.ISubjectBiz;
import com.mingsoft.bbs.entity.ForumEntity;
import com.mingsoft.bbs.entity.SubjectEntity;
import net.mingsoft.comment.biz.ICommentBiz;
import net.mingsoft.comment.entity.CommentEntity;
import com.mingsoft.people.biz.IPeopleUserBiz;
import com.mingsoft.people.entity.PeopleUserEntity;
import com.mingsoft.util.StringUtil;

/**
 * 帖子评论切面
 * @author 史爱华
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015-11-21<br/>
 * 历史修订：<br/>
 */
@Component
//首先初始化切面类
@Aspect
//声明为切面类，底层使用动态代理实现AOP
public class SubjectCommentAop extends BaseAop{
	
	/**
	 * 帖子业务层
	 */
	@Autowired
	private ISubjectBiz subjectBiz;
	
	/**
	 * 用户实体
	 */
	@Autowired
	private IPeopleUserBiz peopleUserBiz;
	
	/**
	 * 注入板块业务层
	 */
	@Autowired
	private IForumBiz forumBiz;
	
	
	/**
	 * 注入评论业务层
	 */
	@Autowired
	private ICommentBiz commentBiz;
	
	
	
	
	/**
	 * 保存评论
	 */
	@Pointcut("execution(* com.mingsoft.bbs.biz.impl.SubjectCommentBizImpl.saveComment(..) )")
	public void saveComment(){
	}
	
	
	/**
	 * 保存的切面
	 * @param jp
	 */
	@After("saveComment()")
	public void saveComment(JoinPoint jp){
		Object[] obj = jp.getArgs();
		//帖子的保存
		if (obj[0] instanceof CommentEntity) {
			CommentEntity comment = (CommentEntity) obj[0];
			if(comment==null){
				return;
			}
			//对帖子的总评论进行统计
			SubjectEntity subject = (SubjectEntity) this.subjectBiz.getEntity(comment.getCommentBasicId());
			if(subject!=null){
				subject.setSubjectLastCommentTime(new Date());
				subject.setSubjectTotalComment(subject.getSubjectTotalComment()+1);
				//评论者的用户
				PeopleUserEntity people = (PeopleUserEntity) this.peopleUserBiz.getEntity(comment.getCommentPeopleId());
				if(people!=null){
					String peopleName = people.getPeopleName();
					if(StringUtil.isBlank(peopleName)){
						peopleName=people.getPuNickname();
					}
					subject.setSubjectLastCommentPeopleName(peopleName);
					subjectBiz.updateStatisticsInfo(subject);
				}
				
				//如果评论的不是发帖人自己则，进行消息的发送
				if(comment.getCommentPeopleId()!=subject.getBasicPeopleId()){
				//	this.sendMessage(0, subject.getBasicPeopleId(), people.getPeopleId(), subject,"comment.subject", MessageTypeEnum.BBS,null);
				}
				//判断是否是子评论
				if(comment.getCommentCommentId()>0){
					CommentEntity parentComment = (CommentEntity) this.commentBiz.getEntity(comment.getCommentCommentId());
					//自己对自己进行回复不会进行消息的发送
					if(parentComment!=null && parentComment.getCommentPeopleId()!=comment.getCommentPeopleId()){
				//		this.sendMessage(0,parentComment.getCommentPeopleId(), comment.getCommentPeopleId(), subject,"comment.reply", MessageTypeEnum.BBS,null);
					}
				}
				ForumEntity forum = forumBiz.getByForumId(subject.getBasicCategoryId(), subject.getBasicAppId());
				if(forum!=null){
					forum.setForumCommentCount(forum.getForumCommentCount()+1);
					forum.setForumLastCommentTime(new Date());
					forumBiz.updateStatisticsInfo(forum);
					return; 
				}
			}
		}
	}
	
	
}

