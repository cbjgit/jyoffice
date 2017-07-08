list
===
* 注释

	select #use("cols")# from sys_role_auth where #use("condition")#

cols
===

	id,role_id,auth_id,create_by,create_date

condition
===

	1 = 1  
	@if(!isEmpty(roleId)){
	 and `role_id`=#roleId#
	@}
	@if(!isEmpty(authId)){
	 and `auto_id`=#authId#
	@}
	
