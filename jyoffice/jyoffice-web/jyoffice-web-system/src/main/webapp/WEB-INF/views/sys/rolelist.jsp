<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<div>
		<a href="javascript:void(0);" key="0" class="btn btn-default myeditoperator" role="button">创建角色</a>
		<span>${message}</span>
	</div>
	<p></p>
	<table class="table table-hover table-condensed table-bordered">
		<tr>
			<th>角色编码</th>
			<th>角色名称</th>
			<th>描述</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pager.resultList}" var="pl">
			<tr>
				<td>${pl.roleCode}</td>
				<td>${pl.roleName}</td>
				<td>${pl.roleDesc}</td>
				<td>${pl.createBy}</td>
				<td><fmt:formatDate value="${pl.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<a href="javascript:void(0);" key="${pl.id}" class="btn btn-default myeditoperator" role="button">编辑</a>
					<a href="javascript:void(0);" key="${pl.id}" class="btn btn-default mydelete" role="button">删除</a>
					<a href="${context}/sys/role/auth/${pl.id}"class="btn btn-default" role="button">分配权限</a>
					<a href="${context}/sys/role/userlist/${pl.id}" key="${pl.id}" class="btn btn-default roleuser" role="button">分配用户</a>
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
						if(result){window.location.href="${context}/sys/role/delete/"+id;} 
					}
				});
			});
			
			$(".myeditoperator").bind("click",function(){
				var id = $(this).attr("key");
				$.get("${context}/sys/role/edit/"+id, 
						function(result){
					opendialog({ title:"角色编辑",message: result },"_editform");
				}); 
			});
		});
	</script>
</body>
</html>
