package com.wangdg.lcs.solr;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import com.wangdg.lcs.seg.ISegmenter;
import com.wangdg.lcs.seg.SegmenterManager;
import com.wangdg.lcs.seg.TermData;

/**
 * LCS分词器 Lucene Tokenizer适配器类
 * 
 * @author wangdg
 */
public class LCSTokenizer extends Tokenizer {

    /** 词元文本属性 */
    private final CharTermAttribute termAttr;

    /** 词元位移属性 */
    private final OffsetAttribute offsetAttr;

    /** 分析结果列表 */
    private List<TermData> termDataList;

    /** 指针 */
    private int pointer;

    /** 分语策略 */
    private String segmentPolicy;

    public LCSTokenizer(Reader input, String policy) {
        super(input);
        segmentPolicy = policy;
        termAttr = this.addAttribute(CharTermAttribute.class);
        offsetAttr = this.addAttribute(OffsetAttribute.class);
        pointer = 0;
    }

    @Override
    public boolean incrementToken() throws IOException {

        if (termDataList == null) {
            termDataList = this.getTermDataList(input, segmentPolicy);
        }

        this.clearAttributes();
        if (pointer < termDataList.size()) {
            TermData term = termDataList.get(pointer);
            termAttr.append(term.getTerm());
            termAttr.setLength(term.getTerm().length());
            offsetAttr.setOffset(term.getStart(), term.getEnd());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 使用LCS分词
     * 
     * @param reader
     *            文本Reader
     * @param policy
     *            分词策略
     * @return 分词列表
     * @throws IOException
     *             异常
     */
    protected List<TermData> getTermDataList(Reader reader, String policy) throws IOException {

        // 分词列表
        List<TermData> list = new ArrayList<TermData>();
        if (reader == null) {
            return list;
        }

        // 取得分词器
        ISegmenter segmenter = SegmenterManager.getInstance().getSegmenter(policy);

        // 读取文本
        StringBuilder textBuf = new StringBuilder();
        char[] charBuf = new char[521];
        int readSize = 0;
        while ((readSize = reader.read(charBuf, 0, charBuf.length)) > 0) {
            textBuf.append(charBuf, 0, readSize);
        }

        return segmenter.analyze(textBuf.toString());
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        pointer = 0;
    }
}
