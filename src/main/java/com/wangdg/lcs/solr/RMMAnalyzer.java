package com.wangdg.lcs.solr;

import com.wangdg.lcs.seg.ISegmenter;
import com.wangdg.lcs.seg.concrete.RMMSegmenter;
import com.wangdg.lcs.trie.LCSDictionary;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

import java.io.Reader;

/**
 * RMM分词器 Lucene Analyzer接口实现
 *
 * @author wangdg
 */
public class RMMAnalyzer extends Analyzer {

    /** 分词器 */
    private ISegmenter segmenter;

    public RMMAnalyzer() {
        super();
        LCSDictionary dict = LCSDictionary.loadDefaultDictionary();
        segmenter = new RMMSegmenter(dict);
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        Tokenizer t = new LCSTokenizer(reader, segmenter);
        return new TokenStreamComponents(t);
    }
}
