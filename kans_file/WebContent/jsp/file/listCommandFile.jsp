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
	<script type="text/javascript"
	src="${base}/js/easyUI/jquery-1.4.4.min.js"></script>
	<script type="text/javascript">
		function getCkxValue(){
		    var objs=document.getElementsByName("fName");
		    if (objs.length == 0) {
		    	alert("请选择文件！");
		    	return ;
		    }
		    
		    var boxes = document.getElementsByTagName("input");
		    var str = "";
		    for(i=0;i<boxes.length;i++){
		        if(boxes[i].name=="fName" && boxes[i].checked == true){
		        	str += boxes[i].value + ",";
		        }
		    }
		    str=str.substring(0,str.length-1);
		    $("#str").val(str);
		    $("#batForm").submit();
		    
		}
		
		function allChecked() {
			var objs=document.getElementsByName("fName");
			for(i=0;i<objs.length;i++){
				if ($("#allcheck").attr("checked") == true) {
					objs[i].checked = true;
				} else {
					objs[i].checked = false;
				}
			}
		}
	</script>
</head>
<body>
		<!-- 遍历文件名list集合 -->
		<form action="batchDownload" method="post" id="batForm">
		<div style="font-size: 14px;">
		<table>
			<tr><td><input type="checkbox" id="allcheck" onclick="allChecked()" /></td><td>文件名</td><td>&nbsp;&nbsp;&nbsp;最后更新时间</td><td>&nbsp;&nbsp;&nbsp;大小</td></tr>
			<c:forEach var="file" items="${fileNames}">
				<c:url value="/file/fileDownload" var="downurl">
	    			<c:param name="filePath" value="${file.path}"></c:param>
	    			<c:param name="fileName" value="${file.fileName}"></c:param>
	    		</c:url> 
	    		
    			<tr>
	    		<td><input type="checkbox" name="fName" value="${file.path}${file.fileName}" /></td>
	    		<td><a href="${downurl}">${file.path}${file.fileName}</a></td>
	    		<td>&nbsp;&nbsp;&nbsp;${file.displayUploadTime}</td>
	    		<td>&nbsp;&nbsp;&nbsp;${file.displayFileSize}</td>
    			</tr>
			</c:forEach>
			</table>
			</div>
			<input type="hidden" id="str" name="str" />
			<br />
			<br />
			<br />
		</form>
		<button onclick="getCkxValue()">批量打包下载</button>
</body>
</html>