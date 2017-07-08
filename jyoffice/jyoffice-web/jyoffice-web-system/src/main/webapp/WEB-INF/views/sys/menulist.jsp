<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<div>
		<a href="javascript:void(0);" key="0" parentId="${parentId}" class="btn btn-default myeditoperator" role="button">创建菜单</a>
		<span>${message}</span>
	</div>
	<p></p>
	<table class="table table-hover table-condensed table-bordered">
		<tr>
			<th>菜单编码</th>
			<th>菜单名称</th>
			<th>描述</th>
			<th>请求地址</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pager.resultList}" var="m">
			<tr>
				<td>${m.menuCode}</td>
				<td>${m.menuName}</td>
				<td>${m.menuDesc}</td>
				<td>${m.menuUrl}</td>
				<td>${m.createBy}</td>
				<td><fmt:formatDate value="${m.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<a href="javascript:void(0);" key="${m.id}" parentId="${m.parentId}" class="btn btn-default myeditoperator" role="button">编辑</a>
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
						if(result){window.location.href="${context}/sys/menu/delete/"+id;} 
					}
				});
			});
			
			$(".myeditoperator").bind("click",function(){
				var id = $(this).attr("key");
				var parentId = $(this).attr("parentId");
				$.get("${context}/sys/menu/edit/"+parentId+"/"+id, 
						function(result){
					opendialog({ title:"菜单编辑",message: result },submitMenu);
					
				}); 
			});
			

			var submitMenu = function(){
				var flag = false;
				if(validform().form()){
					$.ajax({
						  type: 'POST',
						  url: $("#_editform").attr("action"),
						  data: $('#_editform').serialize(),
						  dataType: "json",
						  async:false,
						  success: function(data){
							  if(data.state == "1"){
								  flag = true;
								  parent.refreshTree("${parentId}");
							  }
							  else{
								  bootbox.alert(data.message);
								  flag = false;
							  }
						  },
						  error:function(XMLHttpRequest, textStatus, errorThrown) {
							  bootbox.alert(errorThrown);
							  flag = false;
						  }
					});
				}
				return false;
			}
		});
	</script>
</body>
</html>
