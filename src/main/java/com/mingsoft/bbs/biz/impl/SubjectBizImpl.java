package com.mingsoft.bbs.biz.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.basic.biz.impl.BasicBizImpl;
import com.mingsoft.basic.dao.ICategoryDao;
import com.mingsoft.basic.dao.IModelDao;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.bbs.biz.ISubjectBiz;
import com.mingsoft.bbs.constant.ModelCode;
import com.mingsoft.bbs.constant.e.SubjectEnum;
import com.mingsoft.bbs.dao.ISubjectDao;
import com.mingsoft.bbs.entity.SubjectEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;

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
 * @author 杨新远
 * 
 * @version 140-000-000
 * 
 *          <p>
 *          版权所有 铭飞科技
 *          </p>
 * 
 * @ClassName: SubjectBizImpl
 * 
 * @Description: 帖子业务层的实现类
 * 
 *               <p>
 *               Comments: 继承BasicBizImpl，实现ISubjectBiz
 *               </p>
 * 
 *               <p>
 *               Creatr Date:2015-3-26
 *               </p>
 * 
 *               <p>
 *               Modification history:暂无
 *               </p>
 */

@Service("subjectBiz")
public class SubjectBizImpl extends BasicBizImpl implements ISubjectBiz {

	/**
	 * 声明持久化层
	 */
	@Autowired
	private ISubjectDao subjectDao;

	
	/**
	 * 模块管理持久化层
	 */
	@Autowired
	private IModelDao modelDao;
	
	/**
	 * 分类关联持久化层
	 */
	@Autowired
	private ICategoryDao categoryDao;


	@Override
	public int countByForumId(int appId, int categoryId,SubjectEnum isDisplay) {
		//获取帖子模块实体
		ModelEntity model = modelDao.getEntityByModelCode(ModelCode.BBS_SUBJECT.toString());
		//帖子是否显示
		Integer display =null;
		if(isDisplay!=null){
			display =isDisplay.toInt() ;
		}
		if(categoryId!=0){
			//获取当前传入的板块id的实体
			CategoryEntity category = (CategoryEntity) categoryDao.getEntity(categoryId);
			//判断该板块实体是否存在
			if(category==null){
				return 0;
			}
			category.setCategoryManagerId(0);
			if(category.getCategoryCategoryId()==0){
				//判断是否存在子级
				List<CategoryEntity> child = this.categoryDao.queryChilds(category);
				
				if(child!=null && child.size()>0){
					//如果大于0否则判断是第二级或者是第三级（最多支持三级）
					category = (CategoryEntity) categoryDao.getEntity(category.getCategoryCategoryId());
					if(category==null){
						return subjectDao.getCountBySecondForumIdAndSubjectTypeId(appId, categoryId,null, model.getModelId(),null, null,display);
					}
					return subjectDao.getCountByFirstForumIdAndSubjectTypeId(appId, categoryId,null, model.getModelId(),null, null,display);
				}
				return subjectDao.getCountByForumIdAndSubjectTypeId(appId, categoryId,null, model.getModelId(),null, null,display);
			}
			//如果大于0否则判断是第二级或者是第三级（最多支持三级）
			category = (CategoryEntity) categoryDao.getEntity(category.getCategoryCategoryId());
			if(category!=null){
				//如果父板块仍存在父板块，则表示是最低级，否则是第二级
				if(category.getCategoryCategoryId()==0){
					category.setCategoryId(categoryId);
					//判断是否存在子级
					List<CategoryEntity> child = this.categoryDao.queryChilds(category);
					if(child!=null && child.size()>0){
						return subjectDao.getCountBySecondForumIdAndSubjectTypeId(appId, categoryId,null, model.getModelId(),null, null,display);
					}
					return subjectDao.getCountByForumIdAndSubjectTypeId(appId, categoryId,null, model.getModelId(),null, null,display);
				}
			}
		}	
		return subjectDao.getCountByForumIdAndSubjectTypeId(appId, categoryId,null, model.getModelId(),null, null,display);
	}

