package com.wangdg.lcs.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 工具类
 */
public class Utils {

    /**
     * 读取文件内空
     * 
     * @param file
     *            文件对象
     * @return 行内容字符串数组
     * @throws IOException
     */
    public static List<String> readFileLines(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            List<String> list = new ArrayList<String>();
            String line = reader.readLine();
            while (line != null) {
                list.add(line);
                line = reader.readLine();
            }
            return list;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
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

    /**
     * 判断字符串是不是空
     * 
     * @param str
     *            目标字符串
     * @return 是/否
     */
    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        if ("".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符是否为数字
     * 
     * @param c
     *            字符
     * @return 是/否
     */
    public static boolean isDigit(char c) {
        return isFullWidthDigit(c) || isHalfWidthDigit(c);
    }

    private static boolean isFullWidthDigit(char c) {
        return 0xff10 <= c && c <= 0xff19;
    }

    private static boolean isHalfWidthDigit(char c) {
        return '0' <= c && c <= '9';
    }

    /**
     * 字符是否为常用汉字
     * 
     * @param c
     *            字符
     * @return 是/否
     */
    public static boolean isCommonChinese(char c) {
        if (0x4e00 <= c && c <= 0x9fa5) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符是否为英文字符
     * 
     * @param c
     *            字符
     * @return 是/否
     */
    public static boolean isEnglishLetter(char c) {
        return isFullWidthEnglishLetter(c) || isHalfWidthEnglishLetter(c);
    }

    private static boolean isFullWidthEnglishLetter(char c) {
        return (0xff41 <= c && c <= 0xff5a) || (0xff21 <= c && c <= 0xff3a);
    }

    private static boolean isHalfWidthEnglishLetter(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
    }

    /**
     * 字符是否有效
     * 
     * @param c
     *            字符
     * @return 是/否
     */
    public static boolean isValidChar(char c) {
        return isDigit(c) || isCommonChinese(c) || isEnglishLetter(c);
    }

    /**
     * 标准化字符串
     * 
     * @return 字符串
     */
    public static String uniformString(String str) {
        if (str == null) {
            return null;
        }
        return new String(uniformChars(str.toCharArray()));
    }

    /**
     * 标准化字符数组
     * 
     * @return 字符数组
     */
    public static char[] uniformChars(char[] array) {

        if (array == null) {
            return null;
        }

        int length = array.length;
        char[] retArray = new char[length];

        for (int i = 0; i < length; i++) {
            char c = array[i];
            if (isFullWidthDigit(c)) { // 全角数字
                c = (char) (c + ('a' - 0xff41));
            } else if (isFullWidthEnglishLetter(c)) { // 全角英文
                c = (char) (c + ('0' - 0xff10));
            }
            if ('A' <= c && c <= 'Z') {
                c = (char) (c + ('a' - 'A'));
            }
            retArray[i] = c;
        }
        return retArray;
    }
}
