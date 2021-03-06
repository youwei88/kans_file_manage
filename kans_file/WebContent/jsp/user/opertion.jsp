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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限分配</title>

<link rel="stylesheet" type="text/css" href="${base}/js/easyUI/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${base}/js/easyUI/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${base}/js/easyUI/themes/demo.css">

<script type="text/javascript" src="${base}/js/easyUI/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${base}/js/easyUI/jquery.easyui.min.js"></script>
</head>
<body>
	<!-- <h2>CheckBox Tree</h2>
	<p>Tree nodes with check boxes.</p>
	<div style="margin:20px 0;">
		<a href="#" class="easyui-linkbutton" onclick="getChecked()">GetChecked</a> 
	</div>
	<div style="margin:10px 0">
		<input type="checkbox" checked onchange="$('#tt').tree({cascadeCheck:$(this).is(':checked')})">CascadeCheck 
		<input type="checkbox" onchange="$('#tt').tree({onlyLeafCheck:$(this).is(':checked')})">OnlyLeafCheck
	</div> -->
	<%-- <button onclick="window.location.href='${base}/role/editRoleOperation'">修改权限</button> --%>
	<div class="easyui-panel" style="padding:5px">
		<ul id="tt" class="easyui-tree" data-options="url:'${base}/user/getOpertion?roleid=${roleId}',method:'get',animate:true,checkbox:true"></ul>
		<button id="btn" style="visibility: false" onclick="getChecked(${roleId},'/role/modifyRoleOperation')">提交</button>
	</div>
	<script type="text/javascript">
		function getChecked(roleid, url){
			debugger;
			// 全选中
			var nodes = $('#tt').tree('getChecked');
			// 半选中
			var nodes2 = $('#tt').tree('getChecked','indeterminate');
			
			if (nodes.length==0) {  
		        $.messager.alert("提示", "请选择要赋予的权限！", "info");  
		        return;  
		    } else {
		    	var s = '';
				for(var i=0; i<nodes.length; i++){
					if (0 != nodes[i].id) {
					if (s != '') s += ',';
					s += nodes[i].id;
					}};
				
				for(var j=0; j<nodes2.length; j++){
					if (0 != nodes2[j].id) {
					if (s != '') 
						s += ',';
					s += nodes2[j].id;
				}};
			
				 $.messager.confirm('提示', '确定如此赋权吗?', function (r) {  
			            if (!r) {  
			                return;  
			            }  
			            //提交  
			            $.ajax({  
			                type: "POST",  
			                async: false,  
			                url: "<%=base%>" + url,
			                data: {roleid:roleid,idStr:s},  
			                success: function () {  
			                        $.messager.alert("提示", "恭喜您，赋权成功！", "info");
			                        $('#tt').tree('reload');
			                },
			                error:function(){
			                	
			                	$.messager.alert('通知','提交失败2!');
			                }
			            });  
			        }); 
		    }
		}
	</script>
</body>
</html>