	@Override
	public int countByPeopleId(int appId, int peopleId) {
		//获取帖子模块实体
		ModelEntity model =modelDao.getEntityByModelCode(ModelCode.BBS_SUBJECT.toString());
		return subjectDao.countByPeopleId(appId, peopleId,model.getModelId());
	}


	@Override
	public void delete(int appId, String[] ids) {
		subjectDao.delete(appId, ids,null);
	}
	
	@Override
	public void deleteByPeopleId(int appId, String[] ids,Integer peopleId) {
		subjectDao.delete(appId, ids,peopleId);
	}



	@Override
	public List<SubjectEntity> queryByPeopleId(int appId, int peopleId, PageUtil page) {
		//获取帖子模块实体
		ModelEntity model =modelDao.getEntityByModelCode(ModelCode.BBS_SUBJECT.toString());
		return subjectDao.queryByPeopleId(appId, peopleId, page,model.getModelId());
	}

	@Override
	public int getCountByDay(int appId, Integer categoryId, String today, String tomorrow) {
		return subjectDao.getCountByDay(appId, categoryId, today, tomorrow);
	}

	@Override
	protected IBaseDao getDao() {
		return subjectDao;
	}

	@Override
	public void updateSubjectDisplay(int basicId, SubjectEnum subjectMark,int peopleId) {
		
		subjectDao.updateSubjectDisplay( basicId, subjectMark.toInt());
	}


	


	

	@Override
	public int getCountBySubjectTypeId(int appId, Integer subjectTypeId,SubjectEnum isDisplay) {
		//获取帖子模块实体
		ModelEntity model = modelDao.getEntityByModelCode(ModelCode.BBS_SUBJECT.toString());
		Integer display =null;
		if(isDisplay!=null){
			display =isDisplay.toInt() ;
		}
		return subjectDao.getCountBySubjectTypeId(appId, subjectTypeId,model.getModelId(),display);
	}

	@Override
	public List<SubjectEntity> queryBySubjectTypeId(int appId,
			Integer subjectTypeId,PageUtil page,SubjectEnum isDisplay) {
		//获取帖子模块实体
		ModelEntity model = modelDao.getEntityByModelCode(ModelCode.BBS_SUBJECT.toString());
		Integer display =null;
		if(isDisplay!=null){
			display =isDisplay.toInt() ;
		}
		return subjectDao.queryBySubjectTypeId(appId, subjectTypeId,page,model.getModelId(),display);
	}

	@Override
	public int getCountByMapForSearch(int appId,  Map whereMap,SubjectEnum isDisplay) {
		//获取帖子模块实体
		ModelEntity model =modelDao.getEntityByModelCode(ModelCode.BBS_SUBJECT.toString());
		Integer display =null;
		if(isDisplay!=null){
			display =isDisplay.toInt() ;
		}
		return subjectDao.getCountByMapForSearch(appId, model.getModelId(), whereMap,display);
	}

	@Override
	public List<SubjectEntity> queryByMapForSearch(int appId, PageUtil page,
			Map whereMap,String orderBy,boolean order,SubjectEnum isDisplay) {
		//获取帖子模块实体
		ModelEntity model = modelDao.getEntityByModelCode(ModelCode.BBS_SUBJECT.toString());
		if (orderBy!=null) {
			if (orderBy.equals("sort")) {
				orderBy = "basic_sort";
			} else if (orderBy.equals("date")) {
				orderBy = "basic_datetime";
			} else if (orderBy.equals("hit")) {
				orderBy = "basic_hit";
			} else if (orderBy.equals("updatedate")) {
				orderBy = "basic_updatedate";
			} else if (orderBy.equals("id")) {
				orderBy = "basic_id";
			}else if (orderBy.equals("commentnum")) {
				orderBy = "SUBJECT_COMMENT_COUNT";
			}else if(orderBy.equals("commenttime")){
				orderBy = "SUBJECT_LAST_COMMENT_TIME";
			}else{
				orderBy=null;
			}
		}
		Integer display =null;
		if(isDisplay!=null){
			display =isDisplay.toInt() ;
		}
		return this.subjectDao.queryByMapForSearch(appId, model.getModelId(), page, whereMap,orderBy,order,display);
	}

	
	@Override
	public void updateSort(Integer basicId,Integer categoryId,int basicSort,int appId,Integer peopleId) {
		//获取帖子模块实体
		ModelEntity model = modelDao.getEntityByModelCode(ModelCode.BBS_SUBJECT.toString());
		subjectDao.updateSort(basicId,categoryId, basicSort,appId,model.getModelId());
	}

