package com.wangdg.lcs;

import java.util.List;

import com.wangdg.lcs.seg.ISegmenter;
import com.wangdg.lcs.seg.TermData;
import com.wangdg.lcs.seg.concrete.FMMSegmenter;
import com.wangdg.lcs.seg.concrete.RMMSegmenter;

import junit.framework.TestCase;

public class SegmentTest extends TestCase {

    public void test01() {

        String str = "春夏新品韩版男装男士条纹V领短袖T恤青少年休闲紧身打底海魂衫潮";

        ISegmenter segmenter = new FMMSegmenter();
        List<TermData> segs = segmenter.analyze(str);
        System.out.println(segs);

        segmenter = new RMMSegmenter();
        segs = segmenter.analyze(str);
        System.out.println(segs);
    }
}
