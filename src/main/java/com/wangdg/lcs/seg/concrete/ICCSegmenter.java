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

    @Override
    public List<TermData> analyze(char[] array) {

        List<TermData> dataList = new ArrayList<TermData>();
        if (array == null || array.length == 0) {
            return dataList;
        }

        char[] charArray = Utils.uniformChars(array);
        int pointer = 0;
        int strLength = charArray.length;

        while (pointer < strLength) {

            char c = charArray[pointer];

            // 无效字符
            if (!Utils.isValidChar(c)) {
                pointer += 1;
                continue;
            }

            // 非中文字符处理
            if (!Utils.isCommonChinese(c)) {
                StringBuilder buf = new StringBuilder();
                buf.append(c);
                while (pointer < strLength) {
                    pointer += 1;
                    char cc = charArray[pointer];
                    if (Utils.isValidChar(cc) && !Utils.isCommonChinese(cc)) {
                        buf.append(cc);
                    } else {
                        TermData data = new TermData();
                        data.setTerm(buf.toString());
                        data.setStart(pointer - buf.length());
                        data.setEnd(pointer - 1);
                        dataList.add(data);
                        break;
                    }
                }
                if (pointer < strLength) {
                    continue;
                }
            }

            TermData data = TermData.create(String.valueOf(c), pointer, pointer);
            dataList.add(data);
            
            pointer += 1;
        }
        return dataList;

    }
}
