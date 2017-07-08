<%@ page contentType="text/html;charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录</title>
<%request.setAttribute("context", request.getContextPath());%>
<link rel="stylesheet" type="text/css" href="${context}/css/login.css" />
</head>
<body>
	<div id="loginpanelwrap">

		<div class="loginheader">
			<div class="logintitle">
				<a href="#">见一办公系统登录</a>
			</div>
		</div>

		<form action="${context}/login" method="post">
		<div class="loginform">

			<div class="loginform_row">
				<label>账号:</label> <input type="text" class="loginform_input"
					name="loginId" value="chensheng"/>
			</div>
			<div class="loginform_row">
				<label>密码:</label> <input type="password" class="loginform_input"
					name="password" value="123456"/>
			</div>
			<div class="loginform_row">
				<input type="submit" class="loginform_submit" value="登录" />
			</div>
			<div class="clear"></div>
		</div>
		</form>

	</div>


</body>
</html>