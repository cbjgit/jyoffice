<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
<style type="text/css">

</style>
</head>
<body>
	<c:forEach items="${list}" var="fm">
	<div style="width:100%"><a href="${context}/process/application/${fm.id}/-">${fm.formKey} ${fm.formName} V.${fm.version}</a></div>
	</c:forEach>
</body>
</html>