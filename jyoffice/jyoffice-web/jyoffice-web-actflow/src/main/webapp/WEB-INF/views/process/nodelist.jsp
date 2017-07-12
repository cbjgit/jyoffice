<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<div>
		<c:if test="${empty actProcess.definitionId}">
		<a href="javascript:void(0);" key="0" class="btn btn-default myeditoperator" role="button">新增环节</a>
		</c:if>
		<span>${message}</span>
	</div>
	<p></p>
	<table class="table table-hover table-condensed table-bordered">
		<tr>
			<th>环节编码</th>
			<th>环节名称</th>
			<th>环节类型</th>
			<th>是否会签</th>
			<th>会签方式</th>
			<th>会签完成条件</th>
			<th>任务分配类型</th>
			<th>分配人</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pager.resultList}" var="pl">
			<tr>
				<td>${pl.nodeId}</td>
				<td>${pl.nodeNames}</td>
				<td>
					<c:choose>
						<c:when test="${pl.nodeTypes eq 'userTask'}">用户任务</c:when>
						<c:when test="${pl.nodeTypes eq 'exclusiveGateway'}">并行分支</c:when>
						<c:when test="${pl.nodeTypes eq 'parallelGateway'}">单行分支</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${pl.multi eq 0}">否</c:when>
						<c:when test="${pl.multi eq 1}">是</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${pl.multiType eq 0}">并行</c:when>
						<c:when test="${pl.multiType eq 1}">串行</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${pl.completionCondition eq 1}">全部同意</c:when>
						<c:when test="${pl.completionCondition eq 2}">同意占百分比？</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${pl.assigneeType eq 1}">固定处理人</c:when>
						<c:when test="${pl.assigneeType eq 2}">用户选择</c:when>
					</c:choose>
				</td>
				<td>${pl.assgnee}</td>
				<td>
					<c:if test="${empty actProcess.definitionId}">
					<a href="javascript:void(0);" key="${pl.id}" class="btn btn-default myeditoperator" role="button">编辑</a>
					<a href="javascript:void(0);" key="${pl.id}" class="btn btn-default mydelete" role="button">删除</a>
					</c:if>
					<a href="javascript:void(0);" key="${pl.id}" class="btn btn-default myviewoperator" role="button">查看</a>
				</td>
			</tr>
		</c:forEach>
	</table>

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
						if(result){window.location.href="${context}/process/node/delete/"+id;} 
					}
				});
			});
			
			$(".myeditoperator").bind("click",function(){
				var id = $(this).attr("key");
				$.get("${context}/process/node/editnode/${processId}/"+id, 
						function(result){
					opendialog({ title:"编辑节点",size: "large",message: result },"myfrom");
				}); 
			});
			
			$(".myviewoperator").bind("click",function(){
				var id = $(this).attr("key");
				$.get("${context}/process/node/editnode/${processId}/"+id+"?view=true", 
						function(result){
					opendialogview({ title:"查看节点",size: "large",message: result });
				}); 
			});
			
		});
	</script>
</body>	
</html>
