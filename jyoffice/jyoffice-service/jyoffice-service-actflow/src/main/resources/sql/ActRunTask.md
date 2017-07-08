list
===
* 注释

	select t.id_,t.assignee_,t.proc_inst_id_,t.name_,t.task_def_key_,t.create_time_,p.bus_key,
	(select d.task_url from act_def_node d where d.process_id = p.process_id and d.node_id = t.task_def_key_) task_url
	from act_ru_task t  
	inner join act_instance_ext p on p.instance_id = t.proc_inst_id_
	where #use("condition")#
	
condition
===

	1 = 1  
	@if(!isEmpty(userId)){
	 and t.ASSIGNEE_=#userId#
	@}
	@if(!isEmpty(createTimeStart)){
	 and t.CREATE_TIME_ >= #createTimeStart#
	@}
	@if(!isEmpty(createTimeEnd)){
	 and t.CREATE_TIME_ <= #createTimeEnd#
	@}
	@if(!isEmpty(processKey)){
	 and p.process_key <= #processKey#
	@}
	
