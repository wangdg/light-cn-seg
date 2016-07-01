package com.wangdg.lcs.solr;

import com.wangdg.lcs.seg.ISegmenter;
import com.wangdg.lcs.seg.concrete.ICCSegmenter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

import java.io.Reader;

/**
 * ICC分词器 Lucene Analyzer接口实现
 *
 * @author wangdg
 */
public class ICCAnalyzer extends Analyzer {

    /** 分词器 */
    private ISegmenter segmenter;

    public ICCAnalyzer() {
        super();
        segmenter = new ICCSegmenter();
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        Tokenizer t = new LCSTokenizer(reader, segmenter);
        return new TokenStreamComponents(t);
    }
}
