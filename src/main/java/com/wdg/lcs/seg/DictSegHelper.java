package com.wdg.lcs.seg;

import java.util.ArrayList;
import java.util.List;

import com.wdg.lcs.trie.Dictionary;

/**
 * 词典分词工具类
 */
public class DictSegHelper {

	/**
	 * 正向最大匹配算法
	 * 
	 * @param str
	 *            文本
	 * @param dict
	 *            词典
	 * @return 分词结果
	 */
	public static List<SegmentData> fmm(String str, Dictionary dict) {
		List<SegmentData> dataList = new ArrayList<>();
		int pointer = 0;
		int strLength = str.length();
		while (pointer < strLength) {
			int length = strLength - pointer;
			SegmentData data = null;
			for (int l = length; l > 0; l--) {
				String sub = str.substring(pointer, pointer + l);
				if (dict.contains(sub)) {
					data = new SegmentData();
					data.setSegment(sub);
					data.setStart(pointer);
					data.setEnd(pointer + l - 1);
					dataList.add(data);
					break;
				}
			}
			if (data != null) {
				pointer += data.length();
			} else {
				SegmentData charData = new SegmentData();
				charData.setSegment(String.valueOf(str.charAt(pointer)));
				charData.setStart(pointer);
				charData.setEnd(pointer);
				dataList.add(charData);
				pointer += 1;
			}
		}
		return dataList;
	}
}
