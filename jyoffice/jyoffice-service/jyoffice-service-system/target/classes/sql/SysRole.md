list
===
* 注释

	select #use("cols")# from sys_role where #use("condition")#
list$count
===
* 注释

	select count(1) from sys_role where #use("condition")#
cols
===

	id,role_code,role_name,role_desc,create_by,create_date,update_by,update_date

condition
===

	1 = 1  
	@if(!isEmpty(roleCode)){
	 and `role_code`=#roleCode#
	@}
	@if(!isEmpty(roleName)){
	 and `role_name`=#roleName#
	@}
