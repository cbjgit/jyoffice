<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<div>
		<a href="javascript:void(0);" key="0" class="btn btn-default myeditoperator" role="button">创建用户</a>
		<span>${message}</span>
	</div>
	<p></p>
	<table class="table table-hover table-condensed table-bordered">
		<tr>
			<th>登录ID</th>
			<th>用户名称</th>
			<th>最后登录时间</th>
			<th>最后登录IP</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pager.resultList}" var="pl">
			<tr>
				<td>${pl.loginId}</td>
				<td>${pl.userName}</td>
				<td><fmt:formatDate value="${pl.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${pl.lastLoginIp}</td>
				<td>
					<a href="javascript:void(0);" key="${pl.id}" class="btn btn-default myeditoperator" role="button">编辑</a>
					<a href="javascript:void(0);" key="${pl.id}" class="btn btn-default mydelete" role="button">删除</a>
					<a href="javascript:void(0);" key="${pl.id}" class="btn btn-default roleuser" role="button">分配角色</a>
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
						if(result){window.location.href="${context}/sys/user/delete/"+id;} 
					}
				});
			});
			
			$(".myeditoperator").bind("click",function(){
				var id = $(this).attr("key");
				$.get("${context}/sys/user/edit/"+id, 
						function(result){
					opendialog({ title:"流程编辑",message: result },"_editform");
				}); 
			});
		});
	</script>
</body>
</html>
