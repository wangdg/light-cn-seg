package com.wangdg.lcs.seg;

import java.util.List;
import java.util.Set;

import com.wangdg.lcs.common.Constants;
import com.wangdg.lcs.trie.LCSDictionary;
import com.wangdg.lcs.trie.UserData;

/**
 * 分词器基类
 *
 * @author wangdg
 */
public abstract class BaseSegmenter implements ISegmenter {

    /** 分词词典 */
    protected LCSDictionary dictionary;

    /** 是否输出附加分词 */
    private boolean outputExtraSegments;

    public BaseSegmenter(LCSDictionary dict) {
        super();
        dictionary = dict;
        outputExtraSegments = false;
    }

    public BaseSegmenter() {
        super();
        dictionary = LCSDictionary.loadDefaultDictionary();
        outputExtraSegments = false;
    }

    @Override
    public List<TermData> analyze(String text) {
        if (text != null) {
            return this.analyze(text.toCharArray());
        } else {
            return null;
        }
    }

    /**
     * 处理非中文Buffer
     *
     * @param buf 非中文Buffer
     * @param start 开始位置
     * @param dataList 导入数据列表
     */
    protected void handleNonCNBuffer(StringBuffer buf, int start, List<TermData> dataList) {
        if (buf != null && buf.length() > 0) {
            String term = buf.toString();
            TermData data = new TermData();
            data.setTerm(term);
            data.setStart(start);
            data.setEnd(start + buf.length() - 1);
            data.setUserData(dictionary.getUserData(term));
            dataList.add(data);
            buf.delete(0, buf.length());
        }
    }

    /**
     * 处理附加分词
     *
     * @param data 分词结果
     * @param dataList 导入数据列表
     */
    protected void handleExtraSegments(TermData data, List<TermData> dataList) {
        if (data == null || dataList == null || !isOutputExtraSegments()) {
            return;
        }
        UserData userData = data.getUserData();
        if (userData != null && !userData.isEmpty()) {
            Set<String> extras = (Set<String>) userData.get(Constants.USER_DATA_KEY_EXTRA);
            if (extras == null || extras.isEmpty()) {
                return;
            }
            String term = data.getTerm();
            for (String extra : extras) {
                int index = term.indexOf(extra);
                if (index < 0) {
                    continue;
                }
                TermData termData = new TermData();
                termData.setTerm(extra);
                termData.setStart(data.getStart() + index);
                termData.setEnd(termData.getStart() + extra.length() - 1);
                termData.setUserData(null);
                dataList.add(termData);
            }
        }
    }

    public boolean isOutputExtraSegments() {
        return outputExtraSegments;
    }

    public void setOutputExtraSegments(boolean outputExtraSegments) {
        this.outputExtraSegments = outputExtraSegments;
    }
}
