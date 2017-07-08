package com.jyoffice.util;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.jyoffice.ca.SessionUtil;

public class RequestUtil<T> {

	public static <T> T getFormData(HttpServletRequest request, T t) {

		Map<String, String[]> value = request.getParameterMap();
		Class<?> clazz = t.getClass();
		try {
			Map<String, Method> methodMap = new HashMap<String, Method>();
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("set")) {
					methodMap.put(method.getName(), method);
				}
			}
			
			String type = null;
			for (Entry<String, String[]> entry : value.entrySet()) {
				String att = entry.getKey();
				att = att.substring(0, 1).toUpperCase() + att.substring(1);

				for (Entry<String, Method> entry2 : methodMap.entrySet()) {
					Method m = entry2.getValue();
					if (entry2.getKey().equals("set" + att)) {
						Class<?>[] pclass = m.getParameterTypes();
						if (pclass.length > 0) {
							type = pclass[0].getName();
							if (type.equals("java.lang.String")) {
								m.invoke(t, entry.getValue()[0]);
							} else if (type.equals("java.lang.Integer")) {
								if (entry.getValue()[0].length() > 0) {
									m.invoke(t, Integer.valueOf(entry.getValue()[0]));
								}else{
									m.invoke(t, 0);
								}
							} else if (type.equals("int")) {
								if (entry.getValue()[0].length() > 0) {
									m.invoke(t, Integer.parseInt(entry.getValue()[0]));
								}else{
									m.invoke(t, 0);
								}
							} else if (type.equals("java.util.Date")) {
								m.invoke(t, new Date(Date.parse(entry.getValue()[0])));
							}
						}
						methodMap.remove(entry2.getKey());
						break;
					}
				}
			}
			
			Method m = clazz.getDeclaredMethod("getId");
			if(m != null){
				Object obj = m.invoke(t);
				if(obj instanceof Integer){
					if((Integer)obj == 0){
						if (methodMap.containsKey("setCreateBy")) {
							m = methodMap.get("setCreateBy");
							m.invoke(t,SessionUtil.getUserId(request));
						}
						if (methodMap.containsKey("setCreateDate")) {
							m = methodMap.get("setCreateDate");
							m.invoke(t,new Date());
						}
					}
					if (methodMap.containsKey("setUpdateBy")) {
						m = methodMap.get("setUpdateBy");
						m.invoke(t,SessionUtil.getUserId(request));
					}
					if (methodMap.containsKey("setUpdateDate")) {
						m = methodMap.get("setUpdateDate");
						m.invoke(t,new Date());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public static String getValues(HttpServletRequest request, String name) {
		return request.getParameter(name);
	}
	
	public static <T> Pager<T> getPager(HttpServletRequest request){
		String page = request.getParameter("page");
		String pagesize = request.getParameter("pagesize");
		Pager<T> pager = new Pager<T>();
		if(pagesize != null && pagesize.trim().length() > 0)
			pager.setPageSize(Integer.parseInt(pagesize));
		if(page != null && page.trim().length() > 0)
			pager.setCurrentPage(Integer.parseInt(page));
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", SessionUtil.getUserId(request));
		Enumeration<String> pname = request.getParameterNames();
		String name= "";
		while(pname.hasMoreElements()){
			name = pname.nextElement();
			if(name.startsWith("ser_")){
				param.put(name.substring(4), request.getParameter(name));
			}
		}
		pager.setParam(param);
		return pager;
	}
}
