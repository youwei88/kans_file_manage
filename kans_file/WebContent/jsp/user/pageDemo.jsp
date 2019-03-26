<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%

	String base = request.getContextPath();
	request.setAttribute("base", base);
	/* String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ base + "/"; */
%>


<html>
<head>
<title>demo</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="demo" />
<script type="text/javascript">

	
	
	function exel(){
        
        var userPhone = $("#userPhone").val();
        var creatTimeStr = $("#creatTimeStr").val();
        var endTimeStr = $("#endTimeStr").val();
        
        window.location.href = '<%=base%>/poi/creatExel?userPhone='+userPhone+'&creatTimeStr='+creatTimeStr+'&endTimeStr='+endTimeStr;
	}
	
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
		<a href="#" onclick="logout()">退出登录</a>
	</form>

	<form id="searchForm" method="post" action="${base}/userList/select">
		<div class="cont box-shadow clearfix" id="bdID">
			<div class="formlist left10">
				<span> 手机号码：<input type="text" style="width: 200px; color: #999999" value="${userTableVo.userPhone}" name="userPhone" id="userPhone">
				开始时间：<input type="text" id="creatTimeStr" name="creatTimeStr" value="${userTableVo.creatTimeStr}" onFocus="WdatePicker({isShowClear:true,readOnly:true})" class="calendar" style="width: 172px;">
				结束时间：<input type="text" id="endTimeStr" name="endTimeStr" value="${userTableVo.endTimeStr}" onFocus="WdatePicker({isShowClear:true,readOnly:true})" class="calendar" style="width: 172px;">
				<input type="submit" class="btn_dark" value="查 询">
				<input type="button" class="btn_dark" onclick="exel()" value="导 出">
				</span>
				<div class="" id="userDiv">
				
					<table style="width: 100%" border="1">
						<tr>
							<th width="25%">创建时间</th>
						</tr>
						
						<c:forEach items="${page.rows}" var="userlist">
						<tr>
							<td><fmt:formatDate value="${userlist.creatTime}" type="both" /></td>
						</tr>
						</c:forEach>
					</table>

					<input type="hidden" id="pageNum" name="pageNum"
						value="${page.pageNum}" /> <input type="hidden" id="pageSize"
						name="pageSize" value="${page.pageSize}" /> <input type="submit"
						id="submitBtn" style="display: none;" />

					<div id="pageMod" class="page" _pages="${page.pages}"
						_pageNum="${page.pageNum}" _pageSize="10" , _total="${page.total}">
						<div id="pagnation" align="center" class="page"></div>
					</div>
				</div>
			</div>
		</div>


	</form>

	
</body>
</html>

