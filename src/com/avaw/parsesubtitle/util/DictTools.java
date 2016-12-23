package com.avaw.parsesubtitle.util;

import com.avaw.parsesubtitle.info.WordInfo;

public abstract class DictTools {
	public String Word;
	public WordInfo wordInfo;
	
	public WordInfo getWordInfo(String Word) {
		this.Word = Word;
		return parseResult(Search());
	}

	public abstract WordInfo parseResult(Object data);
	
	public abstract Object Search();
}