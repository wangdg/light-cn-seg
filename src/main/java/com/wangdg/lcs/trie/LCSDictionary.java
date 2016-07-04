package com.wangdg.lcs.trie;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangdg.lcs.common.DictionaryInitException;
import com.wangdg.lcs.common.Utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 词典类
 *
 * @author wangdg
 */
public class LCSDictionary {

    /** Tree Map */
    private Map<Character, TrieNode> treeMap = new HashMap<Character, TrieNode>();

    public static LCSDictionary loadDefaultDictionary() {
        URL url = LCSDictionary.class.getResource("/main.dic");
        File file;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new DictionaryInitException("Dictionary Init Error!");
        }
        return new LCSDictionary(file);
    }

    public LCSDictionary() {
        super();
    }

    /**
     * 通过文件构建词典
     *
     * @param file 词典文件
     */
    public LCSDictionary(File file) {
        super();
        this.initializeDictionary(file);
    }

    /**
     * 用文件初始化词典
     *
     * @param file 文件
     */
    protected void initializeDictionary(File file) {

        if (file == null || !file.isFile()) {
            return;
        }

        InputStream in = null;
        try {
            in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in , "UTF-8"), 512);
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!Utils.isBlank(trimmed) && !trimmed.startsWith("#")) {
                    String[] array = trimmed.split(":");
                    String word = array[0];
                    UserData userData = null;
                    if (array.length > 1) {
                        String json = array[1];
                        try {
                            JSONObject jsonObject = JSON.parseObject(json);
                            Set<String> keys = jsonObject.keySet();
                            if (!keys.isEmpty()) {
                                userData = new UserData();
                                for (String key : keys) {
                                    userData.put(key, jsonObject.get(key));
                                }
                            }
                        } catch (Exception e) {
                            // do nothing
                        }
                    }
                    this.addWord(word, userData);
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new DictionaryInitException("Dictionary Init Error!");
        } catch (IOException e) {
            throw new DictionaryInitException("Dictionary Init Error!");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

    /**
     * 追加词
     *
     * @param word 词
     * @param userData 附加数据
     */
    public void addWord(String word, UserData userData) {

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
        DictionaryQueryResult result = this.query(word);
        return result.isContain();
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
        DictionaryQueryResult result = this.query(array, start, length);
        return result.isContain();
    }

    /**
     * 取得词的附加信息
     * 如果词在词典中不存在，返回NULL
     *
     * @param word 词
     * @return 附加信息
     */
    public UserData getUserData(String word) {
        DictionaryQueryResult result = this.query(word);
        return result.getUserData();
    }

    /**
     * 取得词的附加信息
     * 如果词在词典中不存在，返回NULL
     *
     * @param array
     *            字符数组
     * @param start
     *            开始位置
     * @param length
     *            长度
     * @return 附加信息
     */
    public UserData getUserData(char[] array, int start, int length) {
        DictionaryQueryResult result = this.query(array, start, length);
        return result.getUserData();
    }

    /**
     * 查询词典
     *
     * @param array
     *            字符数组
     * @param start
     *            开始位置
     * @param length
     *            长度
     * @return 查询结果
     */
    public DictionaryQueryResult query(char[] array, int start, int length) {
        DictionaryQueryResult result = new DictionaryQueryResult();
        result.setContain(false);
        result.setUserData(null);
        if (array == null || length <= 0 || start < 0) {
            return result;
        }
        TrieNode node = treeMap.get(Character.valueOf(array[start]));
        if (node != null) {
            for (int i = start + 1; i < start + length; i++) {
                node = node.findSubNode(array[i]);
                if (node == null) {
                    return result;
                }
            }
            if (node.isWord()) {
                result.setContain(true);
                result.setUserData(node.getUserData());
                return result;
            } else {
                return result;
            }
        } else {
            return result;
        }
    }

    /**
     * 查询词典
     *
     * @param word 词
     * @return 查询结果
     */
    public DictionaryQueryResult query(String word) {
        if (word != null) {
            char[] charArray = word.toCharArray();
            return this.query(charArray, 0, charArray.length);
        } else {
            return this.query(null, 0, 0);
        }
    }
}