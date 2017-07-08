list
===
* 注释

	select t.ID_,t.ASSIGNEE_,t.PROC_INST_ID_,t.NAME_,t.PROC_DEF_ID_,t.TASK_DEF_KEY_,t.CREATE_TIME_ from act_ru_task t where #use("condition")#

list$count
===
* 注释

	select count(1) from act_def_node where #use("condition")#
	
condition
===

	1 = 1  
	@if(!isEmpty(processId)){
	 and t.ASSIGNEE_=#userId#
	@}
	
