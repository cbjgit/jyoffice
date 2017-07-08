package com.jyoffice.ca;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class RedisShiroCacheManager implements CacheManager {

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		System.out.println("name;"+name);
		return null;
	}
}
