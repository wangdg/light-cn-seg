package com.wangdg.lcs.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Trie树结点类
 * 
 * @author wangdg
 */
public class TrieNode {

    /** 字符对象 */
    private Character character;

    /** 子节点Map */
    private Map<Character, TrieNode> subNodeMap = new HashMap<Character, TrieNode>();

    /** 是否成词 */
    private boolean word = false;

    /** 用户数据 */
    private Object userData;

    /**
     * 构造方法
     * 
     * @param ch
     *            字符
     * @param isWord
     *            是否成词
     */
    public TrieNode(char ch, boolean isWord) {
        super();
        this.character = Character.valueOf(ch);
        this.word = isWord;
    }

    /**
     * 追加结点
     * 
     * @param node
     *            结点对象
     */
    public void addNode(TrieNode node) {
        if (node != null) {
            subNodeMap.put(node.getCharacter(), node);
        }
    }

    /**
     * 取得结点字符数据
     * 
     * @return 字符数据
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * 通过字符找相应子结点
     * 
     * @param ch
     *            字符
     * @return 子结点对象
     */
    public TrieNode findSubNode(char ch) {
        return subNodeMap.get(Character.valueOf(ch));
    }

    /**
     * 设置是否成词
     * 
     * @param yes
     *            是/否
     */
    public void setIsWord(boolean yes) {
        this.word = yes;
    }

    /**
     * 是否成词
     * 
     * @return 是/否
     */
    public boolean isWord() {
        return word;
    }

    /**
     * 取得用户数据
     * 
     * @return 用户数据
     */
    public Object getUserData() {
        return userData;
    }

    /**
     * 设置用户数据
     * 
     * @param userData
     *            用户数据
     */
    public void setUserData(Object userData) {
        this.userData = userData;
    }
}
