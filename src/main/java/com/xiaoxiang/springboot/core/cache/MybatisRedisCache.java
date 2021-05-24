package com.xiaoxiang.springboot.core.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.xiaoxiang.springboot.core.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.Set;

/**
 * @author Stephen
 */
@Slf4j
public class MybatisRedisCache implements Cache {

    private final String id;

    private RedisTemplate<String, Object> redisTemplate;

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        log.debug("添加缓存");
        if (value != null) {
            getRedisTemplate().opsForValue().set(key.toString(), value);
        }
    }

    @Override
    public Object getObject(Object key) {
        log.debug("获取缓存");
        try {
            if (key != null) {
                return getRedisTemplate().opsForValue().get(key.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("缓存出错 ");
        }
        return null;
    }

    @Override
    public Boolean removeObject(Object key) {
        log.debug("删除缓存");
        if (key != null) {
            return getRedisTemplate().delete(key.toString());
        }
        return false;
    }

    @Override
    public void clear() {
        log.debug("清空缓存");
        Set<String> keys = redisTemplate.keys("*:" + this.id + "*");
        if (!CollectionUtil.isEmpty(keys)) {
            getRedisTemplate().delete(keys);
        }
    }

    @Override
    public int getSize() {
        Long size = getRedisTemplate().execute(RedisServerCommands::dbSize);
        return Optional.ofNullable(size).orElse(0L).intValue();
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return Optional.ofNullable(redisTemplate).orElse(SpringContextUtil.getBean("redisTemplate", RedisTemplate.class));
    }
}
