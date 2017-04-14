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
        	<input name="id" type="checkBox" value="{{= moderatorPeople.peopleId}}"/>
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
	<@ms.contentNav title="版块管理"></@ms.contentNav >
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
            	<a class="btn btn-primary btn-xs tooltips moderatorPeopleList"  style="color:#fff" data-toggle="tooltip" data-id="{{= categoryId}}" data-original-title="权限设置" >
					         版主管理
				</a>
            	<a class="btn btn-primary btn-xs tooltips editFunction" onclick="editFunction(this)" style="color:#fff" data-toggle="tooltip" data-id="{{= categoryId}}" data-original-title="权限设置" >
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
		
		
		<!--版主列表模态框-->
		<@ms.modal modalName="moderators" title="<span></span>版主管理" style="width:70%;overflow: hidden;" >
			<@ms.contentPanel style="padding: 0;">
				<@ms.panelNav>
						<@ms.panelNavBtnGroup>
							<@ms.panelNavBtnAdd  id="addModerator" title=""/>
							<@ms.panelNavBtnDel id="delModerator" title=""/>
						</@ms.panelNavBtnGroup>
				</@ms.panelNav>
			</@ms.contentPanel>
			<@ms.modalBody>
               	<@ms.table head=['<input type="checkbox" name="allCheck" />','版主账号','版主类型','版主昵称']>
		        </@ms.table>
		    </@ms.modalBody>
		    <@ms.modalButton>
		 		
		 	</@ms.modalButton>
		</@ms.modal>
		<!--版主列表模块框结束-->
		
		<!--删除版主-->    
		<@ms.modal modalName="deleteModerator" title="删除版主!">
			  <@ms.modalBody>
			  		确认删除版所选版主？？
		     </@ms.modalBody>
			 <@ms.modalButton>
		 		<@ms.button class="btn btn-danger deleteAll" value="确定"/>
		 	</@ms.modalButton>
		</@ms.modal>
		<!--删除版主结束-->    
		
		<!--修改版主-->
		<@ms.modal modalName="saveAndUpdate" title="">
			 <@ms.modalBody>
				<@ms.form isvalidation=true name="replyForm" class="form-inline" action="">	
    				<@ms.text name="moderatorName" label="版主帐号" title="moderatorName" placeholder="用户帐号/手机号/邮箱" maxlength="25" style="width:50%"  validation={"required":"true", "data-bv-notempty-message":"账号不能为空","data-bv-stringlength-message":"用户名必须在20个字符以内!"}/>
		    		<@ms.select name="moderatorTypeId" label="版主类型"  list=moderatorTypes  listKey="categoryId" listValue="categoryTitle" value ="" validation={"required":"true", "data-bv-notempty-message":"必须选择版主类型"} />			
		    	</@ms.form>	
		     </@ms.modalBody>
		     <@ms.modalButton>
		 			<@ms.savebutton id="saveUpdateButton"/>  
		 	</@ms.modalButton>
		</@ms.modal>
		<!--修改版主结束-->
		<!--添加配置开始-->    
		<@ms.modal modalName="add" title="权限设置" style="width: 80%;height: 100%;">
		  <@ms.modalBody>
		  		<input name="forumId"  type="hidden"/>
		  		<div class="ms-content-body-panel-nav">用户组权限</div>
		  		<table class="table table-hover">
		  			
					<!--表格栏目属性 开始-->
    				<thead>
        				<tr>
        					<th class="text-center" style="border-bottom:none;">用户权限</th>
        					<#if functionList?has_content>
        						<#list functionList as function>
        						<th  data-id="${function.functionId}" style="border-bottom:none;">
        							<input type="checkBox" name="checkAll" class="checkAll" data-id="${function.functionId}" data-type="people"/>
        							<span>${function.functionTitle}</span>
        						</th>
        						</#list>
        					</#if>
        				</tr>
    				</thead>
    <!--表格栏目属性 结束-->
     				<tbody id="">
     					<!--版主类型-->
     					
        				<!--用户组类型-->
        				<#if peopleCategoryList?has_content>
           					<#list peopleCategoryList as peopleCategory>
           					<tr class="on-focus" data="${peopleCategory.categoryId}">
                            	<td class="text-center groupFunction" data-id="${peopleCategory.categoryId}">${peopleCategory.categoryTitle}</td>
                            	<#if functionList?has_content>
        							<#list functionList as function>
        								<td  data-id="${function.functionId}">
        								<input type="checkBox" name="checkFunction" data-id="${function.functionId}" data-type-id="${peopleCategory.categoryId}" data-type="people"/>
        								</td>
        							</#list>
        						</#if>
                    		</tr>
           					</#list>
                 			<#else>
        				</#if>
     			</tbody>
 			</table>
 			<div class="ms-content-body-panel-nav">版主权限</div>
 			<table class="table table-hover">
					<!--表格栏目属性 开始-->
    				<thead>
        				<tr>
        					<th class="text-center" style="border-bottom:none;">版主权限</th>
        					<#if functionList?has_content>
        						<#list functionList as function>
        						<th  data-id="${function.functionId}" style="border-bottom:none;">
        							<input type="checkBox" name="checkAll" class="checkAll" data-id="${function.functionId}" data-type="modetor"/>
        							<span >${function.functionTitle}</span>
        						</th>
        						</#list>
        					</#if>
        				</tr>
    				</thead>
    				<!--表格栏目属性 结束-->
     				<tbody id="">
        				
        				<!----版主类型---->
        				<#if moderatorTypeList?has_content>
           					<#list moderatorTypeList as moderatorType>
           					<tr class="on-focus" data="${moderatorType.categoryId}">
                            	<td class="text-center groupFunction" data-id="${moderatorType.categoryId}">${moderatorType.categoryTitle}</td>
                            	<#if functionList?has_content>
        							<#list functionList as function>
        								<td  data-id="${function.functionId}">
        								<input type="checkBox" name="checkFunction" data-id="${function.functionId}" data-type-id="${moderatorType.categoryId}" data-type="modetor"/>
        								</td>
        							</#list>
        						</#if>
                    		</tr>
           					</#list>
                 			<#else>
        				</#if>
     			</tbody>
 			</table>
	     </@ms.modalBody>
		 <@ms.modalButton>
	 		<@ms.button class="btn btn-primary" id="saveFunction" value="保存" url=""/>
	 	</@ms.modalButton>
	</@ms.modal>
	</@ms.contentPanel>
