<@ms.html5>
    <@ms.nav title="帖子管理"></@ms.nav>
    <@ms.panel>

	   <@ms.searchForm  name="searchForm" action="${managerPath}/bbs/subject/list.do">
	   		<@ms.select label="板块:" name="forumId" id="forumSelect" value="" list=forumList  listValue="categoryTitle"   listKey="categoryId" default="搜索所有" size="33" style="width:100%" />
		    <@ms.text label="关键字:" name="keyWord" value="" size="33" title="请输入关键字"  placeholder="请输入关键字"   />		
		    <@ms.searchFormButton>
		        <@ms.queryButton form="searchForm"/> <!--搜索按钮-->
		    </@ms.searchFormButton>
		</@ms.searchForm>

    <@ms.panelNav>
    	<@ms.buttonGroup>
	    	<@ms.delButton url="${managerPath}/bbs/subject/delete.do" fieldName="ids"/>
	    </@ms.buttonGroup>
    </@ms.panelNav>
    
				<@ms.table head=['主题标题','发帖人,80','发帖日期','所属板块','显示/屏蔽','点击,90','评论,90'] checkbox="ids">
					<#if subjectList?has_content>
		        			<#list subjectList as subjectList>
	                    	<tr>
	                           <td>
	                            	<input type="checkbox" name="ids" value="${subjectList.subjectBasicId?c?default(0)}">
	                            </td>
	                            <td><a href="${managerPath}/bbs/subject/edit.do?subjectBasicId=${subjectList.subjectBasicId?c?default(0)}">${subjectList.basicTitle?default("")}</a></td>
	                            <td class="ms-text-hide "><#if subjectList.subjectPeopleUser?has_content>${subjectList.subjectPeopleUser.peopleName?default("${subjectList.subjectPeopleUser.peopleUserNickName?default('')}")}</#if></td>
	                            <td >${(subjectList.basicDateTime?string('yyyy-MM-dd HH:mm'))}</td>
	                            <td >${subjectList.subjectForum.categoryTitle?default("")}</td>
	                           	<td ><#if subjectList.subjectDisplay==0>显示<#else>屏蔽</#if></td>
	                            <td >${subjectList.basicHit?c?default(0)}</td>
	                            <td >${subjectList.subjectTotalComment?c?default(0)}</td>
	                    	</tr>
	                    	</#list>
	                    	<#else>
			             	<tr>
					            <td colspan="8" >
					            	<@ms.nodata/>
								</td>
				          	</tr>
					</#if>
	            </@ms.table>
	            <!--分页-->
	            <@ms.showPage page=page displayedPages=4/>	
		
    </@ms.panel>
</@ms.html5>










