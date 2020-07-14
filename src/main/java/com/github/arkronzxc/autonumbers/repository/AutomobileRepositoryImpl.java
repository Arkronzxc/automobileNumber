package com.github.arkronzxc.autonumbers.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AutomobileRepositoryImpl implements AutomobileRepository {

    private final RedisTemplate<Integer, String> template;

    public AutomobileRepositoryImpl(@Qualifier("customRedis") RedisTemplate<Integer, String> template) {
        this.template = template;
    }

    @Override
    public boolean hasKey(Integer key) {
        Boolean hasKey = template.hasKey(key);
        if (hasKey == null) {
            return false;
        }
        return hasKey;
    }

    @Override
    public boolean setIfAbsent(Integer key, String value) {
        Boolean ok = template.opsForValue().setIfAbsent(key, value);
        if (ok == null) {
            return false;
        }
        return ok;
    }

    @Override
    public void set(Integer key, String value) {
        template.opsForValue().set(key, value);
    }
}
