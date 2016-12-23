package com.avaw.parsesubtitle.logic.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.avaw.commons.util.sql.DBConnect;
import com.avaw.parsesubtitle.constant.Constant;

public class Document {
	public boolean saveToDatabase() {
		boolean Success = true;
		DBConnect dbConn = new DBConnect(Constant.DBDriver,Constant.ConStr,Constant.User,Constant.Password);
		if (dbConn.Connect() == false) return false;
		Connection conn = dbConn.getConnection();
		PreparedStatement prestmt = null;
		String sql = "insert into documentinfo(filename,filesize,createtime,words,desc) values(?,?,now(),?,?)";
		try {
			prestmt = conn.prepareStatement(sql);
//				prestmt.setString(1, historyInfo.getUserID());
//				prestmt.setTimestamp(2, (new Timestamp(historyInfo.getCreateTime())));
//				prestmt.setString(3, historyInfo.getAppName_A());
//				prestmt.setString(4, historyInfo.getPackageName_A());
//				prestmt.setString(5, historyInfo.getVersionName_A());
//				prestmt.setString(6, historyInfo.getVersionCode_A());
//				prestmt.setString(7, historyInfo.getAppName_B());
//				prestmt.setString(8, historyInfo.getPackageName_B());
//				prestmt.setString(9, historyInfo.getVersionName_B());
//				prestmt.setString(10, historyInfo.getVersionCode_B());
//				prestmt.setInt(11, historyInfo.getResult());
//				if (prestmt.executeUpdate() <= 0) {
//					historyInfo.setDbID(-1); // ÐèÒªÄÃ»Ø¸Õ²åÈëµÄID
//					Success = false;
//				}
//			}
			return Success;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (prestmt != null)
					prestmt.close();
			} catch (SQLException e) {
			}
		}
	}
}
