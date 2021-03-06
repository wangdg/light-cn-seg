package com.wangdg.lcs.trie;

import com.wangdg.lcs.common.LCSRuntimeException;
import com.wangdg.lcs.common.Utils;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 词典类
 *
 * @author wangdg
 */
public class LCSDictionary {

    /** Tree Map */
    private Map<Character, TrieNode> treeMap = new HashMap<Character, TrieNode>();

    /** 锁对象 */
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static LCSDictionary loadDefaultDictionary() {
        InputStream is = null;
        try {
            is = LCSDictionary.class.getResourceAsStream("/main.dic");
            return new LCSDictionary(is);
        } catch (IOException e) {
            throw new LCSRuntimeException("读取默认词典IO异常");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
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
        initializeQuantifiers();
        initializeDictionary(file);
    }

    /**
     * 通过输入流构建词典
     *
     * @param in 输入流
     * @throws IOException
     */
    public LCSDictionary(InputStream in) throws IOException {
        super();
        initializeQuantifiers();
        initializeDictionary(in);
    }

    /**
     * 通过文件构建词典
     *
     * @param filePath 词典文件路径
     */
    public LCSDictionary(String filePath) {
        super();
        initializeQuantifiers();
        initializeDictionary(new File(filePath));
    }

    protected void initializeQuantifiers() {
        BufferedReader reader = null;
        String line = null;
        try {
            InputStream in = LCSDictionary.class.getResourceAsStream("/quantifier.dic");
            reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) {
                if (!Utils.isBlank(line)) {
                    UserData userData = new UserData();
                    userData.put(LCSUserDataKey.QUANTIFIER, Boolean.TRUE);
                    this.addWord(line.trim(), userData);
                }
            }
        } catch (Exception e) {
            throw new LCSRuntimeException("Dictionary Quantifiers Init Exception.");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

    protected void initializeDictionary(File file) {
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            this.initializeDictionary(in);
        } catch (FileNotFoundException e) {
            throw new LCSRuntimeException("Dictionary Init Exception.");
        } catch (IOException e) {
            throw new LCSRuntimeException("Dictionary Init Exception.");
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
     * 用文件初始化词典
     *
     * @param in 输入流
     */
    protected void initializeDictionary(InputStream in) throws IOException {

        if (in == null) {
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(in , "UTF-8"), 512);
        String line;
        while ((line = reader.readLine()) != null) {
            String trimmed = line.trim();
            if (Utils.isBlank(trimmed) || trimmed.startsWith("#")) {
                continue;
            }
            this.addWord(trimmed, null);
        }
    }

    /**
     * 追加词
     *
     * @param word 词
     * @param userData 附加数据
     */
    public void addWord(String word, UserData userData) {

        if (word == null) {
            return;
        }

        lock.writeLock().lock();

        try {

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

            if (wordLength == 1) {
                node.setIsWord(true);
                node.setUserData(userData);
            }

            for (int i = 1; i < wordLength; i++) {
                boolean isWord = (i == (wordLength - 1));
                TrieNode subNode = node.findSubNode(charArray[i]);
                if (subNode == null) {
                    subNode = new TrieNode(charArray[i], isWord);
                    node.addNode(subNode);
                }
                if (isWord) {
                    subNode.setIsWord(isWord);
                    subNode.setUserData(userData);
                }
                node = subNode;
            }

        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 删除词
     *
     * @param word 词
     */
    public void removeWord(String word) {

        if (word == null) {
            return;
        }

        lock.writeLock().lock();

        try {

            String trimmed = word.trim();
            DictionaryQueryResult qr = this.query(trimmed);
            if (qr.isContain()) {
                TrieNode node = qr.getTrieNode();
                if (node.hasSubNode()) { // 存在子结点
                    node.setIsWord(false);
                    node.setUserData(null);
                } else { // 不存在子结点
                    while (true) {
                        TrieNode parent = node.getParent();
                        if (parent != null) {
                            if (parent.getSubNodeCount() > 1
                                    || parent.isWord()) {
                                break;
                            }
                        } else {
                            break;
                        }
                        node = parent;
                    }
                    if (node.getParent() != null) {
                        node.getParent().removeNode(node.getCharacter());
                    } else {
                        treeMap.remove(node.getCharacter());
                    }
                }
            }

        } finally {
            lock.writeLock().unlock();
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

        lock.readLock().lock();

        try {

            DictionaryQueryResult result = new DictionaryQueryResult();
            result.setContain(false);
            result.setUserData(null);
            if (array == null || length <= 0 || start < 0) {
                return result;
            }

            TrieNode node = treeMap.get(Character.valueOf(array[start]));

            if (node != null) {

                if (length == 1 && node.isWord()) {
                    result.setContain(true);
                    result.setUserData(node.getUserData());
                    result.setTrieNode(node);
                    return result;
                }

                for (int i = start + 1; i < start + length; i++) {
                    node = node.findSubNode(array[i]);
                    // 没找到下一结点时
                    if (node == null) {
                        return result;
                    }
                }
                if (node.isWord()) {
                    result.setContain(true);
                    result.setUserData(node.getUserData());
                    result.setTrieNode(node);
                    return result;
                } else {
                    return result;
                }
            } else {
                return result;
            }

        } finally {
            lock.readLock().unlock();
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
