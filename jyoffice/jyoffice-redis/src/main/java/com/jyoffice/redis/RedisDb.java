package com.jyoffice.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

public class RedisDb {

	int dbIndex;
	JedisPool jedisPool;

	public RedisDb(int dbIndex, JedisPool jedisPool) {
		this.dbIndex = dbIndex;
		this.jedisPool = jedisPool;
	}

	/**根据Key返回字符串
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		Jedis jedis = getJedis();
		try{
			return jedis.get(key);
		}
		finally {jedis.close();}
	}
	
	/**存储字符串
	 * @param key
	 * @param value
	 * @return
	 */
	public String setString(String key,String value){
		Jedis jedis = getJedis();
		try{
			return jedis.set(key,value);
		}
		finally {jedis.close();}
	}
	
	/**返回一个对象
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T>T getObject(String key) {
		Jedis jedis = getJedis();
		try{
			return (T)valueFromBytes(jedis.get(keyToBytes(key)));
		}
		finally {jedis.close();}
	}
	
	/**存储一个对象
	 * @param key
	 * @return
	 */
	public String setObject(String key,Object value) {
		Jedis jedis = getJedis();
		try{
			return jedis.set(keyToBytes(key), valueToBytes(value));
		}
		finally {jedis.close();}
	}
	
	
	/**添加对象到集合
	 * @param key
	 * @param value
	 * @return
	 */
	public Long setToList(String key,Object value){
		Jedis jedis = getJedis();
		try{
			return jedis.lpush(keyToBytes(key), valueToBytes(value));
		}
		finally {jedis.close();}
	}
	/**根据Key获取集合
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T>T getList(String key){
		Jedis jedis = getJedis();
		List<Object> result = new ArrayList<Object>();
		try{
			List<byte[]> list = jedis.lrange(keyToBytes(key), 0, -1);
			for(byte[] b : list){
				result.add(valueFromBytes(b));
			}
			return (T)result;
		}
		finally {jedis.close();}
	}
	
	/**根据Key删除数据
	 * @param key
	 * @return
	 */
	public Long delKey(String key){
		Jedis jedis = getJedis();
		try{
			return jedis.del(key);
		}
		finally {jedis.close();}
	}
	
	/**存储到Hash
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public long setHash(String key,String field,String value){
		Jedis jedis = getJedis();
		try{
			return jedis.hset(key, field, value);
		}
		finally {jedis.close();}
	}
	
	/**返回Hash
	 * @param key
	 * @return
	 */
	public Map<String,String> getHash(String key) {
		Jedis jedis = getJedis();
		try{
			return jedis.hgetAll(key);
		}
		finally {jedis.close();}
	}
	
	/**返回Hash中的值
	 * @param key
	 * @return
	 */
	public String getHashStr(String key,String field) {
		Jedis jedis = getJedis();
		try{
			return jedis.hget(key, field);
		}
		finally {jedis.close();}
	}
	
	public Jedis getJedis() {
		Jedis jedis = jedisPool.getResource();
		jedis.auth("redis");
		return jedis;
	}

	public int getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	public byte[] keyToBytes(String key) {
		return SafeEncoder.encode(key);
	}
	
	public String keyFromBytes(byte[] bytes) {
		return SafeEncoder.encode(bytes);
	}
	
	
	public byte[] valueToBytes(Object value) {
		ObjectOutputStream objectOut = null;
		try {
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			objectOut = new ObjectOutputStream(bytesOut);
			objectOut.writeObject(value);
			objectOut.flush();
			return bytesOut.toByteArray();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if(objectOut != null)
				try {objectOut.close();} catch (Exception e) {}
		}
	}
	
	public String valueToJson(Object value) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(value);
		}
		catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Object valueFromBytes(byte[] bytes) {
		if(bytes == null || bytes.length == 0)
			return null;
		
		ObjectInputStream objectInput = null;
		try {
			ByteArrayInputStream bytesInput = new ByteArrayInputStream(bytes);
			objectInput = new ObjectInputStream(bytesInput);
			return objectInput.readObject();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if (objectInput != null)
				try {objectInput.close();} catch (Exception e) {}
		}
	}
}
