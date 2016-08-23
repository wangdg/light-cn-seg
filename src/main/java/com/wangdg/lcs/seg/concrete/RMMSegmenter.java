package com.wangdg.lcs.seg.concrete;

import com.wangdg.lcs.common.Utils;
import com.wangdg.lcs.seg.BaseSegmenter;
import com.wangdg.lcs.seg.CharBlock;
import com.wangdg.lcs.seg.CharBlockType;
import com.wangdg.lcs.seg.TermData;
import com.wangdg.lcs.trie.DictionaryQueryResult;
import com.wangdg.lcs.trie.LCSDictionary;

import java.util.ArrayList;
import java.util.List;

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

        List<CharBlock> blocks = getCharBlocks(charArray);
        int blockSize = blocks.size();
        int pointer = blockSize - 1;

        while (pointer >= 0) {

            CharBlock eb = blocks.get(pointer);
            if (eb.getType() == CharBlockType.SKIP) {
                pointer -= 1;
                continue;
            }

            TermData data = null;
            int pointerDelta = 1;

            for (int i = 0; i < pointer; i++) {

                CharBlock sb = blocks.get(i);

                int length = eb.getEnd() - sb.getStart() + 1;
                DictionaryQueryResult qr = dictionary.query(
                        charArray,
                        sb.getStart(),
                        length);

                if (qr.isContain()) {
                    data = getTermFactory().create(
                            charArray, sb.getStart(), eb.getEnd(), qr);
                    dataList.add(data);
                    pointerDelta = pointer - i + 1;
                    break;
                }
            }

            // 单字符块处理
            if (data == null) {
                data = getTermFactory().create(eb, dictionary);
                dataList.add(data);
            }

            // 处理Symbol分词
            handleSymbolSegments(data, dataList);

            pointer -= pointerDelta;
        }

        return dataList;
    }
}
