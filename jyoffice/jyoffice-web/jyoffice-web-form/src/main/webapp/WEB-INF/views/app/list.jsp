<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<div>
		<form id="searchForm" action="${context}/process/application/list" method="post">
		<input type="hidden" id="page" name="page" value="${pager.currentPage}">
		<input type="hidden" id="pagesize" name="pagesize" value="${pager.pageSize}">
		<input type="hidden" name="orderby">
		<table class="table-condensed">
			<tr>
				<td>表单名称：</td><td><input type="text" name="ser_formName" class="form-control" value="${pager.param.formName}"></td>
				<td>发起日期：</td>
				<td class="form-inline">
					<input type="text" name="ser_createSDate" class="form-control" value="${pager.param.createSDate}" datetype="date" data-date-format="yyyy-mm-dd" style="width:100px;">
					<input type="text" name="ser_createEDate" class="form-control" value="${pager.param.createEDate}" datetype="date" data-date-format="yyyy-mm-dd" style="width:100px;">
				</td>
				<td><button type="submit" class="btn btn-primary">查 询</button></td>
			</tr>
		</table>
		</form>
	</div>
	<table class="table table-hover table-condensed table-bordered">
		<tr>
			<th>表单名称</th>
			<th>发起人</th>
			<th>发起日期</th>
			<th>当前环节</th>
			<th>最近更新时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pager.resultList}" var="pl">
			<tr>
				<td>${pl.formName}</td>
				<td>${pl.createBy}</td>
				<td><fmt:formatDate value="${pl.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${pl.statusText}</td>
				<td><fmt:formatDate value="${pl.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
				<td>
					<c:if test="${pl.submit eq 0}">
					<a href="${context}/process/application/${pl.formId}/${pl.id}" class="btn btn-default">编辑</a>
					</c:if>
					<c:if test="${pl.submit eq 1}">
					<a href="${context}/flowtask/withdraw/${pl.instanceId}" class="btn btn-default">撤回</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	${pager.html}
	<%@ include file="/WEB-INF/views/footerjs.jsp"%>
	<script src="${context}/js/initdate.js" charset="UTF-8"></script>
</body>
</html>
