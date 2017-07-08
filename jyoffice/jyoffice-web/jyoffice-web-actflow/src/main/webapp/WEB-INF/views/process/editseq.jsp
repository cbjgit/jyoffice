<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<form action="${context}/process/sequnece/saveseq" 
		id="myfrom" method="post">
		<input type="hidden" name="id" value="${actSequence.id}">
		<input type="hidden" name="processId" value="${process.id}">
		<div class="form-group">
	    	<label for="processKey">流程编码</label>
	    	${process.processKey}
	  	</div>
	  	
	  	<div class="form-group">
	    	<label for="processKey">流程编码</label>
	    	${process.processName}
	  	</div>
	  	
	  	<div class="form-group">
	    	<label for="processKey">从环节</label>
	    	<select name="nodeId" class="form-control" style="width:200px;">
				<option value=""></option>
				<option value="start">开始</option>
				<c:forEach items="${nodeList}" var="node">
				<option value="${node.nodeId}">${node.nodeNames}</option>
				</c:forEach>
				<option value="end">结束</option>
			</select>
	  	</div>
	  	
	  	<div class="form-group">
	    	<label for="processKey">到环节</label>
	    	<select name="toNodeId" class="form-control" style="width:200px;">
				<option value=""></option>
				<option value="start">开始</option>
				<c:forEach items="${nodeList}" var="node">
				<option value="${node.nodeId}">${node.nodeNames}</option>
				</c:forEach>
				<option value="end">结束</option>
			</select>
	  	</div>
	  	
	  	<div class="form-group">
	    	<label for="processKey">条件</label>
			<input type="text" name="conditionExpression" value="${actSequence.conditionExpression}" class="form-control">
	  	</div>
	</form>
</body>
<%@ include file="/WEB-INF/views/footerjs.jsp"%>
<script type="text/javascript">
	$(function(){
		if(parseInt('${actSequence.id}') > 0){
			editvalue();
		}
	});
	
	function editvalue(){
		$("select[name=nodeId]").val('${actSequence.nodeId}');
		$("select[name=toNodeId]").val('${actSequence.toNodeId}');
	}
	
	function validform() {
		return $("#myfrom").validate({
			rules : {
				nodeId : {required : true},
				toNodeId : {required : true}
			}
		});
	}
	</script>
</html>
