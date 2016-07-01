package com.wangdg.lcs.seg;

import com.wangdg.lcs.trie.UserData;

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

    /** 补充数据 */
    private UserData userData;

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

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String toString() {
        return String.format("%s(%d, %d)", term, start, end);
    }
}
