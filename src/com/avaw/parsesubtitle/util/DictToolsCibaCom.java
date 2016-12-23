package com.avaw.parsesubtitle.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.avaw.commons.util.constant.Constant;
import com.avaw.commons.util.file.FileUtil;
import com.avaw.parsesubtitle.info.WordInfo;

public class DictToolsCibaCom extends DictTools {
	@Override
	public WordInfo parseResult(Object data) {
		if (data == null || ((String)data).length() <=0) return null;
		try {
			//File f = new File("/Volumes/Data/Project/English/ParseSubtitle/test.xml");
			//SAXReader reader = new SAXReader();
			//Document doc = reader.read(f);
			Document document = DocumentHelper.parseText((String)data);
			Element root = document.getRootElement();
			String VoiceUrl,Example = "";
			
			wordInfo = new WordInfo();
			wordInfo.setWord(root.elementText("key"));
			wordInfo.setPron(root.elementText("pron"));
			VoiceUrl = root.elementText("audio");
			wordInfo.setChinese(root.elementText("def"));
			
			Element sent;
			for (Iterator<Element> i = root.elementIterator("sent"); i.hasNext();) {
				sent = (Element) i.next();
				Example = "o--" + sent.elementText("orig") + Constant.LINE_SEPARATOR;
				Example += "t--" + sent.elementText("trans") + Constant.LINE_SEPARATOR;
			}
			wordInfo.setExample(Example);
			ByteBuffer Bytes = FileUtil.GetHttpFileBytes(VoiceUrl);
			if (Bytes == null) return null;
			wordInfo.setVoice(Bytes.array());	// »ñÈ¡ÒôÆµÎÄ¼þ
			return wordInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object Search() {
		String Xml = "";
		try {
			URL url = new URL("http://dict.cn/" + Word);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Host", "dict.cn");
			con.setRequestProperty("Connection", "keep-alive");
			con.setRequestProperty("Cache-Control", "max-age=0");
			con.setRequestProperty("Upgrade-Insecure-Requests", "1");
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko)");
			con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			//con.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			con.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				Xml += line + Constant.LINE_SEPARATOR;
			}
			return Xml;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}