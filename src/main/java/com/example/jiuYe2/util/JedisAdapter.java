package com.example.jiuYe2.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Component
public class JedisAdapter implements InitializingBean {

    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/0");
    }

    // 插入数据
    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 用完关闭jedis
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    // 删除数据
    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 用完关闭jedis
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    // 获取总数
    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 用完关闭jedis
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    // 是否包含
    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 用完关闭jedis
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

}
