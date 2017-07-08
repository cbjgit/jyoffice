<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<body>
	<form id="myfrom" action="${context}/process/node/saveNode" method="post">
	<input type="hidden" name="id" value="${actNode.id}">
	<input type="hidden" name="processId" value="${process.id}">
	<table class="formtable">
		<tr>
			<td>流程编码</td>
			<td>${process.processKey}</td>
			<td>流程名称</td>
			<td>${process.processName}</td>
		</tr>
		<tr>
			<td>环节编码</td>
			<td><input type="text" id="nodeId" name="nodeId" value="${actNode.nodeId}" class="form-control" ></td>
			<td>环节名称</td>
			<td><input type="text" id="nodeNames" name="nodeNames" value="${actNode.nodeNames}" class="form-control"></td>
		</tr>
		<tr>
			<td>环节类型</td>
			<td><select name="nodeTypes" class="form-control">
				<option value="userTask">用户任务</option>
				<!-- <option value="parallelGateway">并行分支</option> -->
				<option value="exclusiveGateway">单行分支</option>
				</select>
			</td>
			<td>是否会签</td>
			<td><input type="radio" name="multi" value="1" >是 <input type="radio" name="multi" value="0" checked="checked">否</td>
		</tr>
		<tr id="multirow">
			<td>会签方式</td>
			<td><input type="radio" name="multiType" value="1" checked="checked">并行 <input type="radio" name="multiType" value="2" >串行</td>
			<td>会签完成条件</td>
			<td>
				<select name="completionCondition" class="form-control">
					<option value="1">全部同意</option>
					<option value="2">同意占百分比？</option>
				</select>
				<input type="text" name="rate" value="${actNode.rate}" class="form-control">
			</td>
		</tr>
		<tr>
			<td>任务分配方式</td>
			<td>
				<select name="assigneeType" class="form-control">
				<option value="1">固定处理人</option>
				<option value="2">用户选择</option>
				</select>
			</td>
			<td>处理人</td>
			<td><input type="text" name="assgnee" value="${actNode.assgnee}" class="form-control">（登录ID）</td>
		</tr>
		<tr id="assigneeType2row">
			<td>处理人范围</td>
			<td>
				<select name="scopeType" class="form-control">
				<option value="1">角色</option>
				<option value="2">全部</option>
				</select>
			</td>
			<td>范围值</td>
			<td><input type="text" name="scope" value="${actNode.scope}" class="form-control"></td>
		</tr>
		<tr>
			<td>允许撤回</td>
			<td><input type="radio" name="recall" value="1" checked="checked">是 <input type="radio" name="recall" value="0">否</td>
			<td>允许退回</td>
			<td><input type="radio" name="back" value="1" checked="checked">是 <input type="radio" name="back" value="0">否</td>
		</tr>
		<tr>
			<td>待办处理地址</td>
			<td colspan="3"><input type="text" name="taskUrl" value="${actNode.taskUrl}" class="form-control" style="width:300px"></td>
		</tr>
		<tr>
			<td>已办处理地址</td>
			<td colspan="3"><input type="text" name="hitaskUrl" value="${actNode.hitaskUrl}" class="form-control" style="width:300px" ></td>
		</tr>
	</table>
	</form>
</body>
<%@ include file="/WEB-INF/views/footerjs.jsp"%>
<script type="text/javascript">
	
	$(function(){
		
		if(parseInt('${actNode.id}') > 0){
			editvalue();
			multirow(parseInt('${actNode.multi}') == 1);
			rate(parseInt('${actNode.completionCondition}') == 1);
			assgnee(parseInt('${actNode.assigneeType}') != 1);
		}else{
			multirow(false);
			rate(true);
			assgnee(false);
		}
		
		$("input[name=multi]").click(function(){
			if($(this).val() == "1"){
				multirow(true);
			}else{
				multirow(false);
			}
		});
		
		$("select[name=assigneeType]").change(function(){
			if($(this).val() == "1"){
				assgnee(false);
			}else{
				assgnee(true);
			}
		});
		
		$("select[name=completionCondition]").change(function(){
			if($(this).val() == "1"){
				rate(true);
			}else{
				rate(false);
			}
		});
		
		if('${view}' == 'true'){
			$("input").attr("disabled",true);
			$("select").attr("disabled",true);
		}
	});
	
	function editvalue(){
		$("select[name=nodeTypes]").val('${actNode.nodeTypes}');
		$("select[name=completionCondition]").val('${actNode.completionCondition}');
		$("input[name=multi][value=${actNode.multi}]").attr("checked",true);
		$("input[name=multiType]").val('${actNode.multiType}');
		$("select[name=assigneeType]").val('${actNode.assigneeType}');
		$("input[name=recall][value=${actNode.recall}]").attr("checked",true);
		$("input[name=back][value=${actNode.back}]").attr("checked",true);
	}
	
	function multirow(bool){
		if(bool){
			$("#multirow").show();
		}else{
			$("#multirow").hide();
		}
	}
	
	function assgnee(bool){
		$("input[name=assgnee]").attr("readonly",bool);
		if(bool){
			$("input[name=assgnee]").val("");
			$("#assigneeType2row").show();
		}else{
			$("#assigneeType2row").hide();
		}
		
	}
	
	function rate(bool){
		$("input[name=rate]").attr("readonly",bool);
	}
	
	function validform() {
		return $("#myfrom").validate({
			rules : {
				nodeId : {required : true,alphanumeric:true},
				nodeNames : {required : true},
				assigneeType : {required : true}
			}
		});
	}
	</script>
</html>
