<!DOCTYPE html>
<html lang="zh">
 <head>
<#include "/manager/include/macro.ftl"/>
<#include "/manager/include/meta.ftl"/>
</head>
<body>	
<@ms.content>
<@ms.contentBody >
	<@ms.contentNav title="功能列表管理"></@ms.contentNav >
	<@ms.contentPanel>
		<@ms.panelNav>
				<!--列表操作按钮，添加和删除-->
			<@ms.panelNavBtnGroup>
				<@ms.panelNavBtnAdd title=""/>
				<@ms.panelNavBtnDel title=""/>
			</@ms.panelNavBtnGroup>		
		</@ms.panelNav>
		<@ms.table head=['<input type="checkbox"  name="functionId"  value="" id="allCheck">','编号','功能名称','功能方法'] id="tableConterent">
			<#if functionList?has_content>
	        	<#list functionList as function>
                	<tr>
			        	<td class="text-center">
							<input type="checkbox" name="functionIds" value="${function.functionId?c?default(0)}">
			            </td>
			        	<td class="text-center articleId" >${function.functionId?c?default(0)}</td>
			            <td class="text-center " >
			            	<a style="cursor: pointer;">
			            	<span class="edit" data-toggle="tooltip"  data-original-title="点击修改" data-id="${function.functionId?c?default(0)}">
			            	${function.functionTitle?default("")}
			            	</span>
			            	</a>
			            </td>
			            <td class="text-center" >${function.functionMethod?default("")}</td>
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
		
	   	
	   	<!--删除-->    
		<@ms.modal modalName="delete" title="删除板块">
			  <@ms.modalBody>
			  		确定要删除所选的功能吗?
		     </@ms.modalBody>
			 <@ms.modalButton>
		 		<@ms.button class="btn btn-danger rightDelete" value="确定"/>
		 	</@ms.modalButton>
		</@ms.modal>
		
		<!--添加配置开始-->    
		<@ms.modal modalName="addOrEdit" title="功能管理">
		 <@ms.modalBody>
				<@ms.form isvalidation=true name="addEditForm"  action="${basePath}/manager//bbs/function/save.do" method="post"  >
					<@ms.text name="functionTitle" style="width: 40%;" label="功能名称:" title="" placeholder="" value="" validation={"required":"true", "data-bv-notempty-message":"请输功能名称"}/>
					<@ms.text name="functionMethod" style="width: 40%;" label="功能方法:" title="" placeholder="" value="" validation={"required":"true", "data-bv-notempty-message":"请输功能方法"}/>
				</@ms.form>
			</@ms.modalBody>
			<@ms.modalButton>
				<@ms.button  value="保存"  id="addEditBtn"/>
			</@ms.modalButton>
	</@ms.modal>
	<!--添加配置结束--> 
	</@ms.contentPanel>
</@ms.contentBody>
</@ms.content>        

<script>
var categoryId="";
$(function(){
	//自定义表单的表单验证
	$('#addEditForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh',
			autoFocus : true
		}
	});
	//全选
	$("#allCheck").click(function(){  
		if(this.checked){   
			$("input[name='functionIds']").each(function(){this.checked=true;});
		}else{   
			$("input[name='functionIds']").each(function(){this.checked=false;});   
		}
	}); 
	
	//多选删除
	$("#delButton").click(function(){
		ids = $("input[name='functionIds']").serialize();
		if(ids!=""){
			$(".delete").modal();
		}else{
			alert("请选择要删除的功能!");
		}
	});
	//点击新增弹出新增按钮
	$("body").delegate("#addButton", "click", function(){    
		$("form[name=addEditForm]").attr("action",base+"${baseManager}//bbs/function/save.do");
		$("form[name=addEditForm] input").val();
		$("#addEditBtn").text("保存")
		$(".addOrEdit").modal();
	});
	
	//点击保存，对版主类型的权限进行设置
	$(".edit").click(function(){
		var functionId = $(this).attr("data-id");
		$("#addEditBtn").text("更新")
		var editUrl=base+"${baseManager}/bbs/function/"+functionId+"/getEntity.do";
		$(this).request({url:editUrl,type:"json",method:"post",func:function(json){
			if(json!=null){
				$("form[name=addEditForm] input[name=functionTitle]").val(json.functionTitle)
				$("form[name=addEditForm] input[name=functionMethod]").val(json.functionMethod)
				$("form[name=addEditForm]").attr("action",base+"${baseManager}/bbs/function/"+functionId+"/update.do")
			}
			$(".addOrEdit").modal();
		}});		
	});
	
	//点击保存/更新进行功能方法的保存
	$("#addEditBtn").click(function(){
		//如果表单验证成功，则可以进行保存或者更新
		var vobj = $("#addEditForm").data('bootstrapValidator').validate();
		if(vobj.isValid()){
			$(this).postForm("#addEditForm",{func:function(msg){
				if(msg.result==true){
					alert($.trim($("#addEditBtn").text())+"成功");
		    		location.reload();
				}else{
					alert(msg.resultMsg);
				}
			}});
		}
	})
	
	
	//确认删除
	$(".rightDelete").on("click",function(){
		var functionIds = $("input[name=functionIds]").serialize()
			var url ="${managerPath}/bbs/function/delete.do";
			$(this).request({url:url,data:functionIds,method:"post",func:function(msg) { 
			if(msg){
				location.reload();
			}else{
				
			}
		}});
	});
});



function deleteclumnTree(obj){
	categoryId = $(obj).attr("data-id");
	$(".delete").modal();
}


</script>
</body>

</html>













