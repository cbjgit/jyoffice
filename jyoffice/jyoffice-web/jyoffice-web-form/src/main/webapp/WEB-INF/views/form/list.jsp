<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<div>
		<form id="searchForm" action="${context}/form/list" method="post">
		<input type="hidden" id="page" name="page" value="${pager.countPage}">
		<input type="hidden" id="pagesize" name="pagesize" value="${pager.pageSize}">
		<input type="hidden" name="orderby">
		<table class="table-condensed">
			<tr>
				<td>表单编码：</td>
				<td><input type="text" name="ser_formKey" class="form-control" value="${pager.param.formKey}"></td>
				<td>表单名称：</td>
				<td><input type="text" name="ser_formName" class="form-control" value="${pager.param.formName}"></td>
				<td>
				
					<button type="submit" class="btn btn-primary">查 询</button>
					<a href="javascript:void(0);" key="0" class="btn btn-default myeditoperator" role="button">新建表单</a>
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div>
		<span>${message}</span>
	</div>
	<p></p>
	<table class="table table-hover table-condensed table-bordered">
		<tr>
			<th>表单编码</th>
			<th>表单名称</th>
			<th>每行显示列数</th>
			<th>版本</th>
			<th>状态</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pager.resultList}" var="fm">
			<tr>
				<td>${fm.formKey}</td>
				<td>${fm.formName}</td>
				<td>${fm.columnNumber}</td>
				<td>${fm.version}</td>
				<td>
					<c:choose>
						<c:when test="${fm.status == 1}">
							已发布
						</c:when>
						<c:otherwise>
							未发布
						</c:otherwise>
					</c:choose>
				</td>
				<td>${fm.createBy}</td>
				<td><fmt:formatDate value="${fm.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<c:choose>
						<c:when test="${fm.status == 0}">
							<a href="javascript:void(0);" key="${fm.id}" class="btn btn-default myeditoperator" role="button">编辑</a>
							<a href="javascript:void(0);" key="${fm.id}" class="btn btn-default mydelete" role="button">删除</a>
							<a href="${context}/form/element/list/${fm.id}" class="btn btn-default" role="button">设计</a>
							<a href="${context}/form/publish/${fm.id}" class="btn btn-default" role="button">发布</a>
						</c:when>
						<c:otherwise>
							<a href="${context}/form/cancelpublish/${fm.id}" class="btn btn-default" role="button">撤销发布</a>
						</c:otherwise>
					</c:choose>
					<a href="${context}/form/preview/${fm.id}/-" class="btn btn-default" role="button">预览</a>
					<a href="${context}/form/copy/${fm.id}" class="btn btn-default" role="button">复制</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	${pager.html}
	<%@ include file="/WEB-INF/views/footerjs.jsp"%>
	<script type="text/javascript">
		$(function(){
			$(".mydelete").bind("click",function(){
				var id = $(this).attr("key");
				bootbox.confirm({
					size: "small",
					title:"确认提示",
					message:"你确定要删除这条记录嘛?",
					callback:function(result){ 
						if(result){window.location.href="${context}/form/delete/"+id;} 
					}
				});
			});
			
			$(".myeditoperator").bind("click",function(){
				var id = $(this).attr("key");
				$.get("${context}/form/edit/"+id, 
						function(result){
					opendialog({ title:"编辑表单",message: result },"myfrom");
				}); 
			});
		});
	</script>
</body>
</html>
