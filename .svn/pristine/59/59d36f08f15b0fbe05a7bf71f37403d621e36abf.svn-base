<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.Properties"%>
	
<%
	String base = request.getContextPath();
	request.setAttribute("base", base);

	Properties properties = new Properties();
	properties.load(getServletContext().getResourceAsStream("/WEB-INF/config.properties"));
	String fileTypes = properties.getProperty("FILETYPES");
	request.setAttribute("fileTypes", fileTypes);
	
	String isUpload = (String)request.getAttribute("isUpload");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%-- <link rel="stylesheet" type="text/css"	href="${base}/js/easyUI/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css"	href="${base}/js/easyUI/themes/icon.css">
	<link rel="stylesheet" type="text/css"	href="${base}/js/easyUI/themes/demo.css">
	<link rel="stylesheet" type="text/css"	href="${base}/css/uploadfile.css"> --%>
	<%-- <script type="text/javascript"	src="${base}/js/easyUI/jquery-1.4.4.min.js"></script>
	<script type="text/javascript"	src="${base}/js/easyUI/jquery.easyui.min.js"></script> --%>
	<%-- <script type="text/javascript" src="${base}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"	src="${base}/js/jquery.uploadfile.min.js"></script> --%>
	
<style>
a.one:link {color: 	#000000	; text-decoration: none}
a.one:visited {color: 	#0000ff	; text-decoration: none}
a.one:hover {text-decoration: underline;font-size: 150%}
</style>
<script type="text/javascript">
$(function(){
	var isUpload = <%=isUpload%>;
	if (isUpload == 1){
		$("#up").html("<div id='fileuploader'></div>");
		//$("#commonUp").html("<button onclick='commonUpload()'>无插件上载</button>");
		$("#fileuploader").uploadFile({
			url: "<%=base%>/file/fileUpload", //文件上传url
			/* dataType: 'json', */
			formData : {currentDir:$('#currentDir').val(),sapCode:$('#sapCode').val()}, 
			fileName: "uploadFile", //提交到服务器的文件名
			maxFileCount: 30, //上传文件个数（多个时修改此处）
			maxFileSize: 20971520, //单文件大小上限,单位是byte 
			showFileSize: true,
			showProgress: true,
			//returnType: 'json', //服务返回数据
			allowedTypes: "<%=fileTypes%>", //允许上传的文件式
			showDone: true, //是否显示"Done"(完成)按钮
			showDelete: false, //是否显示"Delete"(删除)按钮
			afterUploadAll: function() {
				//上传成功后的回调方法。本例中是将返回的文件名保到一个hidden类开的input中，以便后期数据处理
	//			if(data && data.code === 0) {
	//				$('#image').val(data.url);
	//			}
				var path = $('#currentDir').val();
				$.ajax({  
			        url:"<%=base%>/file/listFile",
			        type:"POST",
			        data: {currentDir:path,sapCode:path.substring(1,9)},
			        success:function(data){
			    		//$.messager.alert('通知','提交成功!');
			    		setTimeout(function () { 
			    			$('#content').html(data);}, 1000);
			        },
			        error: function(XMLHttpRequest, textStatus, errorThrown) {
	                    alert(XMLHttpRequest.status);
	                    alert(XMLHttpRequest.readyState);
	                    alert(textStatus);
	                }
		        });
			}
		});
	}
});
	 // 文件路径打包下载
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
		
	 // 多选框全选
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
		
	 // 右边栏点击目录
		function clickf(path, name) {
		 //alert('path:'+path);
			$.ajax({  
		        url:"<%=base%>/file/fileDownload",
		        type:"POST",
		        data: {filePath:path,fileName:name,sapCode:path.substring(1,9)},
		        success:function(data){
		    		//$.messager.alert('通知','提交成功!');
		    		$('#fileTable').html(data);
		        	//window.location.href = data;
		        },
		        error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }
	        });
		}
		
		function back() {
			var path = $('#currentDir').val().substring(0,$('#currentDir').val().lastIndexOf('/'));
			//alert(path);
			$.ajax({  
		        url:"<%=base%>/file/listFile",
		        type:"POST",
		        data: {currentDir:path,sapCode:path.substring(1,9)},
		        success:function(data){
		    		//$.messager.alert('通知','提交成功!');
		    		$('#content').html(data);
		        },
		        error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }
	        });
		}
		
		function delFile(path,id) {
			$.messager.confirm('提示', '确定删除选中文件吗?', function (r) {
	            if (!r) {  
	                return;  
	            }  
				$.ajax({  
			        url:"<%=base%>/file/delFile",
			        type:"POST",
			        data: {id:id},
			        success:function(data){
			        	$.ajax({  
					        url:"<%=base%>/file/listFile",
					        type:"POST",
					        data: {currentDir:path,sapCode:path.substring(1,9)},
					        success:function(data){
					    		//$.messager.alert('通知','提交成功!');
					    		setTimeout(function () { 
					    			$('#content').html(data);}, 1000);
					        },
					        error: function(XMLHttpRequest, textStatus, errorThrown) {
			                    alert(XMLHttpRequest.status);
			                    alert(XMLHttpRequest.readyState);
			                    alert(textStatus);
			                }
				        });
			        },
			        error: function(XMLHttpRequest, textStatus, errorThrown) {
		                   alert(XMLHttpRequest.status);
		                   alert(XMLHttpRequest.readyState);
		                   alert(textStatus);
		               }
		        });
			});
		}
		
		function commonUpload() {
			$('#commonUpload').show();
		}
		
		function reload() {
			var path = $('#currentDir').val();
			//alert(path);
			$.ajax({  
		        url:"<%=base%>/file/listFile",
		        type:"POST",
		        data: {currentDir:path,sapCode:path.substring(1,9)},
		        success:function(data){
		    		//$.messager.alert('通知','提交成功!');
		    		$('#content').html(data);
		        },
		        error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }
	        });
		}
	</script>
