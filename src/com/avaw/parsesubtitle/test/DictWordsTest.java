package com.avaw.parsesubtitle.test;

import com.avaw.parsesubtitle.info.WordInfo;
import com.avaw.parsesubtitle.util.DictToolsByDictCN;


public class DictWordsTest {
	public static void main(String[] args) {
		DictToolsByDictCN dictTools = new DictToolsByDictCN();
		WordInfo wordInfo;
		
		//根据单词表,获取翻译,音标和读音文件
		wordInfo = dictTools.getWordInfo("using");
		System.out.println(wordInfo);
	}
}