$(function(){

	//获取帖子ID
	var basicId = $("input[name='basicId']").val();
	//获取版块Id
	var categoryId = $("input[name='categoryId']").val();
	//判断用户是否登录
	ms.people.getEntity({"func":function(json){
		if(json.result == true){
			var peopleId= jQuery.parseJSON(json.resultData).peopleId;
			ms.bbs.web.subject.getByBasicId (basicId,{"func":function(msg){//调用方法
				var obj = jQuery.parseJSON(msg.resultMsg);
				
								
					
			if(peopleId==obj.basicPeopleId){
				$(".invitation_con .txt").append('<a href="/mbbs/'+basicId +'/'+categoryId +'/edit.do"> 编辑</a>')
			
				}
			}});
		}
	}}); 
})

