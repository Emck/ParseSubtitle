package com.avaw.parsesubtitle.logic;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.avaw.commons.util.file.FileUtil;
import com.avaw.parsesubtitle.info.format.Format;
import com.avaw.parsesubtitle.logic.format.FormatSrt;
import com.avaw.parsesubtitle.util.WordByLine;

/**
 * 
 * @author emck
 *
 */
public class WordBySubtitleSrcFile {
	private String Filename;
	private Hashtable<String, Integer> WordTables = new Hashtable<String,Integer>();
	
	public WordBySubtitleSrcFile(String Filename) {
		this.Filename = Filename;
	}
	
	public int doParse() {
		String Text = FileUtil.GetTextFileString(Filename);
		if (Text.length() <=0) return -1;
		FormatSrt Formatsrt = new FormatSrt(Text);
		if (Formatsrt.doFormat() == false) return -1;
		List<Format> Subtitles = Formatsrt.getSubtitles();
		Iterator<Format> Subiterator = Subtitles.iterator();
		Format Subinfo;
		List<String> Words = new ArrayList<String>();
		String Word;
		int Count;
		while (Subiterator.hasNext()) {
			Subinfo = Subiterator.next();
			Words = WordByLine.doParse(Subinfo.getSub());
			if (Words == null || Words.size()<=0) continue;

			Iterator<String> Wordsiterator = Words.iterator();
			while (Wordsiterator.hasNext()) {
				Word = Wordsiterator.next();
				if (WordTables.containsKey(Word) == false) Count = 1;
				else Count = WordTables.get(Word) + 1;
				WordTables.put(Word, Count);
			}
		}
		return WordTables.size();
	}

	public Hashtable<String, Integer> getWordTables() {
		return WordTables;
	}
}