package com.wangdg.lcs.trie;

/**
 * 词典查询结果
 */
public class DictionaryQueryResult {

    /** 是否包含 */
    private boolean contain;

    /** 附加信息 */
    private UserData userData;

    public boolean isContain() {
        return contain;
    }

    public void setContain(boolean contain) {
        this.contain = contain;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
