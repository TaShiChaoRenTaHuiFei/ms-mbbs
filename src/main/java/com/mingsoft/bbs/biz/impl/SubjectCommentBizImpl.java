package com.mingsoft.bbs.biz.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.bbs.biz.ISubjectCommentBiz;
import com.mingsoft.bbs.constant.e.SubjectCommentTypeEnum;
import com.mingsoft.bbs.dao.ISubjectCommentDao;
import net.mingsoft.comment.biz.impl.CommentBizImpl;
import net.mingsoft.comment.entity.CommentEntity;
import com.mingsoft.util.PageUtil;

@Service("subjectCommentBizImpl")
public class SubjectCommentBizImpl extends CommentBizImpl implements ISubjectCommentBiz{

	/**
	 * 注入IMCommentDao持久化层
	 */
	@Autowired
	private ISubjectCommentDao subjectCommentDao;
	
	/**
	 * 扩展评论模块不需要重写getDao方法
	 */
//	@Override
//	protected IBaseDao getDao() {
//		// TODO Auto-generated method stub
//		return subjectCommentDao;
//	}
	
	@Override
	@Deprecated
	public List<CommentEntity> queryByView(Integer basicId,Integer commentCommentId, PageUtil page, String orderBy,boolean order) {
		//查询父类评论集合
		List<CommentEntity> list = this.subjectCommentDao.queryByView(basicId, commentCommentId, page, orderBy, order);
		//循环查询父类评论的子评论
		for(int i = 0; i<list.size();i++){ 
			//查询子评论集合
			List<CommentEntity> commentList = this.subjectCommentDao.queryByView(basicId, list.get(i).getCommentId(), null, orderBy, order);
			//把查询到的子类评论集合加到父类评论的commentList里
			list.get(i).setChildComment(commentList);
		}
		return list;
	}

	@Override
	public List<CommentEntity> queryByPeopleId(Integer appId,Integer peopleId, PageUtil page,String orderBy, boolean order) {
		return subjectCommentDao.queryByPeopleId(appId,peopleId,page, orderBy, order);
	}

	@Override
	public int countByPeopleId(Integer peopleId,Integer appId) {
		return subjectCommentDao.countByPeopleId(peopleId, appId);
	}
	
	@Override
	@Deprecated
	public int countByView(Integer basicId,Integer commentCommentId){
		return subjectCommentDao.countByView(basicId, commentCommentId);
	}

	@Override
	public Date getLastPostTime(Integer basicId){
		return subjectCommentDao.getLastPostTime(basicId);
	}
	
	@Override
	public void delete(int[] ids){
		subjectCommentDao.delete(ids);
	}
	

	@Override
	public void delete(int[] ids, int peopleId) {
		subjectCommentDao.delete(ids,peopleId);
	}
	
	
	@Override
	public List<CommentEntity> queryByBasicPeopleId(Integer appId,Integer basicPeopleId,PageUtil page,String orderBy,boolean order){
		return subjectCommentDao.queryByBasicPeopleId(appId, basicPeopleId, page, orderBy, order);
	}
	
	@Override
	public int countByBasicPeopleId(Integer basicPeopleId,Integer appId){
		return subjectCommentDao.countByBasicPeopleId(basicPeopleId, appId);
	}
	
	@Override
	public void deleteByBasicIds(String[] ids){
		subjectCommentDao.deleteByBasicIds(ids);
	}

	

	@Override
	public int getcountByPeopleIdAndMap(int basicPeopleId, int appId, Map whereMap) {
		// TODO Auto-generated method stub
		return subjectCommentDao.getcountByPeopleIdAndMap(basicPeopleId, appId, whereMap);
	}

	@Override
	public int saveComment(CommentEntity comment) {
		// TODO Auto-generated method stub
		return this.saveEntity(comment);
	}

	@Override
	public int getReplyByPeopleId(int peopleId, int appId, Map whereMap) {
		// TODO Auto-generated method stub
		return subjectCommentDao.getReplyByPeopleId(peopleId, appId, whereMap);
	}

	@Override
	public List<CommentEntity> queryReplyByPeopleId(int appId, int peopleId, PageUtil page, String orderBy,
			boolean order, Map whereMap) {
		// TODO Auto-generated method stub
		return subjectCommentDao.queryReplyByPeopleId(appId, peopleId, page, orderBy, order, whereMap);
	}

	@Override
	public void updateType(int[] commentIds, SubjectCommentTypeEnum commentType) {
		// TODO Auto-generated method stub
		if(commentIds.length>0){
			if(commentType!=null){
				subjectCommentDao.updateType(commentIds, commentType.toInt());
			}
			
		}
	}
	
}
