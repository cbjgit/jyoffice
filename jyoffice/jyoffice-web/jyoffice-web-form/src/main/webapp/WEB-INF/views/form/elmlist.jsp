<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/head.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/link.jsp"%>
</head>
<body>
	<div>
		<a href="javascript:void(0);" key="0" fieldtype="text" class="btn btn-default myeditoperator" role="button">新增文本框</a>
		<a href="javascript:void(0);" key="0" fieldtype="textarea" class="btn btn-default myeditoperator" role="button">新增文本域</a>
		<a href="javascript:void(0);" key="0" fieldtype="radio" class="btn btn-default myeditoperator" role="button">新增单选框</a>
		<a href="javascript:void(0);" key="0" fieldtype="checkbox" class="btn btn-default myeditoperator" role="button">新增多选框</a>
		<a href="javascript:void(0);" key="0" fieldtype="select" class="btn btn-default myeditoperator" role="button">新增下拉框</a>
		<a href="${context}/form/preview/${formId}/-" class="btn btn-default" role="button">预览</a>
		<a href="${context}/form/element/elmright/${formId}" class="btn btn-default" role="button">权限配置</a>
		<a href="${context}/form/list" class="btn btn-default" role="button">返回</a>
		<span>${message}</span>
	</div>
	<p></p>
	<table class="table table-hover table-condensed table-bordered">
		<tr>
			<th>字段名称</th>
			<th>字段标题</th>
			<th>字段类型</th>
			<th>默认值</th>
			<th>流程变量</th>
			<th>数据类型</th>
			<th>是否必填</th>
			<th>显示位置</th>
			<th>跨列数</th>
			<th>其他参数</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pager.resultList}" var="fm">
			<tr>
				<td>${fm.fieldName}</td>
				<td>${fm.fieldTitle}</td>
				<td>${fm.fieldType}</td>
				<td>${fm.defaultValue}</td>
				<td>${fm.flowVar}</td>
				<td>${fm.dataType}</td>
				<td>${fm.required}</td>
				<td>${fm.seq}</td>
				<td>${fm.columnNumber}</td>
				<td>${fm.otherParam}</td>
				<td>
					<a href="javascript:void(0);" key="${fm.id}" fieldtype="${fm.fieldType}" class="btn btn-default myeditoperator" role="button">编辑</a>
					<a href="javascript:void(0);" key="${fm.id}" class="btn btn-default mydelete" role="button">删除</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<%@ include file="/WEB-INF/views/footerjs.jsp"%>
	<script type="text/javascript">
		$(function(){
			$(".mydelete").bind("click",function(){
				var id = $(this).attr("key");
				bootbox.confirm({
					size: "small",
					title:"确认提示",
					message:"你确定要删除这条记录嘛?",
					callback:function(result){ 
						if(result){window.location.href="${context}/form/element/delete/"+id;} 
					}
				});
			});
			
			$(".myeditoperator").bind("click",function(){
				var id = $(this).attr("key");
				var fieldtype = $(this).attr("fieldtype");
				var title = "";
				if(fieldtype == "text"){
					title = "文本框";
				}else if(fieldtype == "select"){
					title = "下拉框";
				}else if(fieldtype == "radio"){
					title = "单选框";
				}else if(fieldtype == "textarea"){
					title = "文本域";
				}else if(fieldtype == "checkbox"){
					title = "多选框";
				}
				$.get("${context}/form/element/edit/${formId}/"+id+"/"+fieldtype, 
						function(result){
					if(fieldtype == "text" || fieldtype == "textarea"){
						opendialog({ title:title,size: "large",message: result },"myfrom");
					}else{
						opendialog({ title:title,size: "large",message: result },"myfrom",beforesubmit);
					}
				}); 
			});
		});
		
		function beforesubmit(){
			$("#optonsList option").each(function(i){
				$(this).attr("selected",true);
			});
			
			return true;
		}
	</script>
</body>
</html>
