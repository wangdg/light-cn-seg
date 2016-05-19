package com.wdg.lcs.seg;

import java.io.File;
import java.util.List;

import com.wdg.lcs.trie.Dictionary;
import com.wdg.lcs.trie.DictionaryFileDataSource;
import com.wdg.lcs.trie.IDictionaryDataSource;

/**
 * 分词器基类
 */
public abstract class BaseSegmenter implements ISegmenter {

    /** 分词词典 */
    protected Dictionary dict;

    public BaseSegmenter() {
        super();
        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource("lcg-main.txt").getFile();
        IDictionaryDataSource source = new DictionaryFileDataSource(new File(path));
        this.init(source);
    }

    public BaseSegmenter(IDictionaryDataSource source) {
        super();
        this.init(source);
    }

    protected void init(IDictionaryDataSource source) {
        dict = new Dictionary(source);
    }

    @Override
    public List<TermData> analyze(String text) {
        if (text != null) {
            return this.analyze(text.toCharArray());
        } else {
            return null;
        }
    }
}
