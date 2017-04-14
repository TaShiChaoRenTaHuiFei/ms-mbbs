package com.mingsoft.bbs.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.bbs.entity.SubjectEntity;
import com.mingsoft.util.PageUtil;


/**
 *  
 * <p>
 * <b>铭飞-BBS论坛平台</b> 
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇铭飞科技有限公司
 * </p>
 * 
 * @author  杨新远
 * 
 * @version 140-000-000
 * 
 *          <p>
 *          版权所有 铭飞科技
 *          </p>
 *
 *	        @ClassName: ISubjectDao
 *
 *			@Description: 帖子的数据层
 *
 *          <p>
 *          Comments:  继承IBasicDao
 *          </p>
 * 
 *          <p>
 *          Create Date:2015-3-24
 *          </p>
 * 
 *          <p>
 *          Modification history:暂无
 *          </p>
 */
public interface ISubjectDao extends IBaseDao{
	
	
	/**
	 * 通过用户id查询帖子集合
	 * @param appId 站点id
	 * @param peopleId 用户id
	 * @param page 分页实体
	 * @return list结果集(帖子)
	 */
	List<SubjectEntity> queryByPeopleId(@Param("appId") Integer appId,@Param("peopleId") Integer peopleId,@Param("page") PageUtil page,@Param("modelId")int modelId);
	
	
	
	
	
	
	
	/**
	 * 通过用户id查询帖子总数
	 * @param appId 站点id
	 * @param peopleId　栏目Id
	 * @return　帖子总数
	 */
	int countByPeopleId(@Param("appId") Integer appId,@Param("peopleId") Integer peopleId,@Param("modelId")int modelId);
	
	
	/**
	 * 根据basicId删除两张表所有查到的记录
	 * @param appId 站点id
	 * @param list 是basicId的集合 ，逐个删除
	 * @param peopleId 用户编号
	 */
	void delete(@Param("appId") Integer appId,@Param("ids")String[] ids,@Param("peopleId")Integer peopleId);
	
	
	/**
	 * 根据站点ID、帖子ID修改帖子属性
	 * @param appId 站点ID
	 * @param basicId 帖子ID
	 * @param subjectType 帖子类型
	 */
	void deleteSubjectType(@Param("appId") Integer appId,@Param("basicId")Integer basicId,@Param("subjectType") Integer subjectType);
	
	/**
	 * 根据站点ID和时间查询帖子总数
	 * @param appId 站点ID
	 * @param day 时间
	 * @return 帖子总数
	 */
	int getCountByDay(@Param("appId") Integer appId,@Param("categoryId") Integer categoryId, @Param("today") String today,@Param("tomorrow")String tomorrow);

	/**
	 * 根据站点ID、帖子ID修改帖子是否显示内容
	 * @param appId 站点ID
	 * @param basicId 帖子ID
	 * @param subjectMark 判断帖子内容是否显示字段
	 */
	void updateSubjectDisplay(@Param("basicId") int basicId,@Param("subjectMark") Integer subjectMark);
	
	
	/**
	 * 根据站点id和帖子类型查询帖子总数
	 * @param appId 应用id
	 * @param model 模块id
	 * @param subjectTypeId 帖子类型id
	 * @return
	 */
	int  getCountBySubjectTypeId(@Param("appId")int appId,@Param("subjectTypeId")Integer subjectTypeId,@Param("modelId")Integer modelId,@Param("isDisplay")Integer isDisplay);
	
	/**
	 * 根据站点id和帖子类型查询帖子列表
	 * @param appId 应用id
	 * @param subjectTypeId 帖子类型id
	 * @param page 分页对象
	 * @param model 模块id
	 * @return
	 */
	List<SubjectEntity> queryBySubjectTypeId(@Param("appId")int appId,@Param("subjectTypeId")Integer subjectTypeId,@Param("page")PageUtil page,@Param("modelId")Integer modelId,@Param("isDisplay")Integer isDisplay);
	
	/**
	 * 根据站点id 和指定条件查询帖子总数
	 * @param appId 应用id
	 * @param modelId 帖子所属模块id
	 * @param page 分页
	 * @param whereMap 查询条件
	 * @return  帖子总数
	 */
	int getCountByMapForSearch(@Param("appId")int appId,@Param("modelId")int modelId,@Param("whereMap")Map whereMap,@Param("isDisplay")Integer isDisplay);
	
	/**
	 * 根据站点id和指定条件查询帖子的列表
	 * @param appId 应用id
	 * @param modelId 帖子所属模块id
	 * @param page 分页
	 * @param whereMap 查询条件
	 * @return 帖子列表
	 */
	List<SubjectEntity> queryByMapForSearch(@Param("appId")int appId,@Param("modelId")int modelId,@Param("page")PageUtil page,@Param("whereMap")Map whereMap,@Param("orderBy")String orderBy,@Param("order")boolean order,@Param("isDisplay")Integer isDisplay);
	
	
	
	/**
	 * 更新帖子的顺序
	 * @param basicId 帖子id
	 * @param categoryId 分类id
	 * @param basicSort 更新后的排序
	 * @param appId 帖子所在的应用id
	 * @param modelId 帖子对应的模块id
	 */
	void updateSort(@Param("basicId")Integer basicId,@Param("categoryId")Integer categoryId,@Param("basicSort")int basicSort,@Param("appId")int appId ,@Param("modelId")int modelId);
	
