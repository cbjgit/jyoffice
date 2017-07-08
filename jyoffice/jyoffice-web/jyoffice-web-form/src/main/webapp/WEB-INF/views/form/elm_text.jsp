<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>

	<form id="myfrom" action="${context}/form/element/save/${frmForm.id}/${elmId}/${fieldtype}" method="post" data-parsley-validate="" class="form-inline">	
		<table class="formtable">
	    	<tr>
	    		<td>字段名称：</td>
	    		<td><input type="text" class="form-control" name="fieldName" value="${element.fieldName}" maxlength="50"></td>
	    		<td>字段标题：</td>
	    		<td><input type="text" class="form-control" name="fieldTitle" value="${element.fieldTitle}" style="width:300px;" maxlength="50"></td>
	    	</tr>
	  		<tr>
		    	<td>数据类型：</td>
		    	<td>
		    	<select id="dataType" name="dataType" class="form-control">
		    		<option value="text">普通文本</option>
		    		<option value="integer">整数</option>
		    		<option value="float">数字</option>
		    		<option value="email">邮箱地址</option>
		    		<option value="date">日期控件</option>
		    	</select>
		    	</td>
		    	<td>控件宽度：</td>
		    	<td>
		    		<input type="text" class="form-control" id="width" name="width" style="width:60px">px
		    		<span>&nbsp;日期样式：
		    		<select class="form-control" id="datestyle" name="datestyle">
		    			<option value="yyyy-MM-dd">yyyy-MM-dd</option>
		    			<option value="yyyy-MM-dd HH:mm">yyyy-MM-dd HH:mm</option>
		    			<option value="HH:mm">HH:mm</option>
		    		</select>
		    		</span>
		    	</td>
		  	</tr>
		  	<tr>
		    	<td>默认值：</td>
		    	<td>
		    		<select class="form-control" id="defaultValue" name="defaultValue">
		    			<option value=""></option>
		    			<option value="sysdate">当前日期</option>
		    			<option value="login">当前登录人</option>
		    			<option value="loginJob">当前登录人职位</option>
		    			<option value="loginDept">当前登录人部门</option>
		    		</select>
		    	</td>
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
		  		<td>是否为流程变量：</td>
		    	<td>
		    	<input type="checkbox" class="form-control" id="flowVar" name="flowVar" value="1">是
		    	</td>
		    	<td>是否必须填写：</td>
		    	<td>
		    	<input type="checkbox" class="form-control" id="required" name="required" value="1">是
		    	</td>
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
		$("#datestyle").parent().hide();
		$("#dataType").change(function(){
			if($(this).val() == "date"){
				$("#datestyle").parent().show();
			}else{
				$("#datestyle").parent().hide();
			}
		})
		$("#columnNumber").val('${element.columnNumber}');
		if('${element.flowVar}' == '1'){
			$("#flowVar").attr("checked",true);
		}
		if('${element.required}' == '1'){
			$("#required").attr("checked",true);
		}
		$("#dataType").val('${element.dataType}');
		$("#defaultValue").val('${element.defaultValue}');
		var oparam = ${element.otherParam};
		if(oparam){
			$("#width").val(oparam.width);
			if('${element.dataType}' == "date"){
				$("#datestyle").val(oparam.datestyle);
				$("#datestyle").parent().show();
			}
		}
	});
	
	function validform() {
		return $("#myfrom").validate({
			rules : {
				fieldName : {required : true,alphanumeric:true},
				fieldTitle : {required : true},
				seq : {required : true,digits:true},
				columnNumber : {required : true,digits:true},
				dataType : {required : true},
				width : {digits:true}
			}
		});
	}
</script>
</html>
