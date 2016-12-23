package com.avaw.parsesubtitle.test;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.Enumeration;
//import java.util.Hashtable;
//
//import javax.media.Manager;
//import javax.media.NoPlayerException;
//import javax.media.Player;
//
//import com.avaw.commons.util.net.HttpPostAndGet;
//import com.avaw.parsesubtitle.info.WordInfo;
//import com.avaw.parsesubtitle.logic.SaveToDatabase;
//import com.avaw.parsesubtitle.logic.WordBySubtitleSrcFile;
//import com.avaw.parsesubtitle.logic.db.Word;
//import com.avaw.parsesubtitle.util.DictToolsCibaCom;

public class Main {
	public static void main(String[] args) {
//		String Filename = "/Volumes/Data/English/Friends-1/other/test.srt";
//		String Filename = "/Volumes/Data/English/Friends.S01E02.The.One.With.The.Sonogram.At.The.End.eng.srt";
//		String Filename = "/Volumes/Data/Works/Project/English/Tools/ParseSubtitle/Friends.S01E01.eng.srt";
//		WordBySubtitleSrcFile wordByFile = new WordBySubtitleSrcFile(Filename);
//		if (wordByFile.doParse() <=0) {
//			System.out.println("Parse Error!");
//			return;
//		}
//		SaveToDatabase save = new SaveToDatabase(Filename,wordByFile.getWordTables());
//		if (save.doSave() == false) {
//			System.out.println("Save to Database Error!");			
//		}
//		System.out.println("Succeed!");


//		DictTools dictTools = new DictTools();
//		Word word = new Word();
//		WordInfo wordInfo = null;
//		String sWord;
//		while ((wordInfo = word.getOneRandWordInfoByStatus(1)) != null) {
//			//根据单词表,获取翻译,音标和读音文件
//			sWord = wordInfo.getWord();
//			System.out.println(sWord);
//			wordInfo = dictTools.getWordInfo(wordInfo.getWord());
//			if (wordInfo == null) {
//				System.out.println(sWord);
//				continue;
//			}
//			if (!sWord.equals(wordInfo.getWord())) {
//				System.out.println(sWord + " " + wordInfo.getWord());
//				wordInfo.setStatus(2);
//				wordInfo.setWord(sWord);
//			}
//			else {
//				wordInfo.setStatus(1);				
//			}
//			word.updateWordPron(wordInfo);
//			System.out.println(word.getOneWordInfoByWord(wordInfo.getWord()));
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
		
//		Player player;
//		String mp3 = "/Users/emck/Music/我的最爱/辛晓琪-俩俩相忘.mp3";
//		try {
//			player = Manager.createPlayer(new File(mp3).toURI().toURL());
//			player.start();
//			//Thread.sleep(10000);
//		} catch (NoPlayerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}