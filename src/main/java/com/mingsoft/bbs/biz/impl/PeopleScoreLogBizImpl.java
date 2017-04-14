package com.mingsoft.bbs.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.bbs.bean.PeopleScoreBean;
import com.mingsoft.bbs.biz.IPeopleGroupBiz;
import com.mingsoft.bbs.biz.IPeopleScoreLogBiz;
import com.mingsoft.bbs.dao.IForumFunctionScoreDao;
import com.mingsoft.bbs.dao.IFunctionDao;
import com.mingsoft.bbs.dao.IPeopleScoreLogDao;
import com.mingsoft.bbs.entity.ForumFunctionScoreEntity;
import com.mingsoft.bbs.entity.FunctionEntity;
import com.mingsoft.bbs.entity.PeopleGroupEntity;
import com.mingsoft.bbs.entity.PeopleScoreLogEntity;

/**
 * 用户积分变更日志业务层实现类
 * @Package com.mingsoft.bbs.biz.impl
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年12月03日<br/>
 * 历史修订：<br/>
 */
@Service("peopleScoreLogBiz")
public class PeopleScoreLogBizImpl   extends BaseBizImpl implements IPeopleScoreLogBiz{
	
	/**
	 * 注入用户日志变更持久化层
	 */
	@Autowired
	private IPeopleScoreLogDao peopleScoreLogDao;
	
	/**
	 * 用户组业务层
	 */
	@Autowired
	private IPeopleGroupBiz peopleGroupBiz;
	
	/**
	 * 功能业务层
	 */
	@Autowired
	private IFunctionDao functionDao;
	
	
	/**
	 * 板块的操作积分奖励规则持久化层
	 */
	@Autowired
	private IForumFunctionScoreDao forumFunctionScoreDao;
	
	
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return peopleScoreLogDao;
	}

	@Override
	public List<PeopleScoreLogEntity> queryByPeopleId(int peopleId) {
		// TODO Auto-generated method stub
		return peopleScoreLogDao.queryByPeopleId(peopleId);
	}

	@Override
	public List<PeopleScoreBean> queryByPeopleIdGroupByScoreTypeId(int peopleScoreLogPeopleId) {
		
		
		// TODO Auto-generated method stub
		return peopleScoreLogDao.queryByPeopleIdGroupByScoreTypeId(peopleScoreLogPeopleId);
	}

	@Override
	public void saveBatch(int peopleId, int appId, int forumId,String functionMethod) {
		//获取发帖的功能实体
		FunctionEntity function = functionDao.getByFunctionMethod(appId, functionMethod);
		if(function==null){
			return;
		}
		//获取用户所在组
		PeopleGroupEntity peopleGroup = peopleGroupBiz.getPeopleGroup(peopleId, appId);
		if(peopleGroup==null){
			return;
		}
		//获取用户操作日志的变更
		List<ForumFunctionScoreEntity> forumFunctionScoreList =  this.forumFunctionScoreDao.queryList(forumId,peopleGroup.getCategoryId(),function.getFunctionId());
		if(forumFunctionScoreList!=null && forumFunctionScoreList.size()>0){
			List<PeopleScoreLogEntity> peopleScoreLogList = new ArrayList<PeopleScoreLogEntity>();
			for(int i=0;i<forumFunctionScoreList.size();i++){
				ForumFunctionScoreEntity forumfucScore = forumFunctionScoreList.get(i);
				//如果积分数是0则不进行积分变更记录的增加
				if(forumfucScore.getForumFunctionScoreScoreNum()!=0){
					PeopleScoreLogEntity peopleScoreLog= new PeopleScoreLogEntity();
					//插入操作的积分类型
					peopleScoreLog.setPeopleScoreLogBankScoreId(forumfucScore.getForumFunctionScoreBankScoreId());
					//操作的板块id
					peopleScoreLog.setPeopleScoreLogForumId(forumId);
					//操作的积分数量
					peopleScoreLog.setPeopleScoreLogScoreNum(forumfucScore.getForumFunctionScoreScoreNum());;
					peopleScoreLog.setPeopleScoreLogFunctionId(function.getFunctionId());
					peopleScoreLog.setPeopleScoreLogPeopleId(peopleId);
					peopleScoreLogList.add(peopleScoreLog);
				}
				
			}
			//对积分变更日志进行批量保存
			if(peopleScoreLogList!=null && peopleScoreLogList.size()>0){
				this.peopleScoreLogDao.saveBatch(peopleScoreLogList);
			}
			
		}
		
	}
	
	

}
