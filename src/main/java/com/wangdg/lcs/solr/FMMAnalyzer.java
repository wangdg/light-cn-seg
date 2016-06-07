package com.wangdg.lcs.solr;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

import com.wangdg.lcs.seg.ISegmenter;
import com.wangdg.lcs.seg.concrete.FMMSegmenter;
import com.wangdg.lcs.trie.Dictionary;

/**
 * FMM分词器 Lucene Analyzer接口实现
 * 
 * @author wangdg
 */
public class FMMAnalyzer extends Analyzer {

    /** 分词器 */
    private ISegmenter segmenter;
    
    public FMMAnalyzer() {
        super();
        Dictionary dict = Dictionary.loadDefaultDictionary();
        segmenter = new FMMSegmenter(dict);
    }
    
    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        Tokenizer t = new LCSTokenizer(reader, segmenter);
        return new TokenStreamComponents(t);
    }
}
