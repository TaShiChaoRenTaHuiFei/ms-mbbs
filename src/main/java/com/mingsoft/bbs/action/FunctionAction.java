package com.mingsoft.bbs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.bbs.biz.IFunctionBiz;
import com.mingsoft.bbs.entity.FunctionEntity;
import com.mingsoft.util.StringUtil;

/**
 * mbbs 功能管理控制层
 * @Package com.mingsoft.bbs.action
 * @author 史爱华
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年11月15日<br/>
 * 历史修订：<br/>
 */
@Controller("bbsManagerFunction")
@RequestMapping("/${managerPath}/bbs/function")
public class FunctionAction extends BaseAction{
	
	
	@Autowired
	private IFunctionBiz functionBiz;
	
	/**
	 * 功能列表页
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return 功能列表页面地址
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request,HttpServletResponse response,ModelMap model) {
		
		int appId  = this.getAppId(request)	;
		List<FunctionEntity> functionList =  functionBiz.queryByAppId(appId);
		model.addAttribute("functionList", functionList);
		return view("/bbs/function/function_list");
	}
	
	/**
	 * 保存功能实体 
	 * @param function 功能实体
	 * @param request
	 * @param response
	 */
	@RequestMapping("/save")
	public void save(@ModelAttribute FunctionEntity function,HttpServletRequest request,HttpServletResponse response){
		if(function==null){
			this.outJson(response, false);
			return;
		}
		int appId = this.getAppId(request);
		function.setFunctionAppId(this.getAppId(request));
		//判断是否存在重复的功能方法名
		if(functionBiz.getByFunctionMethod(appId, function.getFunctionMethod())!=null){
			this.outJson(response,null,false,this.getResString("err.exist",com.mingsoft.base.constant.Const.RESOURCES,this.getResString("function.method", com.mingsoft.bbs.constant.Const.RESOURCES)));
			return;
		}
		functionBiz.saveEntity(function);
		this.outJson(response,null,true, JSONObject.toJSONString(function));
	}
	
	/**
	 * 更新功能实体
	 * @param function 功能实体
	 * @param functionId 要更新的功能实体的id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{functionId}/update")
	public void update(@ModelAttribute FunctionEntity function,@PathVariable int functionId,HttpServletRequest request,HttpServletResponse response){
		if(function==null){
			this.outJson(response, false);
			return;
		}
		int appId = this.getAppId(request);
		FunctionEntity oldFunction = (FunctionEntity) this.functionBiz.getEntity(functionId);
		//判断更新的时候是否有更改方法名
		if(!oldFunction.getFunctionMethod().equals(function.getFunctionMethod())){
			//判断是否存在重复的功能方法名
			if(functionBiz.getByFunctionMethod(appId, function.getFunctionMethod())!=null){
				this.outJson(response,null,false,this.getResString("err.exist",com.mingsoft.base.constant.Const.RESOURCES,this.getResString("function.method", com.mingsoft.bbs.constant.Const.RESOURCES)));
				return;
			}
		}
		function.setFunctionId(functionId);
		functionBiz.updateEntity(function);
		this.outJson(response,null, true);
	}
	
	/**
	 * 根据id获取功能实体信息
	 * @param functionId 功能id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{functionId}/getEntity")
	public void getEntity(@PathVariable int functionId,HttpServletRequest request,HttpServletResponse response){
		FunctionEntity function = (FunctionEntity) this.functionBiz.getEntity(functionId);
		this.outJson(response, JSONObject.toJSONString(function));
	}
	
	/**
	 * 批量删除功能实体
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response){
		//获取要删除的功能id
		String[] functionIds = request.getParameterValues("functionIds");
		//判断参数id是否为空
		if(!StringUtil.isBlank(functionIds)){
			//判断传入的参数id是否是数字集合
			if(StringUtil.isIntegers(functionIds)){
				int[] _functionIds = StringUtil.stringsToInts(functionIds);
				//获取应用id
				int appId = this.getAppId(request);
				//进行批量删除
				this.functionBiz.delete(appId, _functionIds);
				this.outJson(response, true);
				return;
			}
			
		}
		this.outJson(response, false);
	}
	
}
