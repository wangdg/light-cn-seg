package com.wangdg.lcs.trie;

/**
 * 词语数据
 *
 * @author wangdg
 */
public class WordData {

    /** 词 */
    private String text;

    /** 用户数据 */
    private UserData userData;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
