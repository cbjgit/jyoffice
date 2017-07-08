list
===
* 注释

	select #use("cols")# from frm_element_right where #use("condition")#
list$count
===
* 注释

	select count(1) from frm_element_right where #use("condition")#
cols
===

	id,node_id,field_name,rights,form_id

condition
===

	1 = 1  
	@if(!isEmpty(nodeId)){
	 and `node_id`=#nodeId#
	@}
	@if(!isEmpty(formId)){
	 and `form_id`=#formId#
	@}
	
