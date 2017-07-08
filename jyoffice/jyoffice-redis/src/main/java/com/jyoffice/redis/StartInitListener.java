package com.jyoffice.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class StartInitListener implements ApplicationListener<ContextRefreshedEvent> {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/*@Autowired
	RedisConfig redisConfig;*/

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (event.getApplicationContext().getParent() == null) {
			/*	log.info("正在连接redis:"+redisConfig.getHost() +":" +redisConfig.getPort());
			
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(20);
			config.setMaxIdle(5);
			config.setMaxWaitMillis(1000l);
			config.setTestOnBorrow(true);
			JedisPool jedisPool = new JedisPool(config, redisConfig.getHost(), redisConfig.getPort(),
					redisConfig.getTimeout(), redisConfig.getPassword(), RedisCache.RDB0);
			RedisDb db = new RedisDb(RedisCache.RDB0, jedisPool);
			jedisPool.getResource().ping();
			RedisCache.addRedisDb(db);*/

			System.out.println("系统启动完成..........");
		}
	}
}