</head>
<body>
	<div id="up"></div>
	
	<!-- 遍历文件名list集合 -->
	<%-- <form action="batchDownload" method="post" id="batForm"> --%>
	<div style="font-size: 12px;" id="fileTable">
		
		<div id="commonUpload" style="display: none;">
			上传文件:<form action="<%=base%>/file/fileUpload" method="post" enctype="multipart/form-data">
				<input type="file" name="commonFiles" multiple="multiple"/>
				<input type="hidden" name="sapCode" value="${sapCode}" />
				<input type="hidden" name="currentDir" value="${currentDir}" />
				<input type="hidden" name="fileTypes" value="${fileTypes}" />
				<input type="submit" value="上传" />
			</form>
		</div>
	
		<form id="fileForm">
				<input type="hidden" id="currentDir" name="currentDir" value="${currentDir}" />
				<input type="hidden" id="sapCode" name="sapCode" value="${sapCode}"><span id="sp"></span>
		</form>
		<div id="commonUp"></div>
	路径：<span>${currentDir}</span>&nbsp;&nbsp;<span class="icon icon-reload" style="cursor: pointer;" onclick="reload()">&nbsp;</span>
	<table>
		<tr><!-- <td><input type="checkbox" id="allcheck" onclick="allChecked()" /></td> --><td>文件名<hr /></td><td>&nbsp;&nbsp;&nbsp;最后更新时间<hr /></td><td>&nbsp;&nbsp;&nbsp;大小<hr /></td><td>&nbsp;&nbsp;&nbsp;创建者<hr /></td></tr>
		<tr><!-- <td>&nbsp;&nbsp;&nbsp;</td> --><td><span class="icon icon-back" style="cursor: pointer;" onclick="back()">&nbsp;</span><a class="one" href="javascript:void(0)" onclick="back()">......</a></td></tr>
		<c:forEach var="file" items="${fileNames}">
			<c:url value="/file/fileDownload" var="downurl">
    			<c:param name="filePath" value="${file.path}"></c:param>
    			<c:param name="fileName" value="${file.fileName}"></c:param>
    		</c:url> 
   			<tr>
    		<%-- <td><input type="checkbox" name="fName" value="${file.path}" /></td> --%>
    		<td><span class="${file.imgSrc}">&nbsp;</span>
    			<c:choose>
					<c:when test="${fn:indexOf(file.fileName,'.')==-1}">
						<a class="one" href="javascript:void(0)" onclick="clickf('${file.path}','${file.fileName}')">${file.fileName}</a>
					</c:when>
					<c:otherwise>
						<a class="one" href="${downurl}">${file.fileName}</a>
					</c:otherwise>
				</c:choose>
			</td>
    		<td>&nbsp;&nbsp;&nbsp;${file.displayUploadTime}</td>
    		<td>&nbsp;&nbsp;&nbsp;${file.displayFileSize}</td>
    		<td>&nbsp;&nbsp;&nbsp;${file.author}</td>
    		<td>&nbsp;&nbsp;&nbsp;
    			<c:choose>
					<c:when test="${fn:indexOf(file.fileName,'.')!=-1 && isUpload==1}">
						<a href="javascript:void(0)" onclick="delFile('${file.path}','${file.id}')">删除</a>
					</c:when>
				</c:choose>
			</td>
   			</tr>
		</c:forEach>
		</table>
		</div>
		<input type="hidden" id="str" name="str" />
		<br />
		<br />
		<br />
	<%-- </form> --%>
</body>
</html>