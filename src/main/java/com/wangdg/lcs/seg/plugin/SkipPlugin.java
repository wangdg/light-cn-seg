package com.wangdg.lcs.seg.plugin;

import com.wangdg.lcs.seg.ISegmentPlugin;
import com.wangdg.lcs.seg.TermData;
import com.wangdg.lcs.trie.TermType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 忽略词插件
 */
public class SkipPlugin implements ISegmentPlugin {

    private static Set<String> IGNORE_STRINGS = new HashSet<String>();
    private static Set<String> WHITE_LIST = new HashSet<String>();
    static {
        IGNORE_STRINGS.add("送");
        IGNORE_STRINGS.add("赠");
        IGNORE_STRINGS.add("赠送");
        WHITE_LIST.add("同学");
        WHITE_LIST.add("朋友");
        WHITE_LIST.add("家人");
        WHITE_LIST.add("男朋友");
        WHITE_LIST.add("女朋友");
        WHITE_LIST.add("女友");
        WHITE_LIST.add("男友");
        WHITE_LIST.add("闺蜜");
        WHITE_LIST.add("老公");
        WHITE_LIST.add("老婆");
    }

    @Override
    public void doPost(List<TermData> dataList) {
        Set<TermData> removeTerms = new HashSet<TermData>();
        TermData flagTerm = null;
        for (TermData t : dataList) {
            if (IGNORE_STRINGS.contains(t.getTerm())) {
                flagTerm = t;
            } else if (flagTerm != null) {
                if ((t.getType() == TermType.WORD || t.getType() == TermType.SYMBOL_WORD)
                        && !WHITE_LIST.contains(t.getTerm())) {
                    removeTerms.add(t);
                    flagTerm = null;
                }
            }
        }
        dataList.removeAll(removeTerms);
    }

    @Override
    public boolean isOnlyUsedOnSmartMode() {
        return true;
    }
}
