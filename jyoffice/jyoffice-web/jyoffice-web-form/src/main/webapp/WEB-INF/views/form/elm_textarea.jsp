<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<span>${message}</span>
	<form id="myfrom" action="${context}/form/element/save/${frmForm.id}/${elmId}/${fieldtype}" method="post" data-parsley-validate="" class="form-inline">	
		<table class="formtable">
	    	<tr>
	    		<td>字段名称：</td>
	    		<td><input type="text" class="form-control" name="fieldName" value="${element.fieldName}" maxlength="50"></td>
	    		<td>字段标题：</td>
	    		<td><input type="text" class="form-control" name="fieldTitle" value="${element.fieldTitle}"  style="width:300px;"  maxlength="50"></td>
	    	</tr>
	  		<tr>
		    	<td>默认值：</td>
		    	<td><input type="text" class="form-control" name="defaultValue" value="${element.defaultValue}" maxlength="30"></td>
		    	<td>显示顺序：</td>
		    	<td>
		    		<input type="text" class="form-control" name="seq" value="${element.seq}" style="width:60px">
		    		&nbsp;&nbsp;占位列数：
		    		<select id="columnNumber" name="columnNumber" class="form-control">
		    			<c:forEach begin="1" end="${frmForm.columnNumber}" var="cn"><option value="${cn}">${cn}</option></c:forEach>
		    		</select>
		    	</td>
		  	</tr>
		  	<tr>
		  		<td>是否必填：</td>
		    	<td>
		    	<input type="checkbox" class="form-control" id="required" name="required" value="1">是
		    	</td>
		    	<td>控件样式：</td>
		    	<td>width：<input type="text" class="form-control" id="width" name="width" style="width:60px">px
		    	&nbsp;&nbsp;height：<input type="text" class="form-control" id="height" name="height" style="width:60px">px</td>
		  	</tr>
		  	<tr>
		  		<td>备注：</td>
		    	<td colspan="3">
		    	<input type="text" class="form-control" id="fieldDescption" name="fieldDescption" style="width:600px" value="${element.fieldDescption}" maxlength="100">
		    	</td>
		  	</tr>
	  	</table>
	</form>
</body>
<%@ include file="/WEB-INF/views/footerjs.jsp"%>
<script type="text/javascript">
	$(function(){
		if('${element.required}' == '1'){
			$("#required").attr("checked",true);
		}
		$("#columnNumber").val('${element.columnNumber}');
		var oparam = ${element.otherParam};
		if(oparam){
			$("#width").val(oparam.width);
			$("#height").val(oparam.height);
		}
	});
	
	function validform() {
		return $("#myfrom").validate({
			rules : {
				fieldName : {required : true,alphanumeric:true},
				fieldTitle : {required : true},
				seq : {required : true,digits:true},
				columnNumber : {required : true,digits:true},
				width : {required : true,digits:true},
				height : {required : true,digits:true}
			}
		});
	}
</script>
</html>
