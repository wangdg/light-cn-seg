package com.wangdg.lcs;

import java.util.List;

import com.wangdg.lcs.seg.ISegmenter;
import com.wangdg.lcs.seg.TermData;
import com.wangdg.lcs.seg.concrete.FMMSegmenter;
import com.wangdg.lcs.seg.concrete.ICCSegmenter;
import com.wangdg.lcs.seg.concrete.RMMSegmenter;
import com.wangdg.lcs.trie.Dictionary;

import junit.framework.TestCase;

public class SegmentTest extends TestCase {

    private static String TEST_TEXT_01 = "清华同方 笔记本电脑14 15.6寸i5 i7独显游戏商务超锋锐S2 S5炫风";
    
    private ISegmenter fmm;
    private ISegmenter rmm;
    private ISegmenter icc;
    
    public SegmentTest() {
        super();
        Dictionary dict = Dictionary.loadDefaultDictionary();
        fmm = new FMMSegmenter(dict);
        rmm = new RMMSegmenter(dict);
        icc = new ICCSegmenter();
    }
    
    public void test01() {
        List<TermData> segs = fmm.analyze(TEST_TEXT_01);
        System.out.println(segs);
    }
    
    public void test02() {
        List<TermData> segs = rmm.analyze(TEST_TEXT_01);
        System.out.println(segs);
    }
    
    public void test03() {
        List<TermData> segs = icc.analyze(TEST_TEXT_01);
        System.out.println(segs);
    }
}
