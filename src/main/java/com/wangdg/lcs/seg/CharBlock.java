package com.wangdg.lcs.seg;

/**
 * 文本块
 */
public class CharBlock {

    /** 字符数组 */
    private char[] charArray;

    /** 开始位置 */
    private int start;

    /** 结束位置 */
    private int end;

    /** 文本块类型 */
    private CharBlockType type;

    public static CharBlock of(char[] chars, int start, int end, CharBlockType type) {
        return new CharBlock(chars, start, end, type);
    }

    public CharBlock(char[] chars, int start, int end, CharBlockType type) {
        super();
        this.charArray = chars;
        this.start = start;
        this.end = end;
        this.type = type;
    }

    /**
     * 合并目标Block，生成新Block
     *
     * @param block 目标Block
     * @return 合成后Block
     */
    public CharBlock join(CharBlock block) {

        if (block == null) {
            return this;
        }

        if (this.charArray != block.getCharArray()) {
            throw new RuntimeException("文本块合并必须基于共同字符数组");
        }

        if (this.end != (block.getStart() + 1)) {
            throw new RuntimeException("只可以合并相邻的文本块");
        }

        return CharBlock.of(this.charArray, this.start, block.end, CharBlockType.JOIN);
    }

    public char[] getCharArray() {
        return charArray;
    }

    public int getStart() {
        return start;
    }

    @Override
    public String toString() {
        String str = new String(charArray, start, end - start + 1);
        return String.format("%s(%d, %d, %s)", str, start, end, type);
    }
}
