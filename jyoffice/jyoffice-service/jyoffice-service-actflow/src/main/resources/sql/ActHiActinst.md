list
===
* 注释

	select #use("cols")# from act_hi_actinst where #use("condition")# order by START_TIME_

cols
===

	ID_,PROC_DEF_ID_,PROC_INST_ID_,EXECUTION_ID_,ACT_ID_,TASK_ID_,CALL_PROC_INST_ID_,ACT_NAME_,ACT_TYPE_,ASSIGNEE_,START_TIME_,END_TIME_,DURATION_,DELETE_REASON_,TENANT_ID_

condition
===

	1 = 1 and `ACT_TYPE_` in('userTask','startEvent','endEvent')
	@if(!isEmpty(instanceId)){
	 and `PROC_INST_ID_`=#instanceId#
	@}
	
	
