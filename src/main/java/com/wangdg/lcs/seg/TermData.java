package com.wangdg.lcs.seg;

import com.wangdg.lcs.trie.TermType;
import com.wangdg.lcs.trie.UserData;

/**
 * 分词单元信息
 *
 * @author wangdg
 */
public class TermData implements Comparable<TermData>, Cloneable {

    /** 分词文本 */
    private String term;

    /** 开始位置 */
    private int start;

    /** 结束位置 */
    private int end;

    /** 补充数据 */
    private UserData userData;

    /** 分词类型 */
    private TermType type;

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

    public TermType getType() {
        return type;
    }

    public void setType(TermType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s(%d, %d, %s)", term, start, end, type);
    }

    @Override
    public int compareTo(TermData o) {
        if (o != null) {
            if (this.getStart() > o.getStart()) {
                return 1;
            } else if (this.getStart() < o.getStart()) {
                return -1;
            } else {
                return 0;
            }
        } else {
            return 1;
        }
    }

    public TermData clone() {
        TermData t;
        try {
            t = (TermData) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
        return t;
    }
}
