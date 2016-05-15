package com.wdg.lcs;

import java.io.File;
import java.util.List;

import com.wdg.lcs.seg.ISegmenter;
import com.wdg.lcs.seg.SegmentData;
import com.wdg.lcs.seg.SimpleSegmenter;
import com.wdg.lcs.trie.Dictionary;
import com.wdg.lcs.trie.DictionaryFileDataSource;
import com.wdg.lcs.trie.IDictionaryDataSource;

import junit.framework.TestCase;

public class SegmentTest extends TestCase {

	public void test01() {
		IDictionaryDataSource source = new DictionaryFileDataSource(new File("/Users/wangdg/Documents/main.txt"));
		Dictionary dict = new Dictionary(source);
		ISegmenter segmenter = new SimpleSegmenter();
		segmenter.addDictionary(null, dict);
		List<SegmentData> segs = segmenter.analyze("他们觉得我那样很老套，不过我个人比较喜欢能控制并发线程的数量。");
		System.out.println(segs);
	}
}
