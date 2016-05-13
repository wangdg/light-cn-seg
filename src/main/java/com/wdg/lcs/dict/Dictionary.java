package com.wdg.lcs.dict;

import java.util.HashMap;
import java.util.Map;

import com.wdg.lcs.trie.TrieNode;
import com.wdg.lcs.trie.WordData;

/**
 * 词典类
 */
public class Dictionary<T> {

	/** Tree Map */
	private Map<Character, TrieNode<T>> treeMap = new HashMap<>();
	
	/**
	 * 通过文件构建词典
	 * 
	 * @param file 文件对象
	 */
	public Dictionary(IDictionaryDataSource<T> source) {
		super();
		while (source.hasNext()) {
			WordData<T> wordData = source.next();
			this.addWord(wordData.getText(), wordData.getUserData());
		}
	}
	
	/**
	 * 追加词
	 * 
	 * @param word 词
	 */
	public void addWord(String word, T userData) {
		if (word != null ) {
			
			String trim = word.trim();
			if (trim.length() <= 0) {
				return;
			}
			
			char[] charArray = trim.toCharArray();
			
			TrieNode<T> node = treeMap.get(Character.valueOf(charArray[0]));
			if (node == null) {
				boolean isWord = (charArray.length == 1);
				node = new TrieNode<T>(charArray[0], isWord);
				if (isWord) {
					node.setUserData(userData);
				}
				treeMap.put(node.getCharacter(), node);
			}
			
			int wordLength = trim.length();
			for (int i = 1; i < wordLength; i++) {
				boolean isWord = (i == (wordLength - 1));
				TrieNode<T> subNode = node.findSubNode(charArray[i]);
				if (subNode == null) {
					subNode = new TrieNode<T>(charArray[i], isWord);
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
	 * @param word 词
	 * @return 是/否
	 */
	public boolean contains(String word) {
		if (word != null) {
			
			String trim = word.trim();
			if (trim.length() <= 0) {
				return false;
			}
			
			char[] charArray = trim.toCharArray();
			TrieNode<T> node = treeMap.get(Character.valueOf(charArray[0]));
			if (node != null) {
				int wordLength = charArray.length;
				for (int i = 1; i < wordLength; i++) {
					node = node.findSubNode(charArray[i]);
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
			
		} else {
			return false;
		}
	}
}
