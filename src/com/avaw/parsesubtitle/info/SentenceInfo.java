package com.avaw.parsesubtitle.info;

public class SentenceInfo {
	private int ID;					// ID
	private String KeyWord;			// 关键单词
	private String Pron;			// 音标
	private int Status = 0;			// 状态
	private String English;			// 英文
	private String Chinese;			// 中文
//	private String Example;			// 例句
//	private String Memo;			// 备注
//	private String Type;			// 类型(暂未使用)
//	private byte[] Voice = null;	// 语音
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getKeyWord() {
		return KeyWord;
	}
	public void setKeyWord(String keyWord) {
		KeyWord = keyWord;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getPron() {
		return Pron;
	}
	public void setPron(String pron) {
		Pron = pron;
	}
	public String getChinese() {
		return Chinese;
	}
	public void setChinese(String chinese) {
		Chinese = chinese;
	}
	public String getEnglish() {
		return English;
	}
	public void setEnglish(String english) {
		English = english;
	}
}