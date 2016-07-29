package com.wangdg.lcs.seg;

import java.util.List;
import java.util.Set;

import com.wangdg.lcs.common.Constants;
import com.wangdg.lcs.common.Utils;
import com.wangdg.lcs.trie.DictionaryQueryResult;
import com.wangdg.lcs.trie.LCSDictionary;
import com.wangdg.lcs.trie.TermType;
import com.wangdg.lcs.trie.UserData;

/**
 * 分词器基类
 *
 * @author wangdg
 */
public abstract class BaseSegmenter implements ISegmenter {

    /** 分词词典 */
    protected LCSDictionary dictionary;

    /** 细力度分词 */
    protected boolean smart;

    public BaseSegmenter(LCSDictionary dict) {
        super();
        dictionary = dict;
        smart = false;
    }

    public BaseSegmenter() {
        super();
        dictionary = LCSDictionary.loadDefaultDictionary();
        smart = false;
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
            String term = buf.toString();
            TermData data = new TermData();
            data.setTerm(term);
            data.setStart(start);
            data.setEnd(start + buf.length() - 1);
            this.fillTermUserDataAndType(data, dictionary, TermType.SYMBOL);
            dataList.add(data);
            this.handleExtraSegments(data, dataList);
            this.handleSymbolSegments(data, dataList);
            buf.delete(0, buf.length());
        }
    }

    /**
     * 处理附加分词
     *
     * @param data 分词结果
     * @param dataList 导入数据列表
     */
    protected void handleExtraSegments(TermData data, List<TermData> dataList) {
        if (data == null || dataList == null || !isSmart()) {
            return;
        }
        UserData userData = data.getUserData();
        if (userData != null && !userData.isEmpty()) {
            Set<String> extras = (Set<String>) userData.get(Constants.USER_DATA_KEY_EXTRA);
            if (extras == null || extras.isEmpty()) {
                return;
            }
            String term = data.getTerm();
            for (String extra : extras) {
                int index = term.indexOf(extra);
                if (index < 0) {
                    continue;
                }
                TermData termData = new TermData();
                termData.setTerm(extra);
                termData.setStart(data.getStart() + index);
                termData.setEnd(termData.getStart() + extra.length() - 1);
                termData.setUserData(null);
                termData.setType(TermType.EXTRA);
                dataList.add(termData);
            }
        }
    }

    /**
     * 处理SYMBOL类型分词
     *
     * @param data 分词结果
     * @param dataList 导入数据列表
     */
    protected void handleSymbolSegments(TermData data, List<TermData> dataList) {

        if (data == null || data.getType() != TermType.SYMBOL || !isSmart()) {
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

    /**
     * 添充分词Data中的userData和type
     *
     * @param data 分词数据
     * @param dict 词典
     * @param defType 词典中不存在使用哪种type
     */
    protected void fillTermUserDataAndType(TermData data, LCSDictionary dict, TermType defType) {
        if (data == null || dict == null) {
            return;
        }
        String term = data.getTerm();
        if (term != null) {
            DictionaryQueryResult qr = dict.query(term);
            if (qr != null && qr.isContain()) {
                data.setUserData(qr.getUserData());
                data.setType(TermType.WORD);
            } else {
                data.setUserData(null);
                data.setType(defType);
            }
        }
    }

    public boolean isSmart() {
        return smart;
    }

    public void setSmart(boolean smart) {
        this.smart = smart;
    }
}
