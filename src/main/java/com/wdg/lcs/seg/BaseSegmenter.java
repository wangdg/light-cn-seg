package com.wdg.lcs.seg;

import java.util.List;

/**
 * 分词器基类
 */
public abstract class BaseSegmenter implements ISegmenter {

	@Override
	public List<TermData> analyze(String text) {
		if (text != null) {
			return this.analyze(text.toCharArray());
		} else {
			return null;
		}
	}
}
