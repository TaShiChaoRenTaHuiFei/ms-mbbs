<!DOCTYPE html>
<html lang="zh">
 <head>
  	<#include "/manager/include/macro.ftl"/>
  	<#include "/manager/include/meta.ftl"/>
</head>
<body>	
	<@ms.content>
		<@ms.contentBody>
			<@ms.contentNav title="用户组管理"></@ms.contentNav >
			<@ms.contentPanel>
				<@ms.panelNav>
				<@ms.panelNavBtnGroup>
					<@ms.panelNavBtnAdd title=""/>
					<@ms.panelNavBtnDel title=""/>
					
				</@ms.panelNavBtnGroup>
				</@ms.panelNav>
				<table class="table table-hover">
					<!--表格栏目属性 开始-->
    				<thead>
        				<tr >
        					<th class="text-center" style="border-bottom:none;">
        						<input type="checkbox" name="allCheck">
        					</th>
        					<th class="text-center" style="border-bottom:none;">
        						等级
        					</th>
        					<th class="text-left" style="border-bottom:none;">头衔</th>
        					<#if bankScoreList?has_content>
        						<#list bankScoreList as bankScore>
        						<th class="text-center"  data-id="${bankScore.bankScoreId}" style="border-bottom:none;">
        							<span>${bankScore.bankScroeTitle}</span>
        						</th>
        						</#list>
        					</#if>
        					
        				</tr>
        				<#if peopleGroupList?has_content>
		        			<#list peopleGroupList as peopleGroup>
	                    	<tr style="border-bottom: 1px solid #ddd;border-top: 1px solid #ddd;">
	                    		<td class="text-center">
	                    			<input type="checkBox" name="ids" value="${peopleGroup.categoryId?default(0)}" data-sort="${peopleGroup.categorySort?default(1)}"></td>
	                            <td class="text-center">
	                            	${peopleGroup.categorySort?default(1)}级
	                            </td>  
	                            <td class="text-left">
	                            	<a href="${basePath}/manager/bbs/people/group/${peopleGroup.categoryId?default(0)}/edit.do">${peopleGroup.categoryTitle}</a>
	                            </td>
	                            <#if bankScoreList?has_content>
	                            		<#if peopleGroup.peopleGroupScoreList?has_content>
	                            			<#list peopleGroup.peopleGroupScoreList as peopleGroupScore>
	                            					<td class="text-center">
	                            						${peopleGroupScore.peopleGroupScoreMinScore}-${peopleGroupScore.peopleGroupScoreMaxScore?default(0)}
	                            					</td>
	                            			</#list>
	                            			
	                            		<#else>
	                            		</#if>  
	                            </#if>
	                            
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
			</@ms.contentPanel>		
		</@ms.contentBody>
		
		<!--删除用户组-->    
		<@ms.modal modalName="delete" title="删除头衔!">
			  <@ms.modalBody>
			  		确认删除版所选头衔？？
		     </@ms.modalBody>
			 <@ms.modalButton>
		 		<@ms.button class="btn btn-danger deleteAll" value="确定"/>
		 	</@ms.modalButton>
		</@ms.modal>
		
	</@ms.content>                
</body>
<script>
	$(function(){
		$("#addButton").click(function(){
			location.href="${basePath}/manager/bbs/people/group/add.do"
		})
		
		//全选
   		$("input[name='allCheck']").on("click",function(){  
			if(this.checked){   
				$("input[name='ids']").each(function(){
					this.checked=true;
				});
			}else{   
				$("input[name='ids']").each(function(){
					this.checked=false;
				});   
			}
		});
		var ids="";
		//多选删除
		$("#delButton").click(function(){
			//遍历所有已经勾选的用户组，如果存在第
			//获取所有的checkBox
			obj = document.getElementsByName("ids");
			var maxSort = $("input[name='ids']:last").attr("data-sort");
			var isDel=true;
			$("input[name='ids']:checked").each(function(){
				if($(this).attr("data-sort")==1){
					isDel=false
					alert("1级用户不能删除");
					return;
				}
			});
			
			if(isDel==false){
				return;
			}
			alert(isDel);
			ids = $("input[name='ids']").serialize();
			
			if(ids!=""){
				$(".delete").modal();
			}else{
				alert("请选择要删除的用户分组!");
			}
		});
		//删除多个用户分组
		$(".deleteAll").click(function(){
			//判断等级一的是否被选中，如果选择则删除的时候提示不能进行删除
			
			deletes(ids);
		});
		
	})
	//删除文章
	function deletes(ids){
		if(ids!=""){
			$(this).request({url:base+"${baseManager}/bbs/people/group/delete.do",type:"json",data:ids,method:"post",func:function(msg) {
				if (msg.result) {
					alert("删除成功！")
					location.href="${basePath}/manager/bbs/people/group/list.do"; 
				} else {
					alert("删除失败");
				}
			}});
		}else{
    		alert("请选择用户组！");
    	}
	}
</script>
</html>













