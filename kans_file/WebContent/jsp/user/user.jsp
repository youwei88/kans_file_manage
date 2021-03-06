<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
<link rel="stylesheet" type="text/css"
	href="${base}/js/easyUI/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${base}/js/easyUI/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${base}/js/easyUI/themes/demo.css">
<script type="text/javascript"
	src="${base}/js/easyUI/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="${base}/js/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${base}/jsp/user/user.js"></script>
</head>
<body id="b">
	<!-- <div style="margin: 20px 0;"></div> -->
	<div id="addDiv" class="easyui-dialog" closed="true" modal="true"
		data-options="title:'新增用户',buttons:[{text:'确定',handler:function(){submitButton('addDiv','addForm')}},{text:'关闭',handler:function(){$('#addDiv').dialog('close')}}]">
		<form id="addForm" action="<%=base%>/user/addUser" method="post">
			<table>
				
				<tr>
					<td>账号：</td>
					<td>
						<input name="userName" id="userName" />
					</td>
				</tr>
				<tr>
					<td>密码：</td>
					<td>
						<input name="password" id="password" />
					</td>
				</tr>
				<tr>
					<td>真实姓名：</td>
					<td>
						<input name="realName" id="realName" />
					</td>
				</tr>
				<tr>
					<td>状态：</td>
					<td>
						<select name="status" id="status" >
								<option value="1">启用</option>
								<option value="0">停用</option>
							</select>
							<!--<input name="status" id="status" />-->
					</td>
				</tr>
				<tr>
					<td>email：</td>
					<td>
						<input name="email" id="email" />
					</td>
				</tr>
				<tr>
					<td>手机号：</td>
					<td>
						<input name="telephone" id="telephone" />
					</td>
				</tr>
				<tr>
					<td>QQ：</td>
					<td>
						<input name="QQ" id="QQ" />
					</td>
				</tr>
				<tr>
					<td>角色：</td>
					<td>
						<select name="role" id="role" ></select>
					</td>
				</tr>
			</table>
			</form>
		</div>
		
		<div id="editDiv" class="easyui-dialog" closed="true" modal="true"
		data-options="title:'修改用户',buttons:[{text:'确定',handler:function(){submitButton('editDiv','editForm')}},{text:'关闭',handler:function(){$('#editDiv').dialog('close')}}]">
		<form id="editForm" action="<%=base%>/user/updateUser" method="post">
			<table>
				<input name="id" id="id" type="hidden"/>
				<tr>
					<td>账号：</td>
					<td>
						<input name="userName" id="userName" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td>真实姓名：</td>
					<td>
						<input name="realName" id="realName" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td>email：</td>
					<td>
						<input name="email" id="email" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td>手机号：</td>
					<td>
						<input name="telephone" id="telephone" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td>QQ：</td>
					<td>
						<input name="qq" id="qq" class="easyui-textbox"/>
					</td>
				</tr>
			</table>
			</form>
		</div>

	<div id="tb" style="padding: 3px">
		<span>用户名:</span> <input id="userName"	style="line-height: 26px; border: 1px solid #ccc"> 
		<!-- <span>状态:</span> <input id="productid" style="line-height: 26px; border: 1px solid #ccc">  -->
		<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">查询</a>
	</div>
	<table id="dg" title="数据集合" style="width: 100%; height: 80%"
		iconCls="icon-search">
	</table>

	<div id="operationDiv"></div>

	<script>
		 $(function(){
			 $('#dg').datagrid({
			        title: "列表",  
			        url: "<%=base%>/user/userList",
			        //pageSize:5,
			        pageList : [ 5, 10, 15, 20 ],//可以选择的分页集合  
		            nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取  
		            striped : true,//设置为true将交替显示行背景。  
		            collapsible : true,//显示可折叠按钮  
		            singleSelect: false, //允许选择多行  
	                selectOnCheck: true,//true勾选会选择行，false勾选不选择行, 1.3以后有此选项。重点在这里  
	                checkOnSelect: true, //true选择行勾选，false选择行不勾选, 1.3以后有此选项
			        columns: [  
			            [  
			                //{field: 'ck', checkbox: true, width: 180},  
			                //{field: 'id', title: '主键ID', width: 180, align: "center"},
			                {field: 'userName', title: '账号', width: 180, align: "center"}, 
			                {field: 'realName', title: '真实名称', width: 180, align: "center"},
			                {field: 'email', title: '邮箱', width: 180, align: 'center'},  
			                {field: 'telphone', title: '手机号', width: 180, align: 'center'}, 
			                {field: 'qq', title: 'qq', width: 180, align: "center"}  
			            ]  
			        ], toolbar: [  
			            {
			                text: '添加用户',  
			                iconCls: 'icon-add',
							handler: function () {
								prepareOptions('<%=base%>')
							}
			            },
			            '-',
			            {
			                text: '修改用户',
			                iconCls: 'icon-edit',
			                handler: function () {
			                	var selRow = $('#dg').datagrid('getSelected');
			                	if (selRow==undefined) {  
			        		        $.messager.alert("提示", "请选择数据！", "info");  
			        		        return; 
			                	}
			                	
 			                	$('#editDiv').form('load',{
 			                		id:selRow.id,
 			                		userName:selRow.userName,
 			                		realName:selRow.realName,
 			                		email:selRow.email,
 			                		telephone:selRow.telphone,
 			                		qq:selRow.qq	
			                    }); 
 			                	
 			                	//$('#realName').textbox('textbox').attr('readonly',true);
 			                	//$('#realName').attr('readonly',true);
			                	$('#editDiv').dialog('open'); 
			                }  
			            },
			            '-',
			            {
			                text: '修改角色',  
			                iconCls: 'icon-edit',
			                handler: function () {
			                	/* $('#w').window('open'); */
			                    /* openDialog("add_dialog", "edit");   */
			                    
/* 			                    var selRow = $('#dg').datagrid('getSelections');
			                    if (selRow.length>1) {  
			                        $.messager.alert("提示", "请选择一个用户！", "info");  
			                        return; 
			                    }
			                    var userid = selRow[0].id; */
			                    
			                	var selRow = $('#dg').datagrid('getSelected');
			                	if (selRow==undefined) {  
			        		        $.messager.alert("提示", "请选择数据！", "info");  
			        		        return; 
			                	}
			                	
			                	var userid = selRow.id;
			                    
 			                	dialogWindow("operationDiv", "<%=base%>/user/editRole?userid="+userid, "修改角色", "icon-edit",$('#dg'));
			                }  
			            },
			            '-',  
			            {
			                text: '删除用户',  
			                iconCls: 'icon-remove',
							handler: function () {
			                	deletedata('<%=base%>/user/delUser')
			                }  
			            }
			        ],
			        loadMsg : '数据装载中......',  
		            singleSelect:true,//为true时只能选择单行  
		            fitColumns:true,//允许表格自动缩放，以适应父容器  
		            //id : 'xh',//当数据表格初始化时以哪一列来排序  
		            //id : 'desc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
		            remoteSort : false,  
		             frozenColumns : [ [ {  
		                field : 'ck',  
		                checkbox : true  //多选框
		            } ] ],   
		            pagination : true,//分页  
		            rownumbers : true//行数  
			    });  
			  
			 	/* debugger */
			    //设置分页控件  
			    var p = $('#dg').datagrid('getPager');
			    p.pagination({
			        //pageSize: 5,//每页显示的记录条数，默认为10  
			        pageList: [5, 10, 15],//可以设置每页记录条数的列表  
			        beforePageText: '第',//页数文本框前显示的汉字  
			        afterPageText: '页    共 {pages} 页',  
			        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
			    });  
			    
			    $("input").keydown(function(e) {  
				     // 回车键事件  
				        if(e.which == 13) {  
				        	doSearch();
				        }  
				 });
		 });		
		 
		 
		 
	</script>
</body>
</html>