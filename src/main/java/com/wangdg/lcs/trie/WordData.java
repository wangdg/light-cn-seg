package com.wangdg.lcs.trie;

/**
 * 词语数据
 */
public class WordData {

    /** 词 */
    private String text;

    /** 用户数据 */
    private Object userData;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getUserData() {
        return userData;
    }

    public void setUserData(Object userData) {
        this.userData = userData;
    }
}
