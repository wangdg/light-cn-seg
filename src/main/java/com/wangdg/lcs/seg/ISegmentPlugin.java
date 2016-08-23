package com.wangdg.lcs.seg;

import java.util.List;

/**
 * 分词器监听接口
 */
public interface ISegmentPlugin {

    /**
     * 分词过程结束时调用
     *
     * @param dataList 分词数据列表
     */
    void doPost(List<TermData> dataList);

    /**
     * 是否只作用于smart模式
     *
     * @return 是/否
     */
    boolean isUsedOnSmartMode();
}
