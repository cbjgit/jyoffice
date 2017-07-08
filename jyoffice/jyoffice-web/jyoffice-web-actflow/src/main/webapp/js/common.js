$(function(){

	//只读控件不能获取焦点
	$("input[readonly]").focus(function(){$(this).blur();});
	$("select[readonly]").focus(function(){
		var val = $(this).val();
		var text = $(this).find("option:selected").text();
		$(this).empty();
		$(this).append("<option value=\""+val+"\">"+ text +"</option>");
		$(this).blur();
	});
	
	
	//日期控件事件
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


function doPager(page){
	$("#page").val(page);
	$("#searchForm").submit();
}


function openUserDialog(option,mutil,callbackfunc){
	
	var buttons = {  
        Cancel: {  
            label: "取消",  
            className: "btn-default"
        },  
        Save: {  
            label: "确定",  
            className: "btn-primary",  
            callback: function(){
            	var chk_value =[]; 
            	$('input[name=_userId]:checked').each(function(){ 
            		chk_value.push($(this).val()); 
            	}); 
            	if(chk_value.length == 0){
            		bootbox.alert("请选择用户");
            		return false;
            	}
            	if(!mutil && chk_value.length > 1){
            		bootbox.alert("只能选择一个用户");
            		return false;
            	}
            	return callbackfunc(chk_value);
            }
        }  
    }  
	
	option.buttons = buttons;
	bootbox.dialog(option);
	
}
