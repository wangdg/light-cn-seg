package com.wangdg.lcs.seg.concrete;

import java.util.ArrayList;
import java.util.List;

import com.wangdg.lcs.common.Utils;
import com.wangdg.lcs.seg.BaseSegmenter;
import com.wangdg.lcs.seg.TermData;

/**
 * 逆向最大配置分词
 * 
 * @author wangdg
 */
public class RMMSegmenter extends BaseSegmenter {

    @Override
    public List<TermData> analyze(char[] array) {

        List<TermData> dataList = new ArrayList<TermData>();
        if (array == null || array.length == 0) {
            return dataList;
        }

        char[] charArray = Utils.uniformChars(array);
        int pointer = array.length - 1;

        while (pointer >= 0) {

            char c = charArray[pointer];

            // 无效字符
            if (!Utils.isValidChar(c)) {
                pointer -= 1;
                continue;
            }

            // 非中文字符处理
            if (!Utils.isCommonChinese(c)) {
                StringBuilder buf = new StringBuilder();
                buf.insert(0, c);
                while (pointer >= 0) {
                    pointer -= 1;
                    char cc = charArray[pointer];
                    if (Utils.isValidChar(cc) && !Utils.isCommonChinese(cc)) {
                        buf.insert(0, cc);
                    } else {
                        TermData data = TermData.create(
                                buf.toString(), pointer - 1, pointer + buf.length());
                        dataList.add(data);
                        break;
                    }
                }
                if (pointer >= 0) {
                    continue;
                }
            }

            TermData data = null;
            for (int i = 0; i < charArray.length; i++) {
                if (dict.contains(charArray, i, pointer - i + 1)) {
                    data = new TermData();
                    data.setTerm(new String(charArray, i, pointer - i + 1));
                    data.setStart(i);
                    data.setEnd(pointer);
                    dataList.add(data);
                    break;
                }
            }
            
            if (data != null) {
                pointer -= data.length();
            } else {
                TermData charData = TermData.create(String.valueOf(c), pointer, pointer);
                dataList.add(charData);
                pointer -= 1;
            }
        }
        return dataList;
    }
}
