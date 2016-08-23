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
                    list.add(CharBlock.of(chars, pointer, pointer, CharBlockType.CHAR));
                } else {
                    if (Utils.isValidChar(c)) {
                        buffer.append(c);
                    } else {
                        handleValidCharBuffer(list, buffer, chars, pointer - buffer.length());
                        list.add(CharBlock.of(chars, pointer, pointer, CharBlockType.SKIP));
                    }
                }
                pointer += 1;
            }
            handleValidCharBuffer(list, buffer, chars, chars.length - buffer.length());
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

    protected TermDataFactory getTermFactory() {
        return TermDataFactory.getInstance();
    }

    public boolean isSmart() {
        return smart;
    }

    public void setSmart(boolean smart) {
        this.smart = smart;
    }
}
