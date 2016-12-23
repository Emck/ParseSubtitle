package com.avaw.commons.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import com.avaw.commons.util.constant.Constant;
import com.avaw.commons.util.text.FormatData;

/**
 * 文件相关的类,支持以下方法
 * 清除文件目录(删除目录下所有文件,但保留目录)
 * 复制目录(包含目录下所有文件)
 * 复制文件
 * 删除文件
 * 删除目录(删除目录下所有文件,不保留目录)
 * 获取文件的MD5和SHA
 * 获取指定HTTP Url返回的数据,保存到指定文件
 * 压缩文件或目录
 * 解压缩文件到当前目录
 * @author Emck
 *
 */
public class FileUtil {
	private static final String MD5 = "MD5";
	private static final String SHA = "SHA";
	
	public static String GetTextFileString(String FileName) {
		String texts;
		try {
			BufferedReader br = new BufferedReader(new FileReader(FileName));
			String row;
			texts = "";
			while ((row = br.readLine()) != null) {
				texts += row + Constant.LINE_SEPARATOR;
				//System.out.println(row);
			}
			br.close();
		} catch (IOException e){
			e.printStackTrace();
			return null;
		}
		return texts;
	}
	/**
	 * 得到文件的MD5码(小写字母),用于校验
	 * @param FileName 需要计算的文件名路径
	 * @return 返回计算后的MD5值
	 */
	public static String GetFileMD5String(String FileName) {
		return getFileString(new File(FileName),MD5);
	}
	/**
	 * 得到文件的SHA码(小写字母),用于校验
	 * @param FileName 需要计算的文件名路径
	 * @return 返回计算后的SHA值
	 */
	public static String GetFileSHAString(String FileName) {
		return getFileString(new File(FileName),SHA);
	}
	/**
	 * 得到文件的MD5 或 SHA ,用于校验
	 * @param SourceFile 文件路径
	 * @param jProgressBar 进度条(需要界面支持)
	 * @param Algorithm 算法,支持 MD5 和 SHA
	 * @return 返回计算后的字符串
	 */
	private static String getFileString(File SourceFile, /*JProgressBar jProgressBar,*/ String Algorithm) {
		if (SourceFile.exists() == false) return null;	//如果文件不存在则返回null
		if (Algorithm == null || Algorithm.length() <=0) return null;	//如果算法为空则返回null
		FileInputStream fileInputStream = null;
		//if (jProgressBar != null) {
		//	jProgressBar.setMaximum((int) SourceFile.length());
		//	jProgressBar.setValue(0);
		//	jProgressBar.setString("Ready Calculate MD5 from file: " + SourceFile.getName());			
		//}
		try {
			MessageDigest md = MessageDigest.getInstance(Algorithm);
			fileInputStream = new FileInputStream(SourceFile);
			byte[] buffer = new byte[8192];
			int length = -1;
			//int value = 0;
			while ((length = fileInputStream.read(buffer)) != -1) {
				md.update(buffer, 0, length);
				//value += length;
				//if (jProgressBar != null) jProgressBar.setValue(value);
			}
			return FormatData.BytesToHexString(md.digest());
		} catch (Exception e) {
			return null;
		} finally {
			try {  fileInputStream.close(); }
			catch (IOException ex) { }
		}
	}
	/**
	 * 获取HTTP远程的文件
	 * 
	 * @param Url 要获取的远程HTTP主机文件路径(例如http://www.imageus.cn/test.jsp)
	 * @param DescFile 保存的文件名(例如 /tmp/2.jsp)
	 * @param RefererUrl 刷新URL(例如 http://photo.imageus.cn/)
	 * @return 获取成功返回true,否则返回false
	 */
	public static boolean GetHttpFile(String Url, String DescFile, String RefererUrl) {
		HttpURLConnection httpConnect = null;
		BufferedInputStream bis = null;
		FileOutputStream fw = null;
		try {
			URL url = new URL(Url);
			httpConnect = (HttpURLConnection) url.openConnection();
			//postget.setRequestHeader(SystemParams.HeaderUserAgent);
			//postget.setRequestHeader(SystemParams.HeaderAccept);
			//postget.setRequestHeader(SystemParams.HeaderAcceptLanguage);
			//////postpost.setRequestHeader(SystemParams.HeaderAcceptEncoding);	//加入这个后,返回的数据是压缩过的数据,所以需要特殊处理
			//postget.setRequestHeader(SystemParams.HeaderAcceptCharset);
			//postget.setRequestHeader(SystemParams.HeaderKeepAlive);
			//postget.setRequestHeader(SystemParams.HeaderConnection);
			if (RefererUrl != null && RefererUrl.length() > 0)
				httpConnect.setRequestProperty("Referer", RefererUrl);
			//postget.addRequestHeader("Content-Type","text/html; charset=gbk");
			//postget.setRequestHeader("Cookie", LoginCookie);			
			httpConnect.setDoInput(true);
			httpConnect.connect();
			bis = new BufferedInputStream(httpConnect.getInputStream());
			byte buff[] = new byte[8192];
			int len = 0;
			fw = new FileOutputStream(DescFile);
			while ((len = bis.read(buff)) > 0)
				fw.write(buff, 0, len);
			fw.flush();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (fw != null)
					fw.close();
				if (bis != null)
					bis.close();
				if (httpConnect != null)
					httpConnect.disconnect();
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * 获取HTTP远程的内容,并存储到ByteBuffer中
	 * @param Url 要获取的远程HTTP主机文件路径(例如http://www.imageus.cn/test.jsp)
	 * @param RefererUrl 刷新URL(例如 http://photo.imageus.cn/)
	 * @return 获取成功返回true,否则返回false
	 */
	public static ByteBuffer GetHttpFileBytes(String Url, String RefererUrl) {
		HttpURLConnection httpConnect = null;
		BufferedInputStream bis = null;
		try {
			URL url = new URL(Url);
			httpConnect = (HttpURLConnection) url.openConnection();
			//postget.setRequestHeader(SystemParams.HeaderUserAgent);
			//postget.setRequestHeader(SystemParams.HeaderAccept);
			//postget.setRequestHeader(SystemParams.HeaderAcceptLanguage);
			//////postpost.setRequestHeader(SystemParams.HeaderAcceptEncoding);	//加入这个后,返回的数据是压缩过的数据,所以需要特殊处理
			//postget.setRequestHeader(SystemParams.HeaderAcceptCharset);
			//postget.setRequestHeader(SystemParams.HeaderKeepAlive);
			//postget.setRequestHeader(SystemParams.HeaderConnection);
			if (RefererUrl != null && RefererUrl.length() > 0)
				httpConnect.setRequestProperty("Referer", RefererUrl);
			//postget.addRequestHeader("Content-Type","text/html; charset=gbk");
			//postget.setRequestHeader("Cookie", LoginCookie);			
			httpConnect.setDoInput(true);
			httpConnect.connect();
			bis = new BufferedInputStream(httpConnect.getInputStream());
			ByteBuffer Bytes = ByteBuffer.allocate(httpConnect.getContentLength() + 100);
			
			byte buff[] = new byte[8192];
			int len = 0;
			while ((len = bis.read(buff)) > 0)
				Bytes.put(buff, 0, len);
			return Bytes;
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (httpConnect != null)
					httpConnect.disconnect();
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * 获取HTTP远程的内容,并存储到ByteBuffer中
	 * @param Url 要获取的远程HTTP主机文件路径(例如http://www.imageus.cn/test.jsp)
	 * @return 获取成功返回true,否则返回false
	 */
	public static ByteBuffer GetHttpFileBytes(String Url) {
		return GetHttpFileBytes(Url,null);
	}
	
	/**
	 * 获取HTTP远程的文件
	 * @param Url 要获取的远程HTTP主机文件路径(例如http://www.imageus.cn/test.jsp)
	 * @param DescFile 保存的文件名(例如 /tmp/2.jsp)
	 * @return 获取成功返回true,否则返回false
	 */
	public static boolean GetHttpFile(String Url, String DescFile) {
		return GetHttpFile(Url,DescFile,null);
	}
	/**
	 * 得到指定文件夹下所有文件名(支持子目录)
	 * @param Path 制定的文件夹
	 * @param Filter 文件名过滤规则
	 * @return 返回文件名列表
	 */
	public static List<String> GetFolderAllFileNames(String Path, FileFilter Filter) {
		if (Path == null)
			return null;
		else
			return GetFolder(new File(Path), Filter);
	}
	private static List<String> GetFolder(File Folder, FileFilter Filter) {
		File[] allFiles = Folder.listFiles(Filter); // 得到该文件夹下的所有文件夹和文件数组
		List<String> allFileName = new ArrayList<String>();
		for (int i = 0; i < allFiles.length; i++) {
			if (allFiles[i].isDirectory()) // 如果为文件夹...
				allFileName.addAll(GetFolder(allFiles[i], Filter)); // 递归调用此文件夹
			else if (allFiles[i].isFile()) // 如果是文件...
				allFileName.add(allFiles[i].getPath()); // 保存文件名
		}
		return allFileName;
	}
	/**
	 * 复制文件
	 * @param SourceFile 待复制的文件
	 * @param DescFile 复制后的目标文件
	 * @param BuffSize 复制缓冲区大小(建议使用操作系统的默认块大小,这样会加快复制速度,减少无用的磁盘操作)
	 * @return 复制成功返回true,否则返回false
	 */
	public static boolean CopyFile(String SourceFile, String DescFile, int BuffSize) {
		byte buff []=new byte[BuffSize];
		int leagth;		//长度
		try {
			FileInputStream in=new FileInputStream(SourceFile);
			FileOutputStream out=new FileOutputStream(DescFile);
			leagth=in.read(buff,0,BuffSize);	//读取文件到缓冲区中
			while(leagth>0) {					//读取到了数据则...
				out.write(buff,0,leagth);		//写实际读取到的内容到目标文件中
				leagth=in.read(buff,0,BuffSize);//继续读取文件到缓冲区中
			}
			in.close();
			out.close();
			return true;						//复制结束
		}
		catch (Exception e){ return false; }
	}
	/**
	 * 复制文件
	 * @param SourceFile 待复制的文件
	 * @param DescFile 复制后的目标文件
	 * @return 复制成功返回true,否则返回false
	 */
	public static boolean CopyFile(String SourceFile, String DescFile) {
		return CopyFile(SourceFile,DescFile,4096);
	}
	/**
	 * 文件夹拷贝,将源文件夹下的所有文件及其子文件夹(文件)拷贝到目标文件夹(文件)下
	 * @param SourceFile 源文件
	 * @param DesFile 目标文件
	 * @return 复制成功返回true,否则返回false
	 */
	public static boolean CopyDirectiory(String SourceFile,String DesFile) {
		//递归调用模式
		java.io.File source = new java.io.File(SourceFile);
		if (!source.exists()) return false;		//源文件不存在则返回false
		java.io.File des = new java.io.File(DesFile);
		if (!des.exists()) des.mkdirs();		//目标文件不存在则创建
		java.io.File[] file= source.listFiles();
		for (int i=0;i<file.length;i++){
			if (file[i].isFile()) { //如果是文件则...
				CopyFile(file[i].getPath(),DesFile+Constant.FileSeparator+file[i].getName());	//复制文件
			} else if (file[i].isDirectory()) {		//如果是目录则...
				CopyDirectiory(file[i].getPath(),DesFile+Constant.FileSeparator+file[i].getName());	//递归调用复制目录 
			}
		}
		return true;
	}
	/**
	 * 清空指定的目录
	 * @param delFolder 指定目录
	 * @return 清空成功返回true,否则返回false
	 */	
	public static boolean ClearFolder(String Folder) {
		java.io.File fileFolder = new java.io.File(Folder);
		return DeleteFolder(fileFolder,false);	//删除目录下所有文件,但不删除自己
	}
	/**
	 * 清空指定的目录
	 * @param delFolder 指定目录
	 * @return 清空成功返回true,否则返回false
	 */		
	public static boolean ClearFolder(java.io.File Folder) {
		return DeleteFolder(Folder,false);	//删除目录下所有文件,但不删除自己
	}
	/**
	 * 删除指定的目录(目录允许为非空)
	 * @param delFolder 指定目录
	 * @return 删除成功返回true,否则返回false
	 */
	public static boolean DeleteFolder(java.io.File Folder) {
		return DeleteFolder(Folder, true); // 删除目录下所有文件,且删除自己
	}
	/**
	 * 删除指定的目录(目录允许为非空)
	 * @param delFolder 指定目录
	 * @return 删除成功返回true,否则返回false
	 */
	public static boolean DeleteFolder(String Folder) {
		File fileFolder = new File(Folder);
		return DeleteFolder(fileFolder, true); // 删除目录下所有文件,且删除自己
	}
	/**
	 * 删除指定的目录(目录允许为非空)
	 * @param delFolder 指定目录
	 * @param IsDeleteMyself 是否要删除自身目录(true=删除; false=不删除)
	 * @return 删除成功返回true,否则返回false
	 */
	private static boolean DeleteFolder(File Folder, boolean IsDeleteMyself) {
		// 递归调用模式
		boolean hasDeleted = true; // 目录是否已删除
		File[] allFiles = Folder.listFiles(); // 得到该文件夹下的所有文件夹和文件数组
		for (int i = 0; i < allFiles.length; i++) {
			if (hasDeleted) { // 为true时操作
				if (allFiles[i].isDirectory())
					hasDeleted = DeleteFolder(allFiles[i]); // 如果为文件夹,则递归调用删除文件夹的方法
				else if (allFiles[i].isFile()) {
					try { // 删除文件
						if (!allFiles[i].delete())
							hasDeleted = false; // 删除失败,返回false
					} catch (Exception e) {
						hasDeleted = false; // 异常,返回false
					}
				}
			} else
				break; // 为false,跳出循环
		}
		if (IsDeleteMyself == true && hasDeleted == true)
			Folder.delete(); // 该文件夹已为空文件夹,删除它
		return hasDeleted;
	}
	/**
	 * 删除指定的文件
	 * @param FileName 待删除的文件名
	 * @return 删除成功返回true,否则返回false
	 */
	public static boolean DeleteFile(String FileName) {
		File file = new File(FileName);
		return file.delete();
	}
}