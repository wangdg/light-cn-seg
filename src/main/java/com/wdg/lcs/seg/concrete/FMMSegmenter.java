package com.wdg.lcs.seg.concrete;

import java.util.ArrayList;
import java.util.List;

import com.wdg.lcs.common.Utils;
import com.wdg.lcs.seg.BaseSegmenter;
import com.wdg.lcs.seg.TermData;
import com.wdg.lcs.trie.Dictionary;

/**
 * 正向最大配置分词
 */
public class FMMSegmenter extends BaseSegmenter {

	/** 分词词典 */
	private Dictionary dict;
	
	public FMMSegmenter(Dictionary dict) {
		super();
		this.dict = dict;
	}
	
	@Override
	public List<TermData> analyze(char[] array) {
		
		List<TermData> dataList = new ArrayList<>();
		if (array == null || array.length == 0) {
			return dataList;
		}
		
		char[] charArray = Utils.uniformChars(array);
		int pointer = 0;
		int strLength = charArray.length;
		
		StringBuilder invalidBuffer = new StringBuilder();
		
		while (pointer < strLength) {
			
			char c = charArray[pointer];
			if (!Utils.isValidChar(c)) {
				invalidBuffer.append(c);
				pointer += 1;
				continue;
			}
			
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
				charData.setTerm(String.valueOf(c));
				charData.setStart(pointer);
				charData.setEnd(pointer);
				dataList.add(charData);
				pointer += 1;
			}
		}
		return dataList;
	}
}
