package com.wangdg.lcs.seg;

import java.util.List;

import com.wangdg.lcs.trie.LCSDictionary;

/**
 * 分词器基类
 *
 * @author wangdg
 */
public abstract class BaseSegmenter implements ISegmenter {

    /** 分词词典 */
    protected LCSDictionary dictionary;

    public BaseSegmenter(LCSDictionary dict) {
        super();
        dictionary = dict;
    }

    public BaseSegmenter() {
        super();
        dictionary = LCSDictionary.loadDefaultDictionary();
    }

    @Override
    public List<TermData> analyze(String text) {
        if (text != null) {
            return this.analyze(text.toCharArray());
        } else {
            return null;
        }
    }

    /**
     * 处理非中文Buffer
     *
     * @param buf 非中文Buffer
     * @param start 开始位置
     * @param dataList 导入数据列表
     */
    protected void handleNonCNBuffer(StringBuffer buf, int start, List<TermData> dataList) {
        if (buf != null && buf.length() > 0) {
            dataList.add(TermData.create(buf.toString(),
                    start,
                    start + buf.length() - 1));
            buf.delete(0, buf.length());
        }
    }
}
