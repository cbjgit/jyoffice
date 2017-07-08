<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<form id="_editform" action="${context}/process/definition/save" method="post">
		<input type="hidden" name="id" value="${process.id}">
		<input type="hidden" name="status" value="${process.status}">
	  	<div class="form-group">
	    	<label for="processKey">流程编码</label>
	    	<input type="text" class="form-control" name="processKey" id="processKey" value="${process.processKey}" placeholder="流程编码">
	  	</div>
	  	<div class="form-group">
	    	<label for="processName">流程名称</label>
	    	<input type="text" class="form-control" name="processName" id="processName" value="${process.processName}" placeholder="流程名称" style="width:300px;">
	  	</div>
	  	<div class="form-group">
	    	<label for="descption">描述</label>
	    	<textarea id="descption" name="descption" class="form-control" style="width:100%">${process.descption}</textarea>
	  	</div>
	</form>
</body>
<%@ include file="/WEB-INF/views/footerjs.jsp"%>
<script type="text/javascript">
function validform() {
	return $("#_editform").validate({
		rules : {
			processKey : {required : true,alphanumeric:true},
			processName : {required : true}
		}
	});
}
</script>
</html>
