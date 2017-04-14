<!DOCTYPE html>
<html lang="zh">
 <head>
<#include "/manager/include/macro.ftl"/>
<#include "/manager/include/meta.ftl"/>
</head>

<!--版主列表jquery 模板-->
<script id="peopleList" type="text/x-jquery-tmpl"> 
	 <tr>
        <td class="text-center">
        <@ms.checkbox  name="id" value="{{= moderatorPeople.peopleId}}"/> 
        </td>
        <td class="text-center mdPeopleName updateModerator " style="cursor: pointer;" onclick="updateModerator(this,{{= moderatorForumId}},{{= moderatorPeople.peopleId}})" >
        	<a data-original-title="点击编辑" data-toggle="tooltip">
       			 {{if moderatorPeople.peopleName!=""}} 
       			 	{{= moderatorPeople.peopleName}}
       			 {{else moderatorPeople.peoplePhone!=""}}
       			 	{{= moderatorPeople.peoplePhone}}
       			 {{else}}
       			 	{{= moderatorPeople.peopleMail}}
       			 {{/if}}
       		</a>
       		
        </td>
        <td class="text-center moderatorTypeName">{{= moderatorType.categoryTitle}}</td>
        <td class="text-center moderatorTypeName">{{= moderatorPeople.peopleUser.peopleUserNickName}}</td>
       
	</tr>
</script> 
<!--无版主列表jquery 模板-->
<script id="noPeopleList" type="text/x-jquery-tmpl"> 
	 <tr>
       <td colspan="12" class="text-center"><@ms.nodata content="暂无版主"/></td>
	</tr>
</script> 

<body>	
<@ms.content>
<@ms.contentBody >
	<@ms.contentNav title="版主类型管理"></@ms.contentNav >
	<@ms.contentPanel>
		<@ms.panelNav>
				<!--列表操作按钮，添加和删除-->
			<@ms.panelNavBtnGroup>
				<@ms.panelNavBtnAdd title=""/>
			</@ms.panelNavBtnGroup>		
		</@ms.panelNav>
		<@ms.table head=['编号','标题','操作'] id="tableConterent">
			<#if categoryJson?has_content && categoryJson!="[]">
	        	<@ms.treeTable treeId="peopleAddressTree"  style="width:60%" tbodyId="tableConterent" json="${categoryJson?default('')}" jsonName="categoryTitle" jsonId="categoryId" jsonPid="categoryCategoryId"/>
	      	<#else>
             	<tr>
		            <td colspan="4" class="text-center">
		            	<@ms.nodata/>
					</td>
	          	</tr>                          
        	</#if>
		</@ms.table>
		
		<script id="afterpeopleAddressTree" type="text/x-jquery-tmpl">
			<td class="text-center">
				<a class="btn btn-xs red tooltips addModeratorFunction" data-toggle="tooltip" data-id="{{= categoryId}}" data-original-title="添加" >
                 	<i class="glyphicon glyphicon-plus"></i>
                </a>	
				<a class="btn btn-xs red tooltips editclumnTree" onclick="editclumnTree(this)" data-toggle="tooltip" data-id="{{= categoryId}}" data-original-title="编辑">
             		<i class="glyphicon glyphicon-pencil"></i>
            	</a>
				<a class="btn btn-xs red tooltips deleteclumnTree" onclick="deleteclumnTree(this)" data-toggle="tooltip" data-id="{{= categoryId}}" data-original-title="删除" >
             		<i class="glyphicon glyphicon-trash"></i>
            	</a>
            	
            	<a class="btn btn-primary btn-xs tooltips editFunction" onclick="editFunction(this)" style="color:#fff" data-toggle="tooltip" data-text="{{= categoryTitle}}" data-id="{{= categoryId}}" data-original-title="权限设置" >
					<i class="glyphicon glyphicon-user"></i> 权限设置
				</a>
			
			</td>
		</script>
	   	
	   	<!--删除-->    
		<@ms.modal modalName="delete" title="删除板块">
			  <@ms.modalBody>
			  		确定要删除所选的板块吗?
		     </@ms.modalBody>
			 <@ms.modalButton>
		 		<@ms.button class="btn btn-danger rightDelete" value="确定"/>
		 	</@ms.modalButton>
		</@ms.modal>
		
		
		<!--版主权限设置开始-->    
		<@ms.modal modalName="add" title="版主权限设置" style="width:650px;height: 100%;">
		  <@ms.modalBody>
		  		<input name="forumId"  type="hidden"/>
		  		<div style="margin:10px;" id="moderatorTitle">当前版主类型： <span></span></div>	 
			     <div style="margin:10px;float:left;">功能权限：</div>	   	
			     <div class="form-group" style="width:500px;float:left;">
						 <#if functionList?has_content>
        						<#list functionList as function>
        						<div style="float:left;margin:10px;">
        							<input type="checkBox" name="checkFunction" data-id="${function.functionId}"/>
        							<span >${function.functionTitle}</span>
        						</div>
        						</#list>
        				</#if>	
			     </div>	
	     </@ms.modalBody>
		 <@ms.modalButton>
	 		<@ms.button class="btn btn-primary" id="saveFunction" value="保存" url=""/>
	 	</@ms.modalButton>
	</@ms.modal>
	</@ms.contentPanel>
