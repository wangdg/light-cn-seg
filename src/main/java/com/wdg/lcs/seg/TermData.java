package com.wdg.lcs.seg;

/**
 * 分词单元信息
 */
public class TermData {

	/** 分词文本 */
	private String term;
	
	/** 开始位置 */
	private int start;
	
	/** 结束位置 */
	private int end;

	public int length() {
		if (term != null) {
			return term.length();
		} else {
			return 0;
		}
	}
	
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return term;
	}
}
