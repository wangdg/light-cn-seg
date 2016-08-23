package com.wangdg.lcs.seg;

import com.wangdg.lcs.trie.DictionaryQueryResult;
import com.wangdg.lcs.trie.LCSDictionary;
import com.wangdg.lcs.trie.TermType;

/**
 * TermData工厂类
 */
public class TermDataFactory {

    /**
     * Single Instance
     */
    private static TermDataFactory instance = new TermDataFactory();

    private TermDataFactory() {
        super();
    }

    public static TermDataFactory getInstance() {
        return instance;
    }

    public TermData create(char[] array, int start, int end, DictionaryQueryResult qr) {

        if (qr == null || array == null) {
            return null;
        }

        TermData data = new TermData();
        data.setTerm(new String(array, start, end - start + 1));
        data.setStart(start);
        data.setEnd(end);
        data.setUserData(qr.getUserData());
        data.setType(TermType.WORD);
        return data;
    }

    public TermData create(CharBlock block, LCSDictionary dict) {

        if (block == null) {
            return null;
        }

        TermData data = new TermData();
        data.setTerm(new String(
                block.getCharArray(),
                block.getStart(),
                block.length()));

        data.setStart(block.getStart());
        data.setEnd(block.getEnd());

        DictionaryQueryResult qr = null;
        if (dict != null) {
            qr = dict.query(
                    block.getCharArray(),
                    block.getStart(),
                    block.length());
        }

        if (qr.isContain()) {
            data.setUserData(qr.getUserData());
        } else {
            data.setUserData(null);
        }

        CharBlockType charType = block.getType();
        if (charType == CharBlockType.CHAR) {
            if (qr.isContain()) {
                data.setType(TermType.WORD);
            } else {
                data.setType(TermType.CHAR);
            }
        } else {
            data.setType(TermType.SYMBOL);
        }

        return data;
    }
}
