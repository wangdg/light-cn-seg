package com.wangdg.lcs.trie;

import com.wangdg.lcs.common.LCSRuntimeException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 词典管理器
 */
public class LCSDictionaryManager {

    /** Single Instance */
    private static LCSDictionaryManager instance = new LCSDictionaryManager();

    /** 词典Map */
    private Map<Object, LCSDictionary> dictMap = new HashMap<Object, LCSDictionary>();

    private LCSDictionaryManager() {
        super();
    }

    public static LCSDictionaryManager getInstance() {
        return instance;
    }

    public LCSDictionary loadDictionary(File file) {
        LCSDictionary dict = dictMap.get(file);
        if (dict == null) {
            dict = this.createDictionary(file);
            dictMap.put(file, dict);
        }
        return dict;
    }

    protected LCSDictionary createDictionary(File file) {
        File loadFile = file;
        if (loadFile == null) {
            InputStream in = getClass().getResourceAsStream("/main.dic");
            try {
                return new LCSDictionary(in);
            } catch (IOException e) {
                throw new LCSRuntimeException("Create Dictionary Exception.");
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // do thing
                    }
                }
            }
        }
        return new LCSDictionary(loadFile);
    }



    public void putDictionary(Object key, LCSDictionary dict) {
        dictMap.put(key, dict);
    }

    public void removeDictionary(Object key) {
        dictMap.remove(key);
    }
}
