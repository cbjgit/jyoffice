<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<form id="_editform" action="${context}/sys/menu/save" method="post">
		<input type="hidden" name="id" value="${menu.id}">
		<input type="hidden" name="parentId" value="${menu.parentId}">
		<input type="hidden" name="isParent" value="${menu.isParent}">
	  	<div class="form-group">
	    	<label for="menuCode">菜单编码</label>
	    	<input type="text" class="form-control" name="menuCode" id="menuCode" value="${menu.menuCode}" >
	  	</div>
	  	<div class="form-group">
	    	<label for="menuName">菜单名称</label>
	    	<input type="text" class="form-control" name="menuName" id="menuName" value="${menu.menuName}" style="width:300px;">
	  	</div>
	  	<div class="form-group">
	    	<label for="menuUrl">请求地址</label>
	    	<input type="text" class="form-control" name="menuUrl" id="menuUrl" value="${menu.menuUrl}" style="width:300px;">
	  	</div>
	  	<div class="form-group">
	    	<label for="menuDesc">描述</label>
	    	<textarea id="descption" name="menuDesc" class="form-control" style="width:100%">${menu.menuDesc}</textarea>
	  	</div>
	</form>
</body>
<%@ include file="/WEB-INF/views/footerjs.jsp"%>
<script type="text/javascript">
function validform() {
	return $("#_editform").validate({
		rules : {
			menuCode : {required : true,alphanumeric:true},
			menuName : {required : true}
		}
	});
}
</script>
</html>
