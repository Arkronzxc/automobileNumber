package com.github.arkronzxc.autonumbers.repository;

public interface AutomobileRepository {

    boolean hasKey(Integer key);

    boolean setIfAbsent(Integer key, String value);

    void set(Integer key, String value);
}
