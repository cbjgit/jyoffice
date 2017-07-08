package com.jyoffice.ca;

import java.util.Set;

public interface RoleService {

	Set<String> findRoles(Long... roleIds); //根据角色编号得到角色标识符列表
	Set<String> findPermissions(Long[] roleIds); //根据角色编号得到权限字符串列表
}
