<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String base = request.getContextPath();
	request.setAttribute("base", base);
	/* String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ base + "/"; */
%>

<html>
<head>
<meta charset="UTF-8">
<title></title>

</head>
<body>
	<div class="easyui-layout" fit="true">
		<div region="center" border="false"
			style="padding: 10px; background: #fff; border: 1px solid #ccc;">
			<table cellpadding=3>
				<tr>
					<td>新密码：</td>
					<td><input id="txtNewPass" type="Password" class="txt01" /></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input id="txtRePass" type="Password" class="txt01" /></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input id="txtRePass" type="Password" class="txt01" /></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input id="txtRePass" type="Password" class="txt01" /></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input id="txtRePass" type="Password" class="txt01" /></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input id="txtRePass" type="Password" class="txt01" /></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input id="txtRePass" type="Password" class="txt01" /></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input id="txtRePass" type="Password" class="txt01" /></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input id="txtRePass" type="Password" class="txt01" /></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input id="txtRePass" type="Password" class="txt01" /></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input id="txtRePass" type="Password" class="txt01" /></td>
				</tr>
			</table>
		</div>
		<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
			<a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:dialogClose('sendProduct')"> 确定</a> 
			<a id="btnCancel" class="easyui-linkbutton" icon="icon-no" href="javascript:dialogClose('sendProduct')">取消</a>
		</div>
	</div>
</body>
</html>