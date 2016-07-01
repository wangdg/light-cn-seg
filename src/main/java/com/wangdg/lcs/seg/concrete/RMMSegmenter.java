package com.wangdg.lcs.seg.concrete;

import java.util.ArrayList;
import java.util.List;

import com.wangdg.lcs.common.Utils;
import com.wangdg.lcs.seg.BaseSegmenter;
import com.wangdg.lcs.seg.TermData;
import com.wangdg.lcs.trie.DictionaryQueryResult;
import com.wangdg.lcs.trie.LCSDictionary;

/**
 * 逆向最大配置分词
 *
 * @author wangdg
 */
public class RMMSegmenter extends BaseSegmenter {

    public RMMSegmenter(LCSDictionary dict) {
        super(dict);
    }

    public RMMSegmenter() {
        super();
    }

    @Override
    public List<TermData> analyze(char[] array) {

        List<TermData> dataList = new ArrayList<TermData>();
        if (array == null || array.length == 0) {
            return dataList;
        }

        char[] charArray = Utils.uniformChars(array);
        int pointer = array.length - 1;
        StringBuffer nonCNBuffer = new StringBuffer();

        while (pointer >= 0) {

            char c = charArray[pointer];

            // 无效字符
            if (!Utils.isValidChar(c)) {
                this.handleNonCNBuffer(nonCNBuffer, pointer + 1, dataList);
                pointer -= 1;
                continue;
            }

            // 非中文字符处理
            if (!Utils.isCommonChinese(c)) {
                nonCNBuffer.insert(0, c);
                pointer -= 1;
                continue;
            }

            // 处理非中文Buffer
            this.handleNonCNBuffer(nonCNBuffer, pointer + 1, dataList);

            TermData data = null;
            for (int i = 0; i < charArray.length; i++) {
                DictionaryQueryResult qr = dictionary.query(charArray, i, pointer - i + 1);
                if (qr.isContain()) {
                    data = new TermData();
                    data.setTerm(new String(charArray, i, pointer - i + 1));
                    data.setStart(i);
                    data.setEnd(pointer);
                    data.setUserData(qr.getUserData());
                    dataList.add(data);
                    break;
                }
            }

            if (data != null) {
                pointer -= data.length();
            } else {
                TermData charData = new TermData();
                charData.setTerm(String.valueOf(c));
                charData.setStart(pointer);
                charData.setEnd(pointer);
                charData.setUserData(null);
                dataList.add(charData);
                pointer -= 1;
            }
        }

        // 处理非中文Buffer
        this.handleNonCNBuffer(nonCNBuffer, 0, dataList);

        return dataList;
    }
}
