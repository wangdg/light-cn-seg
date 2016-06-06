package com.wangdg.lcs.solr;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

/**
 * LCS分词器 Lucene Analyzer接口实现
 * 
 * @author wangdg
 */
public class LCSAnalyzer extends Analyzer {

    /** 分词策略 */
    private String policy;
    
    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        Tokenizer t = new LCSTokenizer(reader, policy);
        return new TokenStreamComponents(t);
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }
}
