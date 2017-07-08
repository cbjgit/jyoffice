list
===
* 注释

	select #use("cols")# from sys_menu where #use("condition")#

list$count
===
* 注释

	select count(1) from sys_menu where #use("condition")#
	
cols
===

	id,parent_id,menu_code,menu_name,is_parent,context,menu_url,menu_desc,create_by,create_date,update_by,update_date

condition
===

	1 = 1  
	@if(!isEmpty(parentId)){
	 and `parent_id`=#parentId#
	@}
	@if(!isEmpty(menuCode)){
	 and `menu_code`=#menuCode#
	@}
	@if(!isEmpty(menuName)){
	 and `menu_name`=#menuName#
	@}
	@if(!isEmpty(isParent)){
	 and `is_parent`=#isParent#
	@}
	
