package com.wangdg.lcs.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户数据类
 */
public class UserData {

    private Map<String, Object> map = new HashMap<String, Object>();

    public void put(String key, Object val) {
        map.put(key, val);
    }

    public Object get(String key) {
        return map.get(key);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Map<String, Object> map() {
        return map;
    }
}
