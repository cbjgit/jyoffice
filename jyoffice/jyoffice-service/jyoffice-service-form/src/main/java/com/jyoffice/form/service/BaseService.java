package com.jyoffice.form.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.db.KeyHolder;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyoffice.util.Pager;

public class BaseService<T, PK extends Serializable> {

	@Autowired
	protected SQLManager sqlManager;

	public List<T> selectAll(Class<T> claszz) {
		return sqlManager.all(claszz);
	}
	
	public void insert(T t) {
		insert(t, true);
	}

	public void insert(T t, boolean autoKey) {
		sqlManager.insert(t, autoKey);
	}

	public long insertKey(T t) {
		KeyHolder holder = new KeyHolder();
		sqlManager.insert(t.getClass(), t, holder);
		return holder.getLong();
	}
	
	public void updateById(T t) {
		sqlManager.updateById(t);
	}

	public void updateTemplateById(T t) {
		sqlManager.updateTemplateById(t);
	}

	protected void getPager(String sqlId,Class<T> clazz, Pager<T> pager) {
		PageQuery<T> page = new PageQuery<T>(pager.getCurrentPage(), pager.getParam());
		page.setPageSize(pager.getPageSize());
		
		sqlManager.pageQuery(sqlId, clazz, page);
		
		pager.setTotal(page.getTotalRow());
		pager.setCountPage((int)page.getTotalPage());
		pager.setResultList(page.getList());
	}
	
	protected void getPager(String sqlId,Pager<Map<String,Object>> pager) {
		PageQuery<Map<String,Object>> page = new PageQuery<Map<String,Object>>(pager.getCurrentPage(), pager.getParam());
		page.setPageSize(pager.getPageSize());
		
		sqlManager.pageQuery(sqlId, Map.class, page);
		
		pager.setTotal(page.getTotalRow());
		pager.setCountPage((int)page.getTotalPage());
		pager.setResultList(page.getList());
	}
	
	protected List<T> getList(String sqlId,Class<T> clazz, Map<String,Object> param) {
		return sqlManager.select(sqlId, clazz, param);
	}
	
	public void deleteById(Class<T> clazz,Object pk) {
		sqlManager.deleteById(clazz, pk);
	}
	
	/**根据主键获取对象，未找到抛出异常
	 * @param sqllId
	 * @param clazz
	 * @param pk
	 * @return
	 */
	protected T get(String sqllId,Class<T> clazz,Object pk) {
		return sqlManager.selectUnique(sqllId, pk, clazz);
	}
	
	/**获取对象,未找到返回nll
	 * @param sqllId
	 * @param clazz
	 * @param param
	 * @return
	 */
	protected T get(String sqllId,Class<T> clazz,Map<String,Object> param) {
		return sqlManager.selectSingle(sqllId, param, clazz);
	}
	
	/**根据主键获取对象，未找到抛出异常
	 * @param clazz
	 * @param pk
	 * @return
	 */
	public T get(Class<T> clazz,Object pk) {
		return sqlManager.unique(clazz, pk);
	}
	
}
