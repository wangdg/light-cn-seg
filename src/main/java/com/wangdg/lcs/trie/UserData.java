package com.wangdg.lcs.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户数据类
 */
public class UserData {

    private Map<String, String> map = new HashMap<String, String>();

    public void put(String key, String val) {
        map.put(key, val);
    }

    public String get(String key) {
        return map.get(key);
    }
}
