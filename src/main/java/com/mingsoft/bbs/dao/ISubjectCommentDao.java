package com.mingsoft.bbs.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;

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
 *	        @ClassName: IMCommentDao
 *
 *			@Description: 评论持久化层的接口
 *
 *          <p>
 *          Comments:  继承IBaseDao
 *          </p>
 * 
 *          <p>
 *          Creatr Date:2015-4-6 上午9:11:25
 *          </p>
 * 
 *          <p>
 *          Modification history:暂无
 *          </p>
 */
public interface ISubjectCommentDao extends IBaseDao {
	/**
	 * 通过试图表根据文章编号查询评论
	 * 进行分页，排序
	 * @param basicId  文章编号
	 * @param commentCommentId 父类id
	 * @param page 分页对象
	 * @param orderBy  排序
	 * @param order  排序方式 ,true:asc;fales:desc
	 * @return 数据集合
	 */
	public List<CommentEntity> queryByView(@Param("basicId") Integer basicId,@Param("commentCommentId") Integer commentCommentId,@Param("page") PageUtil page,@Param("orderBy")String orderBy,@Param("order") boolean order); 
	
	/**
	 * 通过试图表根据文帖子id与父类id查询评论总条数
	 * @param basicId  帖子id
	 * @param commentCommentId 父类id
	 * @return
	 */
	public int countByView(@Param("basicId") Integer basicId,@Param("commentCommentId") Integer commentCommentId);
	/**
	 * 通过试图表根据文用户id与站点id查询评论
	 * 进行分页，排序
	 * @param appid  站点id
	 * @param peopleId  用户id
	 * @param page  分页对象
	 * @param orderBy  排序
	 * @param order  排序方式  ,true:asc;fales:desc
	 * @return 数据集合
	 */
	public List<CommentEntity> queryByPeopleId(@Param("appId") Integer appId,@Param("peopleId") Integer peopleId,@Param("page") PageUtil page,@Param("orderBy")String orderBy,@Param("order") boolean order);
	
	/**
	 * 通过试图表根据用户id与站点id查询评论历史总条数
	 * @param appid  站点id
	 * @param peopleId  用户id
	 * @return  评论总数
	 */
	public int countByPeopleId(@Param("peopleId") Integer peopleId,@Param("appId") Integer appId);

	/**
	 * 根据basicId查询最新评论时间
	 * @param basicId  帖子id
	 * @return
	 */
	public Date  getLastPostTime(@Param("basicId") Integer basicId);
	
	/**
	 * 删除评论，多条或者一条
	 * @param ids  多条评论集合
	 * @param peopleId  用户编号
	 */
	public  void delete(@Param("ids")int[] ids,@Param("peopleId") int peopleId);
	
	/**
	 * 通过发帖作者id查询最新动态列表
	 * @param appId 站点id
	 * @param basicPeopleId  发帖作者id
	 * @param page 分页对象
	 * @param orderBy 排序
	 * @param order 排序方式  ,true:asc;fales:desc
	 * @return 最新动态集合
	 */
	public List<CommentEntity> queryByBasicPeopleId(@Param("appId") Integer appId,@Param("basicPeopleId") Integer basicPeopleId,@Param("page") PageUtil page,@Param("orderBy")String orderBy,@Param("order") boolean order);
	
	/**
	 * 通过发帖作者id查询最新动态总数
	 * @param basicPeopleId 发帖作者id
	 * @param appId 站点id
	 * @return 总体数
	 */
	public int countByBasicPeopleId(@Param("basicPeopleId") Integer basicPeopleId,@Param("appId") Integer appId);
	

	/**
	 * 根据帖子id删除帖子下面的所有评论
	 * @param ids  多条帖子id集合
	 */
	public  void deleteByBasicIds(@Param("ids")String[] ids);
	
	
	/**
	 * 根据用户id以及其他条件查询该用户帖子的所有评论总数
	 * @param basicPeopleId 用户id
	 * @param appId 应用id
	 * @param whereMap 其他条件
	 * @return 该用户所发帖子的所有评论总数
	 */
	public int getcountByPeopleIdAndMap(@Param("basicPeopleId") int basicPeopleId,@Param("appId") int appId,@Param("whereMap")Map whereMap);
	
	/**
	 * 根据用户id查询他的评论回复总条数
	 * @param peopleId 用户id
	 * @param appId  应用id
	 * @param whereMap 其他查询条件条件
	 * @return 回复总数
	 */
	int getReplyByPeopleId(@Param("peopleId") int peopleId,@Param("appId") int appId,@Param("whereMap")Map whereMap);
	
	/**
	 * 查询我的回复评论
	 * @param appId  应用id
	 * @param peopleId 用户id
	 * @param page 分页对象
	 * @param orderBy 依据排序字段
	 * @param order  是否降序
	 * @return 回复列表
	 */
	List<CommentEntity> queryReplyByPeopleId(@Param("appId") Integer appId,@Param("peopleId") Integer peopleId,@Param("page") PageUtil page,@Param("orderBy")String orderBy,@Param("order") boolean order,@Param("whereMap")Map whereMap);


	/**
	 * 更新评论的状态
	 * @param commentIds 评论id
	 * @param commentType 评论类型
	 */
	public void updateType(@Param("commentIds")int[] commentIds,@Param("commentType")int commentType);
	
}
