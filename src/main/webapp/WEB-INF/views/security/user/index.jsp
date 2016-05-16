<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/common/base.jsp"%>
<%@ include file="/common/jqueryEasyUi.jsp"%>
<title>用户管理</title>
<script type="text/javascript">
//获得选中行
function getSelected() {
	var selected = $('#userGrid').datagrid('getSelected');
	return selected;
}
//查询
function searchForm(){
	var param=$('#searchForm').serialize();
	var row = $('#orgTree').tree('getSelected');
	if(row)
		param+="&orgId="+row.id;
	$('#userGrid').datagrid({
		url : "${ctx}/security/user/list?"+param
	});
};
//新增
function add(){
	$("#userDialog").dialog({modal:true}).dialog("open").dialog('setTitle','新增用户');
 	$("#save").show();
 	$("#edit").hide();
 	$("#submitForm").form('clear');
}
//修改
function edit(){
	var row = getSelected();
	if(row){
		$.getJSON("${ctx}/security/user/roleIds",{id:row.id},function(json){
			$("#userDialog").dialog({modal:true}).dialog("open").dialog('setTitle','修改用户');
		 	$("#save").hide();
		 	$("#edit").show();
		 	$("#submitForm").form('load', row);
            if(json!=null){
    		 	$("#roleIds").combobox('setValues',json);
            }
		});
	}else{
		$.messager.alert('消息','请选择要修改的数据!','warning');
	}
}
/**
 * 锁定/解锁用户
 */
function locked(){
	var row = getSelected();
	if(row){
		var msg = "";
		if(row.locked=="Y"){
			msg = "是否要解锁这个用户";
		}else{
			msg = "是否要锁定这个用户";
		}
		var flag = confirm(msg);
		if(flag){
			$.getJSON("${ctx}/security/user/locked",{id:row.id,lock:row.locked},function(json){
				if(json.success){
					 $("#userGrid").datagrid("reload");
				}
			});
		}
	}else{
		$.messager.alert('消息','请选择要修改的数据!','warning');
	}
}
//提交表单
function formSubmit(operation){
	//验证表达提交是否有问题
	if(!$('#submitForm').form('validate')){
		alert("表单提交出现错误");
		return null;
	}
	var url="";
	if(operation=='save')
	   url='${ctx}/security/user/save';
	else if(operation='edit')
	   url='${ctx}/security/user/update';
	//提交数据
	$.ajax({
		  url: url,
		  dataType: 'json',
		  type:'post',
		  data: $('#submitForm').serialize(),
		  success: function(data){
			  Popbox.topCenter(data.message);
			  if(data.success){
				  $("#userGrid").datagrid("reload");
				  $('#userDialog').dialog('close');
			  }
		  }
	});
}
$(function(){
	  $('#orgTree').tree({  
	    url : '${ctx}/security/user/orgTree',
		singleSelect : true,
		animate:true,
		lines:true,
		onClick:function(node){
			//清空from表单
			$("#realNameSearch").val("");
			$("#usernameSearch").val("");
			$('#userGrid').datagrid({
				url : "${ctx}/security/user/list?orgId="+node.id
			});
		}
	});
	$("#userGrid").datagrid({
		url:'${ctx}/security/user/list',
		fitColumns:true,
		fit:true,
		border:false,
		nowrap:true,
		rownumbers:true,
		singleSelect:true,
		showFooter:true,
		pagination:true,
		border:false,
		pageSize:20,
		columns:[[
	        {field:"username", title:"用户名", width:10},
			{field:"realName", title:"真实姓名", width:10},
			{field:"locked", title:"锁定状态", width:10,formatter:function(value){
				if(value=="N"){
					return "<span style=\"color:green;\">"+value+"</span>"
				}else if(value=="Y"){
					return "<span style=\"color:red;\">"+value+"</span>"
				}else{
					return value;
				}
			}},
			{field:"orgName", title:"组织机构", width:10},
			{field:"createTime", title:"创建日期", width:10}
		]],
		toolbar : '#tb',
		footer:'#ft'
	});
});
</script>
</head>
<body class="easyui-layout">
	<!-- 西 -->
	<div id="west" data-options="region:'west',split:true,border:false" style="width:180px;padding: 10px;">
       <ul id="orgTree"></ul>  
	</div>
	<!-- 中 -->
	<div data-options="region:'center',border:false">
	    <table id="userGrid"></table>
	</div>
	<!-- toolbar -->
	<div id="tb" style="padding:5px 5px;">
	    <form id="searchForm">
			<span style="margin: 0px 2px">用户名：</span><input id="usernameSearch" type="text" name="username" style="width: 100px;"/>
			<span style="margin: 0px 2px">真实姓名：</span><input id="realNameSearch" type="text" name="realName" style="width: 100px;"/>
		    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchForm();">搜索</a>
		</form>
	</div>
	<!-- footer -->
	<div id="ft" style="padding:2px 5px;">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add();">新增</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit();">修改</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="locked();">锁定/解锁</a>
	</div>
	<!-- 增加/修改用户-->
	<div id="userDialog" class="easyui-dialog" style="width:390px; height:400px; padding:10px 20px;" closed="true">
		<form  id="submitForm">
		    <input type="hidden" name="id"/>
			<table width="100%" cellpadding="0" cellspacing="0" class="oper_table">
				<tr>
					<td class="td_marked">用户名：</td>
					<td class="td_content">
					   <input type="text" name="username" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]'"/>
					</td>
				</tr>
				<tr>
					<td class="td_marked">密码：</td>
					<td class="td_content">
					   <input type="password" name="password" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]'"/>
					</td>
				</tr>
				<tr>
					<td class="td_marked">真实姓名：</td>
					<td class="td_content">
					    <input type="text" name="realName" class="easyui-validatebox" data-options="validType:'length[0,20]'"/>
					</td>
				</tr>
				<tr>
					<td class="td_marked">组织机构：</td>
					<td class="td_content">
					    <select id="orgId" name="orgId" class="easyui-combotree" style="width:200px;" data-options="url:'${ctx}/security/user/orgTree'"></select>
					</td>
				</tr>
				<tr>
					<td class="td_marked">角色：</td>
					<td class="td_content">
					     <select id="roleIds" name="roleIds" class="easyui-combobox" style="width:200px;" data-options="url:'${ctx}/security/user/roleList',valueField:'id',textField:'name',required:true,editable:false,multiple:true"></select>
					</td>
				</tr>
				<tr>
				   <td class="td_content" align="center" colspan="2">
				       <a href="#" id="save" onclick="formSubmit('save')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
				       <a href="#" id="edit" onclick="formSubmit('edit')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">更新</a>
				       <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#userDialog').dialog('close');">取消</a>
				   </td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>