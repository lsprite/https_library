package com.ctrlsoft.xm_pwjkxj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
	/**
	 * 获取单个文件的MD5值！
	 * 
	 * @param file
	 * @return
	 */

	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len = 0;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			if (in != null) {
				while ((len = in.read(buffer, 0, 1024)) != -1) {
					if (digest != null) {
						digest.update(buffer, 0, len);
					} else {
						return null;
					}

				}
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// TODO: handle exception\
				LogUtil.e(e.getMessage());
			}
		}
		if (digest == null) {
			return null;
		} else {
			BigInteger bigInt = new BigInteger(1, digest.digest());
			return bigInt.toString(16);
		}

	}

	public static void deleteFile(String path) {
		try {
			File file = new File(path);
			file.delete();
			file = null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			LogUtil.e("删除文件失败:" + path);
		}
	}

	/*
	 * Java文件操作 获取不带扩展名的文件名
	 */
	public static String getFileNameNoEx(String filename) {
		filename = getReallyFileName(filename);
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	//
	public static String getReallyFileName(String url) {
		try {
			LogUtil.e(url);
			String[] ss = url.split("\\?");// "url":
											// "/api/records?token=BD765FE9AC6DB",去掉后面的token
			String[] t = ss[0].split("/");
			return t[t.length - 1];
		} catch (RuntimeException e) {
			// TODO: handle exception
			return "";
		}
	}

	public static boolean haveHtml(String str) {
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		Pattern pattern = Pattern.compile(regEx_html);
		Matcher matcher = pattern.matcher(str);
		return matcher.find() || compileExChar(str) || str.contains("\\")
				|| str.contains("'") || str.contains("\"");
	}

	public static boolean compileExChar(String str) {
		String limitEx = "[`~@#$%^&*()=|{}':;'\\[\\]<>/~@#￥%……&*（）——|{}【】‘；：”“’]";
		Pattern pattern = Pattern.compile(limitEx);
		Matcher m = pattern.matcher(str);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}
}