	@Override
	public int getCountByForumIdAndSubjectTypeId(int appId, Integer categoryId, String keyWord, 
			String flag, String noFlag,SubjectEnum isDisplay) {
		String[] _flag = null;
		String[] _noFlag = null;
		if (!StringUtil.isBlank(flag)) {
			_flag = flag.split(",");
		}
		if (!StringUtil.isBlank(noFlag)) {
			_noFlag = noFlag.split(",");
		}		//获取帖子模块实体
		ModelEntity model = modelDao.getEntityByModelCode(ModelCode.BBS_SUBJECT.toString());
		Integer display =null;
		if(isDisplay!=null){
			display =isDisplay.toInt() ;
		}
		//默认是取顶级的id
		//如果分类id为0表示查询所有的帖子
		if(categoryId!=0){
			//获取当前传入的板块id的实体
			CategoryEntity category = (CategoryEntity) categoryDao.getEntity(categoryId);
			//判断该板块实体是否存在
			if(category==null){
				return 0;
			}
			category.setCategoryManagerId(0);
			if(category.getCategoryCategoryId()==0){
				//判断是否存在子级
				List<CategoryEntity> child = this.categoryDao.queryChilds(category);
				if(child!=null && child.size()>0){
					//如果大于0否则判断是第二级或者是第三级（最多支持三级）
					category = (CategoryEntity) categoryDao.getEntity(category.getCategoryCategoryId());
					if(category!=null){
						return subjectDao.getCountBySecondForumIdAndSubjectTypeId(appId, categoryId,keyWord, model.getModelId(),_flag, _noFlag,display);
					}
					return subjectDao.getCountByFirstForumIdAndSubjectTypeId(appId, categoryId,keyWord, model.getModelId(),_flag, _noFlag,display);
				}
				return subjectDao.getCountByForumIdAndSubjectTypeId(appId, categoryId,keyWord, model.getModelId(),_flag, _noFlag,display);
				
			}
			//如果大于0否则判断是第二级或者是第三级（最多支持三级）
			category = (CategoryEntity) categoryDao.getEntity(category.getCategoryCategoryId());
			if(category!=null){
				//如果父板块仍存在父板块，则表示是最低级，否则是第二级
				if(category.getCategoryCategoryId()==0){
					category.setCategoryId(categoryId);
					//判断是否存在子级
					List<CategoryEntity> child = this.categoryDao.queryChilds(category);
					if(child!=null && child.size()>0){
						return subjectDao.getCountBySecondForumIdAndSubjectTypeId(appId, categoryId,keyWord, model.getModelId(),_flag, _noFlag,display);
					}
					return subjectDao.getCountByForumIdAndSubjectTypeId(appId, categoryId, keyWord, model.getModelId(), _flag, _noFlag,display);
					
				}
			}
		}
		return subjectDao.getCountByForumIdAndSubjectTypeId(appId, categoryId, keyWord, model.getModelId(), _flag, _noFlag,display);
	}

