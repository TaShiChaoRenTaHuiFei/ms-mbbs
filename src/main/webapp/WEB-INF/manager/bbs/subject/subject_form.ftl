<@ms.html5>
	<@ms.nav title="帖子编辑" back=true>
		<@ms.saveButton  id="saveUpdate" postForm="subjectForm" /> 
	</@ms.nav>
	<@ms.panel>
		<@ms.form isvalidation=true name="subjectForm" action="${managerPath}/bbs/subject/update.do" redirect="${managerPath}/bbs/subject/list.do?${params}">
			<input type="hidden" name="subjectBasicId" value="${subject.basicId}"/> 
			<input type="hidden" name="basicId" value="${subject.basicId}"/> 
			
			<@ms.text name="basicTitle" width="400" label="标题"	title="标题" size="5"  placeholder="请输入标题"  value="${subject.basicTitle?default('')}"  validation={"maxlength":"300","required":"true", "data-bv-notempty-message":"标题不能为空","data-bv-stringlength-message":"标题在300个字符以内!"}/>
			<@ms.text name="basicSort"  width="200" label="自定义顺序" title="自定义顺序" size="5"  placeholder="请输入顺序" value="${subject.basicSort?c?default(0)}" validation={"data-bv-between":"true","data-bv-between-message":"自定义顺序必须大于0","data-bv-between-min":"0", "data-bv-between-max":"99999999","data-bv-notempty-message":"自定义顺序不能为空"}/>
				
			<@ms.checkbox name="basicType" label="属性" list=subjectTypes listKey="categoryId"  listValue="categoryTitle" valueList=subject.basicTypeIds  />
			<@ms.formRow label="缩略图" width="400">
					<@ms.uploadImg path="upload/mbbs/${appId}/" inputName="basicThumbnails" size="1" msg="提示:缩略图,支持jpg格式"  maxSize="2" imgs="${subject.basicThumbnails?default('')}"  />
			</@ms.formRow>
			<@ms.textarea name="basicDescription" label="描述" width="600" wrap="Soft" rows="4"  size=""  value="${subject.basicDescription?default('')}" placeholder="请输入对该的简短描述，以便用户查看简略"/>
			<@ms.textarea name="articleKeyword" label="关键字" width="600" wrap="Soft" rows="4"  size="" placeholder="请输入关键字"   value="${subject.articleKeyword?default('')}"/>
			<!--新填字段内容开始-->
			<div id="addFieldForm">		
			</div>
			<@ms.hidden name="articleTypeJson" />
			<@ms.editor name="subjectContent" label="内容" content="${subject.subjectContent?default('')}"  appId="${appId?default(0)}"/>			
		</@ms.form>
	</@ms.panel>
</@ms.html5>