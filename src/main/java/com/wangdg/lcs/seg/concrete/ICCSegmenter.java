package com.wangdg.lcs.seg.concrete;

import java.util.ArrayList;
import java.util.List;

import com.wangdg.lcs.common.Utils;
import com.wangdg.lcs.seg.BaseSegmenter;
import com.wangdg.lcs.seg.TermData;

/**
 * 中文单词分词算法
 */
public class ICCSegmenter extends BaseSegmenter {

    public ICCSegmenter() {
        super(null);
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

            TermData data = new TermData();
            data.setTerm(String.valueOf(c));
            data.setStart(pointer);
            data.setEnd(pointer);
            data.setUserData(null);
            dataList.add(data);

            pointer += 1;
        }

        // 处理非中文Buffer
        this.handleNonCNBuffer(nonCNBuffer,
                pointer - nonCNBuffer.length(), dataList);

        return dataList;
    }
}
