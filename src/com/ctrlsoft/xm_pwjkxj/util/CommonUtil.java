package com.ctrlsoft.xm_pwjkxj.util;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.inputmethod.InputMethodManager;

public class CommonUtil {

	/**
	 * @description:������Ҫ��Intent
	 * @author:Sunny
	 * @return:Intent
	 * @param f
	 * @return
	 */
	public static Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	/**
	 * 检查存储卡是否插入
	 */
	public static boolean isHasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean existFile(String path) {
		try {
			File file = new File(path);
			return file.exists();
		} catch (RuntimeException e) {
			LogUtil.e(e.getMessage());
			return false;
		}
	}

	/**
	 * 将输入法隐藏
	 * 
	 * @param activity
	 */
	public static void hideInputMethod(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
				.getWindowToken(), 0);
	}

	/**
	 * 获取系统版本号
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int getVersionCode(Context context) {
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = context.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager
					.getPackageInfo(context.getPackageName(),
							PackageManager.GET_CONFIGURATIONS);
			return packInfo.versionCode;
		} catch (RuntimeException | NameNotFoundException e) {
			return 0;
		}
	}

	public static String getVersionName(Context context) {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			LogUtil.e(e.getMessage());
		}
		String version = packInfo.versionName;
		return version;
	}

	public static String getPsdnIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& inetAddress instanceof Inet4Address) {
						// if (!inetAddress.isLoopbackAddress() && inetAddress
						// instanceof Inet6Address) {
						return "{\"result\":\"true\",\"ipaddress\":\""
								+ inetAddress.getHostAddress().toString()
								+ "\"}";
					}
				}
			}
		} catch (RuntimeException | SocketException e) {
			LogUtil.e(e.getMessage());
		}
		return "{\"result\":\"false\"}";
	}
}
