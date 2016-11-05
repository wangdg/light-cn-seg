package com.wangdg.lcs.seg.plugin;

import com.wangdg.lcs.common.LCSRuntimeException;
import com.wangdg.lcs.common.Utils;
import com.wangdg.lcs.seg.ISegmentPlugin;
import com.wangdg.lcs.seg.TermData;
import com.wangdg.lcs.trie.TermType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 同义词插件
 */
public class SynonymPlugin implements ISegmentPlugin {

    /** 逗号常量 */
    private static final String COMMA = ",";

    /** 尖头 */
    private static final String ARROW = "=>";

    /** 空格 */
    private static final String SPACE = " ";

    /** 空白 */
    private static final String EMPTY = "";

    /** 初始化IO异常 */
    private static final String ERROR_INIT_IO = "加载同义词文件IO异常";

    /** 扩展词Map */
    private Map<String, Set<String>> extWordMap = new HashMap<String, Set<String>>();

    /** 统一词Map */
    private Map<String, String> unifyWordMap = new HashMap<String, String>();

    /**
     * 构造方法
     *
     * @param file 同义词配置文件
     */
    public SynonymPlugin(File file) {
        super();
        try {
            this.initializeWithFile(file);
        } catch (IOException e) {
            throw new LCSRuntimeException(ERROR_INIT_IO);
        }
    }

    /**
     * 构造方法
     */
    public SynonymPlugin() {
        super();
    }

    protected void initializeWithFile(File file) throws IOException {

        if (file == null) {
            return;
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        while ((line = reader.readLine()) != null) {

            String trimmed = line.trim();
            if (Utils.isBlank(trimmed)) {
                continue;
            }

            if (trimmed.contains(COMMA)) { // 统一词

                String[] array = trimmed.split(COMMA);
                List<String> list = new ArrayList<String>();
                for (String word : array) {
                    String trimmedWord = word.trim();
                    if (!EMPTY.equals(trimmedWord)) {
                        list.add(trimmedWord);
                    }
                }

                int size = list.size();
                if (size > 1) {
                    String uniWord = list.get(0);
                    for (int i = 1; i < size; i++) {
                        unifyWordMap.put(list.get(i), uniWord);
                    }
                }

            } else if (trimmed.contains(ARROW)) { // 扩展词

                String[] array = trimmed.split(ARROW);
                if (array.length <= 1) {
                    continue;
                }

                String origin = array[0].trim();
                if (EMPTY.equals(origin)) {
                    continue;
                }

                Set<String> words = new HashSet<String>();
                String[] wordArray = array[1].split(SPACE);
                for (String word : wordArray) {
                    String trimmedWord = word.trim();
                    if (!EMPTY.equals(trimmedWord)) {
                        words.add(trimmedWord);
                    }
                }

                if (!words.isEmpty()) {
                    extWordMap.put(origin, words);
                }
            }
        }

        reader.close();
    }

    public void load(File file) {
        try {
            this.initializeWithFile(file);
        } catch (IOException e) {
            throw new LCSRuntimeException(ERROR_INIT_IO);
        }
    }

    @Override
    public void doPost(List<TermData> dataList) {

        Set<TermData> addWords = new HashSet<TermData>();

        for (TermData t : dataList) {

            // 扩展词
            Set<String> words = extWordMap.get(t.getTerm());
            if (words != null) {
                for (String word : words) {
                    TermData tt = t.clone();
                    tt.setTerm(word);
                    t.setType(TermType.SYNONYM);
                    addWords.add(tt);
                }
            }

            // 统一词
            String unifyWord = unifyWordMap.get(t.getTerm());
            if (unifyWord != null) {
                t.setTerm(unifyWord);
            }
        }

        dataList.addAll(addWords);
    }

    @Override
    public boolean isOnlyUsedOnSmartMode() {
        return false;
    }
}
