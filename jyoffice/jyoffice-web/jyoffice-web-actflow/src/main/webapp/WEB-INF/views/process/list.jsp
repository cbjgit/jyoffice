<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<div>
		<a href="javascript:void(0);" key="0" class="btn btn-default myeditoperator" role="button">创建流程</a>
		<span>${message}</span>
	</div>
	<p></p>
	<table class="table table-hover table-condensed table-bordered">
		<tr>
			<th style="width:5%">流程编码</th>
			<th style="width:10%">流程名称</th>
			<th style="width:5%">版本</th>
			<th style="width:10%">部署信息</th>
			<th style="width:10%">部署时间</th>
			<th style="width:15%">描述</th>
			<th style="width:5%">状态</th>
			<th style="width:5%">创建人</th>
			<th style="width:10%">创建时间</th>
			<th style="width:25%">操作</th>
		</tr>
		<c:forEach items="${pager.resultList}" var="pl">
			<tr>
				<td>${pl.processKey}</td>
				<td>${pl.processName}</td>
				<td>${pl.version}</td>
				<td>${pl.deployId}/${pl.definitionId}</td>
				<td><fmt:formatDate value="${pl.deployeeDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${pl.descption}</td>
				<td>
					<c:if test="${pl.status == 1}">启用</c:if>
					<c:if test="${pl.status == 0}">停用</c:if>
				</td>
				<td>${pl.createBy}</td>
				<td><fmt:formatDate value="${pl.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<a href="${context}/process/deployee/diagram/${pl.id}" target="_blank" class="btn btn-default">流程图</a>
					<a href="${context}/process/deployee/xml/${pl.id}" target="_blank" class="btn btn-default">流程XML</a>
					<a href="${context}/process/definition/copy/${pl.id}" class="btn btn-default">复制</a>
					<c:if test="${empty pl.deployId}">
					<a href="${context}/process/deployee/deploy/${pl.id}" class="btn btn-default">部署</a>
					</c:if>
					
					<c:if test="${pl.status == 0 or pl.status == -1}">
					<a href="javascript:void(0);" key="${pl.id}" class="btn btn-default myeditoperator" role="button">编辑</a>
					<a href="javascript:void(0);" key="${pl.id}" class="btn btn-default mydelete" role="button">删除</a>
					<a href="${context}/process/definition/disenabled/${pl.id}/1" class="btn btn-default">启用</a>
					</c:if>
					<c:if test="${pl.status == 1}">
					<a href="${context}/process/definition/disenabled/${pl.id}/0" class="btn btn-default">停用</a>
					</c:if>
					
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
						if(result){window.location.href="${context}/process/definition/delete/"+id;} 
					}
				});
			});
			
			$(".myeditoperator").bind("click",function(){
				var id = $(this).attr("key");
				$.get("${context}/process/definition/edit/"+id, 
						function(result){
					opendialog({ title:"流程编辑",message: result },"_editform");
				}); 
			});
		});
	</script>
</body>
</html>
