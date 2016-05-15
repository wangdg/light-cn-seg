package com.wdg.lcs.seg;

/**
 * 分词单元信息
 */
public class SegmentData {

	/** 分词文本 */
	private String segment;
	
	/** 开始位置 */
	private int start;
	
	/** 结束位置 */
	private int end;

	public int length() {
		if (segment != null) {
			return segment.length();
		} else {
			return 0;
		}
	}
	
	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
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
		return segment;
	}
}
