package com.mingsoft.bbs.action.people;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import net.mingsoft.attention.constant.e.AttentionTypeEnum;
import net.mingsoft.attention.entity.BasicAttentionEntity;
import com.mingsoft.base.entity.ListJson;
import com.mingsoft.bbs.action.BaseAction;
import com.mingsoft.bbs.biz.IAttentionBiz;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.util.PageUtil;



/**
 * <p>
 * <b>铭飞-科技</b>
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2015 - 2016
 * </p>
 * 
 * @author 石马人山
 * 
 * @version 300-001-001
 * 
 * @Description: bbs帖子关注模块页面控制层
 *
 * <p>
 * Create Date:2015-6-17 下午10:44:27
 * </p>
 * 
 * <p>
 * Modification history:
 * </p>
 */
@Controller("bbs/peopleAttention")
@RequestMapping("/people/bbs/attention")
public class AttentionAction extends BaseAction{


	/**
	 * 注入关注业务层
	 */
	@Resource(name="bbsAttentionBiz")
	private IAttentionBiz attentionBiz;
	
	
	/**
	 * 查询用户收藏，顶，帖子列表带分页</br>
	 * 
	 * 传入参数:</br>
	 * 		pageNo:分页,页码</br>
	 * 		pageSize:一页显示的数量</br>
	 * 		type:收藏类型</br>
	 * 			1:收藏
	 * 			2:顶
	 * @param request
	 * @param response
	 */
	@RequestMapping("/query")
	@ResponseBody
	public void query(HttpServletRequest request,HttpServletResponse response){
		
		//如果当前页数不存在则默认为第一页
		int pageNo = this.getInt(request, "pageNo", 1);
		
		//如果每页显示条数不存在默认为10条记录
		int pageSize  = this.getInt(request,"pageSize",10); 
		
		//如果关注类型不存在默认为收藏类型
		int attentionType = this.getInt(request,"type",AttentionTypeEnum.COLLECT.toInt());
		
		//通过模块编码获取模块id
		int modelId = this.getModelCodeId(request, com.mingsoft.bbs.constant.ModelCode.BBS_SUBJECT);

		//获取用户ID
		PeopleEntity people = (PeopleEntity) this.getPeopleBySession(request);
		int peopleId = people.getPeopleId();
		
		//获取APPid
		int appId = this.getAppId(request);

		//获取关注的数量
		int count = this.attentionBiz.countByPeople(peopleId, appId, this.getAttentionType(attentionType, AttentionTypeEnum.COLLECT), modelId);
		
		//创建分页对象
		PageUtil page = new PageUtil(pageNo,pageSize, count, getUrl(request)+"/people/bbs/attention/queryList.do");
		
		//查询关注的列表
		List<BasicAttentionEntity> peopleSubjectAttentionlist = this.attentionBiz.queryPageAndSbujectByPeopleIdAndAppId(peopleId, appId,attentionType,page);
		
		//如果关注不存在则输出空，否则输出查询到的信息
		ListJson json = new ListJson(count,peopleSubjectAttentionlist);
		this.outJson(response,JSONObject.toJSONStringWithDateFormat(json,"yyyy-MM-dd HH:mm:ss"));

	}
	
	/**
	 * 遍历关注枚举类
	 * @param type 传入的关注类型
	 * @param def 枚举类参数
	 * @return 返回整形
	 */
	private AttentionTypeEnum getAttentionType(int type,AttentionTypeEnum def) {
		AttentionTypeEnum[] ate = AttentionTypeEnum.values();
		for (AttentionTypeEnum _ate:ate) {
			if (type==_ate.toInt()) {
				return _ate;
			}
		}
		return def;
	}
}
