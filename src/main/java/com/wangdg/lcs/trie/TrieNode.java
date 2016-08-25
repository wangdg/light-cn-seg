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
    private UserData userData;

    /** 父结点 */
    private TrieNode parent;

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
            node.setParent(this);
            subNodeMap.put(node.getCharacter(), node);
        }
    }

    /**
     * 删除指定字符结点
     *
     * @param c 指定字符
     */
    public void removeNode(Character c) {
        if (c != null) {
            subNodeMap.remove(c);
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
    public UserData getUserData() {
        return userData;
    }

    /**
     * 设置用户数据
     *
     * @param userData
     *            用户数据
     */
    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    /**
     * 取得子结点个数
     *
     * @return 子结点个数
     */
    public int getSubNodeCount() {
        return subNodeMap.size();
    }

    /**
     * 是否存在子结点
     *
     * @return 是/否
     */
    public boolean hasSubNode() {
        return !subNodeMap.isEmpty();
    }

    /**
     * 取得父结点
     *
     * @return 父结点
     */
    public TrieNode getParent() {
        return parent;
    }

    /**
     * 设置结点的父结点
     *
     * @param parent 父结点
     */
    public void setParent(TrieNode parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return String.valueOf(character);
    }
}
