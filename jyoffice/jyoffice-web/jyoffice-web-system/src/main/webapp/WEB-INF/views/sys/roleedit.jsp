<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<form id="_editform" action="${context}/sys/role/save" method="post">
		<input type="hidden" name="id" value="${role.id}">
	  	<div class="form-group">
	    	<label for="roleCode">角色编码</label>
	    	<input type="text" class="form-control" name="roleCode" id="roleCode" value="${role.roleCode}" placeholder="角色编码">
	  	</div>
	  	<div class="form-group">
	    	<label for="roleName">角色名称</label>
	    	<input type="text" class="form-control" name="roleName" id="roleName" value="${role.roleName}" placeholder="角色名称" style="width:300px;">
	  	</div>
	  	<div class="form-group">
	    	<label for="roleDesc">描述</label>
	    	<textarea id="descption" name="roleDesc" class="form-control" style="width:100%">${role.roleDesc}</textarea>
	  	</div>
	</form>
</body>
<%@ include file="/WEB-INF/views/footerjs.jsp"%>
<script type="text/javascript">
function validform() {
	return $("#_editform").validate({
		rules : {
			roleCode : {required : true,alphanumeric:true},
			roleName : {required : true}
		}
	});
}
</script>
</html>
