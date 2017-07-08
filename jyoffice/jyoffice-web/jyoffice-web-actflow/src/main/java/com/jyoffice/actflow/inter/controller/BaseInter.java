package com.jyoffice.actflow.inter.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseInter {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * @param map
	 * @param signMsg
	 *            key1=value1&key2=value2
	 * @return
	 */
	boolean validSign(TreeMap<String, Object> map, String signMsg) {

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

		log.info("第一次转换:" + bf);
		String md5Str = "";
		try {
			
			byte [] bytes = Base64.encodeBase64(bf.toString().toLowerCase().getBytes("UTF-8"));
			
			md5Str = DigestUtils.md5Hex(bytes);
			
		} catch (UnsupportedEncodingException e) {
			log.error("md5加密错误：", e);
		}
		log.info("第二次加密:" + md5Str);

		return md5Str.equals(signMsg);
	}

	<T> T jsonToBean(String json, Class<T> cls) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return (T) mapper.readValue(json, cls);
		} catch (IOException e) {
			log.error("转Object出错：",e);
		}

		return null;
	}
}
