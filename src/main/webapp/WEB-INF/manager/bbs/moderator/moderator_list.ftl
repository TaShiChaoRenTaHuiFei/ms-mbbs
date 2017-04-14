<!DOCTYPE html>
<html lang="zh">
 <head>
  	<#include "/manager/include/macro.ftl"/>
  	<#include "/manager/include/meta.ftl"/>
</head>

<!--版主列表-->
<script id="peopleList" type="text/x-jquery-tmpl"> 
	 <tr>
        <td class="text-center">
        <@ms.checkbox  name="id" value="{{= moderatorPeople.peopleId}}"/> 
        </td>
        <td class="text-center mdPeopleName updateModerator " style="cursor: pointer;" onclick="updateModerator(this,{{= moderatorForumId}},{{= moderatorPeople.peopleId}})" >
        	<a data-original-title="点击编辑" data-toggle="tooltip">
       			 {{= moderatorPeople.peopleName}}
       		</a>
        </td>
        <td class="text-center moderatorTypeName">{{= moderatorType.categoryTitle}}</td>
        <td class="text-center moderatorTypeName">{{= moderatorPeople.peopleUser.peopleUserNickName}}</td>
	</tr>
</script> 
<!--无版主列表-->
<script id="noPeopleList" type="text/x-jquery-tmpl"> 
	 <tr>
       <td colspan="12" class="text-center"><@ms.nodata content="暂无版主"/></td>
	</tr>
</script> 

