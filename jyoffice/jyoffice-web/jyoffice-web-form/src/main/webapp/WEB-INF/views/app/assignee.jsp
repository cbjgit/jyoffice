<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<table class="formtable">
		<tr>
			<td>下一步环节</td>
			<td>${actNode.nodeNames}[${actNode.nodeId}] ：${actNode.multi == 1 ? "会签环节":"单人审批"}</td>
		</tr>
		<tr>
			<td>选择处理人</td>
			<td class="form-inline">
				<select id="dialog_leftselect" style="width:220px;" multiple="multiple" size="12" class="form-control">
					<option value="zhangsan">zhangsan</option>
					<option value="lishi">lishi</option>
					<option value="wangwu">wangwu</option>
					<option value="chenliu">chenliu</option>
					<option value="liuqi">liuqi</option>
				</select>
				<select style="width:220px;" multiple="multiple" size="12" id="dialog_selectassignee" class="form-control"></select>
				<div>会签环节可选择多人处理，单人审批环节只能选一人处理。</div>
			</td>
		</tr>
	</table>	
</body>
<%@ include file="/WEB-INF/views/footerjs.jsp"%>
<script type="text/javascript">
	$(function(){
		$('#dialog_leftselect').dblclick(function(){  
			if('${actNode.multi}' == '0' && $("#dialog_selectassignee option").length > 0){
				return;
			}
            $('#dialog_leftselect option:selected').appendTo($("#dialog_selectassignee"));
            $('#dialog_leftselect option:selected').remove();
        }).bind("contextmenu",function(){return false;}); // 该组件禁用右键菜单
        $('#dialog_selectassignee').dblclick(function(){  
            $('#dialog_selectassignee option:selected').appendTo($("#dialog_leftselect"));
            $('#dialog_selectassignee option:selected').remove();
        }).bind("contextmenu",function(){return false;}); // 该组件禁用右键菜单
	});
	</script>
</html>
