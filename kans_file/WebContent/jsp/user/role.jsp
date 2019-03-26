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
	<div id="addDiv" class="easyui-dialog" modal="true"
		data-options="title:'增加角色',closable:true,closed:true,buttons:[{text:'确定',handler:function(){submitButton('addDiv','addForm')}},{text:'关闭',handler:function(){$('#addDiv').dialog('close')}}]">
		<form id="addForm" action="<%=base%>/role/saveRole" method="post">
			<table>
				<input type="hidden" name="id" id="id"/>
				<tr>
					<td>角色名称：</td>
					<td>
						<input name="roleName" id="roleName" />
					</td>
				</tr>
<!--
				<tr>
					<td>状态：</td>
					<td>
						<select name="status" id="status" >
								<option value="1">启用</option>
								<option value="0">停用</option>
							</select>
							<input name="status" id="status" />
					</td>
				</tr>
				-->
				<tr>
					<td>备注：</td>
					<td>
						<input name="mark" id="mark" />
					</td>
				</tr>
			</table>
			</form>
		</div>

	<table id="dg" title="数据集合" style="width: 100%; height: 80%" iconCls="icon-search">

	</table>


	<div id="roleDiv"></div>

	<script>
		 $(function(){
			 $('#dg').datagrid({
			        title: "列表",  
			        url: "<%=base%>/role/roleList",
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
			                {field: 'id', title: '主键ID', width: 180, hidden:'true',align: "center"},
			                {field: 'roleName', title: '角色名称', width: 180, align: "center"},
			                {field: 'status', title: '状态', width: 180, hidden:'true',align: 'center',formatter : function(value,row,index){
			                    if(value=='1'){
			                    		return '启用'
			                    	}else{
			                    		return '停用'
			                    	}                        
			                  }
			                },
			                {field: 'mark', title: '备注', width: 180, align: "center"}
			            ]  
			        ], toolbar: [  
			            {  
			                text: '添加',  
			                iconCls: 'icon-add',  
			                handler: function () {
			                	$('#addForm').form('clear');
			                	$('#addDiv').dialog('open');
			                }  
			            },  
			            '-',
			            {
			                text: '修改',
			                iconCls: 'icon-edit',
			                handler: function () {
			                	var selRow = $('#dg').datagrid('getSelected');
			                	if (selRow==undefined) {  
			        		        $.messager.alert("提示", "请选择数据！", "info");  
			        		        return; 
			                	}
			                	
			                	$('#addForm').form('clear');
			                	
			                	
 			                	$('#addDiv').form('load',{
 			                		id:selRow.id,
 			                		roleName:selRow.roleName,
 			                		mark:selRow.mark	
			                    }); 
 			                	
			                	$('#addDiv').dialog('open'); 
			                }  
			            },
			            {
			                text: '管理角色权限',
			                iconCls: 'icon-edit',
			                handler: function () {
			                	var selRow = $('#dg').datagrid('getSelections');
			                	if (selRow.length == 0){
			                		$.messager.alert("提示", "请选择角色！", "info");
			                		return ;
			                	}
			                    var roleId = selRow[0].id;
					        	window.location.href="<%=base%>/role/selectRole?roleId="+roleId;
			                }  
			            },
			            '-',  
			            {
			                text: '删除',  
			                iconCls: 'icon-remove',
			                handler: function () {
			                	deletedata('<%=base%>/role/delRole')
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
		 });
		 
	</script>
</body>
</html>