package com.avaw.commons.util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接类(无连接池)
 */

public class DBConnect {
	private String ErrorMessage = null; // 保存最后一次的错误信息
	private String DBDriver; // JDBC的连接设备
	private String ConStr; // 服务器连接串
	private String User; // 数据库访问的用户名
	private String Password; // 数据库访问的密码
	private boolean IsAutoCommit = true; // 自动提交
	private Connection cconn = null; // 用于连接的类

	/**
	 * 构造函数，用于设置默认的连接参数(不可以定义返回值)
	 */
	public DBConnect() {
		DBDriver = null;
	};

	/**
	 * 构造函数，用于设置默认的连接参数
	 * 
	 * @param dtype 数据库默认的参数Oracle、Mysql(该方法将被淘汰)
	 */
	public DBConnect(String dtype) {
		SetDefaultDatabase(dtype); // 设定默认连接
	}

	/**
	 * 构造函数，用于设置连接参数
	 * 
	 * @param dbdriver 设置JDBC Driver
	 * @param constr 设置连接参数
	 * @param user 设置用户名
	 * @param Password 设置密码
	 */
	public DBConnect(String dbdriver, String constr, String user, String password) {
		DBDriver = dbdriver; // 设置JDBC Driver
		ConStr = constr; // 设置连接参数
		User = user; // 设置用户名
		Password = password; // 设置密码
	}

	// ///////////////////////////////////////////////////
	/**
	 * 设置JDBC Driver
	 * 
	 * @param drv JDBC Driver
	 */
	public void setDBDriver(String drv) {
		DBDriver = drv;
	}

	/**
	 * 取得JDBC Driver
	 * 
	 * @return JDBC Driver
	 */
	public String getDBDriver() {
		return (DBDriver);
	}

	/**
	 * 设置数据库连接参数
	 * 
	 * @param con 数据库连接参数
	 */
	public void setConStr(String con) {
		ConStr = con;
	}

	/**
	 * 取得数据库连接参数
	 * 
	 * @return 数据库连接参数
	 */
	public String getConStr() {
		return (ConStr);
	}

	/**
	 * 设置数据库用户名
	 * 
	 * @param user 数据库用户名
	 */
	public void setUser(String user) {
		User = user;
	}

	/**
	 * 取得数据库用户名
	 * 
	 * @return 数据库用户名
	 */
	public String getUser() {
		return (User);
	}

	/**
	 * 设置数据库用户密码
	 * 
	 * @param pass 数据库用户密码
	 */
	public void setPassword(String pass) {
		Password = pass;
	}

	/**
	 * 取得数据库用户密码
	 * 
	 * @return 数据库用户密码
	 */
	public String getPassword() {
		return (Password);
	}

	/**
	 * 设置数据库自动提交标志
	 * 
	 * @param boolean commit 自动提交标志
	 */
	public void setAutoCommit(boolean commit) {
		this.IsAutoCommit = commit;
	}

	/**
	 * 设置数据库自动提交标志
	 * 
	 * @param boolean commit 自动提交标志
	 */
	public void setAutoCommit(String commit) {
		if (commit.equalsIgnoreCase("false") == true)
			IsAutoCommit = false;
	}

	/**
	 * 获取自动提交标志
	 * 
	 * @return boolean 返回提交标志
	 */
	public boolean getAutoCommit() {
		return IsAutoCommit;
	}

	/**
	 * 得到Connection连接
	 * 
	 * @return 返回连接
	 */
	public Connection getConnection() {
		return (cconn);
	}

	/**
	 * 得到最后的错误信息
	 * 
	 * @return 返回错误信息
	 */
	private boolean AutoCommit(boolean commit) {
		if (cconn == null) {
			ErrorMessage = "数据库未连接"; // 出错，返回错误信息
			return false;
		}
		try {
			cconn.setAutoCommit(commit);
			return true;
		} catch (SQLException e) {
			ErrorMessage = "修改数据库为自动提交 = " + commit + " 出错"; // 出错，返回错误信息
		}
		return (false);
	}

	public String GetLastErrorMessage() {
		return (ErrorMessage);
	} // 得到最后的错误操作信息

	/**
	 * 连接数据库
	 * 
	 * @return 连接成功返回true,否则返回false
	 */
	public boolean Connect() {
		try {
			// Class.forName(DBDriver).newInstance();
			Class.forName(DBDriver);
			cconn = DriverManager.getConnection(ConStr, User, Password);
			AutoCommit(IsAutoCommit); // 设置数据库提交模式
			return true; // 正常
		} catch (Exception e) { // 异常处理
			ErrorMessage = "数据库连接出错，错误信息：" + e.getMessage(); // 出错，返回错误信息
			return (false);
		}
	}

	/**
	 * 关闭数据库连接
	 */
	private void close() {
		try {
			if (cconn != null) { // 连接不为空
				cconn.close(); // 关闭数据库连接
			}
		} catch (SQLException e) {
			ErrorMessage = "数据库断开连接出错，错误信息：" + e.getMessage(); // 出错，返回错误信息
		}
		cconn = null; // 设置连接设备为空
	}

	/**
	 * 设置默认的连接参数
	 * 
	 * @param dtype 数据库默认的参数Oracle、Mysql(该方法将被淘汰)
	 */
	private void SetDefaultDatabase(String dtype) {
		if (dtype == "Mysql") {
			DBDriver = "org.gjt.mm.mysql.Driver"; // 设置JDBC Driver
			ConStr = "jdbc:mysql://localhost/agent"; // 设置连接参数
			User = "root"; // 设置用户名
			Password = ""; // 设置密码
		}
		if (dtype == "Oracle") {
			DBDriver = "oracle.jdbc.driver.OracleDriver"; // 设置JDBC Driver
			ConStr = "jdbc:oracle:thin:@192.168.3.8:1521:ora21cn"; // 设置连接参数
			User = "mail"; // 设置用户名
			Password = "mail"; // 设置密码
		}
	}

	/**
	 * 关闭数据库连接
	 */
	public void DisConnect() {
		close();
	} // 关闭
}