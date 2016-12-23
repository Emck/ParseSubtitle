package com.avaw.parsesubtitle.logic.db;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.avaw.commons.util.sql.DBConnect;
import com.avaw.parsesubtitle.constant.Constant;
import com.avaw.parsesubtitle.info.SentenceInfo;
import com.avaw.parsesubtitle.info.WordInfo;

public class Sentence {
//	public WordInfo getOneWordInfoByWord(String Word) {
//		DBConnect dbConn = new DBConnect(Constant.DBDriver,Constant.ConStr,Constant.User,Constant.Password);
//		if (dbConn.Connect() == false) return null;
//		Connection conn = dbConn.getConnection();
//		Statement stmt = null;
//		ResultSet rs = null;
//		try {
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery("select * from words where word='" + Word + "'");
//			if (rs.next() == false) return null;
//			WordInfo info = new WordInfo();
//			info.setID(rs.getInt("id"));
//			info.setWord(rs.getString("word"));
//			info.setPron(rs.getString("pron"));
//			info.setStatus(rs.getInt("status"));
//			info.setChinese(rs.getString("chinese"));
//			info.setExample(rs.getString("example"));
//			info.setType(rs.getString("type"));
//			Blob Voice = rs.getBlob("voice");
//			if (Voice != null)
//				info.setVoice(Voice.getBytes(1,(int )Voice.length()));
//			return info;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			try {
//				if (rs != null) rs.close();
//				if (stmt != null) stmt.close();
//				if (conn != null) conn.close();
//			} catch (SQLException e) {
//			}
//		}
//	}

//	public List<WordInfo> getAllWordInfos() {
//		return getWordInfosByStatus(-1,-1,-1);
//	}
//
//	public List<WordInfo> getWordInfosByID(int BeginID,int EndID) {
//		return getWordInfosByStatus(-1,BeginID,EndID);
//	}
//	
//	public List<WordInfo> getWordInfosByStatus(int Status) {
//		return getWordInfosByStatus(Status,-1,-1);
//	}

	
//	public List<WordInfo> getWordInfosByStatus(int Status,int BeginID,int EndID) {
//		DBConnect dbConn = new DBConnect(Constant.DBDriver,Constant.ConStr,Constant.User,Constant.Password);
//		if (dbConn.Connect() == false) return null;
//		Connection conn = dbConn.getConnection();
//		Statement stmt = null;
//		ResultSet rs = null;
//		try {
//			stmt = conn.createStatement();
//			String SQL = "select * from words";
//			if (Status != -1 && BeginID == -1 && EndID == -1)
//				SQL += " where status=" + Status;
//			else if (Status != -1 && BeginID != -1 && EndID != -1)
//				SQL += " where status=" + Status + " and id>=" + BeginID + " and id<=" + EndID;
//			else if (Status == -1 && BeginID != -1 && EndID != -1)
//				SQL += " where id>=" + BeginID + " and id<=" + EndID;
//			rs = stmt.executeQuery(SQL);
//			List<WordInfo> Infos = new ArrayList<WordInfo>();
//			WordInfo info;
//			while (rs.next()) {
//				info = new WordInfo();
//				info.setID(rs.getInt("id"));
//				info.setWord(rs.getString("word"));
//				info.setPron(rs.getString("pron"));
//				info.setChinese(rs.getString("chinese"));
//				info.setExample(rs.getString("example"));
//				info.setType(rs.getString("type"));
//				Blob Voice = rs.getBlob("voice");
//				if (Voice != null)
//					info.setVoice(Voice.getBytes(1,(int )Voice.length()));
//				Infos.add(info);
//			}
//			return Infos;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			try {
//				if (rs != null) rs.close();
//				if (stmt != null) stmt.close();
//				if (conn != null) conn.close();
//			} catch (SQLException e) {
//			}
//		}
//	}
	
//	public WordInfo getOneRandWordInfoByStatus(int Status) {
//		DBConnect dbConn = new DBConnect(Constant.DBDriver,Constant.ConStr,Constant.User,Constant.Password);
//		if (dbConn.Connect() == false) return null;
//		Connection conn = dbConn.getConnection();
//		Statement stmt = null;
//		ResultSet rs = null;
//		try {
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery("select * from words where status=" + Status + " and id >= (select floor(RAND() * (select MAX(id) FROM words))) ORDER BY id limit 1");
//			if (rs.next() == false) return null;
//			WordInfo info = new WordInfo();
//			info.setID(rs.getInt("id"));
//			info.setWord(rs.getString("word"));
//			info.setPron(rs.getString("pron"));
//			info.setChinese(rs.getString("chinese"));
//			info.setExample(rs.getString("example"));
//			info.setType(rs.getString("type"));
//			Blob Voice = rs.getBlob("voice");
//			if (Voice != null)
//				info.setVoice(Voice.getBytes(1,(int )Voice.length()));
//			return info;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			try {
//				if (rs != null) rs.close();
//				if (stmt != null) stmt.close();
//				if (conn != null) conn.close();
//			} catch (SQLException e) {
//			}
//		}
//	}
	
	private boolean isExistSentence(Connection conn,SentenceInfo sInfo) {
		PreparedStatement prestmt = null;
		ResultSet rs = null;
		try {
			prestmt = conn.prepareStatement("select count(id) as counts from sentence where keyword=? and english=?");
			prestmt.setString(1, sInfo.getKeyWord());
			prestmt.setString(2, sInfo.getEnglish());
			rs = prestmt.executeQuery();
			rs.next();
			if (rs.getInt("counts") <= 0) return false;
			else return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (rs != null) rs.close();
				if (prestmt != null) prestmt.close();
			} catch (SQLException e) {
			}
		}
	}
	