<body>	
	<@ms.content>
		<@ms.contentBody>
			<@ms.contentNav title="版主管理"></@ms.contentNav >
			<@ms.contentPanel>
				<@ms.panelNav>
					<@ms.button class="btn btn-success moderatorPeopleList" toggle="modal" value="版主管理" icon="plus"/>-
				</@ms.panelNav>
				<@ms.table head=['单选','板块ID','所属版块名']>
	           			<#if moderatorList?has_content>
		        			<#list moderatorList as moderatorList>
	                    	<tr>
	                            <td class="text-center" style="width:10%">
	                            	<input type="radio" name="radioCategoryId" value="${moderatorList.moderatorForum.forumCategoryId?c?default(0)}">
	                            </td>
	                            <td class="text-center" style="width: 20%;">${moderatorList.moderatorForum.forumCategoryId?c?default(0)}</td>
	                            <td class="text-center categoryTitle">${moderatorList.moderatorForum.categoryTitle?default("暂无板块")}</td>
	                            <!--<td class="text-center moderatorName">${moderatorList.moderatorPeopleList?default("暂无版主")}</td>-->
	                    	</tr>
	                 		</#list>
	                 		<#else>
			             	<tr>
					            <td colspan="4" class="text-center">
					            	<@ms.nodata/>
								</td>
				          	</tr>
	        			</#if>  
	            </@ms.table>
			</@ms.contentPanel>		
		</@ms.contentBody>
		
	<@ms.modal modalName="moderators" title="<span></span>版主管理" style="width:70%;overflow: hidden;" >
			<@ms.contentPanel style="padding: 0;">
				<@ms.panelNav>
						<@ms.panelNavBtnGroup>
							<@ms.panelNavBtnAdd value="新增版主"/>
							<@ms.panelNavBtnDel value="删除版主"/>
						</@ms.panelNavBtnGroup>
				</@ms.panelNav>
			</@ms.contentPanel>
			<@ms.modalBody>
               	<@ms.table head=['<input type="checkbox" name="allCheck" />','版主账号','版主类型','版主昵称']>
  					
		        </@ms.table>
		    </@ms.modalBody>
		    <@ms.modalButton>
		 		<@ms.panelNavBtnGroup>
					<@ms.contentNavBack value="返回列表"/>
				</@ms.panelNavBtnGroup>
		 	</@ms.modalButton>
		</@ms.modal>

		<!--删除版主-->    
		<@ms.modal modalName="delete" title="删除版主!">
			  <@ms.modalBody>
			  		确认删除版所选版主？？
		     </@ms.modalBody>
			 <@ms.modalButton>
		 		<@ms.button class="btn btn-danger deleteAll" value="确定"/>
		 	</@ms.modalButton>
		</@ms.modal>
		
		<!--修改版主-->
		<@ms.modal modalName="saveAndUpdate" title="">
			 <@ms.modalBody>
				<@ms.form isvalidation=true name="replyForm" class="form-inline" action="">	
    				<@ms.text name="moderatorName" label="版主帐号" title="moderatorName" placeholder="请输入版主帐号" maxlength="12"  validation={"required":"true", "data-bv-notempty-message":"必填项目"}/>
		    		<@ms.text name="oldModeratorName" label="当前版主帐号" maxlength="12" value="" />
		    		<@ms.text name="newModeratorName" label="更改为" title="newModeratoName" maxlength="12"  placeholder="新版主名"/>
		    		<@ms.select name="moderatorTypeId" label="版主类型"  list=moderatorTypes  listKey="categoryId" listValue="categoryTitle" value ="" validation={"required":"true", "data-bv-notempty-message":"必填项目"} />			
		    	</@ms.form>	
		     </@ms.modalBody>
		     <@ms.modalButton>
		 			<@ms.savebutton id="saveUpdateButton"/>  
		 	</@ms.modalButton>
		</@ms.modal>
		
		
	</@ms.content>                
	<script>
	$(function(){
		var forumId;
		
		//添加按钮点击
		$("#addButton").click(function() {
			$("#replyForm").attr("action","${managerPath}/bbs/moderator/"+forumId+"/save.do");
			$("#saveUpdateButton").text("保存");
			$("#saveAndUpdateTitle").text("添加版主");
			$("#replyForm input[name='newModeratorName']").parent().hide();
			$("#replyForm input[name='oldModeratorName']").parent().hide();
			$("#replyForm input[name='moderatorName']").parent().show();
			$("#saveUpdateButton").attr("onclick","saveButton("+forumId+")");
			$(".saveAndUpdate").modal();
		});
		
		//删除按钮点击
		$("#delButton").click(function() {
			var ids = $("input[name='id']").serialize();
			if(ids!=""){
				$(".delete").modal();
			}else{
				alert("请选择版主!");
			}
		});
		
		
		
		//打开所选当前板块的版主列表
		$(".moderatorPeopleList").click(function(){
			var radioData = $("input[name='radioCategoryId']").serialize();
			if(radioData!="" || radioData==null){
				$("input[name='radioCategoryId']").each(function(){
		        	if($(this).is(':checked')){
		        		//获取板块名
		        		var name = $(this).parents("tr").find(".categoryTitle").text();
		        		//模态框的当前板块名
			         	$("#moderatorsTitle span").html(name);
			         	//当前板块id
		         		$("input[name='moderatorForumId']").val($(this).val());
		         		forumId=$(this).val();
		         		//打开模态框
		         		openModeratorList($(this).val());
		        	}
				})
			}else{
	    		$(".moderators").modal("hide");
	    		alert("请选择板块!");
			}
		})
		
		 
		//批量删除
		$(".deleteAll").click(function(){
			//将checkbox序列化
			var ids = $("input[name='id']").serialize();
			if(ids!=""){
				$.ajax({ 
					type: "POST", 
					url: base+"${baseManager}/bbs/moderator/"+forumId+"/delete.do",
					data:ids,
					dataType:"Json",
	    			success: function(msg){
	    				if (msg.result) {
	    					alert("删除成功！");
	    					//关闭该模块
	    					$(".delete").modal("hide");
	    					//显示该板块的版主列表
	    					location.reload();
	    				}else{
	    					alert("删除失败！");
	    				}
	    				    				
	    			},
	    		});
	    	}else{
	        	alert("请选择版主！");
	        }
		});
		
		
		//版主全选
	   	$("input[name='allCheck']").on("click",function(){   
			if(this.checked){   
				$("input[name='checkbox']").each(function(){this.checked=true;});
			}else{   
				$("input[name='checkbox']").each(function(){this.checked=false;});   
			}   
		}); 
	});
	/*********************以下是方法名方法************/
	
	
	//刷新版主列表
	function openModeratorList (forumId){
		$("#moderatorsDialog").find("tbody").html("");
	 	$.ajax({ 
			type: "POST", 
			url: base+"${baseManager}/bbs/moderator/"+forumId+"/query.do",
			success: function(msg){
				var json = JSON.parse(msg);
				$("#moderatorsDialog table tbody").html();
				if (jQuery.parseJSON(json.resultMsg) !=undefined && jQuery.parseJSON(json.resultMsg).length>0) {
			 		$("#peopleList").tmpl(jQuery.parseJSON(json.resultMsg)).appendTo("#moderatorsDialog table tbody");
				}else{
					$("#noPeopleList").tmpl().appendTo("#moderatorsDialog table tbody");
				}
				$(".moderators").modal();
			},
		});
	 }
		 //更新模态框呼出触发事件
		 function updateModerator(obj,forumId,peopleId) {
			if($(obj).text() !="" && forumId!="" &&peopleId!="" ){
				$.ajax({ 
					type: "POST", 
					url: base+"${baseManager}/bbs/moderator/"+forumId+"/getModerator.do",
					data:"peopleId="+peopleId,
					dataType:"Json",
					success: function(msg){
						var json =JSON.parse(msg.resultMsg);
						if(msg != ""){
					 		$("#replyForm input[name='oldModeratorName']").val(json.moderatorPeople.peopleName);
					 		$("#replyForm input[name='oldModeratorName']").attr("readonly",true)
					 		//清空输入框信息
					 		$("#replyForm input[name='newModeratorName']").val("");
					 		$("#replyForm input[name='newModeratorName']").parent().show();
							$("#replyForm input[name='oldModeratorName']").parent().show();
							$("#replyForm input[name='moderatorName']").parent().hide();
							$("#replyForm").attr("action","${managerPath}/bbs/moderator/"+forumId+"/update.do");
					 		$("#saveUpdateButton").text("更新");
					 		$("#saveAndUpdateTitle").text("修改版主");
							$("#saveUpdateButton").attr("onclick","updateButton("+forumId+")");
					   		$("#replyForm select option").each(function(){
						    	if($(this).text() == json.moderatorType.categoryTitle ){
						    		$(this).attr("selected", true);
									return false;
						        }
						   });
					        $(".saveAndUpdate").modal();//打开模态框
						}
					},
				});
			}else{
				alert("请选择版主!");
				$(".update").modal("hide");//关闭
			}
		 };
		 
		 
		 		//保存版主
	 	function saveButton(forumId) {
	 		var userName=$("#replyForm input[name='moderatorName']").val();
	 		//表单验证
	 		var vobj = $("#replyForm").data('bootstrapValidator').validate();
			if(vobj.isValid()){
	 			//保存版主
				$(this).postForm("#replyForm",{func:function(data) {
					if(data.result){
						alert("保存成功!");
						$(".saveAndUpdate").modal("hide");//关闭
						location.reload();
					}else{
						alert("该版主已存在或找不到该会员账号!");
					}
					
				}});
			} else {
				alert("表单验证失败");
			}  
		 };

		 	
		 
		 //修改版主
	 	function updateButton(forumId) {
	 		var userName=$("#replyForm input[name='newModeratorName']").val();
	 		//表单验证
	 		var vobj = $("#replyForm").data('bootstrapValidator').validate();
			if(vobj.isValid()){
				$(this).postForm("#replyForm",{func:function(data) {
					if(data.result){
						alert("更新成功!");
						$(".saveAndUpdate").modal("hide");//关闭
						location.reload();
					}else{
						alert("更新失败!");
					}
				}});
			} else {
				alert("表单验证失败");
			}  
		 };
	</script> 
</body>
</html>













