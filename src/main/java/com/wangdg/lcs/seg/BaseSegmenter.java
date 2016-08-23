package com.wangdg.lcs.seg;

import com.wangdg.lcs.common.Utils;
import com.wangdg.lcs.seg.plugin.SymbolPlugin;
import com.wangdg.lcs.trie.LCSDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * 分词器基类
 *
 * @author wangdg
 */
public abstract class BaseSegmenter implements ISegmenter {

    /** 分词词典 */
    protected LCSDictionary dictionary;

    /** 插件列表 */
    private List<ISegmentPlugin> plugins = new ArrayList<ISegmentPlugin>();

    /** 细力度分词 */
    protected boolean smart;

    public BaseSegmenter(LCSDictionary dict) {
        super();
        dictionary = dict;
        smart = false;
        this.initializeDefaultPlugins();
    }

    public BaseSegmenter() {
        super();
        dictionary = LCSDictionary.loadDefaultDictionary();
        smart = false;
        this.initializeDefaultPlugins();
    }

    private void initializeDefaultPlugins() {
        ISegmentPlugin symbolPlugin = new SymbolPlugin(dictionary);
        this.addPlugin(symbolPlugin);
    }

    @Override
    public List<TermData> analyze(String text) {
        return this.analyze(text.toCharArray());
    }

    @Override
    public List<TermData> analyze(char[] array) {
        if (array != null) {
            List<TermData> dataList = this.doAnalysis(array);
            for (ISegmentPlugin plugin : plugins) {
                if (plugin.isUsedOnSmartMode()) {
                    if (isSmart()) {
                        plugin.doPost(dataList);
                    }
                } else {
                    plugin.doPost(dataList);
                }
            }
            return dataList;
        } else {
            return null;
        }
    }

    @Override
    public void addPlugin(ISegmentPlugin plugin) {
        if (plugin != null) {
            plugins.add(plugin);
        }
    }

    /**
     * 处理过程
     *
     * @param array 文本字符数组
     * @return 分词数据列表
     */
    protected abstract List<TermData> doAnalysis(char[] array);

    /**
     * 文本分块
     *
     * @param chars 字符数组
     * @return 文本分块列表
     */
    protected static List<CharBlock> getCharBlocks(char[] chars) {
        List<CharBlock> list = new ArrayList<CharBlock>();
        if (chars != null) {
            int pointer = 0;
            StringBuilder buffer = new StringBuilder();
            while (pointer < chars.length) {
                char c = chars[pointer];
                if (Utils.isCommonChinese(c)) {
                    handleValidCharBuffer(list, buffer, chars, pointer - buffer.length());
                    list.add(CharBlock.of(chars, pointer, pointer, CharBlockType.CHAR));
                } else {
                    if (Utils.isValidChar(c)) {
                        buffer.append(c);
                    } else {
                        handleValidCharBuffer(list, buffer, chars, pointer - buffer.length());
                        list.add(CharBlock.of(chars, pointer, pointer, CharBlockType.SKIP));
                    }
                }
                pointer += 1;
            }
            handleValidCharBuffer(list, buffer, chars, chars.length - buffer.length());
        }
        return list;
    }

    private static void handleValidCharBuffer(List<CharBlock> blocks, StringBuilder buf, char[] chars, int start) {
        if (blocks == null || buf == null) {
            return;
        }
        if (buf.length() > 0) {
            blocks.add(CharBlock.of(chars,
                    start,
                    start + buf.length() - 1,
                    CharBlockType.SYMBOL));
            buf.delete(0, buf.length());
        }
    }

    /**
     * 返回TermData生产工厂对象
     *
     * @return 工厂对象
     */
    protected TermDataFactory getTermFactory() {
        return TermDataFactory.getInstance();
    }

    public boolean isSmart() {
        return smart;
    }

    public void setSmart(boolean smart) {
        this.smart = smart;
    }
}
