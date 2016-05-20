package com.wangdg.lcs.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * 词典类
 */
public class Dictionary {

    /** Tree Map */
    private Map<Character, TrieNode> treeMap = new HashMap<Character, TrieNode>();

    /**
     * 通过文件构建词典
     * 
     * @param file
     *            文件对象
     */
    public Dictionary(IDictionaryDataSource source) {
        super();
        while (source.hasNext()) {
            WordData wordData = source.next();
            this.addWord(wordData.getText(), wordData.getUserData());
        }
    }

    /**
     * 追加词
     * 
     * @param word
     *            词
     */
    public void addWord(String word, Object userData) {

        if (word != null) {

            String trim = word.trim();
            if (trim.length() <= 0) {
                return;
            }

            char[] charArray = trim.toCharArray();

            TrieNode node = treeMap.get(Character.valueOf(charArray[0]));
            if (node == null) {
                boolean isWord = (charArray.length == 1);
                node = new TrieNode(charArray[0], isWord);
                if (isWord) {
                    node.setUserData(userData);
                }
                treeMap.put(node.getCharacter(), node);
            }

            int wordLength = trim.length();
            for (int i = 1; i < wordLength; i++) {
                boolean isWord = (i == (wordLength - 1));
                TrieNode subNode = node.findSubNode(charArray[i]);
                if (subNode == null) {
                    subNode = new TrieNode(charArray[i], isWord);
                    node.addNode(subNode);
                } else {
                    if (isWord) {
                        subNode.setIsWord(isWord);
                        subNode.setUserData(userData);
                    }
                }
                node = subNode;
            }
        }
    }

    /**
     * 是否包含词
     * 
     * @param word
     *            词
     * @return 是/否
     */
    public boolean contains(String word) {
        if (word != null) {
            char[] charArray = word.toCharArray();
            return this.contains(charArray, 0, charArray.length);
        } else {
            return false;
        }
    }

    /**
     * 是否包含词
     * 
     * @param array
     *            字符数组
     * @param start
     *            开始位置
     * @param length
     *            长度
     * @return 是/否
     */
    public boolean contains(char[] array, int start, int length) {
        if (array == null || length <= 0 || start < 0) {
            return false;
        }
        TrieNode node = treeMap.get(Character.valueOf(array[start]));
        if (node != null) {
            for (int i = start + 1; i < start + length; i++) {
                node = node.findSubNode(array[i]);
                if (node == null) {
                    return false;
                }
            }
            if (node.isWord()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
