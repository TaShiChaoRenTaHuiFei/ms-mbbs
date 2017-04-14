<!DOCTYPE html>
<html lang="en">
<head>
<#include "/manager/include/meta.ftl"/>
</head>
<body>	
	<#assign action="/manager/category/save.do"/>
	<#assign name="添加"/>
	<#if category?has_content>
		<#assign action="/manager/category/update.do"/>
		<#assign name="更新"/>
	</#if>
	<@ms.content>
		<@ms.contentBody>
			<@ms.contentNav title="用户组管理">
				<@ms.savebutton id="saveFormButton" value="${name}"/>
				<@ms.contentNavBack value="返回用户组列表"/>
			</@ms.contentNav>
				<@ms.contentPanel>
	    			<@ms.form isvalidation=true name="saveForm" style="width:80%" class="form-inline" action="">
				    		<input name="categorySort" value="${categorySort?default(1)}" type="hidden"/>
				    		<@ms.text name="sort" label="当前等级" value="${categorySort?default(1)}级" style=" width:15%" readonly="readonly"/>
				    		<@ms.text name="categoryTitle" label="用户组名称(头衔)" title="组名称(头衔)" maxlength="30" style=" width:35%" placeholder="用户组名称(头衔)"  validation={"required":"true", "data-bv-notempty-message":"请填写用户组名称"}/>
				    		<@ms.textarea id="description" name="categoryDescription" label="描述" style=" width:50%" title="用户组描述" placeholder="用户组描述" maxlength="150"/>
				    		<input name="categoryModelId" value="${modelId?default(0)}" type="hidden"/>
				    		
				    		<!--用户组图标-->
				    		<div class="form-group margin15">
			        			<label class="col-md-3 control-label col-xs-3">图标</label>
			        			<div class="col-md-4 uploadImg col-xs-4" style="padding:0">
						    		<#if category?has_content> 
										<@uploadImg path="upload/bbs/${appId}/" inputName="categorySmallImg" size="15" filetype="" msg=""  maxSize="1" imgs="${category.categorySmallImg?default('')}" />
									<#else> 
										<@uploadImg path="upload/bbs/${appId}/" inputName="categorySmallImg" size="15" filetype="" msg=""  maxSize="1" imgs="" />
									</#if>
							    </div>
			            	</div>	
			            	<!--积分范围--->
			            	<div class="form-group">
			        			<label class="col-md-3 control-label col-xs-3">满足条件</label>
			        			<div class="col-md-6  col-xs-6" style="padding:0">
						    		<table class="table table-hover">
						    			<thead>
										 <#if bankScoreList?has_content>
	                            			<#list bankScoreList as bankScore>
	                            			<tr style="border-top: 1px solid #ddd;">
	                            				<td class="text-center" style="width: 20%;">${bankScore.bankScroeTitle}</td>
	                            				<td class="text-center peopleGroupScore" data-text="${bankScore.bankScroeTitle}" style="width: 20%;" data-id="${bankScore.bankScoreId}" readOnly>
	                            					<input  name="peopleGroupScoreMinScore" value="0"  readonly="readonly"  data-id="${bankScore.bankScoreId}" style=" width:40%;margin-right: 10px;" class="form-control"/>
	                            					<span style="float:left;line-height:33px">一</span>
	                            					<input  name="peopleGroupScoreMaxScore" value="0" data-max-num="0"  data-id="${bankScore.bankScoreId}" style=" width:40%;margin-left: 10px;" class="form-control"/>	 
	                            				</td>
	                            			</tr>
	                            			</#list>
	                 					<#else>
			             					<tr>
					            				<td colspan="10" class="text-center">
					            					<@ms.nodata/>
												</td>
											</tr>
	        							</#if>  
    									</thead>
									</table>
							    </div>
			            	</div>	
			            	<!--功能权限以及积分规则范围--->
			            	<div class="form-group">
			        			<label class="col-md-3 control-label col-xs-3">操作奖励</label>
			        			<div class="col-md-8  col-xs-8" style="padding:0">
						    		<table class="table table-hover">
						    			<thead>
											<tr style="border-top: 1px solid #ddd;">
        										<th class="text-left" style="border-bottom:none; ">
        											<input type="checkBox"  value="" class="checkAll">功能
        										</th>
        										<#if bankScoreList?has_content>
        											<#list bankScoreList as bankScore>
        											<th class="text-center"  data-id="${bankScore.bankScoreId?default(0)}" style="border-bottom:none;">
        												<span>${bankScore.bankScroeTitle}</span>
        											</th>
        											</#list>
        										</#if>
        									</tr>
        									<#if funcList?has_content>
		        								<#list funcList as func>
	                    					<tr>
	                           				 	<td class="text-left groupFunctionTd" style="width:13%">
	                            					<input type="checkBox" name="groupFunction" value="" data-id="${func.functionId?default(0)}">${func.functionTitle}
	                            				</td>
	                            				<#if bankScoreList?has_content>
        											<#list bankScoreList as bankScore>
        											<td class="text-center forumGroupScore" style="width: 20%;" data-id="${bankScore.bankScoreId?default(0)}" data-function-id="${func.functionId?default(0)}">
        												<input  name="forumFunctionScoreScoreNum" value="0" data-id="${bankScore.bankScoreId?default(0)}" data-function-id="${func.functionId?default(0)}" style="margin:0 auto;float:none;width:40%;" class="form-control"/>
        											</td>
        											
        											</#list>
        										</#if>
	                    					</tr>
	                 							</#list>
	                 						<#else>
			             					<tr>
					           					<td colspan="4" class="text-center">
					            					<@ms.nodata/>
												</td>
				          					</tr>
	        								</#if>  
    									</thead>
									</table>
							    </div>
			            	</div>	
	    			</@ms.form>			
	        	</@ms.contentPanel>		
		</@ms.contentBody>
	</@ms.content>