</@ms.contentBody>
</@ms.content>        
<script>
var categoryId="";
$(function(){

	$("body").delegate("#addButton,.addModeratorFunction", "click", function(){    
		var categoryCategoryId = $(this).attr("data-id");
		if(categoryCategoryId==undefined){
			location.href = base+"${baseManager}/bbs/moderator/type/add.do?modelId=${modelId?default(0)}&categoryCategoryId=${categoryCategoryId?default(0)}";
			return;
		}
		location.href = base+"${baseManager}/bbs/moderator/type/add.do?modelId=${modelId?default(0)}&categoryCategoryId="+categoryCategoryId;
	});
	
	//点击对应的功能上的checkBox框则，进行所有版主类型的全选
	$(".checkAll").click(function(){
		var functionId = $(this).attr("data-id")
		if(this.checked){   
			$("input[data-id="+functionId+"]").each(function(){this.checked=true;});   
		}else{   
			$("input[data-id="+functionId+"]").each(function(){this.checked=false;});   
		}
	})
	
	//点击用户组对应的checkBox框则，进行所有用户组类型的全选
	$(".checkAllPeopleFunction").click(function(){
		var functionId = $(this).attr("data-id")
		if(this.checked){   
			$("input[data-id="+functionId+"]").each(function(){this.checked=true;});   
		}else{   
			$("input[data-id="+functionId+"]").each(function(){this.checked=false;});   
		}
	})
	
	//点击保存，对版主类型的权限进行设置
	$("#saveFunction").click(function(){
		$("input[name=checkPeopleFunction]").attr("checked",false);
		var groupFunctionJson = new Array();
		//获取勾选的权限
		$("input[name=checkFunction]").each(function(){
			//获取功能id
			var groupFunctionFunctionId = $(this).attr("data-id");
			//获取板块id
			var groupFunctionForumId = 0;
			//获取版主类型id
			var groupFunctionGroupId =categoryId;
			//判断当前checkBox是否选中
			if(this.checked){
				//定义数组存放已选择配置，与下面点击分类的数组区分开，
				var json = '{"groupFunctionFunctionId":"'+groupFunctionFunctionId+'","groupFunctionForumId":"'+groupFunctionForumId+'","groupFunctionGroupId":"'+groupFunctionGroupId+'"}';
				groupFunctionJson.push(json);
			}
		});   
		var saveUrl = "${basePath}/manager/bbs/group/function/0/"+categoryId+"/save.do";
		groupFunctionJson = "["+groupFunctionJson+"]";
		$(document).request({url:saveUrl,type:"html",data:"groupFunctionJson="+groupFunctionJson,func:function(msg) {
			$(".add").modal('hide');
		}}); 
		
	})
	
	
	
	
	//确认删除
	$(".rightDelete").on("click",function(){
	
		$(this).request({url: base+"${baseManager}/category/"+categoryId+"/delete.do",type:"json",method:"post",func:function(msg) {
			var columnJson = $.parseJSON(msg.resultMsg);
			if(columnJson==false){
				alert("请先删除子栏目");
				$(".delete").modal("hide");
			}else{
				alert("删除成功");
				location.reload();
			}
		}});
	});
});


function editclumnTree(obj){
	var categoryId = $(obj).attr("data-id");
	location.href = base+"${baseManager}/bbs/moderator/type/"+categoryId+"/edit.do?modelId=${modelId?default(0)}";
}

function deleteclumnTree(obj){
	categoryId = $(obj).attr("data-id");
	$(".delete").modal();
}
/**
* 点击弹出版主的权限设置界面
*/

function editFunction(obj){
	$("input[name=checkAll]").attr("checked",false);
	$("input[name=checkFunction]").attr("checked",false);
	categoryId = $(obj).attr("data-id");
	$("#moderatorTitle span").text($(obj).attr("data-text"))
	$(".add").modal();
	$("input[name=forumId]").val($(obj).attr("data-id"));
	var queryUrl="${basePath}/manager/bbs/group/function/"+$(obj).attr("data-id")+"/queryByGroupId.do"
	//获取板块的权限
	$(document).request({url:queryUrl,type:"json",func:function(msg) {
		if(msg!=null){
			for(var i=0;i<msg.length;i++){
				$("input[data-id="+msg[i].groupFunctionFunctionId+"]").prop("checked","true");
			}
		}
	}}); 
}

</script>
</body>

</html>













