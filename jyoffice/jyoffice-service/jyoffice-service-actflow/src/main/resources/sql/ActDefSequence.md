list
===
* 注释

	select #use("colslist")# from act_def_sequence a where #use("condition")#

list$count
===
* 注释

	select count(1) from act_def_sequence a where #use("condition")#
	
colslist
===

	id,node_id,to_node_id,condition_expression,process_id,
	(select node_names from act_def_node b where b.node_id = a.node_id and b.process_id = a.process_id)node_name,
	(select node_names from act_def_node b where b.node_id = a.to_node_id and b.process_id = a.process_id)tonode_name

cols
===

	id,node_id,to_node_id,condition_expression,process_id

condition
===

	1 = 1  
	@if(!isEmpty(processId)){
	 and `process_id`=#processId#
	@}
	@if(!isEmpty(nodeId)){
	 and `node_id`=#nodeId#
	@}
	@if(!isEmpty(toNodeId)){
	 and `to_node_id`=#toNodeId#
	@}
	
copySequence
===
	insert into act_def_sequence
		(node_id,to_node_id,condition_expression,process_id)
	select 
		node_id,to_node_id,condition_expression,#newProcessId# 
	from act_def_sequence where process_id=#processId#