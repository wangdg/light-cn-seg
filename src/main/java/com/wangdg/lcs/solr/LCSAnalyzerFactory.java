package com.wangdg.lcs.solr;

import com.wangdg.lcs.common.LCSRuntimeException;
import com.wangdg.lcs.seg.ISegmenter;
import com.wangdg.lcs.seg.concrete.FMMSegmenter;
import com.wangdg.lcs.seg.concrete.ICCSegmenter;
import com.wangdg.lcs.seg.concrete.RMMSegmenter;
import com.wangdg.lcs.trie.LCSDictionary;
import com.wangdg.lcs.trie.LCSDictionaryManager;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeSource;

import java.io.File;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Lucene Analyzer Factory
 */
public class LCSAnalyzerFactory extends TokenizerFactory {

    /** 是否输出附加分词 */
    private boolean extra = false;

    /** 算法 */
    private String alg;

    /** 词典名 */
    private String dict;

    /** 分词器Map */
    private Map<String, ISegmenter> segmenterMap = new HashMap<String, ISegmenter>();

    public LCSAnalyzerFactory(Map<String, String> args) {

        super(args);
        assureMatchVersion();
        this.setExtra("true".equals(args.get("extra")));
        this.setAlg(args.get("alg"));
        this.setDict(args.get("dict"));

        // 读取词典
        File file = null;
        if (dict != null) {
            URL url = getClass().getClassLoader().getResource(dict);
            try {
                file = new File(url.toURI());
            } catch (URISyntaxException e) {
                throw new LCSRuntimeException("Dictionary Init Error!");
            }
        }
        LCSDictionary dict = LCSDictionaryManager.getInstance().loadDictionary(file);

        // FMM
        FMMSegmenter fmm = new FMMSegmenter(dict);
        fmm.setOutputExtraSegments(extra);
        segmenterMap.put("fmm", fmm);

        // RMM
        RMMSegmenter rmm = new RMMSegmenter(dict);
        rmm.setOutputExtraSegments(extra);
        segmenterMap.put("rmm", rmm);

        // ICC
        ICCSegmenter icc = new ICCSegmenter();
        segmenterMap.put("icc", icc);
    }

    @Override
    public Tokenizer create(AttributeSource.AttributeFactory factory, Reader input) {
        ISegmenter segmenter = segmenterMap.get(alg);
        Tokenizer _IKTokenizer = new LCSTokenizer(input, segmenter);
        return _IKTokenizer;
    }

    protected void setExtra(boolean extra) {
        this.extra = extra;
    }

    protected void setAlg(String alg) {
        this.alg = alg;
    }

    protected void setDict(String dict) {
        this.dict = dict;
    }
}
