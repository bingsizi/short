<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/common/base.jsp"%>
<%@ include file="/common/jqueryEasyUi.jsp"%>
<title>组织机构管理</title>
<script type="text/javascript">
//获得选中行
function getSelected() {
	var selected = $('#orgTree').treegrid('getSelected');
	return selected;
}
//提交表单
function formSubmit(operation){
	//验证表达提交是否有问题
	if(!$('#orgForm').form('validate')){
		alert("表单提交出现错误...请注意检查必填项");
		return null;
	}
	var url="";
	if(operation=='save')
	   url='${ctx}/security/org/save';
	else if(operation='edit')
	   url='${ctx}/security/org/update';
	//提交数据
	$.ajax({
		  url: url,
		  dataType: 'json',
		  border:false,
		  type:'post',
		  data: $('#orgForm').serialize(),
		  success: function(data){
			  Popbox.topCenter(data.message);
			  if(data.success){
				  var row = getSelected();
				  if(row && row.parentId!=null){
						$('#orgTree').treegrid('reload', row.parentId);
				  }else{
						$("#orgTree").treegrid('reload');
				  }
				  $('#orgDialog').dialog('close');
			  }
		  }
	});
}
//初始化数据
$(function(){
	$("#orgTree").treegrid({
		url : '${ctx}/security/org/list',
		idField : 'id',
		treeField : 'name',
		fit : true,
	    border:false,
		rownumbers : true,
		fitColumns : true,
		loadMsg:"正在加载数据...",
		autoRowHeight : false,
		toolbar : [{
			iconCls:'icon-collapse-all',
			text:'全部折叠',
			handler:function(){
				$('#orgTree').treegrid('collapseAll');
	        }
		},{
			iconCls:'icon-expand-all',
			text:'全部展开',
			handler:function(){
			    $('#orgTree').treegrid('expandAll');
			}
		},{
			iconCls : 'icon-reload',
			text : '刷新',
			handler : function() {
				var selected = getSelected();
				if(selected)
				    $("#orgTree").treegrid('reload',selected.id);
				else
					$("#orgTree").treegrid('reload');
			}
		},{
		 	text:'新增',
		 	iconCls:'icon-add',
		 	handler:function(){
		 		var row = getSelected();
			 	$("#orgDialog").dialog({modal:true}).dialog("open").dialog('setTitle','新增组织');
			 	$("#save").show();
			 	$("#edit").hide();
			 	$("#orgForm").form('clear');
			 	if(row){
				 	$("#parentName").html(row.name);
				 	$("#_parentId").val(row.id);
			 	}else{
			 		$("#parentName").html("无上级");
				 	$("#_parentId").val(null);
			 	}
			 	$("#sel").combobox('setValue','0');
		 	}
		},{
		 	text:'修改',
		 	iconCls:'icon-edit',
		 	handler:function(){
		 		var selected = getSelected();
		 		if (selected) {
		 			$("#orgDialog").dialog({modal:true}).dialog("open").dialog('setTitle','修改组织');
				 	$("#save").hide();
				 	$("#edit").show();
				 	var node = $("#orgTree").treegrid('getParent',selected.id);
				 	if(node==null)
				 	   $("#parentName").html("无上级");
				 	else
				 	   $("#parentName").html(node.name);
		 			$("#orgForm").form('load', selected);
		 			$("#_parentId").val(selected._parentId);
		 			$("#sel").combobox('setValue',selected.sel);
			 	} else {
			 		$.messager.alert('消息','请选择要修改的组织!','warning');
				}
		 	}
		} ,{
		 	text:'删除',
		 	iconCls:'icon-remove',
		 	handler:function(){
		 		var selected = getSelected();
		 		//判断是否选中了要删除的组织
		 		if (!selected) {
		 			$.messager.alert('消息','请选择要删除的组织!','warning');
		 			return ;
			 	}
		 		//判断组织下是否有子组织
		 		var nodes = $("#orgTree").treegrid('getChildren',selected.id);
		 		if(nodes.length>0){
		 			$.messager.alert('消息','请先删除子组织','warning');
		 			return ;
		 		}
		 		$.messager.confirm('消息提醒', '确定删该组织吗?此操作不可恢复!', function(r){
					if (r){
						//提交数据
						$.getJSON("${ctx}/security/org/delete",{id:selected.id},function(json){
							if(json.success){
								var node = $('#orgTree').treegrid('getParent', selected.id);
								if(node==null){
									$('#orgTree').treegrid('reload');
								}else{
									$('#orgTree').treegrid('reload',node.id);
								}
							}
							Popbox.topCenter(json.message);
						});
					}
				});
		 	}
		}],
		columns : [ [ {
			field : 'name',
			title : '组织名称',
			width : 50
		}, {
			field : 'createTime',
			title : '创建时间',
			width : 50
		}] ]
	});
});
</script>
</head>
<body style="margin: 0px;">
<!-- tree -->
<table id="orgTree"></table>
<!-- 增加/修改组织 -->
<div id="orgDialog" class="easyui-dialog" style="width:390px; height:320px; padding:10px 20px;" closed="true">
	<form id="orgForm">
	    <input type="hidden" name="id"/>
	    <input type="hidden" id="_parentId" name="parentId"/>
		<table width="100%" cellpadding="0" cellspacing="0" class="oper_table">
			<tr>
				<td class="td_marked">上级：</td>
				<td class="td_content">
				   <span id="parentName"></span>
				</td>
			</tr>
			<tr>
				<td class="td_marked">组织名称：</td>
				<td class="td_content">
				   <input type="text" name="name" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]'"/>
				</td>
			</tr>
			<tr>
			   <td class="td_content" align="center" colspan="2">
			        <a href="#" id="save" onclick="formSubmit('save')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
					<a href="#" id="edit" onclick="formSubmit('edit')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">更新</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#orgDialog').dialog('close');">取消</a>
			   </td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>