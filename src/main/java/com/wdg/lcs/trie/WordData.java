package com.wdg.lcs.trie;

/**
 * 词语数据
 */
public class WordData<T> {

	/** 词 */
	private String text;
	
	/** 用户数据 */
	private T userData;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public T getUserData() {
		return userData;
	}

	public void setUserData(T userData) {
		this.userData = userData;
	}
}
