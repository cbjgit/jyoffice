list
===
* 注释

	select #use("cols")# from act_def_process where #use("condition")#
	
list$count
===
* 注释

	select count(1) from act_def_process where #use("condition")#
	
cols
===

	id,process_key,process_name,version,descption,deployee_date,status,create_by,create_date,update_by,update_date,deploy_id,definition_id

condition
===

	1 = 1  
	@if(!isEmpty(processKey)){
	 and `process_key`=#processKey#
	@}
	@if(!isEmpty(processName)){
	 and `process_name`=#processName#
	@}
	@if(!isEmpty(status)){
	 and `status`=#status#
	@}
	