</@ms.contentBody>
</@ms.content>        
<script>
	//刷新版主列表
	function openModeratorList (forumId){
		$("#moderatorsDialog").find("tbody").html("");
		//查询版主列表
		$(document).request({url:base+"${baseManager}/bbs/moderator/"+forumId+"/query.do",type:"json",func:function(msg) {
			var json =msg;
			$("#moderatorsDialog table tbody").html();
			if (jQuery.parseJSON(json.resultMsg) !=undefined && jQuery.parseJSON(json.resultMsg).length>0) {
			 	$("#peopleList").tmpl(jQuery.parseJSON(json.resultMsg)).appendTo("#moderatorsDialog table tbody");
			}else{
				$("#noPeopleList").tmpl().appendTo("#moderatorsDialog table tbody");
			}
			$(".moderators").modal();
		}}); 
	 }
	 //保存版主
	 function saveButton(forumId) {
	 		var userName=$("#replyForm input[name='moderatorName']").val();
	 		//表单验证
	 		var vobj = $("#replyForm").data('bootstrapValidator').validate();
	 		//将按钮进行禁用
			if($("#saveUpdateButton").attr("data-isClick")=="true"){
				return;
			}
			$("#saveUpdateButton").attr({"disabled":"disabled"});
			if(vobj.isValid()){
	 			//保存版主
				$(this).postForm("#replyForm",{func:function(data) {
					if(data.result){
						alert("保存成功!");
						$(".saveAndUpdate").modal("hide");//关闭
						location.reload();
					}else{
						alert(data.resultMsg);
					}
					$("#saveUpdateButton").attr("data-isClick","false")
					$("#saveUpdateButton").removeAttr("disabled");
				}});
			}else{
				$("#saveUpdateButton").attr("data-isClick","false")
				$("#saveUpdateButton").removeAttr("disabled");
			}
	};
	 //更新模态框呼出触发事件
	function updateModerator(obj,forumId,peopleId) {
		if($(obj).text() !="" && forumId!="" &&peopleId!="" ){
		//将按钮进行禁用
		
			//查询版主列表
			$(document).request({url:"/manager/bbs/moderator/"+forumId+"/getModerator.do",data:"peopleId="+peopleId,type:"json",func:function(msg) {
				var json =JSON.parse(msg.resultMsg);
				if(msg != ""){
					$("#replyForm input[name='moderatorName']").val(json.moderatorPeople.peopleName);
					$("#replyForm input[name='moderatorName']").attr("readonly",true)
					$("#replyForm").attr("action","${managerPath}/bbs/moderator/"+forumId+"/update.do");
					$("#saveUpdateButton").text("更新");
					$("#saveAndUpdateTitle").text("修改版主");
					$("#saveUpdateButton").attr("onclick","updateButton("+forumId+")");
					$("#replyForm select").find("option[value="+json.moderatorType.categoryId+"]").attr("selected", true);
					$(".saveAndUpdate").modal();//打开模态框
				}
			}}); 
		}else{
				alert("请选择版主!");
				$(".update").modal("hide");//关闭
		}
	};
	//修改版主
	function updateButton(forumId) {
	 	var userName=$("#replyForm input[name='moderatorName']").val();
	 	//表单验证
	 	var vobj = $("#replyForm").data('bootstrapValidator').validate();
	 	if($("#saveUpdateButton").attr("data-isClick")=="true"){
			return;
		}
		$("#saveUpdateButton").attr({"disabled":"disabled"});
		if(vobj.isValid()){
			$(this).postForm("#replyForm",{func:function(data) {
				if(data.result){
					alert("更新成功!");
					$(".saveAndUpdate").modal("hide");//关闭
					location.reload();
				}else{
					alert("更新失败!");
				}
				$("#saveUpdateButton").attr("data-isClick","false")
				$("#saveUpdateButton").removeAttr("disabled");
			}});
		}else{
			$("#saveUpdateButton").attr("data-isClick","false")
			$("#saveUpdateButton").removeAttr("disabled");
		}
	};
	 
	$(function(){
		var forumId =0;
		//打开所选当前板块的版主列表
		$("body").delegate(".moderatorPeopleList", "click", function(){  
			forumId=$(this).attr("data-id");
			//当前板块id
		    $("input[name='moderatorForumId']").val(forumId);
		    //获取当前板块名称
		    var name = $(this).parents("tr").find(".categoryTitle").text();
		    //打开版主列表弹框
		    openModeratorList(forumId);
			var radioData = $("input[name='radioCategoryId']").serialize();
			
		});
		//版主全选
	   	$("input[name='allCheck']").on("click",function(){   
			if(this.checked){   
				$("input[name='checkbox']").each(function(){this.checked=true;});
			}else{   
				$("input[name='checkbox']").each(function(){this.checked=false;});   
			}   
		}); 
		//添加按钮点击
		$("#addModerator").click(function() {
			$("#replyForm").attr("action","${managerPath}/bbs/moderator/"+forumId+"/save.do");
			$("#saveUpdateButton").text("保存");
			$("#saveAndUpdateTitle").text("添加版主");
			$("#replyForm input[name='moderatorName']").removeAttr("readonly");
			$("#saveUpdateButton").attr("onclick","saveButton("+forumId+")");
			$(".saveAndUpdate").modal();
		});
		
		//删除按钮点击
		$("#delModerator").click(function() {
			var ids = $("input[name='id']").serialize();
			if(ids!=""){
				$(".deleteModerator").modal();
			}else{
				alert("请选择版主!");
			}
		});
		//批量删除
		$(".deleteAll").click(function(){
			//将checkbox序列化
			var ids = $("input[name='id']").serialize();
			if(ids!=""){
				//将按钮进行禁用
				if($(this).attr("data-isClick")=="true"){
					return;
				}
				$(".deleteAll").attr({"disabled":"disabled"});
				//查询版主列表
				$(document).request({url:base+"${baseManager}/bbs/moderator/"+forumId+"/delete.do",data:ids,type:"json",func:function(msg) {
					if (msg.result) {
	    				alert("删除成功！");
	    				//关闭该模块
	    				$(".deleteModerator").modal("hide");
	    				//显示该板块的版主列表
	    				location.reload();
	    			}else{
	    				alert("删除失败！");
	    			}
	    			$(".deleteAll").attr("data-isClick","false")
					$(".deleteAll").removeAttr("disabled");
				}}); 
				
	    	}else{
	        	alert("请选择版主！");
	        }
		});
		
	})
