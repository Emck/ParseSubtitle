package com.avaw.parsesubtitle.info;

public class WordInfo {
	private int ID;					// ID
	private String Word;			// 单词
	private String Pron;			// 音标
	private int Status;				// 状态
	private String Chinese;			// 中文
	private String Example;			// 例句
	private String Memo;			// 备注
	private String Type;			// 类型(暂未使用)
	private byte[] Voice = null;	// 语音

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getWord() {
		return Word;
	}
	public void setWord(String word) {
		Word = word;
	}
	public String getPron() {
		return Pron;
	}
	public void setPron(String pron) {
		Pron = pron;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getChinese() {
		return Chinese;
	}
	public void setChinese(String chinese) {
		Chinese = chinese;
	}
	public String getExample() {
		return Example;
	}
	public void setExample(String example) {
		Example = example;
	}
//	public String getMemo() {
//		return Memo;
//	}
//	public void setMemo(String memo) {
//		Memo = memo;
//	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public byte[] getVoice() {
		return Voice;
	}
	public void setVoice(byte[] bs) {
		Voice = bs;
	}
	public String toString() {
		try {
			return "ID: " + ID + " Word: " + Word + " Status: " + Status + " Pron: " + Pron + " Chinese: " + Chinese + " Memo: " + Memo + " Type: " + Type + " Voice: " + Voice.length;
		} catch (Exception e) {
			return "ID: " + ID + " Word: " + Word + " Status: " + Status + " Pron: " + Pron + " Chinese: " + Chinese + " Memo: " + Memo + " Type: " + Type + " Voice: " + Voice;
		}
	}
}