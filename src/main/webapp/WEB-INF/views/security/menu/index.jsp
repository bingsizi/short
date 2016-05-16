<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/common/base.jsp"%>
<%@ include file="/common/jqueryEasyUi.jsp"%>
<title>菜单管理</title>
<script type="text/javascript">
//获得选中行
function getSelected() {
	var selected = $('#menuGrid').datagrid('getSelected');
	return selected;
}
//查询
function searchForm(){
	var param = $('#searchForm').serialize();
	$('#menuGrid').datagrid({
		url : "${ctx}/security/menu/list?" + param
	});
}
//提交表单
function formSubmit(operation){
	//验证表达提交是否有问题
	if(!$('#menuForm').form('validate')){
		alert("表单提交出现错误");
		return null;
	}
	var url="";
	if(operation=='save')
	   url='${ctx}/security/menu/save';
	else if(operation='edit')
	   url='${ctx}/security/menu/update';
	//提交数据
	$.ajax({
		  url: url,
		  dataType: 'json',
		  type:'post',
		  data: $('#menuForm').serialize(),
		  success: function(data){
			  Popbox.topCenter(data.message);
			  if(data.success){
				  $("#menuGrid").datagrid("reload");
				  $('#menuDialog').dialog('close');
			  }
		  }
	});
}
//初始化数据
$(function(){
	$("#menuGrid").datagrid({
		url:'${ctx}/security/menu/list',
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
            {field:"id", title:"ID", width:10},
			{field:"name", title:"名称", width:10},
			{field:"type", title:"类型", width:10},
			{field:"indexUrl", title:"响应地址", width:10},
			{field:"permission", title:"权限",width:10},
			{field:"parentId", title:"上级菜单",width:10},
			{field:"parentIds", title:"所有上级",width:10},
			{field:"seq", title:"排序",width:10}
		]],
		toolbar : '#tb',
		footer:'#ft'
	});
});
// 新增
function add(){
	$("#menuDialog").dialog({modal:true}).dialog("open").dialog('setTitle','新增菜单');
 	$("#save").show();
 	$("#edit").hide();
 	$("#menuForm").form('clear');
 	var url = "${ctx}/security/menu/menuTree?random="+new Date();
 	$("#parentId").combotree({
 		onShowPanel:function(){
 			alert("我才不走");
 		}
 	})
}

//修改
function edit(){
	var selected = getSelected();
	if (selected) {
		$("#menuDialog").dialog({modal:true}).dialog("open").dialog('setTitle','修改菜单');
 		$("#save").hide();
 		$("#edit").show();
 		$('#menuId').attr("readonly","readonly");
		$("#menuForm").form('load', selected);
 	} else {
 		$.messager.alert('消息','请选择要修改的菜单!','warning');
	}
}
//删除
function del(){
	var selected = getSelected();
		if (!selected) {
			$.messager.alert('消息','请选择要删除的菜单!','warning');
			return ;
 	}
		$.messager.confirm('消息提醒', '确定删该菜单吗?此操作不可恢复!', function(r){
		if (r){
			//提交数据
			$.getJSON("${ctx}/security/menu/delete",{id:selected.id},function(json){
				if(json.success){
					var index = $('#menuGrid').datagrid('getRowIndex', selected);
					$('#menuGrid').datagrid('deleteRow', index);
				}
				Popbox.topCenter(json.message);
			});
		}
	});
}
</script>
</head>
<body style="margin: 0px;">
<table id="menuGrid"></table>
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
<!-- 增加/修改菜单框 -->
<div id="menuDialog" class="easyui-dialog" style="width:390px; height:400px; padding:10px 20px;" closed="true">
	<form  id="menuForm">
	    <input type="hidden" name="id"/>
		<table width="100%" cellpadding="0" cellspacing="0" class="oper_table">
			<tr>
				<td class="td_marked">菜单名称：</td>
				<td class="td_content">
				   <input id="title" type="text" name="name" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]'"/>
				</td>
			</tr>
			<tr>
				<td class="td_marked">菜单类型：</td>
				<td class="td_content">
				   <select name="type">
				       <option value="菜单">菜单</option>
				       <option value="功能权限">功能权限</option>
				   </select>
				</td>
			</tr>
			<tr>
				<td class="td_marked">上级菜单：</td>
				<td class="td_content">
			        <select name="parentId" class="easyui-combotree" style="width:200px;" data-options="url:'${ctx}/security/menu/menuTree',onShowPanel:function(){
			           $(this).combotree('reload','${ctx}/security/menu/menuTree')
			        }"></select>
				</td>
			</tr>
			<tr>
				<td class="td_marked">菜单URL：</td>
				<td class="td_content">
				   <input type="text" name="indexUrl" class="easyui-validatebox" data-options="validType:'length[0,50]'"/>
				</td>
			</tr>
			<tr>
				<td class="td_marked">菜单排序：</td>
				<td class="td_content">
				   <input type="text" name="seq" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]'"/>
				</td>
			</tr>
			<tr>
				<td class="td_marked">图标：</td>
				<td class="td_content">
				   <input type="text" name="icon" class="easyui-validatebox" data-options="validType:'length[0,20]'"/>
				</td>
			</tr>
			<tr>
				<td class="td_marked">权限：</td>
				<td class="td_content">
				   <input type="text" name="permission" class="easyui-validatebox" data-options="validType:'length[0,100]'"/>
				</td>
			</tr>
			<tr>
			   <td class="td_content" align="center" colspan="2">
				   <a href="#" id="save" onclick="formSubmit('save')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
				   <a href="#" id="edit" onclick="formSubmit('edit')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">更新</a>
				   <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#menuDialog').dialog('close');">取消</a>
			   </td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>