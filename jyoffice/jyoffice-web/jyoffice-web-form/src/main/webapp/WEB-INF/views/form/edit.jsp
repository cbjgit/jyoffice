<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<form id="myfrom" action="${context}/form/save" method="post">
		<input type="hidden" name="id" value="${form.id}">
		<input type="hidden" name="status" value="${form.status}">
		<div class="form-group form-inline">
	    	<label for="formKey">表单编码：</label>
	    	<input type="text" class="form-control" name="formKey" id="formKey" value="${form.formKey}" placeholder="表单编码">
	  	</div>
	  	<div class="form-group form-inline">
	    	<label for="formName">表单名称：</label>
	    	<input type="text" class="form-control" name="formName" id="formName" value="${form.formName}" placeholder="表单名称" style="width:300px;">
	  	</div>
	  	<div class="form-group form-inline">
	    	<label for="processId">关联流程：</label>
	    	<select class="form-control" name="processId">
	    		<c:forEach items="${processlist}" var="p">
	    		<option value="${p.id}">${p.processName}</option>
	    		</c:forEach>
	    	</select>
	  	</div>
	  	<div class="form-group form-inline">
	    	<label for="processId">每行显示列数：</label>
	    	<input type="text" class="form-control" name="columnNumber" id="columnNumber" value="${form.columnNumber}" style="width:100px;">
	  	</div>
	  	<div class="form-group form-inline">
	    	<label for="version">版本：</label>
	    	<input type="text" class="form-control" name="version" id="version" value="${form.version}" style="width:100px;">
	  	</div>
	  	<div class="form-group">
	    	<label for="descption">描述</label>
	    	<textarea name="descption" class="form-control" style="width:100%">${form.descption}</textarea>
	  	</div>
	</form>
</body>
<%@ include file="/WEB-INF/views/footerjs.jsp"%>
<script type="text/javascript">
function validform() {
	return $("#myfrom").validate({
		rules : {
			formKey : {required : true,alphanumeric:true},
			formName : {required : true},
			version : {required : true,digits:true},
			columnNumber : {required : true,digits:true}
		}
	});
}
</script>
</html>
