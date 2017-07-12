list
===
* 注释

	select #use("cols")# from act_def_node where #use("condition")#

list$count
===
* 注释

	select count(1) from act_def_node where #use("condition")#
	
cols
===

	id,node_id,node_names,node_types,multi,multi_type,assignee_type,
	assgnee,recall,back,completion_condition,process_id,rate,scope,scope_type,task_url,hitask_url

condition
===

	1 = 1  
	@if(!isEmpty(processId)){
	 and `process_id`=#processId#
	@}
	@if(!isEmpty(nodeId)){
	 and `node_id`=#nodeId#
	@}
	@if(!isEmpty(nodeTypes)){
	 and `node_types`=#nodeTypes#
	@}
	
	
copyNode
===
	insert into act_def_node
		(node_id,node_names,node_types,multi,multi_type,assignee_type,
		assgnee,recall,back,completion_condition,process_id,rate,scope,scope_type,task_url,hitask_url)
	select 
		node_id,node_names,node_types,multi,multi_type,assignee_type,
		assgnee,recall,back,completion_condition,#newProcessId#,rate,scope,scope_type,task_url,hitask_url 
	from act_def_node where process_id=#processId#
