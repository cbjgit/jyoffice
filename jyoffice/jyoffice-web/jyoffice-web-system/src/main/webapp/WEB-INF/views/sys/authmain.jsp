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
		<iframe id="seqIframeId" width="100%" height="100%" frameborder="0"></iframe>
	</div>
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

$(function () { 
	resize();
	$('#jstree_div').data('jstree', false).empty();
	$('#jstree_div').jstree({
		 'core':{
			'data' : {
				"dataType" : "json",
		    	'url' : function (node) {
		    		return node.id === '#' ?
		  		          '${context}/treeroots.json' : '${context}/sys/menu/jsonTree/';
	    	    },
	    	    'data' : function (node) {
	    	      return { 'id' : node.id=="#"?"0":node.id};
	    	    }
			}
		}
	}); 
	
	$('#jstree_div').on("changed.jstree", function (e, data) {
	  $("#seqIframeId").attr("src","${context}/sys/auth/list/"+data.selected);
	});
});
</script>
</html>
