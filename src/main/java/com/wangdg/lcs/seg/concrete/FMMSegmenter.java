package com.wangdg.lcs.seg.concrete;

import java.util.ArrayList;
import java.util.List;

import com.wangdg.lcs.common.Utils;
import com.wangdg.lcs.seg.BaseSegmenter;
import com.wangdg.lcs.seg.TermData;
import com.wangdg.lcs.trie.DictionaryQueryResult;
import com.wangdg.lcs.trie.LCSDictionary;
import com.wangdg.lcs.trie.TermType;

/**
 * 正向最大配置分词
 *
 * @author wangdg
 */
public class FMMSegmenter extends BaseSegmenter {

    public FMMSegmenter(LCSDictionary dict) {
        super(dict);
    }

    public FMMSegmenter() {
        super();
    }

    @Override
    public List<TermData> analyze(char[] array) {

        List<TermData> dataList = new ArrayList<TermData>();
        if (array == null || array.length == 0) {
            return dataList;
        }

        char[] charArray = Utils.uniformChars(array);
        int pointer = 0;
        StringBuffer nonCNBuffer = new StringBuffer();

        while (pointer < charArray.length) {

            char c = charArray[pointer];

            // 无效字符
            if (!Utils.isValidChar(c)) {
                this.handleNonCNBuffer(nonCNBuffer,
                        pointer - nonCNBuffer.length(), dataList);
                pointer += 1;
                continue;
            }

            // 非中文字符处理
            if (!Utils.isCommonChinese(c)) {
                nonCNBuffer.append(c);
                pointer += 1;
                continue;
            }

            // 处理非中文Buffer
            this.handleNonCNBuffer(nonCNBuffer,
                    pointer - nonCNBuffer.length(), dataList);

            int length = charArray.length - pointer;
            TermData data = null;
            for (int l = length; l > 0; l--) {
                DictionaryQueryResult qr = dictionary.query(charArray, pointer, l);
                if (qr.isContain()) {
                    data = new TermData();
                    data.setTerm(new String(charArray, pointer, l));
                    data.setStart(pointer);
                    data.setEnd(pointer + l - 1);
                    data.setUserData(qr.getUserData());
                    data.setType(TermType.WORD);
                    dataList.add(data);
                    if (this.isOutputExtraSegments()) {
                        this.handleExtraSegments(data, dataList);
                    }
                    break;
                }
            }
            if (data != null) {
                pointer += data.length();
            } else {
                TermData charData = new TermData();
                charData.setTerm(String.valueOf(c));
                charData.setStart(pointer);
                charData.setEnd(pointer);
                this.fillTermUserDataAndType(charData, dictionary, TermType.CHAR);
                dataList.add(charData);
                pointer += 1;
            }
        }

        // 处理非中文Buffer
        this.handleNonCNBuffer(nonCNBuffer,
                pointer - nonCNBuffer.length(), dataList);

        return dataList;
    }
}
