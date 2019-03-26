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
		<button id="add" style="visibility: false" onclick="addName()">添加</button>
		<button id="delete" style="visibility: false" onclick="delDir()">删除</button>
		<button id="apply" style="visibility: false" onclick="applyDir()">应用到实际目录</button>
		<ul id="tt" class="easyui-tree" data-options="url:'${base}/user/getDirs?isFromSap=1',method:'get',animate:true"></ul>
	</div>
	<div id="addName" class="easyui-dialog" modal="true" closed="true" style="padding:5px;width:200px;height:120px;" title="添加目录">
		目录:<input id="name" name="name" />
		<button onclick="add()">确定</button>
	</div>
	<script type="text/javascript">
		function delDir() {
			var node = $('#tt').tree('getSelected');
			if (!node){
				$.messager.alert('通知',"请选择目录！");
				return ;
			}
			//alert($('#tt').tree('getChildren', node.target).length);
			if (0 != $('#tt').tree('getChildren', node.target).length) {
				$.messager.alert('通知',"请先删除子目录！");
				return ;
			}
			
			var path = node.text;
			var nodep = $('#tt').tree('getParent', node.target);
			if (nodep.text != '根目录'){
				path = nodep.text + '/' + path;
				var nodepp = $('#tt').tree('getParent', nodep.target);
				if (nodepp.text != '根目录'){
					path = nodepp.text + '/' + path;
				}
			}
			$.messager.confirm('提示', '确定删除选中目录?', function (r) {  
	            if (!r) {
	                return;
	            }
	            $.ajax({
					url:"<%=base%>/dir/delDir",
			        type:"POST",
			        data: {id:node.id,path:path},
			        success:function(data){
			        	if (data == 2) {
			        		$.messager.alert('通知','请先删除目录下的文件!');
			        	} else {
			    			$.messager.alert('通知','删除成功!');
			        	}
			    		$('#tt').tree('reload');
			        },
			        error: function(XMLHttpRequest, textStatus, errorThrown) {
                        alert(XMLHttpRequest.status);
                        alert(XMLHttpRequest.readyState);
                        alert(textStatus);
                    }
				});
			});
		}
		function addName() {
			var node = $('#tt').tree('getSelected');
			if (!node) {
				$.messager.alert('通知',"请先选择父目录！");
				return ;
			};
			/* if (node.text == '根目录') {
				$.messager.alert('通知',"一级目录不可创建");
				return ;
			} */
			$('#addName').dialog('open');
		};
		
		function add() {
			var node = $('#tt').tree('getSelected');
			var parentId = node.id;
			var name = $('#name').val();
			var path;
			if (node.text == '根目录') {
				// 文件查看根id
				parentId = '12';
				path = '/' + name;
			} else {
				path = node.text + '/' + name;
				var nodep = $('#tt').tree('getParent', node.target);
				if (nodep.text != '根目录'){
					path = nodep.text + '/' + path;
				}
			}
			// alert(path);
			if (name){
				$.ajax({
					url:"<%=base%>/dir/addDir",
			        type:"POST",
			        data: {parentId:parentId,name:name,path:path,isFromSap:1},
			        success:function(data){
			        	$('#addName').dialog('close');
			    		$.messager.alert('通知','提交成功!');
			    		$('#name').val('');
			    		$('#tt').tree('reload');
			        },
			        error: function(XMLHttpRequest, textStatus, errorThrown) {
                        alert(XMLHttpRequest.status);
                        alert(XMLHttpRequest.readyState);
                        alert(textStatus);
                    }
				});
			} else {
				$.messager.alert('通知',"请写目录名");
			}
		}
		
		function applyDir() {
			$.messager.confirm('提示', '是否确实应用到系统中的数十万个目录?(耗时较长)', function (r) {  
	            if (!r) {  
	                return;  
	            }  
				$.ajax({
					url:"<%=base%>/dir/applyDir",
			        type:"POST",
			        success:function(data){
		    			$.messager.alert('通知','操作成功!');
			        },
			        error: function(XMLHttpRequest, textStatus, errorThrown) {
	                    alert(XMLHttpRequest.status);
	                    alert(XMLHttpRequest.readyState);
	                    alert(textStatus);
	                }
				});
			});  
		}
	</script>
</body>
</html>