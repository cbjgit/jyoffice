<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
	<link href="${context}/js/jstree/themes/default/style.min.css" rel="stylesheet">
</head>
<body style="overflow:hidden;">
	<div style="text-align:center">
		<button id="dosave" type="button" class="btn btn-default" onclick="saveAuth()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="${context}/sys/role/list" class="btn btn-default" role="button">返回</a>
		<form action="${context}/sys/role/auth/save" method="post" id="authForm">
			<input type="hidden" name="authAll" id="authAll">
			<input type="hidden" name="roleId" value="${role.id}">
		</form>
	</div>
	<div id="jstree_div" style="float:left;width:200px;width:100%;overflow:auto;"></div>
</body>

<%@ include file="/WEB-INF/views/footerjs.jsp"%>
<script src="${context}/js/jstree/jstree.min.js" ></script>
<script type="text/javascript">
window.onresize=function(){
	resize();
}
function resize(){
	$("#seqIframeId").parent().css("height",$("body").height()+"px");
	$("#seqIframeId").parent().css("width",$("body").width()-205+"px");
	$("#jstree_div").css("height",$("body").height()-105+"px");
}
function refreshTree(nodeId){
	$("#jstree_div").jstree('refresh', nodeId);
}

var jsonTree = ${jsonTree};
$(function () { 
	
	resize();
	
	$('#jstree_div').jstree({ 
		"plugins" : ["checkbox"],
		'core' : {'data' : jsonTree} 
	});
	
	$('#jstree_div').on("changed.jstree", function (e, data) {
	      console.log(data.selected);
	});
	
});

function saveAuth(){
	var nodes = $('#jstree_div').jstree().get_bottom_checked(); //使用get_checked,get_top_checked,get_bottom_checked 方法 
	$("#authAll").val(nodes);
	$("#authForm").submit();
}
</script>
</html>
