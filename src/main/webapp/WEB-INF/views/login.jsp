<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/common/jqueryEasyUi.jsp"%>
<title>登陆页面</title>
<script type="text/javascript">
function submitForm(){
    $('#submitForm').form('submit',{
    	url:'${ctx}/login',
        onSubmit:function(){
            var flag =  $(this).form('enableValidation').form('validate');
            if(!flag){
            	alert("用户名或密码不能为空");
            }
            return flag;
        },
        success:function(data){
        	var obj = JSON.parse(data);
        	if(obj.success){
        		location.href="${ctx}/main";
        	}else{
        		alert(obj.message);
        	}
        }
    });
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
    <div id="win" class="easyui-window" title="登陆" data-options="modal:true" style="width:400px;padding:10px;">
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