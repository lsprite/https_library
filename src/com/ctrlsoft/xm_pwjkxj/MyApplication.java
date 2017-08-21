package com.ctrlsoft.xm_pwjkxj;

import android.app.Application;

public class MyApplication extends Application {
	private static MyApplication instance;
	private double latitude = 26.117565;
	private double longitude = 119.335708;
	private String jsession = null;
	private boolean isHome = false;

	public String getJsession() {
		return jsession;
	}

	public void setJsession(String jsession) {
		this.jsession = jsession;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public boolean isHome() {
		return isHome;
	}

	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}

	//
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public static MyApplication getInstance() {
		if (instance == null) {
			instance = new MyApplication();
		}
		return instance;
	}

}
