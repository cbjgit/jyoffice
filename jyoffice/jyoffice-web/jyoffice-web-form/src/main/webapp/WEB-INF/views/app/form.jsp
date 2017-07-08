<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
	<style type="text/css">
	#biaoge{
		margin:6px;
		padding:2px;
		text-align:center;
	}
	#biaoge table{
		margin:0px auto;
	}
	</style>
</head>
<body id="biaoge">
	${message}
	<form method="post" id="myform">
		<button id="dosave" type="button" class="btn btn-default">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
		<button id="dosubmit" type="button" class="btn btn-default">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="${context}/home/list" class="btn btn-default" role="button">返回</a>
		<hr>
		<input type="hidden" name="formId" value="${formId}">
		<input type="hidden" name="frmInstanceId" value="${frmInstanceId}">
		<input type="hidden" name="taskId" value="${taskId}">
		<input type="hidden" name="instanceId" value="${instanceId}">
		<input type="hidden" name="taskKey" value="${taskKey}">
		<input type="hidden" name="processId" value="${processId}">
		<input type="hidden" name="service" value="frmRunInstanceService">
		<input type="hidden" name="assignee">
		<input type="hidden" id="actvar" name="actvar">
		<input type="hidden" id="nexttaskKey" name="nexttaskKey">
		${formHtml}
	</form>			
</body>
<script type="text/javascript">
$(function(){

	$("#myform").validate();
	
	$("#dosave").click(function(){
		$("#myform").attr("action","${context}/process/application/save");
		$("#myform").submit();
	});
	$("#dosubmit").click(function(){
		var flowvar = getflowvar();
		$("#actvar").val(flowvar);
		$.get("${context}/flowtask/assigneeMode/${processId}/${taskKey}/"+flowvar, 
				function(result){
			if(result.assigneeType == "-1"){
				bootbox.alert("获取下一环节失败");
			}else if(result.assigneeType == "2" && result.taskKey != "end"){
				$("#nexttaskKey").val(result.taskKey);
				$.get("${context}/flowtask/assignee/${processId}/"+result.taskKey, 
						function(result){
					opendialog({ title:"选择处理人",message: result },do_submit);
				}); 
			}else{
				$("#myform").attr("action","${context}/flowtask/submit");
				$("#myform").submit();
			}
		}); 
	});
	
	var getflowvar=function(){
		var flowvar = {};
		$("input[flowvar]").each(function(){
			if($(this).attr("type") == "radio"){
				if($(this).attr("checked")){
					flowvar[$(this).attr("name")]= $(this).val();
				}
			}else{
				flowvar[$(this).attr("name")] = $(this).val();
			}
		});
		$("select[flowvar]").each(function(){
			flowvar[$(this).attr("name")] =$(this).val();
		});
		return JSON.stringify(flowvar);
	}
	
	var do_submit=function(){
		if($("#dialog_selectassignee").val() == null){
			bootbox.alert("请选择处理人");
			return false;
		}
		
		$("input[name=assignee]").val($("#dialog_selectassignee").val());
		$("#myform").attr("action","${context}/flowtask/submit");
		$("#myform").submit();
	}
	
	//设置只读控件
	<c:forTokens items="${readList}" delims="," var="fd">
	$("[name=${fd}]").attr("readonly",true);
	</c:forTokens>
	
	//设置隐藏控件
	<c:forTokens items="${hideList}" delims="," var="fd">
	$("[name=${fd}]").hide();
	</c:forTokens>
	
	//填充控件数据
	var formData = ${formData};
	var setFormVal = function(data){
		var fieldType = "";
		var fieldName = "";
		var fieldValue = "";
		for(var i in data){
			fieldType = data[i]["fieldType"];
			fieldName = data[i]["fieldName"];
			fieldValue = data[i]["fieldValue"];
			
			if(fieldType == "text"){
				$("input[name="+fieldName+"]").val(fieldValue);
				
			}else if(fieldType == "textarea"){
				$("textarea[name="+fieldName+"]").val(fieldValue);
				
			}else if(fieldType == "select"){
				$("select[name="+fieldName+"]").val(fieldValue);
				
			}else if(fieldType == "radio"){
				$("input[name="+fieldName+"][value="+fieldValue+"]").click();
				
			}else if(fieldType == "checkbox"){
				var v = fieldValue.split(",");
				for(var j = 0;j < v.length;j++){
					$("input[name="+fieldName+"][value="+v[j]+"]").click();
				}
			}
		}
	};
	setFormVal(formData);
});
</script>
<%@ include file="/WEB-INF/views/footerjs.jsp"%>
</html>
