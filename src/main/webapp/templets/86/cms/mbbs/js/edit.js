$(function() {
	ms.bbs.web.category.list({
		"func": function(json) { //调用方法
			$("#channel").tmpl(jQuery.parseJSON(json.resultMsg)).appendTo(".channel");
			$("#categoryChildListTmp").tmpl(jQuery.parseJSON(json.resultMsg)).appendTo(".categoryChildListTmp");
                         var subjectPeopleId =$("input[name=peopleId]").val();
                          //获取父板块名称
        var basicCategoryId = $("input[name=basicCategoryId]").val();
    	var basicCategoryObj = $(".categoryChildListTmp").find("li[data-id="+basicCategoryId +"]");
    	var parentCategoryId=basicCategoryObj.attr("parent-id");
        parentId=parentCategoryId;
    	//获取子板块名称
    	$(".selectclassify span").html(basicCategoryObj.text());
    	var parentCategoryTitle=$(".selectPlatelist ul").find("li[data-id="+parentCategoryId+"]").text();
    	$(".selectPlate span").html(parentCategoryTitle);
    	//不管版主从哪里编辑，basicCategoryId的值都是帖子所属版块ID
    	$("input[name='categoryId']").val(obj.basicCategoryId);
    	//将得到的帖子所属版块ID赋予basicCategoryId，用于数据的提交
    
                        //获取帖子信息
   
		}
	});
	
	//检测标题输入长度
	$(".importtopic input[name='basicTitle']").bind('input propertychange', function() {
		if ($(".importtopic input[name='basicTitle']").val().length <= 70) {
			$("#titleNum").html(70 - $(".importtopic input[name='basicTitle']").val().length);
		}
	});
	var basicId=0;
	var pathArr = new Array();
    //获取地址栏路径
    var str  = bathPath;
    //地址栏分割，得到basicId和父级 
    pathArr=str.split("/");       
    for(i=0;i<pathArr.length;i++){
    	basicId = pathArr[4];
	}
    var subjectPeopleId=0;
    var parentId=0;
    
	//点击修改帖子
	$("#postsubmit").click(function() {
		//对按钮进行禁用
		$("#postsubmit").attr({"disabled":"disabled"});
                 if($("#postsubmit").attr("data-isClick")=="true"){
                   return;
                }
                $("#postsubmit").attr("data-isClick","true")
		$(this).addClass("disable_btn");
		//判断验证码是否填写正确
		if ($("input[name='rand_code']").val().length != 4) {
			$(".input_bottom_err ").show();
			$(".input_bottom_err .text_err").hide();
			$(".input_bottom_err .icon_err").html("验证码输入错误");
			$("#postsubmit").removeAttr("disabled");
			$(this).removeClass("disable_btn");
                          $("#postsubmit").attr("data-isClick","false")
			return;
		}
		//验证帖子标题长度
		if($.trim($(".importtopic input[name='basicTitle']").val()).length<1){
			$(".input_bottom_err").show();
			$(".input_bottom_err .text_err").hide();
			$(".input_bottom_err .icon_err").html("帖子标题不能为空");
			$("#postsubmit").removeAttr("disabled");
                          $("#postsubmit").attr("data-isClick","false")
			$(this).removeClass("disable_btn");
			return;
		}
		//判断是否填写内容
		if(UE.getEditor('editor').getContentLength(UE.getEditor('editor').getContentLength()>0)<=0){
			$(".input_bottom_err").show();
			$(".input_bottom_err .text_err").hide();
			$(".input_bottom_err .icon_err").html("请填写内容!");
                         $("#postsubmit").attr("data-isClick","false")
			$("#postsubmit").removeAttr("disabled");
			$(this).removeClass("disable_btn")
			return;
		}
		//判断填写的内容是否过长
		if(UE.getEditor('editor').getContentLength(true)>10000){
			$(".input_bottom_err").show();
			$(".input_bottom_err .text_err").hide();
			$(".input_bottom_err .icon_err").html("内容过长，请修改！");
			$("#postsubmit").removeAttr("disabled");
                         $("#postsubmit").attr("data-isClick","false")
			$(this).removeClass("disable_btn")
			return;
		}
		//帖子描述
	    var arr = [];
	    arr.push(UE.getEditor('editor').getContentTxt());
	    var descript = arr.join("\n");
	    if(descript.length>0&&descript.length>200){
	    	descript = descript.substring(0,200);
	    }
	    $("textarea[name=basicDescription]").val(descript);
	    //帖子第一张缩略图
	    var bodyContent = UE.getEditor('editor').getContent();
	    if($(bodyContent).find("img").length>0){
	    	 $("input[name=basicThumbnails]").val($(bodyContent).find("img").eq(0).attr("src"));
	    }
		//获取用户是否登陆
		ms.people.getEntity({"func":function(json){
			if(json.result == true){
				var peopleId = jQuery.parseJSON(json.resultData).peopleId;
                                 var subjectPeopleId =$("input[name=peopleId]").val();
				if(subjectPeopleId!=peopleId){
					$("#postsubmit").removeAttr("disabled");
					$(".input_bottom_err").show();
					$(".input_bottom_err .text_err").hide();
					$(".input_bottom_err .icon_err").html("您没有修改该帖子的权限！");
					$("#postsubmit").removeAttr("disabled");
                                         $("#postsubmit").attr("data-isClick","false")
					$("#postsubmit").removeClass("disable_btn")
					return;
				}else{
					var subject= $(".saveSubjectContent").serialize();
					//进行帖子的编辑
					ms.bbs.people.subject.update(basicId,subject,{"func":function(json){
						if(json.result == true) {
							   $(".input_bottom_err .icon_err").hide();
							   $(".input_bottom_err .text_err").show();
							   $(".input_bottom_err .text_err").after("<span class='tips_post'>操作成功</span>");
							   location.href = "/mbbs/"+parentId+"/list.do"; 
						   }else{
							   $(".input_bottom_err ").show();
							   $(".input_bottom_err .text_err").hide();
							   $(".input_bottom_err .icon_err").html(json.resultMsg);
							   $("#postsubmit").removeAttr("disabled");
							    $("#postsubmit").attr("data-isClick","false")
						   }
						$("#postsubmit").removeClass("disable_btn")
						$("#postsubmit").removeAttr("disabled");;
					}});
					
				}
			}else{
				$("#J_modalWeixin").removeClass("hide");
                $(".modal-backdrop").removeClass("hide");
                $(".modal ").css("top","50%");
                $("#J_modalWeixin").css("display","block");
                $(".modal-backdrop").css("display","block");
		        $(".modal-backdrop").removeClass("hide");
		        $("#login_container").removeClass("hide");
		        $(".modal-weixin").removeClass("hide");
                         $("#postsubmit").attr("data-isClick","false")
			}
		}});
		
	})
	
	  
});
