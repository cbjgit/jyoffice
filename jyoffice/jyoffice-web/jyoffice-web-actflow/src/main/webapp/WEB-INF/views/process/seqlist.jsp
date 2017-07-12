<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<div>
		<c:if test="${empty actProcess.definitionId}">
		<a href="javascript:void(0);" key="0" class="btn btn-default myeditoperator" role="button">新增流向</a>
		</c:if>
		<a href="${context}/process/deployee/diagram/${processId}" target="_blank" class="btn btn-default">查看流程图</a>
		<span>${message}</span>
	</div>
	<p></p>
	<table class="table table-hover table-condensed table-bordered">
		<tr>
			<th>从环节</th>
			<th>到环节</th>
			<th>条件</th>
			<c:if test="${empty actProcess.definitionId}"><th>操作</th></c:if>
		</tr>
		<c:forEach items="${pager.resultList}" var="sq">
			<tr>
				<td>${sq.nodeName}[${sq.nodeId}]</td>
				<td>${sq.tonodeName}[${sq.toNodeId}]</td>
				<td>${sq.conditionExpression}</td>
				<c:if test="${empty actProcess.definitionId}">
				<td>
					<a href="javascript:void(0);" key="${sq.id}" class="btn btn-default myeditoperator" role="button">编辑</a>
					<a href="javascript:void(0);" key="${sq.id}" class="btn btn-default mydelete" role="button">删除</a>
				</td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</body>

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
						if(result){window.location.href="${context}/process/sequnece/delete/"+id;} 
					}
				});
			});
			
			$(".myeditoperator").bind("click",function(){
				var id = $(this).attr("key");
				$.get("${context}/process/sequnece/editseq/${processId}/"+id, 
						function(result){
					opendialog({ title:"编辑流向",message: result },"myfrom");
				}); 
			});
		});
	</script>
</html>
