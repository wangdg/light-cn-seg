package com.wangdg.lcs.seg.concrete;

import java.util.ArrayList;
import java.util.List;

import com.wangdg.lcs.common.Utils;
import com.wangdg.lcs.seg.BaseSegmenter;
import com.wangdg.lcs.seg.TermData;

/**
 * 正向最大配置分词
 */
public class FMMSegmenter extends BaseSegmenter {

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
                        buf.delete(0, buf.length());
                        break;
                    }
                }
                if (pointer < strLength) {
                    continue;
                }
            }

            int length = strLength - pointer;
            TermData data = null;
            for (int l = length; l > 0; l--) {
                if (dict.contains(charArray, pointer, l)) {
                    data = new TermData();
                    data.setTerm(new String(charArray, pointer, l));
                    data.setStart(pointer);
                    data.setEnd(pointer + l - 1);
                    dataList.add(data);
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
                dataList.add(charData);
                pointer += 1;
            }
        }
        return dataList;
    }
}
