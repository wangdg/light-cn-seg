package com.wangdg.lcs.seg;

import java.util.List;

/**
 * 分词器接口
 *
 * @author wangdg
 */
public interface ISegmenter {

    /**
     * 对文本进得分词
     *
     * @param text
     *            目标文本
     * @return 分词信息列表
     */
    List<TermData> analyze(String text);

    /**
     * 对文本进得分词
     *
     * @param array
     *            目标文本
     * @return 分词信息列表
     */
    List<TermData> analyze(char[] array);

    /**
     * 添加分词插件
     *
     * @param plugin 分词插件
     */
    void addPlugin(ISegmentPlugin plugin);
}
