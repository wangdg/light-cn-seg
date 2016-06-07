package com.wangdg.lcs.seg.concrete;

import java.util.ArrayList;
import java.util.List;

import com.wangdg.lcs.common.Utils;
import com.wangdg.lcs.seg.BaseSegmenter;
import com.wangdg.lcs.seg.TermData;
import com.wangdg.lcs.trie.Dictionary;

/**
 * 逆向最大配置分词
 * 
 * @author wangdg
 */
public class RMMSegmenter extends BaseSegmenter {

    public RMMSegmenter(Dictionary dict) {
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
                if (dictionary.contains(charArray, i, pointer - i + 1)) {
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
        
        // 处理非中文Buffer
        this.handleNonCNBuffer(nonCNBuffer, 0, dataList);
        
        return dataList;
    }
}
