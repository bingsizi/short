<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/common/base.jsp"%>
<%@ include file="/common/jqueryEasyUi.jsp"%>
<title>在线管理</title>
<script type="text/javascript">
//初始化数据
$(function(){
	$("#onlineGrid").datagrid({
		url:'${ctx}/security/online/list',
		fitColumns:true,
		fit:true,
		nowrap:true,
		rownumbers:true,
		singleSelect:true,
		showFooter:true,
		pagination:true,
		border:false,
		pageSize:20,
		columns:[[
            {field:"userId", title:"用户ID", width:10},
			{field:"username", title:"用户名称", width:10},
			{field:"realName", title:"真实姓名", width:10},
			{field:"startTimestamp", title:"启动时间", width:10},
			{field:"lastAccessTime", title:"最后访问时间", width:10}
		]],
		toolbar : '#tb',
		footer:'#ft'
	});
});
</script>
</head>
<body style="margin: 0px;">
<table id="onlineGrid"></table>
<!-- toolbar -->
<div id="tb" style="padding:5px 5px;">
    <form id="searchForm">
		<span style="margin: 0px 2px">菜单名称：</span><input type="text" name="name" style="width: 100px;"/>
	    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchForm();">搜索</a>
	</form>
</div>
<!-- footer -->
<div id="ft" style="padding:2px 5px;">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add();">新增</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit();">修改</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="del();">删除</a>
</div>
</body>
</html>