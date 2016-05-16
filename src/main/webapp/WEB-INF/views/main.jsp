<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/common/base.jsp"%>
<%@ include file="/common/jqueryEasyUi.jsp"%>
<title>首页面</title>
<style type="text/css">
.toolBar{
	float:right;
	margin-right:20px;
	margin-top: 15px;
}
</style>
</head>
<body class="easyui-layout">
    <!-- 北 -->
	<div data-options="region:'north',border:false" style="height:50px;background:#B3DFDA;padding:10px">
		<img src="${ctx}/static/images/logo.png" height="100%"/>
		<div class="toolBar">
		    [${userInSession.username}]&nbsp;&nbsp;<a href="#">修改密码</a>&nbsp;&nbsp;<a href="${ctx}/logout">退出系统</a>
		</div>
	</div>
	<!-- 西 -->
	<div id="west" data-options="region:'west',split:true" style="width:180px;">
        <c:out value="${menuHtml}" escapeXml="false"></c:out>
	</div>
	<!-- 南 -->
	<div data-options="region:'south',border:true" style="height:25px; line-height:22px; background-color: #B3DFDA; color: #333333; text-align: center;">
		&copy; 河北新龙信息技术有限公司&nbsp;&nbsp;
	</div>
	<!-- 中 -->
	<div id="content" data-options="region:'center',title:'首页',iconCls:'icon-ok'" style="overflow: hidden;">
	    <!-- 工作区 -->
		<iframe id="workFrame" scrolling="auto" frameborder="0" style="width: 100%; height:100%;"></iframe>
	</div>
</body>
<script type="text/javascript">
   $(function(){
		$("*[type='menuItem']").click(function() {
		    var node = {
	    		     name: $(this).attr("name"),
	    		     icon:$(this).attr("icon"),
	    		     url:$(this).attr("url")
	    		   };
		    if(node.name){
		    	goUrl(node);
		    }
		});
		
       function goUrl(node){
	       	var url = node.url;
       		if(url&&url!=null&&url!=""){
           		$("#content").panel({
           			title:node.name,
           			iconCls:node.icon
           		});
       			url = "${ctx}"+url;
       			$("#workFrame").attr('src', url);
       		}else{
       			alert("["+node.name+"]功能未实现");
       		}
       }
   });
</script>
</html>