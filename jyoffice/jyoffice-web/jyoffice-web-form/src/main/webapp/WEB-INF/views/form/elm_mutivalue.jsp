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
		    	<td>默认值：</td>
		    	<td><input type="text" class="form-control" name="defaultValue" value="${element.defaultValue}" maxlength="30"></td>
		    	<td>显示顺序：</td>
		    	<td>
		    		<input type="text" class="form-control" name="seq" value="${element.seq}" style="width:60px">
		    		&nbsp;&nbsp;占位列数:
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
		    	<td>是否为流程变量：</td>
		    	<td>
		    		<input type="checkbox" class="form-control" id="flowVar" name="flowVar" value="1">是
		    	</td>
		  	</tr>
		  	<tr>
		  		<td>显示方式：</td>
		    	<td>
		    		<input type="radio" class="form-control" name="showtype" value="1" checked="checked">横排显示
		    		<input type="radio" class="form-control" name="showtype" value="2">竖排显示
		    	</td>
		  		<td>数据来源：</td>
		    	<td>
		    		<input type="radio" class="form-control" name="dataSource" value="1" checked="checked">自定义
		    		<input type="radio" class="form-control" name="dataSource" value="2">系统中选择
		    	</td>
		  	</tr>
		  	<tr>
		    	<td>数据：</td>
		    	<td colspan="3">
		    		<div style="float:left;width:210px;">
		    			<select size="8" name="optonsList" id="optonsList" multiple="multiple" style="width:200px;">
		    			<c:forEach items="${optList}" var="opt"><option value="${opt.optionValue}==${opt.optionText}">${opt.optionText}</option></c:forEach>
		    			</select>
		    		</div>
		    		<div style="float:left;width:500px;">
		    			<p><button type="button" class="btn btn-default" onclick="removeOption();">删除</button></p>
		    			<p>值：<input type="text" class="form-control" name="optonValue" id="optonValue" style="width:80px;">
		    			文本：<input type="text" class="form-control" name="optonText" id="optonText" style="width:200px;">
		    			<button type="button" class="btn btn-default" onclick="addOption()">增加</button></p>
		    		</div>
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
		$("#columnNumber").val('${element.columnNumber}');
		if('${element.flowVar}' == '1'){
			$("#flowVar").attr("checked",true);
		}
		if('${element.required}' == '1'){
			$("#required").attr("checked",true);
		}
		$("input[name=dataSource][value=${element.dataSource}]").click();
		var oparam = ${element.otherParam};
		if(oparam){
			$("input[name=showtype][value="+ oparam.showtype +"]").click();
		}
	});

	function addOption(){
		var val = $("#optonValue").val();
		var text = $("#optonText").val();
		if(val == "" || text == ""){
			bootbox.alert("不能添加空值");
			return;
		}
		
		var flag = false;
		$("#optonsList option").each(function(){
			var vt = $(this).val().split("==");
			if(val == vt[0] || text == vt[1]){
				bootbox.alert("你添加的值或文本已经重复");
				flag = true;
				return false;
			}
		});
		if(!flag){
			$("#optonsList").append("<option value='"+val+'=='+text+"'>"+text+"</option>");
		}
		$("#optonValue").val("");
		$("#optonText").val("");
	}
	
	function removeOption(){
		$("#optonsList option:selected").remove(); 
	}
	
	function validform() {
		return $("#myfrom").validate({
			rules : {
				fieldName : {required : true,alphanumeric:true},
				fieldTitle : {required : true},
				seq : {required : true,digits:true},
				columnNumber : {required : true,digits:true}
			}
		});
	}
</script>
</html>
