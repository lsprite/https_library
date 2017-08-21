package com.ctrlsoft.xm_pwjkxj.sll;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import android.content.Context;

import com.ctrlsoft.xm_pwjkxj.util.LogUtil;
import com.squareup.okhttp.OkHttpClient;

public class HttpsUtil2 {

	public static OkHttpClient getSslHttpClient(Context pContext) {
		OkHttpClient mOkHttpClient = new OkHttpClient();
		mOkHttpClient.setConnectTimeout(3600, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3600, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3600, TimeUnit.SECONDS);
		try {

			SSLContext sc = SSLContextUtil.setCertificate(pContext);
//			mOkHttpClient.setSslSocketFactory(sc.getSocketFactory());
			mOkHttpClient.setSslSocketFactory(new Tls12SocketFactory(sc
					.getSocketFactory()));
			HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			mOkHttpClient.setHostnameVerifier(DO_NOT_VERIFY);
			// mOkHttpClient.setHostnameVerifier(new HostnameVerifier() {
			// @Override
			// public boolean verify(String hostname, SSLSession session) {
			// return true;
			// }
			// });
		} catch (RuntimeException e) {
			// TODO: handle exception
			LogUtil.e(e.getMessage());
		}
		return mOkHttpClient;
	}

}
