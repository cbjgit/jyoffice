list
===
* 注释

	select c.TASK_ID_,c.TIME_,c.MESSAGE_,c.USER_ID_,t.TASK_DEF_KEY_,t.NAME_ 
	from act_hi_comment c
	inner join act_hi_taskinst t on t.ID_=c.TASK_ID_
	where #use("condition")#

condition
===
	1=1
	@if(!isEmpty(instanceId)){
	 and c.PROC_INST_ID_=#instanceId#
	@}
