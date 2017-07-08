<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<form id="_editform" action="${context}/sys/auth/save" method="post">
		<input type="hidden" name="id" value="${auth.id}">
		<input type="hidden" name="menuId" value="${auth.menuId}">
	  	<div class="form-group">
	    	<label for="authId">权限标识</label>
	    	<input type="text" class="form-control" name="authId" id="authId" value="${auth.authId}" >
	  	</div>
	  	<div class="form-group">
	    	<label for="authName">权限名称</label>
	    	<input type="text" class="form-control" name="authName" id="authName" value="${auth.authName}" style="width:300px;">
	  	</div>
	  	<div class="form-group">
	    	<label for="authPath">请求地址</label>
	    	<textarea rows="4" style="width:400px" class="form-control" name="authPath" id="authPath" >${auth.authPath}</textarea>
	  	</div>
	</form>
</body>
<%@ include file="/WEB-INF/views/footerjs.jsp"%>
<script type="text/javascript">
function validform() {
	return $("#_editform").validate({
		rules : {
			authId : {required : true},
			authName : {required : true},
			authPath : {required : true}
		}
	});
}
</script>
</html>
