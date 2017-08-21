package com.ctrlsoft.xm_pwjkxj.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;

public class SimulatorUtil {

	public static boolean JudgeIsSimulator(Context context) {
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String deviceId = telephonyManager.getDeviceId();
			boolean isSimulator = false;
			if (deviceId == null || deviceId.trim().length() == 0
					|| deviceId.matches("0+")) {
				isSimulator = true;
			}
			String cpuInfo = readCpuInfo();
			if ((cpuInfo.contains("intel") || cpuInfo.contains("amd"))) {
				isSimulator = true;
			}
			if (checkBlueStacksFiles()) {
				isSimulator = true;
			}
			if (checkMoniqi1(context)) {
				isSimulator = true;
			}
			if (checkMoniqi2(context)) {
				isSimulator = true;
			}
			if (CheckEmulatorBuild(context)) {
				isSimulator = true;
			}
			if (CheckOperatorNameAndroid(context)) {
				isSimulator = true;
			}
			if (CheckEmulatorFiles()) {
				isSimulator = true;
			}
			return isSimulator;
		} catch (RuntimeException e) {
			// TODO: handle exception
			LogUtil.e(e.getMessage());
		}
		return false;
	}

	public static String readCpuInfo() {
		String result = "";
		try {
			String[] args = { "/system/bin/cat", "/proc/cpuinfo" };
			ProcessBuilder cmd = new ProcessBuilder(args);

			Process process = cmd.start();
			StringBuffer sb = new StringBuffer();
			String readLine = "";
			BufferedReader responseReader = new BufferedReader(
					new InputStreamReader(process.getInputStream(), "utf-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine);
			}
			responseReader.close();
			result = sb.toString().toLowerCase();
		} catch (IOException ex) {
			LogUtil.e(ex.getMessage());
		}
		return result;
	}

	// //判断bluestacks的关键路径
	private static String[] known_bluestacks = {
			"/data/app/com.bluestacks.appmart-1.apk",
			"/data/app/com.bluestacks.BstCommandProcessor-1.apk",
			"/data/app/com.bluestacks.help-1.apk",
			"/data/app/com.bluestacks.home-1.apk",
			"/data/app/com.bluestacks.s2p-1.apk",
			"/data/app/com.bluestacks.searchapp-1.apk",
			"/data/bluestacks.prop", "/data/data/com.androVM.vmconfig",
			"/data/data/com.bluestacks.accelerometerui",
			"/data/data/com.bluestacks.appfinder",
			"/data/data/com.bluestacks.appmart",
			"/data/data/com.bluestacks.appsettings",
			"/data/data/com.bluestacks.BstCommandProcessor",
			"/data/data/com.bluestacks.bstfolder",
			"/data/data/com.bluestacks.help", "/data/data/com.bluestacks.home",
			"/data/data/com.bluestacks.s2p",
			"/data/data/com.bluestacks.searchapp",
			"/data/data/com.bluestacks.settings",
			"/data/data/com.bluestacks.setup",
			"/data/data/com.bluestacks.spotlight",
			"/mnt/prebundledapps/bluestacks.prop.orig" };

	public static boolean checkBlueStacksFiles() {
		for (int i = 0; i < known_bluestacks.length; i++) {
			String file_name = known_bluestacks[i];
			File qemu_file = new File(file_name);
			if (qemu_file.exists()) {
				return true;
			}
		}
		return false;
	}

	// private static String[] known_qemu_drivers = { "goldfish" };

	private static String[] known_files = {
			"/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace",
			"/system/bin/qemu-props" };

	// private static String[] known_numbers = { "15555215554", "15555215556",
	// "15555215558", "15555215560", "15555215562", "15555215564",
	// "15555215566", "15555215568", "15555215570", "15555215572",
	// "15555215574", "15555215576", "15555215578", "15555215580",
	// "15555215582", "15555215584", };

	// private static String[] known_device_ids = { "000000000000000" // 默认ID
	// };
	//
	// private static String[] known_imsi_ids = { "310260000000000" // 默认的 imsi
	// id
	// };
	/** 通过电池的伏数和温度来判断是真机还是模拟器 */
	private static boolean checkMoniqi1(Context con) {
		boolean reuslt = false;
		IntentFilter intentFilter = new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatusIntent = con.registerReceiver(null, intentFilter);
		int voltage = batteryStatusIntent.getIntExtra("voltage", 99999);
		int temperature = batteryStatusIntent.getIntExtra("temperature", 99999);
		if (((voltage == 0) && (temperature == 0))
				|| ((voltage == 10000) && (temperature == 0))) {
			// 这是通过电池的伏数和温度来判断是真机还是模拟器
			reuslt = true;
		} else {
			reuslt = false;
		}
		return reuslt;
	}

	private static boolean checkMoniqi2(Context con) {
		boolean reuslt = false;
		// linxiangkun
		TelephonyManager telephonyManager = (TelephonyManager) con
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = telephonyManager.getDeviceId();
		// 如果 运行的 是一个 模拟器
		if (deviceId == null || deviceId.trim().length() == 0
				|| deviceId.matches("0+")) {
			reuslt = true;// 这个就是模拟器
		} else {
			reuslt = false;
		}
		return reuslt;
	}

	// 检测手机上的一些硬件信息
	private static Boolean CheckEmulatorBuild(Context context) {
		String BOARD = android.os.Build.BOARD;
		String BOOTLOADER = android.os.Build.BOOTLOADER;
		String BRAND = android.os.Build.BRAND;
		String DEVICE = android.os.Build.DEVICE;
		String HARDWARE = android.os.Build.HARDWARE;
		String MODEL = android.os.Build.MODEL;
		String PRODUCT = android.os.Build.PRODUCT;
		if (BOARD == "unknown" || BOOTLOADER == "unknown" || BRAND == "generic"
				|| DEVICE == "generic" || MODEL == "sdk" || PRODUCT == "sdk"
				|| HARDWARE == "goldfish") {
			return true;
		}
		return false;
	}

	// 检测手机运营商家
	private static boolean CheckOperatorNameAndroid(Context context) {
		String szOperatorName = ((TelephonyManager) context
				.getSystemService("phone")).getNetworkOperatorName();
		if (szOperatorName.toLowerCase().equals("android") == true) {
			return true;
		}
		return false;
	}

	// 检测模拟器上特有的几个文件
	private static Boolean CheckEmulatorFiles() {
		for (int i = 0; i < known_files.length; i++) {
			String file_name = known_files[i];
			File qemu_file = new File(file_name);
			if (qemu_file.exists()) {
				return true;
			}
		}
		return false;
	}
}
