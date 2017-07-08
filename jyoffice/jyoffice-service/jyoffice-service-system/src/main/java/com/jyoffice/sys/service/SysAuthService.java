package com.jyoffice.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.sql.core.SQLReady;
import org.springframework.stereotype.Service;

import com.jyoffice.BaseService;
import com.jyoffice.sys.model.SysAuth;
import com.jyoffice.sys.model.SysRoleAuth;
import com.jyoffice.util.Pager;

@Service
public class SysAuthService extends BaseService<SysAuth, Integer> {

	public void getPager(Pager<SysAuth> pager) {
		super.getPager("SysAuth.list", SysAuth.class, pager);
	}
	
	public void deleteById(int pk) {
		super.deleteById(SysAuth.class, pk);
	}
	
	public SysAuth get(int pk) {
		return super.get(SysAuth.class, pk);
	}
	
	public void saveRoleAuth(Integer roleId, List<SysRoleAuth> raList) {
		sqlManager.executeUpdate(new SQLReady("delete from sys_role_auth where role_id=?", roleId));
		sqlManager.insertBatch(SysRoleAuth.class, raList);
	}
	
	public List<SysRoleAuth> getAuthByRoleId(Integer roleId) {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("roleId", roleId);
		return sqlManager.select("SysRoleAuth.list", SysRoleAuth.class, param);
	}
}
