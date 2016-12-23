package com.avaw.parsesubtitle.test;

import com.avaw.parsesubtitle.logic.SaveToDatabase;
import com.avaw.parsesubtitle.logic.WordByTextFile;

public class Text {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String Filename = "/Users/emck/Desktop/love.txt";
		WordByTextFile wordByFile = new WordByTextFile(Filename);
		if (wordByFile.doParse() <=0) {
			System.out.println("Parse Error!");
			return;
		}
//		System.out.println(wordByFile);
		SaveToDatabase save = new SaveToDatabase(Filename,wordByFile.getWordTables());
		if (save.doSave() == false) {
			System.out.println("Save to Database Error!");			
		}
		System.out.println("Succeed!");
	}
}
