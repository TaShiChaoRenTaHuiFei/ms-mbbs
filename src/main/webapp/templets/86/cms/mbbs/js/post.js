$(function() {

	/*ms.bbs.web.category.list({
		"func": function(json) { //调用方法
			$("#channel").tmpl(jQuery.parseJSON(json.resultMsg)).appendTo(".channel");
			$("#categoryChildListTmp").tmpl(jQuery.parseJSON(json.resultMsg)).appendTo(".categoryChildListTmp");
		}
	});*/
	
	//点击板块下拉出现
	$(".selectPlate").click(function() {
		$(".selectPlatelist").addClass("show_text");
		$(".show_text").slideToggle();
	});
	//鼠标移出,板块列表隐藏
/*	$(".channelclass").mouseleave(function() {
		$(".show_text").slideUp(600).css("display", "block");
		$(".selectPlatelist").removeClass("show_text");
	});
*/
	//点击之后将板块名称放到选择框
	$(".channel").delegate('.categoryTitle', 'click', function() {
		$(".selectPlate span").attr("value", $(this).attr("data-id"));
		$(".selectPlate span").html($(this).text());
		$(".selectPlatelist").removeClass("show_text");
		$(".selectPlatelist").slideUp();
		//选择板块名就清空子板块列表，并且将板块id默认付给子版块
		$(".selectclassify span").html("选择板块分类");
		$("#basicCategoryId").val($(this).attr("data-id"));
		$("#subjectForumFirstId").val($(this).attr("data-id"));
	});



    var subjectForumSecondId=0;

	//点击获取子板块的并让子板块内容显示
	$(".selectclassify").click(function() {
		$(".error_tips").remove();
		$(".selectlist").addClass("show_text_list");
		if ($(".selectPlate span").attr("value") != 0) {
			//遍历所有板块下的子板块，并让其显示
			$(".show_text_list li").each(function() {
				//判断父级板块id是否等于子级板块的父级分类id,
				if($(this).attr("parent-id") == $(".selectPlate span").attr("value")) {
					$(".show_text_list").css("display", "block");
					$(this).css("display", "block");
				}else{
					$(this).css("display", "none");
				}
			});
		}else{
            $(".selectclassify").after("<span class='error_tips' style='margin-left:10px;line-height: 38px;border:none;color:red'>请选择一级板块</span>");
        }
	});
	//鼠标移出子板块列表,隐藏子板块列表
	$(".selectclassify").mouseleave(function() {
		$(".show_text_list").slideUp();
		$(".selectlist").removeClass("show_text_list");
	});

	//点击子板块列表下拉框消失
	$(".categoryChildListTmp").delegate("li", "click", function() {           
		$("#subjectForumSecondId").val($(this).attr("data-id"));
        subjectForumSecondId=$(this).attr("data-id");
		$(".selectclassify span").html($(this).text());
		$(".show_text_list").slideUp();
		$(".selectlist").removeClass("show_text_list");
	});
			 
})		
