<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script type="text/javascript" src="${base}/jsp/user/user.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div region="center" border="false"
			style="padding: 10px; background: #fff; border: 1px solid #ccc;">
			<form id="roleForm" action="<%=base%>/user/updateRole" method="post">
			<input type="hidden" name="userid" value="${userid}" />
			<table cellpadding=3>
				<c:forEach items="${roleList}" var="item" varStatus="status">
					<c:set var="isEnd" value="${status.count}" />

					<c:if test="${isEnd%4 == 1}">
						<tr>
					</c:if>
						<td width="25%" height="26">
							<c:choose>
								<c:when test="${role.id == item.id}">
									<input type="radio" name="roleId" checked="checked" value="${item.id}" />&nbsp;&nbsp;${item.roleName}
								</c:when>
								<c:otherwise>
									<input type="radio" name=roleId value="${item.id}" />&nbsp;&nbsp;${item.roleName}
								</c:otherwise>
							</c:choose>
						</td>
					<c:if test="${isEnd%4 == 0}">
						</tr>
					</c:if>
				</c:forEach>
			</table>
			</form>
		</div>
		<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
			<a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:submitButton('operationDiv','roleForm')"> 确定</a> 
			<a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:dialogClose('operationDiv')">取消</a>
		</div>
	</div>
</body>
</html>