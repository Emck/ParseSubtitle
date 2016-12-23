package com.avaw.parsesubtitle.logic.format;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.avaw.parsesubtitle.info.format.Format;

public class FormatSrt {
	private String SubtitleTexts;
	private List<Format> Subtitles;

	public FormatSrt(String SubtitleTexts) {
		this.SubtitleTexts = SubtitleTexts;
		this.Subtitles = new ArrayList<Format>();
	}

	public boolean doFormat() {
		// BufferedReader br = new BufferedReader(new InputStreamReader(new
		// ByteArrayInputStream(s.getBytes(Charset.forName("utf8"))),
		// Charset.forName("utf8")));
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(SubtitleTexts.getBytes())));
		String line;
		Format info = null;
		int index;
		int Status = 0;
		String Sub = null;
		try {
			while ((line = br.readLine()) != null) {
				switch (Status) {
					case 0: {
						try {
							index = Integer.valueOf(line);
							Status = 1;
							info = new Format();
							info.setIndex(index);
						} catch (Exception e) {
							Status = 0;
						}
						continue;
					}
					case 1: {
						info.setTime(line);
						Status = 2;
						Sub = "";
						continue;
					}
					case 2: {
						if (line.length() >0) {
							Sub += line;
							Status = 2;
						}
						else {
							Status = 0;
							info.setSub(Sub);
							Subtitles.add(info);
							//System.out.println(info);
						}
						continue;
					}
				}
			}
			if (Status ==2) {
				info.setSub(Sub);
				Subtitles.add(info);
				//System.out.println(info);				
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Format> getSubtitles() {
		return Subtitles;
	}
}