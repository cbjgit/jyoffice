list
===
* 注释

	select #use("cols")# from frm_run_instance fr where #use("condition")# order by create_date desc
list$count
===
* 注释

	select count(1) from frm_run_instance where #use("condition")#
	
cols
===

	id,form_id as formId,process_id as processId,form_name as formName,instance_id as instanceId,
	create_by as createBy,create_date as createDate,update_by as updateBy,update_date as updateDate,status,`submit`,
	(select node_names from act_def_node ad where ad.node_id = fr.status and ad.process_id = fr.process_id)statusText

condition
===

	1 = 1  
	@if(!isEmpty(formId)){
	 and `form_id` like #formId#
	@}
	@if(!isEmpty(formName)){
	 and `form_name` like #'%'+formName+'%'#
	@}
	@if(!isEmpty(userId)){
	 and `create_by`=#userId#
	@}
	@if(!isEmpty(createSDate)){
	 and `create_date` >= #createSDate#
	@}
	@if(!isEmpty(createEDate)){
	 and `create_date` <=#createEDate#
	@}
	
