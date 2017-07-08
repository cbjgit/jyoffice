function opendialog(option,postForm,beforesubmit){
	
	var buttons = {  
        Cancel: {  
            label: "取消",  
            className: "btn-default"
        },  
        Save: {  
            label: "保存",  
            className: "btn-primary",  
            callback: function () {
            	var flag = false;
            	if( typeof postForm == 'function'){
            		flag = postForm.call();
            	}else if(typeof postForm == "string"){
            		if( typeof beforesubmit == 'function'){
            			beforesubmit.call();
            		}
            		flag = defaultSubmitFormfunc(postForm);
            	}
            	if(flag){
            		 window.location.href=window.location.href;
            	}else{
            		return false;
            	}
            }  
        }  
    }  
	
	option.buttons = buttons;
	bootbox.dialog(option);
	
}
function opendialogview(option){
	
	var buttons = {  
        Cancel: {  
            label: "取消",  
            className: "btn-default"
        }
    }  
	
	option.buttons = buttons;
	bootbox.dialog(option);
	
}
function defaultSubmitFormfunc(postForm){
	var flag = false;
	if(validform().form()){
		$.ajax({
			  type: 'POST',
			  url: $("#"+postForm).attr("action"),
			  data: $('#'+postForm).serialize(),
			  dataType: "json",
			  async:false,
			  success: function(data){
				  if(data.state == "1"){
					  flag = true;
				  }
				  else{
					  bootbox.alert(data.message);
					  flag = false;
				  }
			  },
			  error:function(XMLHttpRequest, textStatus, errorThrown) {
				  bootbox.alert(errorThrown);
				  flag = false;
			  }
		});
	}
	return flag;
}
function validform(){alert("请重写validform()");}