//	public boolean updateWord(WordInfo wordInfo) {
//		if (wordInfo == null) return false;
//		DBConnect dbConn = new DBConnect(Constant.DBDriver,Constant.ConStr,Constant.User,Constant.Password);
//		if (dbConn.Connect() == false) return false;
//		Connection conn = dbConn.getConnection();
//		PreparedStatement prestmt = null;
//		String sql = "update words set status=?,pron=?,chinese=?,example=?,type=?,voice=? where word=?";
//		try {
//			prestmt = conn.prepareStatement(sql);
//			prestmt.setInt(1, wordInfo.getStatus());
//			prestmt.setString(2, wordInfo.getPron());
//			prestmt.setString(3, wordInfo.getChinese());
//			prestmt.setString(4, wordInfo.getExample());
//			prestmt.setString(5, wordInfo.getType());
//			prestmt.setBytes(6, wordInfo.getVoice());
//			prestmt.setString(7, wordInfo.getWord());
//			if (prestmt.executeUpdate() <= 0) {  //更新失败...
//				System.out.println("Update Error Word: " + wordInfo.getWord());
//				return false;
//			}
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			try {
//				if (prestmt != null)
//					prestmt.close();
//				if (conn != null)
//					conn.close();
//			} catch (SQLException e) {
//			}
//		}
//	}
	
//	public boolean updateWord(int ID,WordInfo wordInfo) {
//		if (wordInfo == null) return false;
//		DBConnect dbConn = new DBConnect(Constant.DBDriver,Constant.ConStr,Constant.User,Constant.Password);
//		if (dbConn.Connect() == false) return false;
//		Connection conn = dbConn.getConnection();
//		PreparedStatement prestmt = null;
//		String sql = "update words set status=?,pron=?,chinese=?,example=?,type=?,voice=? where id=?";
//		try {
//			prestmt = conn.prepareStatement(sql);
//			prestmt.setInt(1, wordInfo.getStatus());
//			prestmt.setString(2, wordInfo.getPron());
//			prestmt.setString(3, wordInfo.getChinese());
//			prestmt.setString(4, wordInfo.getExample());
//			prestmt.setString(5, wordInfo.getType());
//			prestmt.setBytes(6, wordInfo.getVoice());
//			prestmt.setInt(7, ID);
//			if (prestmt.executeUpdate() <= 0) {  //更新失败...
//				System.out.println("Update Error ID: " + ID);
//				return false;
//			}
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			try {
//				if (prestmt != null)
//					prestmt.close();
//				if (conn != null)
//					conn.close();
//			} catch (SQLException e) {
//			}
//		}
//	}
	
	public boolean insertNewSentence(SentenceInfo sInfo) {
		if (sInfo == null) return false;
		DBConnect dbConn = new DBConnect(Constant.DBDriver,Constant.ConStr,Constant.User,Constant.Password);
		if (dbConn.Connect() == false) return false;
		Connection conn = dbConn.getConnection();
		PreparedStatement prestmt = null;
		String sql = "insert into sentence(keyword,status,pron,english,chinese) values(?,?,?,?,?)";
		try {
			if (isExistSentence(conn,sInfo) == true) return false;
			prestmt = conn.prepareStatement(sql);
			prestmt.setString(1, sInfo.getKeyWord());
			prestmt.setInt(2, sInfo.getStatus());
			prestmt.setString(3, sInfo.getPron());
			prestmt.setString(4, sInfo.getEnglish());
			prestmt.setString(5, sInfo.getChinese());
			if (prestmt.executeUpdate() <= 0) {  //插入失败...
				System.out.println("Insert Error Sentence: " + sInfo);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (prestmt != null)
					prestmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
	}
	
	public int initByWordTable() {
		Word word = new Word();
		List<WordInfo> WordInfos = word.getAllWordInfos();
		
		if (WordInfos == null || WordInfos.size() <=0) return 0;
		SentenceInfo sInfo;
		String Example;
		String Temp = "";
		WordInfo wordInfo = null;
		int Count =0;
		for (int i=0; i<WordInfos.size(); i++) {
			wordInfo = WordInfos.get(i);
			System.out.println(i+1 + " [" + wordInfo.getWord() + "]          Status: " + wordInfo.getStatus());
			Example = wordInfo.getExample();
			if (Example == null || Example.length() <=0 || Example.equals("NULL")) {
				System.out.println();
				continue;
			}
			sInfo = new SentenceInfo();
			sInfo.setKeyWord(wordInfo.getWord());
			sInfo.setStatus(wordInfo.getStatus());
			sInfo.setPron(wordInfo.getPron());
			

			BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(Example.getBytes())));
			String line;
			try {
				while ((line = br.readLine()) != null) {
					Temp = "";
					if (line.indexOf("o--") == 0) {
						sInfo.setEnglish(line.substring(3,line.length()));
					}
					else if (line.indexOf("t--") == 0) {
						sInfo.setChinese(line.substring(3,line.length()));
						int Len = 63 - sInfo.getEnglish().length();
						Temp = sInfo.getEnglish();
						for (int j=0; j<=Len; j++) Temp +=" ";
						Temp += sInfo.getChinese();
						System.out.println(Temp);
						if (insertNewSentence(sInfo) == true)
							Count ++;
					}
					else {
						System.out.println("Error: " + line);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println();
		}
		return Count;
	}
}