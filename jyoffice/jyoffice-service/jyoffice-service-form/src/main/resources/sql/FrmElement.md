list
===
* 注释

	select #use("cols")# from frm_element where #use("condition")# order by seq
	
list$count
===
* 注释

	select count(1) from frm_element where #use("condition")#
cols
===

	id,field_name,field_title,field_type,default_value,flow_var,data_type,seq,other_param,required,form_id,column_number,data_source,field_descption


condition
===

	1 = 1  
	@if(!isEmpty(formId)){
	 and `form_id`=#formId#
	@}
	@if(!isEmpty(fieldName)){
	 and `field_name`=#fieldName#
	@}
	
