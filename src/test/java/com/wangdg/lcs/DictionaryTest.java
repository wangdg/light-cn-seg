package com.wangdg.lcs;

import java.io.File;

import com.wangdg.lcs.trie.Dictionary;
import com.wangdg.lcs.trie.DictionaryFileDataSource;
import com.wangdg.lcs.trie.IDictionaryDataSource;

import junit.framework.TestCase;

public class DictionaryTest extends TestCase {

    public void test01() {
        IDictionaryDataSource source = new DictionaryFileDataSource(new File("/Users/wangdg/Documents/main.txt"));
        Dictionary dict = new Dictionary(source);
        System.out.println(dict.contains("北京"));
    }
}