</script>
<script>
var categoryId="";
$(function(){

	$("body").delegate("#addButton,.addModeratorFunction", "click", function(){    
		var categoryCategoryId = $(this).attr("data-id");
		if(categoryCategoryId==undefined){
			location.href = base+"${baseManager}/bbs/forum/add.do?modelId=${modelId?default(0)}&modelTitle=栏目&categoryCategoryId=${categoryCategoryId?default(0)}";
			return;
		}
		location.href = base+"${baseManager}/bbs/forum/add.do?modelId=${modelId?default(0)}&modelTitle=栏目&categoryCategoryId="+categoryCategoryId;
	});
	
	//点击对应的功能上的checkBox框则，进行所有版主类型的全选
	$(".checkAll").click(function(){
		var functionId = $(this).attr("data-id")
		var type=$(this).attr("data-type");
		if(this.checked){   
			$("input[data-id="+functionId+"][data-type="+type+"]").each(function(){this.checked=true;});   
		}else{   
			$("input[data-id="+functionId+"][data-type="+type+"]").each(function(){this.checked=false;});   
		}
	})
	//点击对应的功能checkBox框
	$("input[name=checkFunction]").click(function(){
		var functionId = $(this).attr("data-id")
		if(this.checked){   
			this.checked=true;
		}else{   
			this.checked=false;
		}
		//如果选择的总数==已有的总数则将全选选上
		if($("input[name=checkFunction][data-id="+functionId+"]:checked").length==$("input[name=checkFunction][data-id="+functionId+"]").length){
			$(".checkAll[data-id="+functionId+"]").prop("checked","true");
		}else{
			$(".checkAll[data-id="+functionId+"]").attr("checked",false);;
		}
	})
	
	
	//点击保存，对版主类型的权限进行设置
	$("#saveFunction").click(function(){
		//将按钮进行禁用
		if($(this).attr("data-isClick")=="true"){
			return;
		}
		$("#saveFunction").attr({"disabled":"disabled"});
		$("input[name=checkPeopleFunction]").attr("checked",false);
		var groupFunctionJson = new Array();
		//获取勾选的权限
		$("input[name=checkFunction]").each(function(){
			//获取功能id
			var groupFunctionFunctionId = $(this).attr("data-id");
			//获取板块id
			var groupFunctionForumId = $("input[name=forumId]").val();
			//获取版主类型id
			var groupFunctionGroupId = $(this).attr("data-type-id");
			//判断当前checkBox是否选中
			if(this.checked){
				//定义数组存放已选择配置，与下面点击分类的数组区分开，
				var json = '{"groupFunctionFunctionId":"'+groupFunctionFunctionId+'","groupFunctionForumId":"'+groupFunctionForumId+'","groupFunctionGroupId":"'+groupFunctionGroupId+'"}';
				groupFunctionJson.push(json);
			}
		});   
		
		var saveUrl = "${basePath}/manager/bbs/group/function/"+$("input[name=forumId]").val()+"/0/save.do";
		groupFunctionJson = "["+groupFunctionJson+"]";
		$(document).request({url:saveUrl,type:"html",data:"groupFunctionJson="+groupFunctionJson,func:function(msg) {
			$(".add").modal('hide');
			$("#saveFunction").attr("data-isClick","false")
			$("#saveFunction").removeAttr("disabled");
		}}); 
		
	})
	
	
	
	
	//确认删除
	$(".rightDelete").on("click",function(){
		//将按钮进行禁用
		if($(this).attr("data-isClick")=="true"){
			return;
		}
		$(".rightDelete").attr({"disabled":"disabled"});
		$(this).request({url: base+"${baseManager}/category/"+categoryId+"/delete.do",type:"json",method:"post",func:function(msg) {
			var columnJson = $.parseJSON(msg.resultMsg);
			if(columnJson==false){
				alert("请先删除子栏目");
				$(".delete").modal("hide");
			}else{
				alert("删除成功");
				location.reload();
			}
			$(".rightDelete").attr("data-isClick","false")
			$(".rightDelete").removeAttr("disabled");
		}});
	});
});


function editclumnTree(obj){
	var categoryId = $(obj).attr("data-id");
	location.href = base+"${baseManager}/bbs/forum/"+categoryId+"/edit.do?modelId=${modelId?default(0)}&modelTitle="+"栏目";
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
	$(".add").modal();
	$("input[name=forumId]").val($(obj).attr("data-id"));
	var queryUrl="${basePath}/manager/bbs/group/function/"+$(obj).attr("data-id")+"/queryByForumId.do"
	//获取板块的权限
	$(document).request({url:queryUrl,type:"json",func:function(msg) {
		if(msg!=null){
			for(var i=0;i<msg.length;i++){
				$("input[data-id="+msg[i].groupFunctionFunctionId+"][data-type-id="+msg[i].groupFunctionGroupId+"]").prop("checked","true");
			}
			//判断对应的功能是否全选了，如果全选了则将全选框勾上
			$("input[name=checkAll]").each(function(){
				var functionId = $(this).attr("data-id");
				if($("input[name=checkFunction][data-id="+functionId+"]:checked").length==$("input[name=checkFunction][data-id="+functionId+"]").length){
					$(this).prop("checked","true");
				}else{
					$(this).attr("checked",false);;
				}
			});
		}
	}}); 
}

</script>
</body>

</html>













