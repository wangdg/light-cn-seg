package com.wdg.lcs.seg.concrete;

import java.util.ArrayList;
import java.util.List;

import com.wdg.lcs.seg.BaseSegmenter;
import com.wdg.lcs.seg.TermData;
import com.wdg.lcs.trie.Dictionary;

/**
 * 逆向最大配置分词
 */
public class RMMSegmenter extends BaseSegmenter {

	/** 分词词典 */
	private Dictionary dict;
	
	public RMMSegmenter(Dictionary dict) {
		super();
		this.dict = dict;
	}

	@Override
	public List<TermData> analyze(char[] charArray) {
		List<TermData> dataList = new ArrayList<>();
		if (charArray == null || charArray.length == 0) {
			return dataList;
		}
		int pointer = 0;
		int strLength = charArray.length;
		while (pointer < strLength) {
			int length = strLength - pointer;
			TermData data = null;
			for (int l = length; l > 0; l--) {
				if (dict.contains(charArray, pointer, l)) {
					data = new TermData();
					data.setTerm(new String(charArray, pointer, l));
					data.setStart(pointer);
					data.setEnd(pointer + l - 1);
					dataList.add(data);
					break;
				}
			}
			if (data != null) {
				pointer += data.length();
			} else {
				TermData charData = new TermData();
				charData.setTerm(String.valueOf(charArray[pointer]));
				charData.setStart(pointer);
				charData.setEnd(pointer);
				dataList.add(charData);
				pointer += 1;
			}
		}
		return dataList;
	}
}
