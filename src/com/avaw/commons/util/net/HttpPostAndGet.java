package com.avaw.commons.util.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class HttpPostAndGet {
	private String Url; // 提交的URL
	private Properties Parameters; // 参数列表
	private Properties ReturnData; // 接收到的返回数据
	private int BuffSize = 4096; // 缓冲区默认为4096个字节
	private int ReadCount; // 读到的字节个数
	private String ReturnText; // 得到的字符串数据
	private String LastErrorMessage; // 最后的错误信息

	public HttpPostAndGet() {
		Url = null;
		Parameters = null;
	}

	/**
	 * 设置提交的URL
	 * 
	 * @param Url 提交的URL
	 */
	public void setUrl(String Url) {
		this.Url = Url;
	}

	public void setParameters(Properties Parameters) {
		this.Parameters = Parameters;
	}

	/**
	 * 设置读取缓冲区大小
	 * 
	 * @param Size 字节个数
	 */
	public void setBuffSize(int Size) {
		this.BuffSize = Size;
	}

	@SuppressWarnings("rawtypes")
	private String getParameters() {
		if (Parameters == null)
			return null;
		String Parameter = "";
		String Name, Value, ObjName;
		Vector ValueVector;
		Object Obj;
		Parameters.propertyNames();
		Enumeration Names = Parameters.propertyNames();
		while (Names.hasMoreElements()) {
			Name = (String) Names.nextElement();
			Obj = Parameters.get(Name);
			if (Obj == null)
				continue; // 如果数据为空,则跳过
			ObjName = Obj.getClass().getName();
			if (ObjName.equals("java.lang.String") == true) { // 如果数据类型为String,则...
				Value = (String) Obj;
				Parameter += Name + "=" + Value + "&";
			} else if (ObjName.equals("java.util.Vector") == true) { // 如果数据类型为Vector,则...
				ValueVector = (Vector) Obj;
				Value = "";
				for (int i = 0; i < ValueVector.size(); i++) {
					Value += Name + "=" + ValueVector.get(i) + "&";
				}
				Parameter += Value;
			} else {
				System.out.println("不识别的数据类型,要完善HttpPost类 : " + Obj.getClass().getName());
				continue;
			}
		}
		if (Parameter.length() > 1)
			Parameter = Parameter.substring(0, Parameter.length() - 1); // 去掉末尾的'&'符号
		return Parameter;
	}

	/**
	 * 执行POST动作
	 * 
	 * @param sUrl 提交的URL
	 * @param ParameterName 参数名称
	 * @param Value 提交的数据
	 * @return 返回提交后的返回结果
	 */
	public String doPost(String sUrl, String ParameterName, String Value) {
		Parameters = new Properties();
		Parameters.put(ParameterName, Value);
		return doPost(sUrl);
	}

	/**
	 * 执行POST动作
	 * 
	 * @param sUrl 提交的URL
	 * @return 返回提交后的返回结果
	 */
	public String doPost(String sUrl) {
		ReturnData = new Properties();
		Url = sUrl;
		try {
			URL oUrl = new URL(Url);
			URLConnection connection = oUrl.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			out.write(getParameters()); // 输出参数
			out.flush();
			out.close();
			InputStream in = connection.getInputStream();
			byte[] buff = new byte[BuffSize];
			ReadCount = in.read(buff);
			ReturnText = new String(buff, 0, ReadCount);
			if (ReturnText != null)
				ReturnData.put("Html", ReturnText); // 得到Html数据
		} catch (IOException e) {
			ReturnText = null;
			LastErrorMessage = "异常: " + e.getMessage();
		}
		return ReturnText;
	}

	/**
	 * 执行POST动作
	 * 
	 * @param sUrl 提交的URL
	 * @return 返回提交后的返回结果
	 */
	public String doPost(String sUrl, String Json) {
		ReturnData = new Properties();
		Url = sUrl;
		try {
			URL oUrl = new URL(Url);
			URLConnection connection = oUrl.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			out.write(Json.toString()); // 输出参数
			out.flush();
			out.close();
			InputStream in = connection.getInputStream();
			byte[] buff = new byte[BuffSize];
			ReadCount = in.read(buff);
			ReturnText = new String(buff, 0, ReadCount);
			if (ReturnText != null)
				ReturnData.put("Html", ReturnText); // 得到Html数据
		} catch (IOException e) {
			ReturnText = null;
			LastErrorMessage = "异常: " + e.getMessage();
		}
		return ReturnText;
	}

	public String doGetUrl(String sUrl) {
		Url = sUrl + "?" + getParameters();
		return Url;
	}

	/**
	 * 执行Http的GET数据提交方式
	 * 
	 * @param sUrl 要提交到的URL
	 * @return 返回的HTML代码
	 */
	public String doGet(String sUrl) {
		ReturnData = new Properties();
		// boolean bReturn = false;
		Url = sUrl + "?" + getParameters();
		String inline_temp, inline = "";
		try {
			URL feeURL = new URL(Url); // 取URL的信息
			HttpURLConnection HttpURLCon = (HttpURLConnection) feeURL.openConnection();
			// HttpURLCon.setInstanceFollowRedirects(false);
			// HttpURLCon.setFollowRedirects(false);
			
			HttpURLCon.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/536.2 (KHTML, like Gecko) Chrome/19.0.1061.0 Safari/536.2");
			HttpURLCon.addRequestProperty("Host","dict.cn");
			HttpURLCon.addRequestProperty("Cookie","hh=104TQq; __utma=7761447.944282104.1332840949.1332840949.1332840949.1; __utmc=7761447; __utmz=7761447.1332840949.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic|utmctr=%D4%DA%CF%DF%B7%AD%D2%EB");
			HttpURLCon.setDefaultUseCaches(false);
			HttpURLCon.setUseCaches(false);
			// inline = HttpURLCon.getHeaderField("Location");
			// if (inline != null) ReturnData.put("Location",inline);
			// //得到Location
			InputStream ins = feeURL.openStream(); // 打开数据流
			BufferedReader instream = new BufferedReader(new InputStreamReader(ins));// 创建输入流
			inline = "";
			while ((inline_temp = instream.readLine()) != null) { // 循环取出返回信息
				inline += inline_temp;
			}
			if (inline != null)
				ReturnData.put("Html", inline); // 得到Html数据
		} catch (Exception e) {
			inline = null;
			LastErrorMessage = e.getMessage();
		} // 异常
			// try { inline = new String(inline.getBytes("ISO8859_1"),"GB2312");
			// }
			// catch (java.io.UnsupportedEncodingException e) { inline =
			// "System Encoding Exception!"; }
			// LastErrorMessage = inline; //错误信息
		return inline;
	}

	/**
	 * 返回提交后的返回结果
	 * 
	 * @return 返回提交后的返回结果
	 */
	public String getReturnText() {
		return ReturnText;
	}

	/**
	 * 得到最后的错误信息
	 * 
	 * @return 错误信息
	 */
	public String getLastErrorMessage() {
		return LastErrorMessage;
	}
}