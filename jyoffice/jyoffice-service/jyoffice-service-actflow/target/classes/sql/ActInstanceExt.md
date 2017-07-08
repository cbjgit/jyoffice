list
===
* 注释

	select #use("cols")# from act_instance_ext where #use("condition")#

list$count
===
* 注释

	select count(1) from act_instance_ext where #use("condition")#
	
cols
===

	id,instance_id,bus_key,title,app_userid,app_username,process_id,process_key


condition
===

	1 = 1  
	@if(!isEmpty(instanceId)){
	 and `instance_id`=#instanceId#
	@}
	@if(!isEmpty(title)){
	 and `title`=#title#
	@}
	@if(!isEmpty(appUserid)){
	 and `app_userid`=#appUserid#
	@}
	@if(!isEmpty(appUsername)){
	 and `app_username`=#appUsername#
	@}
	
