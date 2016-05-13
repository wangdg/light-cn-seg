package com.wdg.lcs.dict;

import com.wdg.lcs.trie.WordData;

/**
 * 字典数据源接口
 */
public interface IDictionaryDataSource<T> {

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
	public WordData<T> next();
}
