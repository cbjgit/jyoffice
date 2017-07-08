list
===
* 注释

	select #use("cols")# from sys_user where #use("condition")#
list$count
===
* 注释

	select count(1) from sys_user where #use("condition")#
cols
===

	id,login_id,password,user_name,last_login_time,last_login_ip,emp_id

updateSample
===

	`id`=#id#,`login_id`=#loginId#,`password`=#password#,`user_name`=#userName#,`last_login_time`=#lastLoginTime#,`last_login_ip`=#lastLoginIp#,`emp_id`=#empId#

condition
===

	1 = 1  
	@if(!isEmpty(loginId)){
	 and `login_id`=#loginId#
	@}
	@if(!isEmpty(password)){
	 and `password`=#password#
	@}
	@if(!isEmpty(userName)){
	 and `user_name`=#userName#
	@}
	@if(!isEmpty(empId)){
	 and `emp_id`=#empId#
	@}
	