	/**
	 * 根据帖子的属性和不显示该属性进行帖子的总数的查询
	 * @param appId 应用id
	 * @param categoryId 分类id
	 * @param keyWord 关键字
	 * @param modelId 模块id
	 * @param flag 推荐显示属性
	 * @param noFlag 不推荐显示属性
	 * @return 
	 */
	int getCountByForumIdAndSubjectTypeId(@Param("appId") int appId,@Param("categoryId") Integer categoryId, @Param("keyWord") String keyWord,@Param("modelId")int modelId,@Param("flag")String[] flag,@Param("noFlag")String[] noFlag,@Param("isDisplay")Integer isDisplay);
	
	/**
	 * 根据帖子的属性和不显示该属性进行帖子列表的查询
	 * @param appId 站点id
	 * @param categoryId 三级板块id
	 * @Param modelId 模块id
	 * @Param keyWord 关键字
	 * @param page 分页实体
	 * @param flag 推荐显示属性
	 * @param noFlag 不推荐显示属性
	 * @param orderBy 依据排序字段
	 * @param order 排序方式
	 * @return list结果集(帖子)
	 */
	List<SubjectEntity> queryByForumIdAndSubjectTypeId(@Param("appId")int appId,@Param("categoryId")int categoryId,@Param("modelId")int modelId,@Param("keyWord")String keyWord,@Param("page")PageUtil page,@Param("flag")String[] flag,@Param("noFlag")String[] noFlag,@Param("orderBy")String orderBy,@Param("order")boolean order,@Param("isDisplay")Integer isDisplay);
	
	/**
	 * 根据应用id和模块id或者是分类id获取最大是顺序值
	 * @param appId 应用id
	 * @param modelId  模块id
	 * @param categoryId 分类id
	 * @return 最大是顺序值
	 */
	int getMaxSort(@Param("appId")int appId,@Param("modelId")int modelId,@Param("categoryId")Integer categoryId);
	
	/**
	 * 根据帖子的一级板块id或者帖子的属性和不显示该属性进行帖子列表的查询
	 * @param appId 站点id
	 * @param firstForumId 一级板块id
	 * @Param modelId 模块id
	 * @Param keyWord 关键字
	 * @param page 分页实体
	 * @param flag 推荐显示属性
	 * @param noFlag 不推荐显示属性
	 * @param orderBy 依据排序字段
	 * @param order 排序方式
	 * @return list结果集(帖子)
	 */
	List<SubjectEntity> queryByFirstForumIdAndSubjectTypeId(@Param("appId")int appId,@Param("firstForumId")int firstForumId,@Param("modelId")int modelId,@Param("keyWord")String keyWord,@Param("page")PageUtil page,@Param("flag")String flag,@Param("noFlag")String noFlag,@Param("orderBy")String orderBy,@Param("order")boolean order,@Param("isDisplay")Integer isDisplay);
	
	/**
	 * 根据帖子的属性和不显示该属性进行帖子的总数的查询
	 * @param appId 应用id
	 * @param firstForumId 一级板块id
	 * @param keyWord 关键字
	 * @param modelId 模块id
	 * @param flag 推荐显示属性
	 * @param noFlag 不推荐显示属性
	 * @return 
	 */
	int getCountByFirstForumIdAndSubjectTypeId(@Param("appId") int appId,@Param("firstForumId") Integer firstForumId, @Param("keyWord") String keyWord,@Param("modelId")int modelId,@Param("flag")String[] flag,@Param("noFlag")String[] noFlag,@Param("isDisplay")Integer isDisplay);
	
	/**
	 * 根据帖子的属性和不显示该属性进行帖子列表的查询
	 * @param appId 站点id
	 * @param secondForumId 二级板块id
	 * @Param modelId 模块id
	 * @Param keyWord 关键字
	 * @param page 分页实体
	 * @param flag 推荐显示属性
	 * @param noFlag 不推荐显示属性
	 * @param orderBy 依据排序字段
	 * @param order 排序方式
	 * @return list结果集(帖子)
	 */
	List<SubjectEntity> queryBySecondForumIdAndSubjectTypeId(@Param("appId")int appId,@Param("secondForumId")int secondForumId,@Param("modelId")int modelId,@Param("keyWord")String keyWord,@Param("page")PageUtil page,@Param("flag")String flag,@Param("noFlag")String noFlag,@Param("orderBy")String orderBy,@Param("order")boolean order,@Param("isDisplay")Integer isDisplay);

	/**
	 * 根据帖子的属性和不显示该属性进行帖子的总数的查询
	 * @param appId 应用id
	 * @param firstForumId 一级板块id
	 * @param keyWord 关键字
	 * @param modelId 模块id
	 * @param flag 推荐显示属性
	 * @param noFlag 不推荐显示属性
	 * @return 
	 */
	int getCountBySecondForumIdAndSubjectTypeId(@Param("appId") int appId,@Param("secondForumId") Integer secondForumId, @Param("keyWord") String keyWord,@Param("modelId")int modelId,@Param("flag")String[] flag,@Param("noFlag")String[] noFlag,@Param("isDisplay")Integer isDisplay);
	
	/**
	 * 对帖子的统计字段信息进行更新
	 * @param subject 帖子实体
	 */
	void updateStatisticsInfo(SubjectEntity subject);
	
}
