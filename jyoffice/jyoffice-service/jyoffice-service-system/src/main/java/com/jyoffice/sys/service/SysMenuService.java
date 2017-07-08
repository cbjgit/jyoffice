package com.jyoffice.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jyoffice.BaseService;
import com.jyoffice.sys.model.SysMenu;
import com.jyoffice.util.Pager;

@Service
public class SysMenuService extends BaseService<SysMenu, Integer> {

	public void getPager(Pager<SysMenu> pager) {
		super.getPager("SysMenu.list", SysMenu.class, pager);
	}
	
	public List<SysMenu> getList(Integer parentId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("parentId", parentId);
		return sqlManager.select("SysMenu.list", SysMenu.class, param);
	}
	
	public void deleteById(int pk) {
		super.deleteById(SysMenu.class, pk);
	}
	
	public SysMenu get(int pk) {
		return super.get(SysMenu.class, pk);
	}
}
