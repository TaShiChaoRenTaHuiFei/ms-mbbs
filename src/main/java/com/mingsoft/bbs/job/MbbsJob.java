package com.mingsoft.bbs.job;

import java.util.Calendar;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mingsoft.base.job.BaseJob;
import com.mingsoft.bbs.biz.IForumBiz;


public class MbbsJob extends BaseJob{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//获取当前
		IForumBiz forumBiz = (IForumBiz) this.getBean("forumBiz");
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    final double diff = cal.getTimeInMillis() - System.currentTimeMillis();
	    //如果是凌晨
	    if(diff==0){
	    	//清空掉今日发帖数，将今日发帖数设置成昨日发帖数
	    	//forumBiz.updateStatisticsInfo(forum);
	    }
		
	}

}
