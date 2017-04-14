$(function() {
var host = window.location.host;
if (host=="www.ming-soft.com" ) {
	location.href="http://www.mingsoft.net";
} else if(host=="ms.ming-soft.com") {
	location.href="http://ms.mingsoft.net";
}else if (host=="ming-soft.com" ) {
	location.href="http://www.mingsoft.net";
}


    $('[data-toggle="tooltip"]').tooltip();
    ms.load(["ms","ms.people"],function(ms,people) {
    //获取用户是否登陆
        people.checkLoginStatus(function(json){
            if(json.result){
                people.people.user.info(function(json) {
                    $(".loginNoPass").hide();
                    // 显示登陆框
                    $(".loginSuccess").fadeIn();
                    
                    var NAME = json.peopleName;
                    var PHONE = json.peoplePhone;
                    var MAIL = json.peopleMail;
                    var NICKNAME = json.peopleUserNickName;
                    var PEOPLEUSERICON =  json.peopleUserIcon;
                    if (ms.isEmpty(MAIL) || json.peopleMailCheck!=1) {
                        if( window.location.href.indexOf("/people/bindEmail.do")>0){
                            $(".bindEmail").removeClass("in");
                            $(".bindEmail").hide();  
                        }else{
                            $(".bindEmail").addClass("in");
                            $(".bindEmail").show();  
                        }             
                    }else{
                        $(".bindEmail").removeClass("in");
                        $(".bindEmail").hide();  
                    }

                    if(ms.isEmpty(PEOPLEUSERICON)){
                        $(".loginImg").attr("src","http://cdn.mingsoft.net/global/images/msheader.png");
                        $("#pc_peopleicon").attr("src","http://cdn.mingsoft.net/global/images/msheader.png");
                    }else{
                        $(".loginImg").attr("src",PEOPLEUSERICON);
                        $("#pc_peopleicon").attr("src",PEOPLEUSERICON);
                    }    
                    if( NICKNAME == undefined || NICKNAME.length <=0){                      
                        if( NAME != undefined || NAME.length >0){
                            NICKNAME = NAME;
                        }else if( PHONE != undefined || PHONE.length >0){
                            NICKNAME = PHONE;
                        }else if(MAIL != undefined || MAIL.length >0){
                            NICKNAME = MAIL;
                        }
                    }
                    // 首页显示登陆的用户名
                    $(".userName").html(NICKNAME); 
                    $(".user_icon").attr("src",PEOPLEUSERICON);
                    $(".user-name").attr("href",ms.base+"/people/center.do"); 
                    $(".userName").fadeIn();
                    $(".user_icon").fadeIn();
                })
            }else{
                $(".loginNoPass").fadeIn();
                $("#pc_peopleicon").attr("src","http://cdn.mingsoft.net/global/images/msheader.png");
                // 显示登陆框
                $(".loginSuccess").hide();
                $(".bindEmail").removeClass("in");
                $(".bindEmail").hide();  
            }
        });
        //退出登录
        $(".quitLogin").click(function(){
            people.people.quit();
            if( window.location.href.indexOf("/people")>0){
                location.href=ms.base+"/mbbs/main.do";
                     
            }else{
                location.reload();
            }
        });
        
        $(".gotoBindEmail").click(function(){
            location.href=ms.base+"/people/bindEmail.do";
        });

        $(".theme_nav_btn").click(function(){
            people.checkLoginStatus(function(json){
                if(!json.result){
                    ms.alert("请登录后发贴");
                    return;
                }else{
                    location.href=ms.base+"/mbbs/diypost.do";
                }
            })
        })
    })
    //头部菜单上移出现子菜单
    $(".daohang_li_parentmenu").hover(
      function(){
       $(this).find(".daohang_li_menu").slideDown();
       $(this).addClass("daohang_li_parentmenuhover");
     },
     function(){
       $(this).find(".daohang_li_menu").slideUp(100);
       $(this).removeClass("daohang_li_parentmenuhover");
     });

  });

  function clickevent(){
    alert("文档正在更新中...本月上新！")
  };
  
  function afterLoadFooter(){
    var bodyHeight = $('body').height();
    var winHeight = $(window).height();

    if ((winHeight - bodyHeight) > 0) {
        $('body').height(winHeight);
        $('#footer').addClass('f_position')
    }
  };
