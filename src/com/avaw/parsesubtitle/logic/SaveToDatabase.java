package com.avaw.parsesubtitle.logic;

import java.util.Hashtable;

import com.avaw.parsesubtitle.logic.db.Word;

public class SaveToDatabase {
	//private String Filename;
	private Hashtable<String, Integer> WordTables = new Hashtable<String,Integer>();
	
	public SaveToDatabase(String Filename,Hashtable<String, Integer> WordTables) {
		//this.Filename = Filename;
		this.WordTables = WordTables;
	}
	
	public boolean doSave() {
		//Document doc = new Document();
		Word word = new Word();
		if (word.insertNewWord(WordTables) == false) {
			System.out.println("do Word.save() Error");
			return false;
		}
		return true;
	}
}