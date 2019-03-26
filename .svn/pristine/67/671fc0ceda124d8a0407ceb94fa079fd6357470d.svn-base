<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String base = request.getContextPath();
	request.setAttribute("base", base);
	/* String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ base + "/"; */
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="${base}/css/login.css" />
<link type="text/css" rel="stylesheet" href="${base}/css/style.css" />
<script type="text/javascript" src="${base}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${base}/js/code.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
<style type="text/css">
.nocode {
	display: inline-block;
	width: 100px;
	height: 25px;
}

.code {
	display: inline-block;
	color: #ff0000;
	font-family: Tahoma, Geneva, sans-serif;
	/*font-style: italic;*/
	font-weight: bold;
	text-align: center;
	width: 100px;
	height: 27px;
	line-height: 28px;
	cursor: pointer;
	border: 1px solid #e2b4a2;
	background: #e2b4a2;
}

.yanzhengcode {
	border: 1px solid #d3d3d3;
	padding: 10px 10px;
	width: 181px;
	border-radius: 4px;
	padding-left: 38px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
	-webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow
		ease-in-out .15s;
	-o-transition: border-color ease-in-out .15s, box-shadow ease-in-out
		.15s;
	transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s
}

.p2_logo {
	background: url("../images/login_image/password.png") no-repeat;
	padding: 10px 20px;
	position: absolute;
	top: 22px;
	left: 40px;
}

input:-webkit-autofill, 
 textarea:-webkit-autofill, 
 select:-webkit-autofill { 
       -webkit-box-shadow: 0 0 0 1000px white inset; 
}
 input[type=text]:focus, input[type=password]:focus, textarea:focus {
      -webkit-box-shadow: 0 0 0 1000px white inset; 
}

</style>
</head>

<SCRIPT type="text/javascript">
		
</SCRIPT>

<body>
<BODY>

	<form id="loginFrom" action="${base}/j_spring_security_check"
		method="post">

		<div class="login">
			<ul>
				<li class="li1"><i></i> <input id="j_username" name="j_username"
					class="codeipt" value="" placeholder="请输入用户名"></li>
				<li class="li2"><i></i> <input id="j_password" type="password" name="j_password"
					class="codeipt" value="" placeholder="请输入密码"></li>
				<li class="li3"><i></i> <input type="text" name="" id="yanzhengcode"
					class="codeipt" value="" placeholder="请输入验证码" style="padding-right: 20px"><span id="code" class="nocode" style="font-size: 14px;"></span></li>
				<li class="li5"><input id="check" type="button" value="立即登录" class="btn"></li>
			</ul>
			<div class="login_bg"></div>
		</div>
		<div style="margin-top: 250px;margin-left: 920px">
			<p>
				<%-- 来源:<a href="#" target="_blank">${error}</a> --%>
				<b id="errorinfo">${error}</b>

				<%-- ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message} --%>

			</p>
		</div>
	</form>
</BODY>
</html>