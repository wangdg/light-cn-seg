package com.wangdg.lcs.trie;

/**
 * 字典数据源接口
 * 
 * @author wangdg
 */
public interface IDictionaryDataSource {

    /**
     * 是否有下一个数据
     * 
     * @return 是/否
     */
    public boolean hasNext();

    /**
     * 取得下一条数据
     * 
     * @return 下一条数据
     */
    public WordData next();
}
