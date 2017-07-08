<%@page import="java.util.Map"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
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
	<div>
		<c:forEach items="${nodeList}" var="node">
			<c:choose>
				<c:when test="${nodeId eq node.nodeId}">
				<a href="javascript:void(0)" class="btn btn-primary" role="button">${node.nodeNames}</a>
				</c:when>
				<c:otherwise>
				<a href="${context}/form/preview/${form.id}/${node.nodeId}" class="btn btn-default" role="button">${node.nodeNames}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<a href="${context}/form/list" class="btn btn-default" role="button">返回</a>
	</div>
	<hr>
	${formHtml}
</body>
<%@ include file="/WEB-INF/views/footerjs.jsp"%>
<script type="text/javascript">
$(function(){
	<c:forTokens items="${readList}" delims="," var="fd">
	$("[name=${fd}]").attr("readonly",true);
	</c:forTokens>
	<c:forTokens items="${hideList}" delims="," var="fd">
	$("[name=${fd}]").hide();
	</c:forTokens>
	$("input[readonly]").focus(function(){$(this).blur();});
	$("select[readonly]").focus(function(){
		var val = $(this).val();
		var text = $(this).find("option:selected").text();
		$(this).empty();
		$(this).append("<option value=\""+val+"\">"+ text +"</option>");
		$(this).blur();
	});
	
	$('input[datetype=date]').each(function(){
		if(!$(this).attr("readonly")){
			$(this).datetimepicker({
				language:  'zh-CN',
			    weekStart: 1,
			    todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				minView: 2
			});
		}
	});
	$('input[datetype=datetime]').each(function(){
		if(!$(this).attr("readonly")){
			$(this).datetimepicker({
				language:  'zh-CN',
				weekStart: 1,
		        todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				forceParse: 0,
		        showMeridian: 1
			});
		}
	});
	$('input[datetype=time]').each(function(){
		if(!$(this).attr("readonly")){
			$(this).datetimepicker({
				language:  'zh-CN',
				weekStart: 1,
		        todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 1,
				minView: 0,
				maxView: 1,
				forceParse: 0
			});
		}
	});
});
</script>
</html>
