<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/common/jqueryEasyUi.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/top.pup.js"></script>
<title>登陆页面</title>
<script type="text/javascript">
function submitForm(){
	var isValid = $("#submitForm").form('validate');
	if(isValid){
		$.ajax({
			  url: '${ctx}/login',
			  dataType: 'json',
			  type:'post',
			  data: $("#submitForm").serialize(),
			  success: function(data){
				  if(data.success){
		        		location.href="${ctx}/main";
		          }else{
		        		alert(data.message);
		          }
			  }
		});
	}else{
	    alert("用户名或密码不能为空");
	}
}
function clearForm(){
    $('#submitForm').form('clear');
}
$(function(){
    $('#win').window('open'); 
});
</script>
</head>
<body>
    <div id="win" class="easyui-window" title="登陆" data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,closable:false" style="width:400px;padding:10px;">
        <div style="padding:10px 60px 20px 60px">
	        <form id="submitForm">
	            <table cellpadding="5">
	                <tr>
	                    <td>用户名:</td>
	                    <td><input class="easyui-textbox" type="text" name="username" data-options="required:true"></input></td>
	                </tr>
	                <tr>
	                    <td>密码:</td>
	                    <td><input class="easyui-textbox" type="password" name="password" data-options="required:true,validType:'password'"></input></td>
	                </tr>
	                <tr>
	                    <td>记住我:</td>
	                    <td><input type="checkbox" value="true" name="remember"></td>
	                </tr>
	            </table>
	        </form>
	        <div style="text-align:center;padding:5px">
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清空</a>
	        </div>
        </div>
    </div>
</body>
</html>