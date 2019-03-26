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

<script type="text/javascript">

	$(function(){
		
		initOption();
		 // 回车键事件 
		 // 绑定键盘按下事件  
		 $("input").keydown(function(e) {  
		     // 回车键事件  
		        if(e.which == 13) {  
		        	doMSearch();
		        }  
		 });
		 

		
	});
	
	function initOption(){
	    $.ajax({
	    	type:"POST",
	    	url:"<%=base%>/file/getOption",
	    	error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            },
	    	success:function(data){
		    	 var info = '';	
				 var temp = eval(data);  
				 for (var i = 0; i < temp.length; i++) {  
				 	info += "<option value='" + temp[i].title + "'>" + temp[i].operName + "</option>";  
				  }  
				 
				 info+="<option value='Z005-1'>日本原料</option><option value='Z005-8'>韩国原料</option>";
				 $("#type").append(info);
				 var key = temp[0].title;
				 if(key!=null || key!=''){					 
					 $("#mcode").val(key.substring(key.length-1,key.length));
				 }else{
					 $("#mcode").val(1);
				 }
	    	}
	    });
	}
	
	//查询
	function doMSearch(){
		$('#dg').datagrid('load',{
			code: $('#code').val(),
			dscp: $('#dscp').val(),
			isUserCreated :$('#isUserCreated').val()
		});
	}
	
	function deleteMdata(url) {  
	    //返回选中多行  
	    var selRow = $('#dg').datagrid('getSelections');
	    
	    // 非自建不可删除
	    if (selRow[0].isUserCreated == undefined) {
	    	$.messager.alert("提示", "非自建不可删除", "info"); 
	    	return ;
	    }
	    
	    //判断是否选中行  
	    if (selRow.length==0) {  
	        $.messager.alert("提示", "请选择要删除的数据！", "info");  
	        return;  
	    }else{    
	    	
	        var temID="";  
	        //批量获取选中行的评估模板ID  
	        for (i = 0; i < selRow.length;i++) {  
	            if (temID =="") {  
	                temID = selRow[i].code;  
	            } else {  
	                temID = selRow[i].code + "," + temID;  
	            }                 
	        }  
	        $.messager.confirm('提示', '是否删除选中数据?', function (r) {  

	            if (!r) {  
	                return;  
	            }  
	            //提交  
	            $.ajax({  
	                type: "POST",  
	                async: false,  
	                url: url + "?idStr=" + temID,
	                data: temID,  
	                success: function (data) {  
	                        $('#dg').datagrid('clearSelections');  
	                        $.messager.alert("提示", "恭喜您，数据删除成功！", "info");
	                        $('#dg').datagrid('reload');  
	                },
	                error:function(data){
	                	$.messager.alert('通知',data);
	                }
	            });  
	        });  

	    }  
	}; 
	
	function changeType() {
		debugger;
		var text = $("#type option:selected").text();
		var str = $("#type option:selected").val();
/* 		if(text!='' && text != "日本原料"){
			$('#mcode').val(str.substring(str.length-1));
		} else if(text!='' && text != "韩国原料"){
			$('#mcode').val('5'+str.substring(str.length-1));
		} else{
			$('#mcode').val('57');
		} */
		
		if(text!='' && text == "日本原料"){
			$('#mcode').val('57');
		}else if(text!='' && text == "韩国原料"){
			$('#mcode').val('58');
		}else{
			$('#mcode').val(str.substring(str.length-1));
		}

/* 		switch(text) {
			case '原料':
				$("#mcode").val('5');
				break;
			case '日本原料':
				$("#mcode").val('57');
				break;
			case '包材':
				$("#mcode").val('4');
				break;
			case '成品':
				$("#mcode").val('1');
				break;
			case '料体':
				$("#mcode").val('6');
				break;
			case '检测方法':
				$("#mcode").val('7');
				break;
			case '项目文件':
				$("#mcode").val('8');
				break;
			case '宣称原料':
				$("#mcode").val('A');
				break;
				
		} */
	}
</script>
</head>

