package com.avaw.parsesubtitle;

import com.avaw.parsesubtitle.logic.SaveToDatabase;
import com.avaw.parsesubtitle.logic.WordBySubtitleSrcFile;

public class ParseSubtitle {

	public static void main(String[] args) {
		String Filename = "/Volumes/Data/Works/Project/English/Tools/ParseSubtitle/Friends.S01E01.eng.srt";
		WordBySubtitleSrcFile wordByFile = new WordBySubtitleSrcFile(Filename);
		if (wordByFile.doParse() <=0) {
			System.out.println("Parse Error!");
			return;
		}
		SaveToDatabase save = new SaveToDatabase(Filename,wordByFile.getWordTables());
		if (save.doSave() == false) {
			System.out.println("Save to Database Error!");			
		}
		System.out.println("Succeed!");
	}
}