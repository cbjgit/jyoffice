package com.jyoffice.sys.service;

import org.springframework.stereotype.Service;

import com.jyoffice.BaseService;
import com.jyoffice.sys.model.SysUser;
import com.jyoffice.util.Pager;

@Service
public class SysUserService extends BaseService<SysUser, Integer> {

	public void getPager(Pager<SysUser> pager) {
		super.getPager("SysUser.list", SysUser.class, pager);
	}
	
	public void deleteById(int pk) {
		super.deleteById(SysUser.class, pk);
	}
	
	public SysUser get(int pk) {
		return super.get(SysUser.class, pk);
	}
}
