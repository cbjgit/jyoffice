list
===
* 注释

	select #use("cols")# from frm_run_data where #use("condition")#

cols
===

	id,frm_instance_id,field_name,string_value,big_value,double_value,integer_value,date_value

updateData
===
	update frm_run_data set string_value = #stringValue#,big_value=#bigValue#,
	double_value=#doubleValue#,integer_value=#integerValue#,date_value=#dateValue#
	where frm_instance_id = #frmInstanceId# and field_name=#fieldName#

condition
===

	1 = 1  
	@if(!isEmpty(frmInstanceId)){
	 and `frm_instance_id`=#frmInstanceId#
	@}
	
