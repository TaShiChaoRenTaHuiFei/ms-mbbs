/*获取用户实体*/
ms.people.info({"func":function(json){
	//用户已登录操作
	if(json.result==false){
		//用户未登录
		location.href = "http://ms.ming-soft.com"; 
	}
}});

