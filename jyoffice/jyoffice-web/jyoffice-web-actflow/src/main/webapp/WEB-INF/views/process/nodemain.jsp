<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
	<link href="${context}/js/jstree/themes/default/style.min.css" rel="stylesheet">
</head>
<body style="overflow:hidden;">
	<div id="jstree_div" style="float:left;width:200px;overflow:auto;"></div>
	<div style="float:left;padding-left:10px;">
		<iframe id="nodeIframeId" width="100%" height="100%" frameborder="0"></iframe>
	</div>
</body>

<%@ include file="/WEB-INF/views/footerjs.jsp"%>
<script src="${context}/js/jstree/jstree.min.js" ></script>
<script type="text/javascript">
window.onresize=function(){
	resize();
}
function resize(){
	$("#nodeIframeId").parent().css("height",$("body").height()+"px");
	$("#nodeIframeId").parent().css("width",$("body").width()-205+"px");
	$("#jstree_div").css("height",$("body").height()+"px");
}
$(function () { 
	resize();
	$('#jstree_div').jstree({
		'core':{
			'data' : {
			    'url' : "${context}/process/definition/jsonTree/0",
			    "dataType" : "json"
			}
		}
	}); 
	
	$('#jstree_div').on("changed.jstree", function (e, data) {
	  $("#nodeIframeId").attr("src","${context}/process/node/list/"+data.selected);
	}); 
	
});
</script>
</html>
