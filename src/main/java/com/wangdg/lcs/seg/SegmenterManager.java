package com.wangdg.lcs.seg;

import java.util.HashMap;
import java.util.Map;

import com.wangdg.lcs.common.SegmentPolicy;
import com.wangdg.lcs.seg.concrete.FMMSegmenter;
import com.wangdg.lcs.seg.concrete.ICCSegmenter;
import com.wangdg.lcs.seg.concrete.RMMSegmenter;

/**
 * 分词器管理类
 */
public class SegmenterManager {

    /** FMM分词功能对象 */
    private static ISegmenter fmmSegmenter = new FMMSegmenter();
    
    /** FMM分词功能对象 */
    private static ISegmenter rmmSegmenter = new RMMSegmenter();
    
    /** FMM分词功能对象 */
    private static ISegmenter iccSegmenter = new ICCSegmenter();

    /** 分词功能对象Map */
    private static Map<String, ISegmenter> segmenterMap = new HashMap<String, ISegmenter>();
    static {
        segmenterMap.put(SegmentPolicy.FMM, fmmSegmenter);
        segmenterMap.put(SegmentPolicy.RMM, rmmSegmenter);
        segmenterMap.put(SegmentPolicy.ICC, iccSegmenter);
    }
    
    /** Single Instance */
    private static SegmenterManager instance = new SegmenterManager();
    
    private SegmenterManager() {
        super();
    }
    
    public static SegmenterManager getInstance() {
        return instance;
    }
    
    public ISegmenter getSegmenter(String policy) {
        return segmenterMap.get(policy);
    }
}
