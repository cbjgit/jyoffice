package com.jyoffice.redis;

import java.util.HashMap;
import java.util.Map;

public class RedisCache {

	public static int RDB0 = 0;
	public static int RDB1 = 1;
	public static int RDB2 = 2;
	public static int RDB3 = 3;

	private static final Map<Integer, RedisDb> cacheMap = new HashMap<Integer, RedisDb>();

	public static synchronized void addRedisDb(RedisDb db) {
		if (db == null)
			throw new IllegalArgumentException("cache can not be null");
		if (cacheMap.containsKey(db.getDbIndex()))
			throw new IllegalArgumentException("cache already exists");

		cacheMap.put(db.getDbIndex(), db);
	}

	public static RedisDb use(String cacheName) {
		return cacheMap.get(cacheName);
	}

	public static void flush(String cacheName) {
		cacheMap.get(cacheName).jedisPool.getResource().flushAll();
	}
}
