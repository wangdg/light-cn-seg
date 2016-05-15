package com.wdg.lcs.seg;

import java.util.List;

import com.wdg.lcs.trie.Dictionary;

/**
 * 分词器接口
 */
public interface ISegmenter {

	/**
	 * 对文本进得分词
	 * 
	 * @param text
	 *            目标文本
	 * @return 分词信息列表
	 */
	public List<SegmentData> analyze(String text);
	
	/**
	 * 添加词典
	 * 
	 * @param name 词典名
	 * @param dict 词典对象
	 */
	public void addDictionary(String name, Dictionary dict);
	
	/**
	 * 删除词典
	 * 
	 * @param name 词典名
	 */
	public void removeDictionary(String name);
}
