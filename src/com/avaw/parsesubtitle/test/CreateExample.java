package com.avaw.parsesubtitle.test;

import com.avaw.parsesubtitle.logic.db.Sentence;

public class CreateExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Sentence sentence = new Sentence();
		System.out.println(sentence.initByWordTable());
		
//		Word word = new Word();
//		WordInfo wordInfo = null;
//		List<WordInfo> WordInfos;
//		//WordInfos = word.getWordInfosByStatus(1,0,100);
//		WordInfos = word.getWordInfosByID(0,500);
//		
//		if (WordInfos == null || WordInfos.size() <=0) return 0;
//		String Example;
//		String New = "";
//		for (int i=0; i<WordInfos.size(); i++) {
//			wordInfo = WordInfos.get(i);
//			System.out.println(i+1 + " [" + wordInfo.getWord() + "] Status: " + wordInfo.getStatus());
//			Example = wordInfo.getExample();
//			if (Example == null || Example.length() <=0 || Example.equals("NULL")) {
//				System.out.println();
//				continue;
//			}
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(Example.getBytes())));
//			String line;
//			try {
//				while ((line = br.readLine()) != null) {
//					if (line.indexOf("o--") == 0) {
//						New = line.substring(3,line.length());
//						//System.out.print(line.substring(3,line.length()));
//					}
//					else if (line.indexOf("t--") == 0) {
//						int Len = 63 - New.length();
//						for (int j=0; j<=Len; j++) New +=" ";
//						New += line.substring(3,line.length());
//						System.out.println(New);
//						//System.out.println("\t\t\t\t\t\t" + line.substring(3,line.length()));						
//					}
//					else {
//						System.out.println("Error: " + line);
//					}
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			System.out.println();
//		}
//		return 1;
	}

}