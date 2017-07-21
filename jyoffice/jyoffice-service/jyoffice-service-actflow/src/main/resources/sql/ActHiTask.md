list
===
* 注释

	select t.id_,t.assignee_,t.proc_inst_id_,t.name_,t.proc_def_id_,t.task_def_key_,t.CLAIM_TIME_,t.END_TIME_, p.bus_key,
	(select d.hitask_url from act_def_node d where d.process_id = p.process_id and d.node_id = t.task_def_key_) task_url
	from act_hi_taskinst t 
	inner join act_instance_ext p on p.instance_id = t.proc_inst_id_
	where #use("condition")#

list$count
===
* 注释

	select count(1) from act_def_node where #use("condition")#
	
condition
===

	t.end_time_ is not null
	@if(!isEmpty(processId)){
	 and t.ASSIGNEE_=#userId#
	@}
	@if(!isEmpty(userId)){
	 and t.ASSIGNEE_=#userId#
	@}
	@if(!isEmpty(timeStart)){
	 and t.END_TIME_ >= #timeStart#
	@}
	@if(!isEmpty(timeEnd)){
	 and t.END_TIME_ <= #timeEnd#
	@}
	@if(!isEmpty(processKey)){
	 and p.process_key <= #processKey#
	@}

getUpTaskKey
===
	select TASK_DEF_KEY_ from( 
		select TASK_DEF_KEY_,END_TIME_ from act_hi_taskinst t where t.END_TIME_ is not NULL and t.PROC_INST_ID_= #instanceId#
		and t.ID_ <(select MIN(tt.ID_) from act_hi_taskinst tt where tt.PROC_INST_ID_= #instanceId# and tt.TASK_DEF_KEY_=#taskDefKey#)
		order by END_TIME_ desc 
	)tm LIMIT 0,1
	
getUpTaskKeyAll
===
	select TASK_DEF_KEY_ from act_hi_taskinst t where t.END_TIME_ is not NULL and t.PROC_INST_ID_= #instanceId#
	and t.ID_ < (select MIN(tt.ID_) from act_hi_taskinst tt where tt.PROC_INST_ID_= #instanceId# and tt.TASK_DEF_KEY_=#taskDefKey#)
