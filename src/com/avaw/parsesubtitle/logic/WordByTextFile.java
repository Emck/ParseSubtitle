package com.avaw.parsesubtitle.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.avaw.parsesubtitle.util.WordByLine;

/**
 * 
 * @author emck
 *
 */
public class WordByTextFile {
	private String Filename;
	private Hashtable<String, Integer> WordTables = new Hashtable<String,Integer>();
	
	public WordByTextFile(String Filename) {
		this.Filename = Filename;
	}
	
	public int doParse() {
		List<String> Words = new ArrayList<String>();
		String Word;
		int Count;
		try {
			BufferedReader br = new BufferedReader(new FileReader(Filename));
			String row;
			while ((row = br.readLine()) != null) {
				//Text += row + Constant.LINE_SEPARATOR;
				//System.out.println(row);
				Words = WordByLine.doParse(row);
				if (Words == null || Words.size()<=0) continue;

				Iterator<String> Wordsiterator = Words.iterator();
				while (Wordsiterator.hasNext()) {
					Word = Wordsiterator.next();
					if (WordTables.containsKey(Word) == false) Count = 1;
					else Count = WordTables.get(Word) + 1;
					WordTables.put(Word, Count);
				}
			}
			br.close();
			return WordTables.size();
		} catch (IOException e){
			e.printStackTrace();
			return -1;
		}
	}

	public Hashtable<String, Integer> getWordTables() {
		return WordTables;
	}
}