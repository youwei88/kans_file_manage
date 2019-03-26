<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
function logout(){
	
	 if(confirm("确定要退出吗？"))
	   {
		 document.userForm.submit();
	   }
    /* layer.confirm("确定要退出吗？",function(){
            
    }); */
}
</script>
</head>
<body>
<form action="../j_spring_security_logout" name="userForm">
</form>
	<h1>Common Page</h1>
	<p>每个人都能访问的页面.</p>
	<a href="../main/admin"> admin权限页面 </a>
	<br />
	<a href="#" onclick="logout()">退出登录</a>

</body>
</html>