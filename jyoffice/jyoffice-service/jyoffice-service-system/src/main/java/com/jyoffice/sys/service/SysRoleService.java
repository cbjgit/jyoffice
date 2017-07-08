package com.jyoffice.sys.service;

import org.beetl.sql.core.engine.PageQuery;
import org.springframework.stereotype.Service;

import com.jyoffice.BaseService;
import com.jyoffice.sys.model.SysRole;
import com.jyoffice.sys.model.SysUser;
import com.jyoffice.util.Pager;

@Service
public class SysRoleService extends BaseService<SysRole, Integer> {

	public void getPager(Pager<SysRole> pager) {
		super.getPager("SysRole.list", SysRole.class, pager);
	}

	public void deleteById(int pk) {
		super.deleteById(SysRole.class, pk);
	}

	public SysRole get(int pk) {
		return super.get(SysRole.class, pk);
	}

	public void getUserPager(Pager<SysUser> pager) {
		PageQuery<SysUser> page = new PageQuery<SysUser>(pager.getCurrentPage(), pager.getParam());
		page.setPageSize(pager.getPageSize());
		
		sqlManager.pageQuery("SysRoleUser.list", SysUser.class, page);
		
		pager.setTotal(page.getTotalRow());
		pager.setCountPage((int)page.getTotalPage());
		pager.setResultList(page.getList());
	}
	
}
