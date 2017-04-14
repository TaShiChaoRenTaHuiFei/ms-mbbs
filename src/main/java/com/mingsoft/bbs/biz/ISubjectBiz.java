package com.mingsoft.bbs.biz;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.basic.biz.IBasicBiz;
import com.mingsoft.bbs.constant.e.SubjectEnum;
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
 *	        @ClassName: ISubjectBiz
 *
 *			@Description: 帖子的业务层
 *
 *          <p>
 *          Comments:  论坛主题
 *          </p>
 * 
 *          <p>
 *          Creatr Date:2015-3-26
 *          </p>
 * 
 *          <p>
 *          Modification history:暂无
 *          </p>
 */
public interface ISubjectBiz extends IBasicBiz{
	
	
	
	/**
	 * 通过用户id查询帖子集合
	 * @param appId 站点id
	 * @param peopleId 用户id
	 * @param page 分页实体
	 * @return list结果集(帖子)
	 */
	List<SubjectEntity> queryByPeopleId(int appId,int peopleId,PageUtil page);
	
	/**
	 * 通过栏目id查询帖子总数
	 * @param appId 站点id
	 * @param categoryId　栏目Id
	 * @return　帖子总数
	 */
	int countByForumId(int appId,int categoryId,SubjectEnum isDisplay);
	/**
	 * 通过用户id查询帖子总数
	 * @param appId 站点id
	 * @param peopleId　栏目Id
	 * @return　帖子总数
	 */
	int countByPeopleId(int appId,int peopleId);
	
   
	
	/**
	 * 根据basicId删除两张表所有查到的记录
	 * @param appId 站点id
	 * @param list 是basicId的集合 ，逐个删除
	 */
	void delete(int appId,String[] ids);
	
	/**
	 * 根据basicId删除两张表所有查到的记录
	 * @param appId 站点id
	 * @param list 是basicId的集合 ，逐个删除
	 * @param peopleId 用户编号
	 */
	void deleteByPeopleId(int appId,String[] ids,Integer peopleId);


	/**
	 * 根据站点ID和时间查询帖子总数
	 * @param appId 站点ID
	 * @param day 时间
	 * @return    帖子总数
	 */
	int getCountByDay(int appId,Integer categoryId,String today,String tomorrow);
	
	/**
	 * 根据站点ID、帖子ID修改帖子是否显示内容
	 * @param appId 站点ID
	 * @param basicId 帖子ID
	 * @param subjectMark 判断帖子内容是否显示字段
	 * @param peopleId 对帖子属性用户进行修改的用户id,为0表示系统管理员
	 */
	void updateSubjectDisplay(int basicId, SubjectEnum subjectMark,int peopleId);

	
	/**
	 * 根据站点id和帖子类型查询帖子总数
	 * @param appId 应用id
	 * @param subjectTypeId 帖子类型id
	 * @return 帖子总数
	 */
	int  getCountBySubjectTypeId(int appId,Integer subjectTypeId,SubjectEnum isDisplay);
	
	/**
	 * 根据站点id和帖子类型查询帖子列表
	 * @param appId 应用id
	 * @param subjectTypeId 帖子类型id
	 *  @param PageUtil 分页对象
	 * @return  帖子列表
	 */
	List<SubjectEntity> queryBySubjectTypeId(int appId,Integer subjectTypeId,PageUtil page,SubjectEnum isDisplay);
	
	/**
	 * 根据站点id 和指定条件查询帖子总数
	 * @param appId 应用id
	 * @param whereMap 查询条件
	 * @return  帖子总数
	 */
	int getCountByMapForSearch(int appId,Map whereMap,SubjectEnum isDisplay);
	
	/**
	 * 根据应用id和指定条件查询帖子列表
	 * @param appId 应用id
	 * @param page 分页
	 * @param whereMap 指定的条件
	 * @return 返回帖子列表
	 */
	List<SubjectEntity> queryByMapForSearch(int appId,PageUtil page,Map whereMap,String orderBy,boolean order,SubjectEnum isDisplay);
	

	/**
	 * 更新帖子的顺序
	 * @param basicId 帖子id
	 * @param categoryId 帖子所在的板块id
	 * @param basicSort 更新后的排序
	 * @param appId 帖子所在的应用id
	 * @param 进行设置置顶的用户
	 */
	void updateSort(Integer basicId,Integer categoryId,int basicSort,int appId,Integer peopleId);
	
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
	int getCountByForumIdAndSubjectTypeId(int appId,Integer categoryId,String keyWord,String flag,String noFlag,SubjectEnum isDisplay);
	
	/**
	 * 根据帖子的属性和不显示该属性进行帖子列表的查询
	 * @param appId 站点id
	 * @param categoryId 栏目id
	 * @Param modelId 模块id
	 * @Param keyWord 关键字
	 * @param page 分页实体
	 * @param flag 推荐显示属性
	 * @param noFlag 不推荐显示属性
	 * @return list结果集(帖子)
	 */
	List<SubjectEntity> queryByForumIdAndSubjectTypeId(int appId,int categoryId,String keyWord,PageUtil page,String flag,String noFlag,String orderBy,boolean order,SubjectEnum isDisplay);
	
	/**
	 * 根据应用id和模块id或者是分类id获取最大是顺序值
	 * @param appId 应用id
	 * @param modelId  模块id
	 * @param categoryId 分类id
	 * @return 最大是顺序值
	 */
	int getMaxSort(int appId,Integer categoryId);
	
	/**
	 * 保存帖子
	 * @param subject 帖子实体
	 * @return
	 */
	int saveSubject(SubjectEntity subject);
	
	/**
	 * 根据帖子id查询帖子实体
	 * @param subjectId 帖子id
	 * @return 帖子实体
	 */
	public SubjectEntity getSubject(int subjectId);
	
	/**
	 * 对帖子的统计字段信息进行更新
	 * @param subject 帖子实体
	 */
	void updateStatisticsInfo(SubjectEntity subject);
	
}
