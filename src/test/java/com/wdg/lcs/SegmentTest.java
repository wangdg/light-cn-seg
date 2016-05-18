package com.wdg.lcs;

import java.io.File;
import java.util.List;

import com.wdg.lcs.seg.ISegmenter;
import com.wdg.lcs.seg.TermData;
import com.wdg.lcs.seg.concrete.FMMSegmenter;
import com.wdg.lcs.trie.Dictionary;
import com.wdg.lcs.trie.DictionaryFileDataSource;
import com.wdg.lcs.trie.IDictionaryDataSource;

import junit.framework.TestCase;

public class SegmentTest extends TestCase {

	public void test01() {
		IDictionaryDataSource source = new DictionaryFileDataSource(new File("/Users/wangdg/Documents/main.txt"));
		Dictionary dict = new Dictionary(source);
		ISegmenter segmenter = new FMMSegmenter(dict);
		List<TermData> segs = segmenter.analyze("尼康 Ｈ COOLPIX S7000 超薄轻便， 背入式影像传感器");
		System.out.println(segs);
	}
}
