package com.wangdg.lcs.seg;

/**
 * 分词单元信息
 * 
 * @author wangdg
 */
public class TermData {

    /** 分词文本 */
    private String term;

    /** 开始位置 */
    private int start;

    /** 结束位置 */
    private int end;

    public static TermData create(String term, int start, int end) {
        if (term != null) {
            TermData data = new TermData();
            data.setTerm(term);
            data.setStart(start);
            data.setEnd(end);
            return data;
        } else {
            return null;
        }
    }
    
    public int length() {
        if (term != null) {
            return term.length();
        } else {
            return 0;
        }
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return String.format("%s(%d, %d)", term, start, end);
    }
}
