package com.avaw.parsesubtitle;

import com.avaw.parsesubtitle.info.WordInfo;
import com.avaw.parsesubtitle.logic.db.Word;
import com.avaw.parsesubtitle.util.DictTools;
import com.avaw.parsesubtitle.util.DictToolsByDictCN;

public class DictWords {
	public static void main(String[] args) {
		DictTools dictTools = new DictToolsByDictCN();
		Word word = new Word();
		WordInfo wordInfo = null;
		String sWord;
		while ((wordInfo = word.getOneRandWordInfoByStatus(0)) != null) {
			//根据单词表,获取翻译,音标和读音文件
			sWord = wordInfo.getWord();
			System.out.println(sWord);
			wordInfo = dictTools.getWordInfo(wordInfo.getWord());
			if (wordInfo == null) {
				System.out.println(sWord);
				continue;
			}
			if (!sWord.equals(wordInfo.getWord())) {
				System.out.println(sWord + " " + wordInfo.getWord());
				wordInfo.setStatus(2);
				wordInfo.setWord(sWord);
			}
			else {
				wordInfo.setStatus(1);				
				word.updateWord(wordInfo);
				System.out.println(word.getOneWordInfoByWord(wordInfo.getWord()));
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}