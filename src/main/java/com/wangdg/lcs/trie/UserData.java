package com.wangdg.lcs.trie;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 用户数据类
 */
public class UserData {

    private Map<String, Object> map = new HashMap<String, Object>();

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key, Object val) {
        lock.writeLock().lock();
        try {
            map.put(key, val);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Object get(String key) {
        lock.readLock().lock();
        try {
            return map.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean isEmpty() {
        lock.readLock().lock();
        try {
            return map.isEmpty();
        } finally {
            lock.readLock().unlock();
        }
    }

    public Map<String, Object> map() {
        return map;
    }
}
