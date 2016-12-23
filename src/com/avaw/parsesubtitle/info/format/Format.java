package com.avaw.parsesubtitle.info.format;

public class Format {
	private int Index;
	private String Time;
	private String Sub;
	public int getIndex() {
		return Index;
	}
	public void setIndex(int index) {
		Index = index;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getSub() {
		return Sub;
	}
	public void setSub(String sub) {
		Sub = sub;
	}
	public String toString() {
		return "Index: " + Index + " Time: " + Time + " Sub: " + Sub;
	}
}