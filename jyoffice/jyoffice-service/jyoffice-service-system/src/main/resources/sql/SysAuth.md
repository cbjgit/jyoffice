list
===
* 注释

	select #use("cols")# from sys_auth where #use("condition")#
list$count
===
* 注释

	select count(1) from sys_auth where #use("condition")#
cols
===

	id,auth_id,auth_name,auth_path,menu_id,create_by,create_date


condition
===

	1 = 1  
	@if(!isEmpty(menuId)){
	 and `menu_id`=#menuId#
	@}
	
