package com.wdg.lcs;

import java.io.File;
import java.util.List;

import com.wdg.lcs.seg.ISegmenter;
import com.wdg.lcs.seg.TermData;
import com.wdg.lcs.seg.concrete.FMMSegmenter;
import com.wdg.lcs.seg.concrete.RMMSegmenter;
import com.wdg.lcs.trie.Dictionary;
import com.wdg.lcs.trie.DictionaryFileDataSource;
import com.wdg.lcs.trie.IDictionaryDataSource;

import junit.framework.TestCase;

public class SegmentTest extends TestCase {

	public void test01() {
		IDictionaryDataSource source = new DictionaryFileDataSource(new File("/Users/wangdg/Documents/main.txt"));
		Dictionary dict = new Dictionary(source);
		
		String str = "春夏新品韩版男装男士条纹V领短袖T恤青少年休闲紧身打底海魂衫潮";
		
		ISegmenter segmenter = new FMMSegmenter(dict);
		List<TermData> segs = segmenter.analyze(str);
		System.out.println(segs);
		
		segmenter = new RMMSegmenter(dict);
		segs = segmenter.analyze(str);
		System.out.println(segs);
	}
}
