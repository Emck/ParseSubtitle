package com.avaw.parsesubtitle.util;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import com.avaw.commons.util.constant.Constant;
import com.avaw.parsesubtitle.info.WordInfo;

/**
 * dict.cn的翻译解析工具
 * @author emck
 * 2016年12月22日 可用
 */

public class DictToolsByDictCN extends DictTools {	
	@Override
	public WordInfo parseResult(Object data) {
		if (data == null) return null;
		try {
			// 获取word标签
			Node word = null;
			List<Node> examples = null;
			for (Node info : ((Element)data).childNodes()) {
				if (info.nodeName().equals("div")) {
					if (info.attr("class").equals("word")) word = info;						// 保存Word
					if (info.attr("class").equals("section sent")) {						// 得到例句相关
						for (Node temp : info.childNodes()) {
							if (temp.toString().trim().length() == 0) continue;				// 如果是空行则忽略
							if (temp.attr("class").equals("layout sort")) examples = temp.childNodes();	// 保存例句
						}
					}
				}
			}
			// 获取音标标签
			Node phonetic = null;
			Node chineses = null;
			for (Node info : word.childNodes()) {
				if (info.nodeName().equals("div")) {
					if (info.attr("class").equals("phonetic")) {							// 得到音标
						for (Node temp : info.childNodes()) {
							if (temp.toString().trim().length() == 0) continue;				// 如果是空行则忽略
							phonetic = temp;												// 保存音标
							// 如果有2个音标，则第二个覆盖第一个(及使用美式音标)
						}
					}
					if (info.attr("class").equals("basic clearfix")) {						// 得到中文解释
						for (Node temp : info.childNodes()) {
							if (temp.nodeName().equals("ul")) chineses = temp;				// 保存中文解释
						}
					}
				}			
			}
			// 获取音标(及美式音标)
			String pron = null;
			for (Node info : phonetic.childNodes()) {
				if (info.toString().trim().length() == 0) continue;							// 如果是空行则忽略
				if (info.attr("lang").equals("EN-US")) pron = info.childNode(0).toString();
			}
			if (pron == null || pron.length() <2) return null;
			pron = pron.substring(1);														// 去掉头部的符号'['
			pron = pron.substring(0, pron.length() -1);										// 去掉尾部的符号']'
			
			// 获取中文解释
			String chinese = "";
			for (Node info : chineses.childNodes()) {
				if (info.toString().trim().length() == 0) continue;							// 如果是空行则忽略
				if (info.attributes().size() >0) continue;									// 如果是空内容则忽略
				chinese += info.childNode(0).childNode(0) + " " + info.childNode(1).childNode(0) + Constant.LINE_SEPARATOR;
			}
			if (chinese.length() <=0) return null;
			
			// 获取例句
			String example = "";
			String hot1 = "<em class=\"hot\">";
			String hot2 = "</em>";
			for (int i=0; i<examples.size(); i++) {
				Node info = examples.get(i);
				if (info.toString().trim().length() == 0) continue;							// 如果是空行则忽略
				if (!info.nodeName().equals("div")) continue;								// 如果不是div(及用作...)则忽略
				if (info.attr("class").length() >0) continue;								// 如果是class则忽略
				if (examples.get(i+2) == null) continue;									// 如果获取下下个node为空则忽略
				// 组合用作....
				example += "---" + info.childNode(0).childNode(0) + Constant.LINE_SEPARATOR;
				// 组合全部例句...
				for (Node info2 : examples.get(i+2).childNodes()) {
					if (info2.toString().trim().length() == 0) continue;					// 如果是空行则忽略
					String temp = info2.outerHtml();				// 得到Html代码
					// 去掉Html代码标记
					int s1 = temp.indexOf("<i class");				// 
					int s2 = temp.indexOf("/i><br> ");
					temp = "o--" + temp.substring(4,s1)  + Constant.LINE_SEPARATOR + "t--" + temp.substring(s2+8,temp.length()-5);
					temp = temp.replace(hot1, "");											// 替换掉热词
					temp = temp.replace(hot2, "");											// 替换掉热词
					// 得到例句
					example += temp + Constant.LINE_SEPARATOR + Constant.LINE_SEPARATOR;
				}
				example += Constant.LINE_SEPARATOR;
			}
			if (example.length() <=0) return null;
			
			wordInfo = new WordInfo();
			wordInfo.setWord(Word);
			wordInfo.setPron(pron);
			wordInfo.setChinese(chinese.trim());
			wordInfo.setExample(example.trim());
			
			// 音频暂时先不处理
			//VoiceUrl = "http://audio.dict.cn/" + "muc0IX8d85fe9ab47ec403f3659eee07646e72.mp3?t=enough";
			//Element sent;
			//for (Iterator<Element> i = root.elementIterator("sent"); i.hasNext();) {
			//	sent = (Element) i.next();
			//	Example = "o--" + sent.elementText("orig") + Constant.LINE_SEPARATOR;
			//	Example += "t--" + sent.elementText("trans") + Constant.LINE_SEPARATOR;
			//}
			//ByteBuffer Bytes = FileUtil.GetHttpFileBytes(VoiceUrl);
			//if (Bytes == null) return null;
			//wordInfo.setVoice(Bytes.array());	// »ñÈ¡ÒôÆµÎÄ¼þ
			
			return wordInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object Search() {
		try {
			Document doc = Jsoup.connect("http://dict.cn/" + Word).userAgent("Mozilla").get();
			return doc.body().getElementById("content").child(0);
		} catch (Exception e) {
			return null;
		}
	}
}