</body>

<script>
	<#if lastPeopleGroup?has_content>
		<#if lastPeopleGroup.peopleGroupScoreList?has_content>
			<#list lastPeopleGroup.peopleGroupScoreList as peopleGroupScore>
			$("input[name=peopleGroupScoreMinScore][data-id=${peopleGroupScore.peopleGroupScoreBankScoreId?default(0)}]").val("${peopleGroupScore.peopleGroupScoreMaxScore?default()}");
			$("input[name=peopleGroupScoreMaxScore][data-id=${peopleGroupScore.peopleGroupScoreBankScoreId?default(0)}]").attr("data-max-num","${peopleGroupScore.peopleGroupScoreMaxScore?default()}");
			</#list>
		</#if>
	</#if>
	//从后台得到值之后，显示在输入框中
	<#if category?has_content>
		$("input[name='categoryTitle']").val("${category.categoryTitle?default('')}");
		$("#description").text("${category.categoryDescription?default('')}");	
		$("input[name='categoryId']").val(${category.categoryId?default(0)});
		$("#categoryId").attr("disabled",false);
		$("input[name=forumFunctionScoreScoreNum]").attr("readonly",'readonly');
		$("input[name=categorySort]").val('${category.categorySort?default(0)}');
	</#if>
	//勾选用户组已经绑定的功能
	<#if groupFunctionList?has_content>
		<#list groupFunctionList as groupFunction>
			$("input[name=groupFunction][data-id=${groupFunction.groupFunctionFunctionId?default(0)}]").prop("checked","true");
			$("input[name=groupFunction][data-id=${groupFunction.groupFunctionFunctionId?default(0)}]").parent().parent().find("input[name=forumFunctionScoreScoreNum]").removeAttr("readonly",'readonly')
		</#list>
	</#if>
	//对应的功能的积分奖励规则
	<#if forumScoreList?has_content>
		<#list forumScoreList as forumScore>
			$("input[name=forumFunctionScoreScoreNum][data-id=${forumScore.forumFunctionScoreBankScoreId}][data-function-id=${forumScore.forumFunctionScoreFunctionId}]").val("${forumScore.forumFunctionScoreScoreNum}");
		</#list>
	</#if>
	//积分约束情况
	<#if peopleGroupScoreList?has_content>
		<#list peopleGroupScoreList as peopleGroupScore>
			$("input[name=peopleGroupScoreMaxScore][data-id=${peopleGroupScore.peopleGroupScoreBankScoreId?default(0)}]").val("${peopleGroupScore.peopleGroupScoreMaxScore?default(0)}");
		</#list>
	</#if>
	//如果选择的总数==已有的总数则将全选选上
	if($("input[name=groupFunction]:checked").length==$("input[name=groupFunction]").length){
		$(".checkAll").prop("checked","true");
	}else{
		$(".checkAll").checked=false;
	}
	//如果所有的功能都被选择，则全选勾上
	//点击对应的功能上的checkBox框则，进行所有版主类型的全选
	$(".checkAll").click(function(){
		var functionId = $(this).attr("data-id")
		if(this.checked){   
			$("input[name=groupFunction]").each(function(){
				this.checked=true;
				$(this).parent().parent().find("input[name=forumFunctionScoreScoreNum]").removeAttr('readonly')
			});   
		}else{   
			$("input[name=groupFunction]").each(function(){
				this.checked=false;
				$(this).parent().parent().find("input[name=forumFunctionScoreScoreNum]").attr("readonly",'readonly')
			});   
		}
	});
	//点击每个功能选择框
	$("input[name=groupFunction]").click(function(){
		var functionId = $(this).attr("data-id")
		if(this.checked){   
			this.checked=true;
			$(this).parent().parent().find("input[name=forumFunctionScoreScoreNum]").removeAttr('readonly')
		}else{   
			this.checked=false;
			$(this).parent().parent().find("input[name=forumFunctionScoreScoreNum]").attr("readonly",'readonly')
		}
		//如果选择的总数==已有的总数则将全选选上
		if($("input[name=groupFunction]:checked").length==$("input[name=groupFunction]").length){
			$(".checkAll").prop("checked","true");
		}else{
			$(".checkAll").attr("checked",false);;
		}
	});
	
	
	$("input[name=categoryCategoryId]").val("${categoryCategoryId?default('0')}");
	//点击修改或保存时,先判断验证是否通过,再提交信息
	$("#saveFormButton").click(function(){
		if($(this).attr("data-isClick")=="true"){
			return;
		}
		$("#saveFormButton").attr({"disabled":"disabled"});
		if($("#saveForm").data('bootstrapValidator').validate().isValid()){
			//获取用户设置的积分范围
			var peopleGroupScoreJson = new Array();
			var isValidaor = true;
			//获取用户设置的积分范围
			$(".peopleGroupScore").each(function(){
				//获取积分类型
				var bankScoreId = $(this).attr("data-id");
				//获取积分最小值
				var peopleGroupScoreMinScore=$(this).find("input[name=peopleGroupScoreMinScore]").val();
				var peopleGroupScoreMaxScore =$(this).find("input[name=peopleGroupScoreMaxScore]").val();
				var maxNum = $(this).find("input[name=peopleGroupScoreMaxScore]").attr("data-max-num")
				if(parseInt(peopleGroupScoreMaxScore)<parseInt(maxNum)){
					isValidaor = false;
					alert("必须要大于上一级的最大"+$(this).attr("data-text")+"范围");
					$(this).attr("data-isClick","false")
					return;
				}
				var json = '{"peopleGroupScoreGroupId":"'+${groupId?default(0)}+'","peopleGroupScoreMinScore":"'+peopleGroupScoreMinScore+'","peopleGroupScoreMaxScore":"'+peopleGroupScoreMaxScore+'","peopleGroupScoreBankScoreId":"'+bankScoreId+'"}';
				peopleGroupScoreJson.push(json)
			});
			if(isValidaor==false){
				$("#saveFormButton").attr("data-isClick","false")
				$("#saveFormButton").removeAttr("disabled");
				return;
			}
			peopleGroupScoreJson ="["+peopleGroupScoreJson+"]";
			var groupFunctionJson=new Array();
			//获取勾选的权限
			$("input[name=groupFunction]").each(function(){
				//获取功能id
				var groupFunctionFunctionId = $(this).attr("data-id");
				//获取板块id
				var groupFunctionForumId = 0;
				//获取版主类型id
				var groupFunctionGroupId =${groupId?default(0)};
				//判断当前checkBox是否选中
				if(this.checked){
					//定义数组存放已选择配置，与下面点击分类的数组区分开，
					var json = '{"groupFunctionFunctionId":"'+groupFunctionFunctionId+'","groupFunctionForumId":"'+0+'","groupFunctionGroupId":"'+groupFunctionGroupId+'"}';
					groupFunctionJson.push(json);
				}
			});   
			groupFunctionJson ="["+groupFunctionJson+"]";
			//获取用户积分奖励规则
			var forumFunctionScoreJson = new Array();
			$(".forumGroupScore").each(function(){
				//板块id
				var forumFunctionScoreForumId=0
				//功能权限id
				var forumFunctionScoreFunctionId=$(this).attr("data-function-id");
				//组id
				var forumFunctionScoreGroupId =${groupId?default(0)};
				//积分类型id
				var forumFunctionScoreBankScoreId=$(this).attr("data-id");
				var forumFunctionScoreScoreNum = $(this).find("input[name=forumFunctionScoreScoreNum]").val();
				
				var json = '{"forumFunctionScoreForumId":"'+0+'","forumFunctionScoreFunctionId":"'+forumFunctionScoreFunctionId+'","forumFunctionScoreGroupId":"'+forumFunctionScoreGroupId+'","forumFunctionScoreBankScoreId":"'+forumFunctionScoreBankScoreId+'","forumFunctionScoreScoreNum":"'+forumFunctionScoreScoreNum+'"}';
				forumFunctionScoreJson.push(json);
			});
			forumFunctionScoreJson ="["+forumFunctionScoreJson+"]";
			var formdata = $("#saveForm").serialize();
			formdata = formdata+"&peopleGroupScoreJson="+peopleGroupScoreJson+"&groupFunctionJson="+groupFunctionJson+"&forumFunctionScoreJson="+forumFunctionScoreJson
			<#if category?has_content>
			$(this).request({url:"${basePath}/manager/bbs/people/group/${groupId?default(0)}/update.do",data:formdata,type:"json",method:"post",func:function(msg) {
				if(msg.result==true){
					alert("更新成功");
					$("#saveFormButton").attr("data-isClick","false")
					$("#saveFormButton").removeAttr("disabled");
					location.href="${basePath}/manager/bbs/people/group/list.do";
				}
			}});
			<#else>
			$(this).request({url:"${basePath}/manager/bbs/people/group/save.do",data:formdata,type:"json",method:"post",func:function(msg) {
				if(msg.result==true){
					alert("保存成功");
					$("#saveFormButton").attr("data-isClick","false")
					$("#saveFormButton").removeAttr("disabled");
					location.href="${basePath}/manager/bbs/people/group/list.do";
				}
			}});
			</#if>
			
		}else{
			$("#saveFormButton").attr("data-isClick","false")
			$("#saveFormButton").removeAttr("disabled");
		}
	});
	
	
</script>
	
</body>
</html>
