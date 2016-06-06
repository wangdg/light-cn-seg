package com.wangdg.lcs;

import java.util.List;

import com.wangdg.lcs.seg.ISegmenter;
import com.wangdg.lcs.seg.TermData;
import com.wangdg.lcs.seg.concrete.FMMSegmenter;
import com.wangdg.lcs.seg.concrete.ICCSegmenter;
import com.wangdg.lcs.seg.concrete.RMMSegmenter;

import junit.framework.TestCase;

public class SegmentTest extends TestCase {

    /** 测试文本 */
    private String text01 = "春夏新品韩版男装男士条纹V领短袖T恤青少年休闲紧身打底海魂衫潮";
    
    private ISegmenter fmm = new FMMSegmenter();
    private ISegmenter rmm = new RMMSegmenter();
    private ISegmenter icc = new ICCSegmenter();
    
    public void test01() {
        List<TermData> segs = fmm.analyze(text01);
        System.out.println(segs);
    }
    
    public void test02() {
        List<TermData> segs = rmm.analyze(text01);
        System.out.println(segs);
    }
    
    public void test03() {
        List<TermData> segs = icc.analyze(text01);
        System.out.println(segs);
    }
}
