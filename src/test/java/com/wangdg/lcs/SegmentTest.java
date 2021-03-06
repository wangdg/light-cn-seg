package com.wangdg.lcs;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.wangdg.lcs.common.LCSRuntimeException;
import com.wangdg.lcs.seg.ISegmenter;
import com.wangdg.lcs.seg.TermData;
import com.wangdg.lcs.seg.concrete.FMMSegmenter;
import com.wangdg.lcs.seg.concrete.RMMSegmenter;
import com.wangdg.lcs.trie.DictionaryQueryResult;
import com.wangdg.lcs.trie.LCSDictionary;

import junit.framework.TestCase;

public class SegmentTest extends TestCase {

    private static String TEST_TEXT_01 = "清华同方 笔记本电脑14 15.6寸i5 i7独显游戏商务超锋锐S2 S5炫风";

    private ISegmenter fmm;
    private ISegmenter rmm;

    public SegmentTest() {
        super();
        LCSDictionary dict = LCSDictionary.loadDefaultDictionary();
        fmm = new FMMSegmenter(dict);
        rmm = new RMMSegmenter(dict);
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
        List<TermData> segs = fmm.analyze("男装");
        System.out.println(segs);
    }

    public void test04() {
        URL url = LCSDictionary.class.getResource("/test1.dic");
        File file;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new LCSRuntimeException("Dictionary Init Error!");
        }
        LCSDictionary dict = new LCSDictionary(file);
        FMMSegmenter segmenter = new FMMSegmenter(dict);
        segmenter.setSmart(true);
        List<TermData> segs = segmenter.analyze("男装和v领手机膜iphone 15.9米 送1 送iphone 送男友");
        System.out.println(segs);
    }

    public void test05() {
        URL url = LCSDictionary.class.getResource("/test1.dic");
        File file;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new LCSRuntimeException("Dictionary Init Error!");
        }
        LCSDictionary dict = new LCSDictionary(file);
        DictionaryQueryResult qr = dict.query("v领");
        System.out.println(qr.isContain());
        dict.removeWord("v领");
        qr = dict.query("v领");
        System.out.println(qr.isContain());
        dict.addWord("v领", null);
        qr = dict.query("v领");
        System.out.println(qr.isContain());
    }
}
