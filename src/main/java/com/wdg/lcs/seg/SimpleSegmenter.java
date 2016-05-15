package com.wdg.lcs.seg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wdg.lcs.common.Utils;
import com.wdg.lcs.trie.Dictionary;

/**
 * 简单分词器类
 */
public class SimpleSegmenter implements ISegmenter {

	/** 字典Map */
	private Map<String, Dictionary> dictMap = new HashMap<>();

	/** 字典权重Map */
	private Map<String, Float> weightMap = new HashMap<>();

	@Override
	public List<SegmentData> analyze(String text) {
		if (!Utils.isBlank(text)) {
			Map<String, List<SegmentData>> resultMap = new HashMap<>();
			dictMap.forEach((name, dict) -> {
				List<SegmentData> segList = DictSegHelper.fmm(text, dict);
				resultMap.put(name, segList);
			});
			return this.mergeSegmentResults(resultMap);
		} else {
			return new ArrayList<>();
		}
	}

	protected List<SegmentData> mergeSegmentResults(Map<String, List<SegmentData>> results) {
		List<SegmentData> list = new ArrayList<>();
		results.forEach((name, subList) -> {
			list.addAll(subList);
		});
		return list;
	}

	@Override
	public void addDictionary(String name, Dictionary dict) {
		if (dict != null) {
			dictMap.put(name, dict);
			weightMap.put(name, 1.0f);
		}
	}

	public void setDictionaryWeight(String name, float weight) {
		weightMap.put(name, 1.0f);
	}

	@Override
	public void removeDictionary(String name) {
		dictMap.remove(name);
		weightMap.remove(name);
	}
}
