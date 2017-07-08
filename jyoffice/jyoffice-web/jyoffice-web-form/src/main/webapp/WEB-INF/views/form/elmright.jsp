<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<div style="text-align:center;font-size:20pt;">表单流程权限控制</div>
	<form id="myfrom" action="${context}/form/element/elmright/save/${form.id}" method="post" data-parsley-validate="">	
		<table class="table table-hover table-bordered">
		<tr id="_rowTitle"><td style="vertical-align:middle;"><strong><sup>字段名称</sup>/<sub>环节名称</sub></strong></td>
		<c:forEach items="${nodeList}" var="node">
			<td>
				<strong><input type="hidden" name="nodeId" value="${node.nodeId}">${node.nodeNames}</strong><br>
				<input type="radio" name="node_${node.nodeId}" value="1" radiotype="ALL" nodeid="${node.nodeId}">全只读<br/>
				<input type="radio" name="node_${node.nodeId}" value="2" radiotype="ALL" nodeid="${node.nodeId}">全可写<br/>
				<input type="radio" name="node_${node.nodeId}" value="3" radiotype="ALL" nodeid="${node.nodeId}">全隐藏<br/>
			</td>
    	</c:forEach>
    	</tr>
    	<c:forEach items="${elmList}" var="elm">
	    	<tr>
	    	<td><strong><input type="hidden" name="fieldName" value="${elm.fieldName}">${elm.fieldTitle}</strong></td>
	    	<c:forEach items="${nodeList}" var="node">
				<td><input type="radio" name="${node.nodeId}_${elm.fieldName}" value="1" radiotype="detail" nodeid="${node.nodeId}">只读&nbsp;
				<input type="radio" name="${node.nodeId}_${elm.fieldName}" value="2"radiotype="detail" nodeid="${node.nodeId}">可写&nbsp;
				<input type="radio" name="${node.nodeId}_${elm.fieldName}" value="3"radiotype="detail" nodeid="${node.nodeId}">隐藏</td>
	    	</c:forEach>
	    	</tr>
	    </c:forEach>
	    	
    	</table>
	  	<div >
	  	<button type="submit" class="btn btn-default">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
	  	<a href="${context}/form/element/list/${form.id}" class="btn btn-default " role="button">返回</a>
	  	</div>
	</form>
	
</body>
<%@ include file="/WEB-INF/views/footerjs.jsp"%>
<script type="text/javascript">
	$(function(){
		$("#_rowTitle input[radiotype=ALL]").click(function(){
			$("input[radiotype=detail][nodeid="+$(this).attr("nodeid")+"][value="+$(this).val()+"]").click();
		})
		
		if("${edit}" == "true"){
			<c:forEach items="${rightList}" var="right">
				$("input[name=${right.nodeId}_${right.fieldName}][value=${right.rights}]").click();
			</c:forEach>
		}else{
			//默认可写
			$("input[radiotype=detail][value=2]").click();
		}
	});
</script>
</html>
