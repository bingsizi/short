<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/common/jqueryEasyUi.jsp"%>
<title>登陆页面</title>
<script type="text/javascript">
//初始化数据
$(function(){
	$("#userGrid").datagrid({
		url : '${ctx}/user/list',
		fitColumns : true,
		border:false,
		fit : true,
		nowrap : false,
		rownumbers : true,
		singleSelect : true,
		showFooter : true,
		pageSize:20,
		pagination:true,
		columns : [[
			{field : "id", hidden : true},
			{field : "username", title : "用户名", width :10},
			{field : "password", title : "密码", width : 10}
		]],
		toolbar : '#tb',
		footer:'#ft'
	});
});
//搜索
function searchForm(){
	var param = $('#searchForm').serialize();
	$('#userGrid').datagrid({
		url : "${ctx}/user/list?" + param
	});
};
</script>
</head>
<body style="margin: 0px;">
<table id="userGrid"></table>
<!-- toolbar -->
<div id="tb" style="padding:5px 5px;">
    <form id="searchForm">
		<span style="margin: 0px 2px">用户名：</span><input type="text" name="username" style="width: 100px;"/>
	    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchForm();">搜索</a>
	</form>
</div>
<!-- footer -->
<div id="ft" style="padding:2px 5px;">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit();">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  onclick="removeI();">删除</a>
</div>
</body>
</html>