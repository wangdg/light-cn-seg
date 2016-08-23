package com.wangdg.lcs.seg;

import com.wangdg.lcs.common.Utils;
import com.wangdg.lcs.trie.DictionaryQueryResult;
import com.wangdg.lcs.trie.LCSDictionary;
import com.wangdg.lcs.trie.TermType;

import java.util.ArrayList;
import java.util.List;

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
            this.handleSymbolSegments(data, dataList);
            buf.delete(0, buf.length());
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

    /**
     * 文本分块
     *
     * @param chars 字符数组
     * @return 文本分块列表
     */
    protected static List<CharBlock> getCharBlocks(char[] chars) {
        List<CharBlock> list = new ArrayList<CharBlock>();
        if (chars != null) {
            int pointer = 0;
            StringBuilder buffer = new StringBuilder();
            while (pointer < chars.length) {
                char c = chars[pointer];
                if (Utils.isCommonChinese(c)) {
                    handleValidCharBuffer(list, buffer, chars, pointer - buffer.length());
                    list.add(CharBlock.of(chars, pointer, pointer, CharBlockType.VALID_CHAR));
                } else {
                    if (Utils.isValidChar(c)) {
                        buffer.append(c);
                    } else {
                        handleValidCharBuffer(list, buffer, chars, pointer - buffer.length());
                        list.add(CharBlock.of(chars, pointer, pointer, CharBlockType.INVALID_CHAR));
                    }
                }
                pointer += 1;
            }
        }
        return list;
    }

    private static void handleValidCharBuffer(List<CharBlock> blocks, StringBuilder buf, char[] chars, int start) {
        if (blocks == null || buf == null) {
            return;
        }
        if (buf.length() > 0) {
            blocks.add(CharBlock.of(chars,
                    start,
                    start + buf.length() - 1,
                    CharBlockType.SYMBOL));
            buf.delete(0, buf.length());
        }
    }

    public boolean isSmart() {
        return smart;
    }

    public void setSmart(boolean smart) {
        this.smart = smart;
    }
}
