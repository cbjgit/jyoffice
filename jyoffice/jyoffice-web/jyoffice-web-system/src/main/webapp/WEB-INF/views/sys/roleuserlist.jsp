<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<div>
		<a href="javascript:void(0);" key="0" class="btn btn-default" id="myeditoperator" role="button">添加用户</a>
		<a href="${context}/sys/role/list" class="btn btn-default" role="button">返回</a>
		<span>${message}</span>
	</div>
	<p></p>
	<table class="table table-hover table-condensed table-bordered">
		<tr>
			<th>用户名称</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pager.resultList}" var="pl">
			<tr>
				<td>${pl.userName}</td>
				<td>${pl.createBy}</td>
				<td><fmt:formatDate value="${pl.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<a href="javascript:void(0);" key="${pl.id}" class="btn btn-default mydelete" role="button">删除</a>
				</td>
			</tr>
		</c:forEach>
		
		<form action="" name="roleuserFrm" method="post">
		<input type="hidden" name="userIds" id="userIds">
		<input type="hidden" name="roleId">
		</form>
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
			
			$("#myeditoperator").bind("click",function(){
				var id = $(this).attr("key");
				$.get("${context}/sys/user/commonlist", 
						function(result){
					openUserDialog({ title:"添加用户",message: result },false,setUser);
				}); 
			});
			
			var setUser=function(val){
				
			}
		});
		
	</script>
</body>
</html>
