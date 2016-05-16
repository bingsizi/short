<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/common/jqueryEasyUi.jsp"%>
<title>未授权</title>
</head>
<body>
	<h1>unauthor</h1>
	<button onclick="javascript:location.href='${ctx}/toLogin'">go login</button>
</body>
</html>