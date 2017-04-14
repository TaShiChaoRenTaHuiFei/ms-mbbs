package com.mingsoft.bbs.biz;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.bbs.constant.e.SubjectCommentTypeEnum;
import net.mingsoft.comment.biz.ICommentBiz;
import net.mingsoft.comment.entity.CommentEntity;
import com.mingsoft.util.PageUtil;
/**
 * 
 *<p>
 * <b>铭飞-BBS论坛平台</b> 
 *</p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇铭飞科技有限公司
 * </p>
 * 
 * @author 李书宇
 * 
 * @version 140-000-000
 * 
 *          <p>
 *          版权所有 铭飞科技
 *          </p>
 *
 *	        @ClassName: IMCommentBiz
 *
 *			@Description: 评论的业务层接口
 *
 *          <p>
 *          Comments:  继承通用评论业务层的接口
 *          </p>
 * 
 *          <p>
 *          Creatr Date:2015-4-6 上午9:43:15
 *          </p>
 * 
 *          <p>
 *          Modification history:暂无
 *          </p>
 */
public interface ISubjectCommentBiz extends ICommentBiz {

	/**
	 * 通过试图表根据文章编号查询评论
	 * 进行分页，排序
	 * @param basicId 文章编号
	 * @param commentCommentId 父类id
	 * @param page 分页对象
	 * @param orderBy  排序
	 * @param order  排序方式,true:asc;fales:desc
	 * @return  数据集合
	 */
	@Deprecated
	public List<CommentEntity> queryByView(Integer basicId,Integer commentCommentId,PageUtil page,String orderBy,boolean order);

	/**
	 * 通过试图表根据用户id查询评论
	 * 进行分页，排序
	 * @param appId  应用编号
	 * @param peopleId  用户id
	 * @param page  分页对象
	 * @param orderBy  排序
	 * @param order  排序方式,true:asc;false:desc
	 * @return  数据集合
	 */
	public List<CommentEntity> queryByPeopleId(Integer appId,Integer peopleId,PageUtil page,String orderBy,boolean order);

	/**
	 * 通过试图表根据用户id查询评论总条数
	 * @param peopleId  用户id
	 * @return  总条数
	 */
	@Deprecated
	public int countByPeopleId(Integer peopleId,Integer appId);
	
	/**
	 * 通过试图表根据帖子id和父类id查询评论总条数
	 * @param basicId  帖子id
	 * @param commentCommentId   父类id
	 * @return
	 */
	@Deprecated
	public int countByView(Integer basicId,Integer commentCommentId);
	
	/**
	 * 通过basicId查询评论的最新时间
	 * @param basicId  帖子id
	 * @return
	 */
	public Date getLastPostTime(Integer basicId);
	
	/**
	 * 多条评论删除
	 * @param ids  评论id集合
	 */
	public void delete(int[] ids);
	
	
	/**
	 * 多条评论删除
	 * @param ids  评论id集合
	 * @param peopleId 用户编号
	 */
	public void delete(int[] ids,int peopleId);
	
	/**
	 * 通过发帖作者id查询最新动态列表
	 * @param appId 站点id
	 * @param basicPeopleId 发帖作者id
	 * @param page  分页对象
	 * @param orderBy 排序
	 * @param order 排序方式  ,true:asc;fales:desc
	 * @return 最新动态集合
	 */
	public List<CommentEntity> queryByBasicPeopleId(Integer appId,Integer basicPeopleId,PageUtil page,String orderBy,boolean order);

	/**
	 *  通过发帖作者id查询最新动态总数
	 * @param basicPeopleId 发帖作者id
	 * @param appId 站点id
	 * @return
	 */
	public int countByBasicPeopleId(Integer basicPeopleId,Integer appId);
	
	/**
	 * 根据帖子id删除帖子下面的所有评论
	 * @param ids  多条帖子id集合
	 */
	public void deleteByBasicIds(String[] ids);
	
	/**
	 * 根据用户id以及其他条件查询该用户帖子的所有评论总数
	 * @param basicPeopleId 用户id
	 * @param appId 应用id
	 * @param whereMap 其他条件
	 * @return 该用户所发帖子的所有评论总数
	 */
	public int getcountByPeopleIdAndMap(int basicPeopleId,int appId,Map whereMap);
	
	/**
	 * 保存评论
	 * @param comment 评论实体
	 * @return 评论id
	 */
	int saveComment(CommentEntity comment);
	
	/**
	 * 根据用户id查询他的评论回复总条数
	 * @param peopleId 用户id
	 * @param appId  应用id
	 * @param whereMap
	 * @return
	 */
	int getReplyByPeopleId(int peopleId,int appId,Map whereMap);
	
	/**
	 * 查询我的回复评论
	 * @param appId  应用id
	 * @param peopleId 用户id
	 * @param page 分页对象
	 * @param orderBy 依据排序字段
	 * @param order  是否降序
	 * @return 回复列表
	 */
	List<CommentEntity> queryReplyByPeopleId( int appId,int peopleId,PageUtil page,String orderBy,boolean order,Map whereMap);
	
	/**
	 * 更新评论的状态
	 * @param commentIds 评论id
	 * @param commentType 评论类型
	 */
	public void updateType(int[] commentIds,SubjectCommentTypeEnum commentType);

}
