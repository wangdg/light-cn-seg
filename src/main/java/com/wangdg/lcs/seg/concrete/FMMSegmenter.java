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

        List<CharBlock> blocks = getCharBlocks(charArray);
        int blockSize = blocks.size();
        int pointer = 0;

        while (pointer < blockSize) {

            CharBlock sb = blocks.get(pointer);
            if (sb.getType() == CharBlockType.SKIP) {
                pointer += 1;
                continue;
            }

            TermData data = null;
            int pointerDelta = 1;

            for (int i = blockSize - 1; i > pointer; i--) {

                CharBlock eb = blocks.get(i);

                int length = eb.getEnd() - sb.getStart() + 1;
                DictionaryQueryResult qr = dictionary.query(
                        charArray,
                        sb.getStart(),
                        length);

                if (qr.isContain()) {
                    data = getTermFactory().create(
                            charArray, sb.getStart(), eb.getEnd(), qr);
                    dataList.add(data);
                    pointerDelta = i - pointer + 1;
                    break;
                }
            }

            // 单字符块处理
            if (data == null) {
                data = getTermFactory().create(sb, dictionary);
                dataList.add(data);
            }

            // 处理Symbol分词
            handleSymbolSegments(data, dataList);

            pointer += pointerDelta;
        }

        return dataList;
    }
}
