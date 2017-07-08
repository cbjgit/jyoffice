<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<form id="_editform" action="${context}/sys/user/save" method="post">
		<input type="hidden" name="id" value="${user.id}">
	  	<div class="form-group">
	    	<label for="loginId">登录ID</label>
	    	<input type="text" class="form-control" name="loginId" id="loginId" value="${user.loginId}" placeholder="登录ID">
	  	</div>
	  	<div class="form-group">
	    	<label for="password">登录密码</label>
	    	<input type="password" class="form-control" name="password" id="password">
	  	</div>
	  	<div class="form-group">
	    	<label for="confimpassword">确认登录密码</label>
	    	<input type="password" class="form-control" name="confimpassword" id="confimpassword">
	  	</div>
	  	<div class="form-group">
	    	<label for="userName">用户名称</label>
	    	<input type="text" class="form-control" name="userName" id="userName" value="${user.userName}" placeholder="用户名称" style="width:300px;">
	  	</div>
	</form>
</body>
<%@ include file="/WEB-INF/views/footerjs.jsp"%>
<script type="text/javascript">
function validform() {
	return $("#_editform").validate({
		rules : {
			loginId : {required : true,alphanumeric:true},
			password : {required : true,alphanumeric:true},
			confimpassword : {required : true,alphanumeric:true},
			userName : {required : true}
		}
	});
}
</script>
</html>
