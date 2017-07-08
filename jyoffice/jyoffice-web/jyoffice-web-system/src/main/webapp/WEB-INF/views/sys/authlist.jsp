<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<div>
		<a href="javascript:void(0);" key="0" menuId="${menuId}" class="btn btn-default myeditoperator" role="button">创建权限</a>
		<span>${message}</span>
	</div>
	<p></p>
	<table class="table table-hover table-condensed table-bordered">
		<tr>
			<th>权限标识</th>
			<th>权限名称</th>
			<th>请求地址</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pager.resultList}" var="m">
			<tr>
				<td>${m.authId}</td>
				<td>${m.authName}</td>
				<td>${m.authPath}</td>
				<td>${m.createBy}</td>
				<td><fmt:formatDate value="${m.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<a href="javascript:void(0);" key="${m.id}" menuId="${m.menuId}" class="btn btn-default myeditoperator" role="button">编辑</a>
					<a href="javascript:void(0);" key="${m.id}" class="btn btn-default mydelete" role="button">删除</a>
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
						if(result){window.location.href="${context}/sys/auth/delete/"+id;} 
					}
				});
			});
			
			$(".myeditoperator").bind("click",function(){
				var id = $(this).attr("key");
				var menuId = $(this).attr("menuId");
				$.get("${context}/sys/auth/edit/"+menuId+"/"+id, 
						function(result){
					opendialog({ title:"权限编辑",message: result },"_editform");
					
				}); 
			});
			
		});
	</script>
</body>
</html>
