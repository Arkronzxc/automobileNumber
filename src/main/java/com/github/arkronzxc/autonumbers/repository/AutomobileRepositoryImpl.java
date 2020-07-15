package com.github.arkronzxc.autonumbers.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AutomobileRepositoryImpl implements AutomobileRepository {

    private final StringRedisTemplate template;

    public AutomobileRepositoryImpl(@Qualifier("customRedis") StringRedisTemplate template) {
        this.template = template;
    }

    @Override
    public boolean hasKey(Integer key) {
        Boolean hasKey = template.hasKey(String.valueOf(key));
        if (hasKey == null) {
            return false;
        }
        return hasKey;
    }

    @Override
    public boolean setIfAbsent(Integer key, String value) {
        Boolean ok = template.opsForValue().setIfAbsent(String.valueOf(key), value);
        if (ok == null) {
            return false;
        }
        return ok;
    }

    @Override
    public void set(Integer key, String value) {
        template.opsForValue().set(String.valueOf(key), value);
    }
}