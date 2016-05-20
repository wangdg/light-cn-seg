package com.wangdg.lcs.trie;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.wangdg.lcs.common.DataInitException;
import com.wangdg.lcs.common.Utils;

/**
 * 文件字典源
 */
public class DictionaryFileDataSource implements IDictionaryDataSource {

    /** 词语集合 */
    private Iterator<String> iterator;

    /**
     * 构造方法
     * 
     * @param file
     *            目标文件
     * @throws DataInitException
     *             数据初始化异常
     */
    public DictionaryFileDataSource(File file) throws DataInitException {
        super();
        List<String> lines = Utils.readFileLines(file);
        if (lines != null) {
            iterator = lines.iterator();
        } else {
            throw new DataInitException("Dictionary Init Error!");
        }
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public WordData next() {
        String word = iterator.next();
        WordData data = new WordData();
        data.setText(word);
        data.setUserData(word);
        return data;
    }
}
