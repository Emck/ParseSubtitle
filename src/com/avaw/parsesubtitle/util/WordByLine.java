package com.avaw.parsesubtitle.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordByLine {
	private static String WordPattern = "\\d+.\\d+|\\w+";
	
	public static List<String> doParse(String Sentence) {
		Pattern pattern=Pattern.compile(WordPattern);  
        Matcher ma=pattern.matcher(Sentence);  
   
        List<String> Words = new ArrayList<String>();
        String Word;
        while (ma.find()) {
        	Word = ma.group().toLowerCase();
        	if (Word.length() <=1) continue;
        	Words.add(Word);
        }
        if (Words.size() == 0) return null;
        else return Words;
	}
}