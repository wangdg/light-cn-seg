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

    public int length() {
        return this.end - this.start + 1;
    }

    public char[] getCharArray() {
        return charArray;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public CharBlockType getType() {
        return type;
    }

    @Override
    public String toString() {
        String str = new String(charArray, start, end - start + 1);
        return String.format("%s(%d, %d, %s)", str, start, end, type);
    }
}