	@Override
	public List<SubjectEntity> queryByForumIdAndSubjectTypeId(int appId, int categoryId,  String keyWord,
			PageUtil page, String flag, String noFlag,String orderBy,boolean order,SubjectEnum isDisplay) {
		String[] _flag = null;
		String[] _noFlag = null;
		if (!StringUtil.isBlank(flag)) {
			_flag = flag.split(",");
		}
		if (!StringUtil.isBlank(noFlag)) {
			_noFlag = noFlag.split(",");
		}
		//获取帖子模块实体
		ModelEntity model = modelDao.getEntityByModelCode(ModelCode.BBS_SUBJECT.toString());
		if (orderBy!=null) {
			if (orderBy.equals("sort")) {
				orderBy = "basic_sort";
			} else if (orderBy.equals("date")) {
				orderBy = "basic_datetime";
			} else if (orderBy.equals("hit")) {
				orderBy = "basic_hit";
			} else if (orderBy.equals("updatedate")) {
				orderBy = "basic_updatedate";
			} else if (orderBy.equals("id")) {
				orderBy = "basic_id";
			}else if (orderBy.equals("commentnum")) {
				orderBy = "SUBJECT_COMMENT_COUNT";
			}else if(orderBy.equals("commenttime")){
				orderBy = "SUBJECT_LAST_COMMENT_TIME";
			}else{
				orderBy=null;
			}
		}
		//帖子是否显示
		Integer display =null;
		if(isDisplay!=null){
			display =isDisplay.toInt() ;
		}
		//默认是取顶级的id
		//如果分类id为0表示查询所有的帖子
		if(categoryId!=0){
			//获取当前传入的板块id的实体
			CategoryEntity category = (CategoryEntity) categoryDao.getEntity(categoryId);
			//判断该板块实体是否存在
			if(category==null){
				return null;
			}
			category.setCategoryManagerId(0);
			if(category.getCategoryCategoryId()==0){
				if(category.getCategoryCategoryId()==0){
					//判断是否存在子级
					List<CategoryEntity> child = this.categoryDao.queryChilds(category);
					if(child!=null && child.size()>0){
						//如果大于0否则判断是第二级或者是第三级（最多支持三级）
						category = (CategoryEntity) categoryDao.getEntity(category.getCategoryCategoryId());
						if(category!=null){
							return subjectDao.queryBySecondForumIdAndSubjectTypeId(appId, categoryId, model.getModelId(), keyWord, page, flag, noFlag, orderBy, order,display);
						}
						return subjectDao.queryByFirstForumIdAndSubjectTypeId(appId, categoryId, model.getModelId(), keyWord, page, flag, noFlag, orderBy, order,display);
					}
				}
				
			}
			//如果大于0否则判断是第二级或者是第三级（最多支持三级）
			category = (CategoryEntity) categoryDao.getEntity(category.getCategoryCategoryId());
			if(category!=null){
				//如果父板块仍存在父板块，则表示是最低级，否则是第二级
				if(category.getCategoryCategoryId()==0){
					category.setCategoryId(categoryId);
					//判断是否存在子级
					List<CategoryEntity> child = this.categoryDao.queryChilds(category);
					if(child!=null && child.size()>0){
						return subjectDao.queryBySecondForumIdAndSubjectTypeId(appId, categoryId, model.getModelId(), keyWord, page, flag, noFlag, orderBy, order,display);
					}
					return subjectDao.queryByForumIdAndSubjectTypeId(appId, categoryId, model.getModelId(), keyWord, page, _flag, _noFlag,orderBy,order,display);
					
				}
			}
		}
		return subjectDao.queryByForumIdAndSubjectTypeId(appId, categoryId, model.getModelId(), keyWord, page, _flag, _noFlag,orderBy,order,display);
	}

	@Override
	public int getMaxSort(int appId,  Integer categoryId) {
		//获取帖子模块实体
		ModelEntity model =modelDao.getEntityByModelCode(ModelCode.BBS_SUBJECT.toString());
		return subjectDao.getMaxSort(appId, model.getModelId(), categoryId);
	}

	@Override
	public int saveSubject(SubjectEntity subject) {
		// TODO Auto-generated method stub
		return this.saveBasic(subject);
	}

	@Override
	public SubjectEntity getSubject(int subjectId) {
		// TODO Auto-generated method stub
		return (SubjectEntity) this.getEntity(subjectId);
	}

	@Override
	public void updateStatisticsInfo(SubjectEntity subject) {
		// TODO Auto-generated method stub
		this.subjectDao.updateStatisticsInfo(subject);
	}

}
