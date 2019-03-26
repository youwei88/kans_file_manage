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
	
	String userPhone = request.getParameter("userPhone");
	String creatTimeStr = request.getParameter("creatTimeStr");
	String endTimeStr = request.getParameter("endTimeStr");
%>

<html>
<head>
<title>demo</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="${base}/js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">

	function exel(){
		document.exelForm.submit();
	}
	
</script>

</head>
<body onload="exel()">
<form action="${base}/poi/creatExel" name="exelForm">
	<input type="hidden" name="userPhone" value="<%=userPhone%>">
	<input type="hidden" name="creatTimeStr" value="<%=creatTimeStr%>">
	<input type="hidden" name="endTimeStr" value="<%=endTimeStr%>">
</form>
	
</body>
</html>

