<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<table class="table table-hover table-condensed table-bordered">
		<tr>
			<th>登录ID</th>
			<th>用户名称</th>
		</tr>
		<c:forEach items="${pager.resultList}" var="pl">
			<tr>
				<td><input name="_userId" type="checkbox" value="${pl.id }">${pl.loginId}</td>
				<td>${pl.userName}</td>
			</tr>
		</c:forEach>
	</table>
	
	<%@ include file="/WEB-INF/views/footerjs.jsp"%>
</body>
</html>
