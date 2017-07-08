list
===
* 注释

	select #use("cols")# from sys_role_user where #use("condition")#
list$count
===
* 注释

	select count(1) from sys_role_user where #use("condition")#
	
cols
===

	id,(select user_name from sys_user u where u.id=user_id)user_name,create_by,create_date

condition
===

	1 = 1  
	@if(!isEmpty(roleId)){
	 and `role_id`=#roleId#
	@}
	@if(!isEmpty(userId)){
	 and `user_id`=#userId#
	@}
	
