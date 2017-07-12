package com.jyoffice.actflow.inter.controller;

import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.activiti.engine.impl.util.ProcessDefinitionUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class TestSgin {

	public static void main(String[] args) {
		
		proess();
		
		
	}
	
	
	public static void proess(){
		Map<String,String> actvar = new HashMap<String, String>();
		actvar.put("days", "5");
		
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put("taskId", "150005");
		//map.put("actvar",actvar);
		//map.put("userId","zhangsi");
		/*
		map.put("appUserId","chenbj");
		map.put("appUserName","陈柏贱");
		map.put("busKey","103");
		map.put("processKey","leval");
		map.put("title","陈柏贱请假流程");*/
		map.put("timestamp", Long.toString(System.currentTimeMillis() / 1000));
		
		StringBuffer bf = new StringBuffer();
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				if(entry.getValue() instanceof Map){
					for (Entry<String, Object> entry2 : ((Map<String, Object>)entry.getValue()).entrySet()) {
						if(entry.getValue().toString().length() > 0){
							bf.append(entry2.getKey()).append("=").append(entry2.getValue()).append("&");
						}
					}
				}else{
					if(entry.getValue().toString().length() > 0){
						bf.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
					}
				}
				
			}
		}
		if (bf.length() > 0)
			bf.deleteCharAt(bf.length() - 1);

		System.out.println("第一次转换:" + bf);
		String md5Str = "";
		try {
			byte [] bytes = Base64.encodeBase64(bf.toString().toLowerCase().getBytes("UTF-8"));
			md5Str = DigestUtils.md5Hex(bytes);
			System.out.println("第二次加密:" + md5Str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
