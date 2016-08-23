package com.wangdg.lcs.seg.plugin;

import com.wangdg.lcs.seg.ISegmentPlugin;
import com.wangdg.lcs.seg.TermData;
import com.wangdg.lcs.trie.DictionaryQueryResult;
import com.wangdg.lcs.trie.LCSDictionary;
import com.wangdg.lcs.trie.TermType;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认分词处理监听器
 */
public class SymbolPlugin implements ISegmentPlugin {

    /** 词典对象 */
    private LCSDictionary dictionary;

    public SymbolPlugin(LCSDictionary dict) {
        super();
        this.dictionary = dict;
    }

    @Override
    public void doPost(List<TermData> dataList) {
        if (dataList == null) {
            return;
        }
        List<TermData> extraList = new ArrayList<TermData>();
        for (TermData data : dataList) {
            // Symbol处理
            if (data.getType() == TermType.SYMBOL) {
                this.handleSymbolSegments(data, extraList);
            }
        }
        if (!extraList.isEmpty()) {
            dataList.addAll(extraList);
        }
    }

    @Override
    public boolean isUsedOnSmartMode() {
        return true;
    }

    /**
     * 处理SYMBOL类型分词
     *
     * @param data 分词结果
     * @param dataList 导入数据列表
     */
    protected void handleSymbolSegments(TermData data, List<TermData> dataList) {

        if (data == null || data.getType() != TermType.SYMBOL) {
            return;
        }

        char[] array = data.getTerm().toCharArray();
        int pt = 0;
        int length = array.length;

        while (pt < length) {

            int l = length - pt;

            TermData t = null;
            for (int i = 1; i <= l; i++) {
                DictionaryQueryResult qr = dictionary.query(array, pt, i);
                if (qr != null && qr.isContain()) {
                    t = new TermData();
                    t.setTerm(new String(array, pt, i));
                    t.setType(TermType.SYMBOL_WORD);
                    t.setUserData(qr.getUserData());
                    t.setStart(data.getStart() + pt);
                    t.setEnd(t.getStart() + pt + i - 1);
                    dataList.add(t);
                    pt = pt + i;
                    break;
                }
            }
            if (t == null) {
                // 剩下的文本不是原词
                if (pt > 0) {
                    t = new TermData();
                    t.setTerm(new String(array, pt, length - pt));
                    t.setType(TermType.SYMBOL_PART);
                    t.setUserData(null);
                    t.setStart(data.getStart() + pt);
                    t.setEnd(t.getStart() + length - pt - 1);
                    dataList.add(t);
                }
                break;
            }
        }
    }
}
