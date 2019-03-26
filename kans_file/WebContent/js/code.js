$(function() {  
    var code = 9999; 
    function codes(){
    	
        //var ranColor = '#' + ('00000' + (Math.random() * 0x1000000 << 0).toString(16)).slice(-6); //随机生成颜色
    	// alert(ranColor)
    	//var ranColor2 = '#' + ('00000' + (Math.random() * 0x1000000 << 0).toString(16)).slice(-6); 
     	var num1 = Math.floor(Math.random() * 100);  
        var num2 = Math.floor(Math.random() * 10);  
        code = num1 + num2;  
        
        $("#code").html(num1 + "+" + num2 + "=?");  
        if ($("#code").hasClass("nocode")) {  
            $("#code").removeClass("nocode");  
            $("#code").addClass("code"); 
           
        }  
        $("#code").css('background','#CCFFCC');
//         $("#code").css('background-image','url(images/code.jpg)');
         $("#code").css('color','FF3300');

    }
    codes()
   
    $("#code").on('click',codes)
    
    $("#check").click(function(){ 
        if ($("#yanzhengcode").val() == code && code != 9999) {  
            $('#loginFrom').submit();
        } else {  
//            alert("输入有误!");  
            $('#errorinfo').html('验证码输入错误');
        }  
    });  
    
 // 回车键事件 
 // 绑定键盘按下事件  
    $(document).keypress(function(e) {  
     // 回车键事件  
        if(e.which == 13) {  
	    	 if ($("#yanzhengcode").val() == code && code != 9999) {  
	             $('#loginFrom').submit();
	         } else {  
//    	    	             alert("输入有误!");  
	             $('#errorinfo').html('验证码输入错误');
	         }  
        }  
    }); 
});  
