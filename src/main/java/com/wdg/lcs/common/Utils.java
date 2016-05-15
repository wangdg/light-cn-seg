package com.wdg.lcs.common;

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
	 * @param str 目标字符串
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
}
