package com.avaw.commons.util.text;

import java.text.DecimalFormat;

public class FormatData {
	private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * 格式化数据类型Double ,输出格式化后字符串
	 * @param Data 需要格式化的数字
	 * @param FormatRules 格式规则
	 * @return string: 返回格式化后字符串
	 */
	public static String FormatToString(final double Data, String FormatRules){
		DecimalFormat formatter = new DecimalFormat(FormatRules);
		return formatter.format(Data);
	}	
	/**
	 * BYTE转换为字符串(小写字母)
	 * @param data BYTE数据
	 * @return 返回字符串
	 */
	public static String BytesToHexString(byte[] Data) {
		char[] temp = new char[Data.length * 2];
		for (int i = 0; i < Data.length; i++) {
			byte b = Data[i];
			temp[i * 2] = hexDigits[b >>> 4 & 0x0f];
			temp[i * 2 + 1] = hexDigits[b & 0x0f];
		}
		return new String(temp);
    }
	/**
	 * 十六进制字符串转换为字节数组函数(不区分大小写)
	 * @param Data 十六进制字符串
	 * @return 返回字节数组
	 */
	public static byte[] HexStringToBytes(String Data) {
		int iLength = Data.length();
		int iBuff = iLength / 2;
		byte[] buff = new byte[iBuff];
		int j = 0;
		for (int i = 0; i < iLength; i+=2) {
			try {
				String s1 = Data.substring(i, i+2);
				buff[j++] = (byte) Integer.parseInt(s1, 16);
			} catch (Exception e ) {
				e.printStackTrace();
				return null;
			}
		}
		return buff;
	}
}