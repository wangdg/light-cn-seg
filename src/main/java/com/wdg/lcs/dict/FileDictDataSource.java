package com.wdg.lcs.dict;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import com.wdg.lcs.common.Constants;
import com.wdg.lcs.common.Utils;
import com.wdg.lcs.trie.WordData;

/**
 * 文件字典源
 */
public class FileDictDataSource implements IDictionaryDataSource<String> {

	/** UTF-8 */
	private static Charset UTF_8 = Charset.forName(Constants.UTF_8);
	
	/** 词语集合 */
	private Iterator<String> iterator;
	
	/**
	 * 构造方法
	 * 
	 * @param file 目标文件
	 * @throws IOException IO异常
	 * @throws FileNotFoundException 找不到文件异常 
	 */
	public FileDictDataSource(File file) throws FileNotFoundException, IOException {
		super();
		List<String> lines = Utils.readFileLines(file, UTF_8);
		if (lines != null) {
			iterator = lines.iterator();
		}
	}
	
	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public WordData<String> next() {
		String word = iterator.next();
		WordData<String> data = new WordData<String>();
		data.setText(word);
		data.setUserData(word);
		return data;
	}
}