<body id="b">
	<!-- <div style="margin: 20px 0;"></div> -->
	<div id="addDiv" class="easyui-dialog" modal="true"
		data-options="title:'增加物料',closable:true,closed:true,buttons:[{text:'确定',handler:function(){submitButton('addDiv','addForm')}},{text:'关闭',handler:function(){$('#addDiv').dialog('close')}}]">
		<form id="addForm" action="<%=base%>/materiel/addMateriel" method="post">
			<table>
				<tr>
					<td>类型：</td>
					<td>
						<select name="type" id="type" onchange="changeType()">

						</select>
					</td>
				</tr>
				<tr>
					<td>物料编号：</td>
					<td>
						<input name="code" id="mcode" maxlength="8" value="" required="required" />
					</td>
				</tr>
				<tr>
					<td>描述：</td>
					<td>
						<input name="dscp" id="dscp" />
					</td>
				</tr>
				<tr>
					<td>物料组：</td>
					<td>
						<input name="grp" id="grp" />
					</td>
				</tr>
				<tr>
					<td>物料组描述：</td>
					<td>
						<input name="grpdesc" id="grpdesc" />
					</td>
				</tr>
				<tr>
					<td>状态：</td>
					<td>
						<select name="mark" id="mark" >
								<option value="">启用</option>
								<option value="X">停用</option>
						</select>
					</td>
				</tr>
				<tr>
				<td>应用到菜单：</td>
					<td>
						<input type="radio" id ="isAppFlag" name="isAppFlag" value="0" checked/> 否 <input type="radio" id ="isAppFlag" name="isAppFlag" value="1" /> 是 
					</td>
				</tr>
			</table>
			</form>
		</div>
		<div id="syncDiv" class="easyui-dialog" modal="true" closed="true" data-options="title:'同步物料'">
			<form action="<%=base%>/materiel/synchronizeMateriels" method="post">
				始: <input type="date" name="start" required="required" />
				<br />
				终: <input type="date" name="end" required="required" />
				<br />
				<input type="submit" />
			</form>
		</div>

	<div id="tb" style="padding: 3px">
		<span>物料号:</span> <input id="code" name="code" style="line-height: 26px; border: 1px solid #ccc"> 
		<span>物料描述:</span> <input id="dscp" name="dscp" style="line-height: 26px; border: 1px solid #ccc"> 
		<span>sap编码:</span>
		<select id="isUserCreated" name="isUserCreated" class="easyui-combobox" style="width:150px;" data-options="required:true">
	       <option value="">请选择</option>
	       <option value="0">是</option>
	       <option value="1">否</option>
		</select>
		<a href="#" class="easyui-linkbutton" plain="true" onclick="doMSearch()">查询</a>
		<%-- <button onclick="javascript:window.location.href='${base}/synchronizeSaps'">手动从sap同步物料</button> --%>
	</div>
					
	<table id="dg" title="数据集合" style="width: 100%; height: 80%" iconCls="icon-search">
	</table>

	<script>
		 $(function(){
			// sap列表
			
			initTable();
			displayButton();
			hideButton();  
			 	/* debugger */

		 });
		 
		 
		 function initTable(){
			 $('#dg').datagrid({
			        title: "列表",  
			        url: "<%=base%>/materiel/materielList",
			        pageList : [ 100, 80, 50 ],//可以选择的分页集合  
		            nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取  
		            striped : true,//设置为true将交替显示行背景。  
		            collapsible : true,//显示可折叠按钮  
			        columns: [  
			            [  
			                {field: 'code', title: '物料编码', width: 10, align: "center"},
			                {field: 'type', title: '类型', width: 10, align: "center"},
			                {field: 'dscp', title: '描述', width: 50, align: "center"},
			                {field: 'grp', title: '物料组', width: 10, align: "center"},
			                {field: 'grpdesc', title: '物料组描述', width: 20, align: "center"},
			                {field: 'mark', title: 'X表示停用', width: 10, align: "center"},
			                {field: 'isUserCreated', title: 'sap编码(是/否)', width: 20, align: "center", formatter: function(value) {
		                		if (value === '0') {
		                			return '<font color="green">是</font>';
		                		}
		                		return '<font color="red">否</font>';
		                		}
			                }
			            ]  
			        ], toolbar: [  
		            {  
		            	id:"materialAdd",
		                text: '添加',  
		                iconCls: 'icon-add',  
		                handler: function () {
		                	$('#addDiv').dialog('open');
		                }  
		            },  
		            '-',
		            {
		            	id:"materialDel",
		                text: '删除',  
		                iconCls: 'icon-remove',
		                handler: function () {
		                	deleteMdata('<%=base%>/materiel/delMateriel')
		                }  
		            },  
		            '-',
		            {
		            	id:"materialSynchro",
		                text: '选择时间范围同步物料',  
		                iconCls: 'icon-edit',
		                handler: function () {
		                	$('#syncDiv').dialog('open');
		                }  
		            }
		        ],
			        loadMsg : '数据装载中......',  
		            singleSelect:true,//为true时只能选择单行  
		            fitColumns:true,//允许表格自动缩放，以适应父容器  
		            remoteSort : false,  
		             frozenColumns : [ [ {  
		                field : 'ck',  
		                checkbox : true  //多选框
		            } ] ],   
		            pagination : true,//分页  
		            rownumbers : true//行数  
			    });  
			 
			    //设置分页控件  
			    var p = $('#dg').datagrid('getPager');
			    p.pagination({
			        pageList: [100, 80, 50],//可以设置每页记录条数的列表  
			        beforePageText: '第',//页数文本框前显示的汉字  
			        afterPageText: '页    共 {pages} 页',  
			        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
			    });    
		 }
		 
 		 function hideButton(){
			//获取所有的toolbar按钮
			    var button=$('div.datagrid div.datagrid-toolbar a'); 
			    for (var i = 0; i < button.length; i++) {
			        var toolbar = button[i];
			        var id = toolbar.id;
			        if (id == "materialAdd") {  //隐藏Id为add的按钮
			            $('div.datagrid div.datagrid-toolbar a').eq(i).hide();
			            $('div.datagrid div.datagrid-toolbar div').eq(i).hide();
			        }
			        if (id == "materialDel") {  //不隐藏id为delete的按钮
			            $('div.datagrid div.datagrid-toolbar a').eq(i).hide();
			            $('div.datagrid div.datagrid-toolbar div').eq(i).hide();
			        }
			        if (id == "materialSynchro") {  //不隐藏id为同步的按钮
			            $('div.datagrid div.datagrid-toolbar a').eq(i).hide();
			            $('div.datagrid div.datagrid-toolbar div').eq(i).hide();
			        }

			    }
		 } 
 		 
 		 
 		 function displayButton(){
  			$.ajax({     
 	            type: "post", //要用post方式       
 	            async: true,//方法所在页面和方法名      
 	            url: "<%=base%>/materiel/getMaterialOperate", 
 	            contentType : 'application/x-www-form-urlencoded',
 	            dataType: "json",     
 	            success: function(data) {  
 	            	var edata = eval(data);
 	            	var button=$('div.datagrid div.datagrid-toolbar a'); 
					for(var i in edata){
					   //alert(edata[i].operName);
					    
					    for (var j = 0; j < button.length; j++) {
					        var toolbar = button[j];
					        var id = toolbar.id;
					        if (id == edata[i].operName) {  //隐藏Id为add的按钮
					            $('div.datagrid div.datagrid-toolbar a').eq(j).show();
					            $('div.datagrid div.datagrid-toolbar div').eq(j).show();
					        }
					    }
 	            	}
 	            },     
 	            error: function(err) {     
 	                alert("后台操作出错,请联系管理员...");     
 	            }     
 	        });
 		 }
		 
		 
/* 		 debugger; */
	        //隐藏第一个按钮
/* 	        $("div.datagrid-toolbar [id ='materialAdd']").eq(0).hide()
	        //隐藏第一条分隔线
	        $('div.datagrid-toolbar div').eq(0).hide();
	        
	        //隐藏第一个按钮
	        $("div.datagrid-toolbar [id ='materialDel']").eq(0).hide()
	        //隐藏第一条分隔线
	        $('div.datagrid-toolbar div').eq(1).hide();
	        
	        //隐藏第一个按钮
	        $("div.datagrid-toolbar [id ='materialSynchro']").eq(0).hide()
	        //隐藏第一条分隔线
	        $('div.datagrid-toolbar div').eq(2).hide(); */
	        
	        
/* 	        $("#materialAdd").hide();
	        
	        $("#materialDel").hide();
	        
	        $("#materialSynchro").hide(); */
		 
	</script>
</body>
</html>