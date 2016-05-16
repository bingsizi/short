<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/common/base.jsp"%>
<%@ include file="/common/jqueryEasyUi.jsp"%>
<title>角色管理</title>
<script type="text/javascript">
//获得选中行
function getSelected() {
	var selected = $('#roleGrid').datagrid('getSelected');
	return selected;
}
//提交表单
function formSubmit(operation){
	//验证表达提交是否有问题
	if(!$('#submitForm').form('validate')){
		alert("表单提交出现错误...");
		return null;
	}
	var url="";
	if(operation=='save')
	   url='${ctx}/security/role/save';
	else if(operation='edit')
	   url='${ctx}/security/role/update';
	//提交数据
	$.ajax({
		  url: url,
		  dataType: 'json',
		  type:'post',
		  data: $('#submitForm').serialize(),
		  success: function(data){
			  Popbox.topCenter(data.message);
			  if(data.success){
				  $("#roleGrid").datagrid("reload");
				  $('#roleDialog').dialog('close');
			  }
		  }
	});
}
//保存菜单
function saveMenu(){
	var row = getSelected();
	if(row){
		var nodes = $('#menuTree').tree('getChecked');
		var s = '';
		for(var i=0; i<nodes.length; i++){
			if (s != '') s += ',';
			s += nodes[i].id;
		}
		//提交数据
		$.ajax({
			  url: '${ctx}/security/role/saveRoleMenu',
			  dataType: 'json',
			  type:'post',
			  data: {id:row.id,menuIds:s},
			  success: function(data){
				  Popbox.topCenter(data.message);
				  if(data.success){
					  $("#menuDialog").dialog({modal:true}).dialog("close");
				  }
			  }
		});
	}else{
		$.messager.alert('消息','请选择一个角色','warning');
	}
}
//初始化数据
$(function(){
	$("#roleGrid").datagrid({
		url:'${ctx}/security/role/list',
		fitColumns:true,
		fit:true,
		border:false,
		nowrap:false,
		rownumbers:true,
		singleSelect:true,
		showFooter:true,
		columns:[[
			{field:"id", title:"ID",width:10},
			{field:"name", title:"角色标识",width:20},
			{field:"description", title:"描述",width:40}
		]],
	    footer:'#ft'
	});
});


//新增
function add(){
	$("#roleDialog").dialog({modal:true}).dialog("open").dialog('setTitle','新增角色');
 	$("#save").show();
 	$("#edit").hide();
 	$("#submitForm").form('clear');
 	$("#name").focus();
}

//修改
function edit(){
	var selected = getSelected();
	if (selected) {
		if(selected.roleType=="系统管理员")
        {
				$.messager.alert('消息','系统角色无法修改!','warning');
				return ;
        }
		$("#roleDialog").dialog({modal:true}).dialog("open").dialog('setTitle','修改角色');
	 	$("#save").hide();
	 	$("#edit").show();
		$("#submitForm").form('load', selected);
 	} else {
 		$.messager.alert('消息','请选择要修改的角色!','warning');
	}
}
//删除
function del(){
	var selected = getSelected();
	if (!selected) {
		$.messager.alert('消息','请选择要删除的角色!','warning');
		return ;
 	}
	if(selected.roleType=="系统管理员")
    {
		$.messager.alert('消息','系统管理员无法删除!','warning');
		return ;
    }
	$.messager.confirm('消息提醒', '确定删该角色吗?此操作不可恢复!', function(r){
		if (r){
			if(selected.roleType=="系统管理员"){
				$.messager.alert('消息','系统管理员无法删除','error');
				return ;
			}
			//提交数据
			$.getJSON("${ctx}/security/role/delete",{id:selected.id},function(json){
				if(json.success){
					var index = $('#roleGrid').datagrid('getRowIndex', selected);
					$('#roleGrid').datagrid('deleteRow', index);
				}
				Popbox.topCenter(json.message);
			});
		}
	});
}

//设置菜单
function setMenu(){
	var selected = getSelected();
	if(selected){
	    $('#menuTree').tree({  
	    	url:'${ctx}/security/role/menuList?id='+selected.id
	    });  
	    $("#menuDialog").dialog({modal:true}).dialog("open").dialog('setTitle','角色菜单配置');
	} else {
 		$.messager.alert('消息','请选择角色!','warning');
	}
}
</script>
</head>
<body style="margin: 0px;">
<table id="roleGrid"></table>
<!-- footer -->
<div id="ft" style="padding:2px 5px;">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add();">新增</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit();">修改</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="del();">删除</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-menu',plain:true" onclick="setMenu();">菜单</a>
</div>
<!-- 增加/修改角色框 -->
<div id="roleDialog" class="easyui-dialog" style="width:390px; height:260px;" closed="true">
	<form id="submitForm">
	    <input type="hidden" name="id"/>
		<table width="100%"  cellpadding="0" cellspacing="0" class="oper_table">
			<tr>
				<td class="td_marked">标识:</td>
				<td class="td_content">
				   <input id="name" type="text" name="name" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]'"/>
				   <br>
				   <span style="color:#d5d5d5;">(用拼音或者英文)</span>
				</td>
			</tr>
			<tr>
				<td class="td_marked">备注：</td>
				<td class="td_content">
				   <textarea name="description" cols="30" rows="4" data-options="validType:'length[1,200]'" maxlength="200"></textarea>
				</td>
			</tr>
			<tr>
			   <td class="td_content" colspan="2" align="center">
			        <a href="#" id="save" onclick="formSubmit('save')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
					<a href="#" id="edit" onclick="formSubmit('edit')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">更新</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#roleDialog').dialog('close');">取消</a>
			   </td>
			</tr>
		</table>
	</form>
</div>
<!-- 菜单配置框 -->
<div id="menuDialog" class="easyui-dialog" style="width:350px; height:400px; padding:10px 20px;" closed="true">
	<ul id="menuTree" class="easyui-tree" data-options="animate:true,checkbox:true,lines:true,cascadeCheck:false"></ul> 
	<br>
    <div class="buttonContent" style="text-align: center;">
		<a href="#" id="menuSave" onclick="saveMenu();" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#menuDialog').dialog('close');">关闭</a>
	</div> 
    <br>
</div>
</body>
</html>