package com.ctrlsoft.xm_pwjkxj.sll;

import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import android.content.Context;

import com.ctrlsoft.xm_pwjkxj.util.LogUtil;

/**
 * HttpsURLConnection
 */
public class HttpsUtil {

	public static HttpsURLConnection getHttpsURLConnection(Context mcontext,
			String urlpath, String method) throws Exception {
		// Setup connection
		//
		LogUtil.log("HttpsURLConnection", urlpath);
		URL url = new URL(urlpath);
		HttpsURLConnection urlConnection = (HttpsURLConnection) url
				.openConnection();
		// urlConnection.setRequestProperty("Cookie",
		// CookieUtil.getCookieString(mcontext));
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
		urlConnection.setRequestProperty("Content-Type", "binary/octet-stream");
		urlConnection.setRequestMethod(method);
		// Set SSL Socket Factory for this request
		SSLContext sslContext = SSLContextUtil.setCertificate(mcontext);
		urlConnection.setSSLSocketFactory(new Tls12SocketFactory(sslContext
				.getSocketFactory()));
		HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		urlConnection.setHostnameVerifier(DO_NOT_VERIFY);
		// ��������֤��
		// urlConnection.setHostnameVerifier(new HostnameVerifier() {
		// @Override
		// public boolean verify(String hostname, SSLSession session) {
		// LogUtil.log("++hostname", hostname);
		// // if ("222.76.241.234".equals(hostname)) {
		// return true;
		// // } else {
		// // return false;
		// // }
		// }
		// });
		return urlConnection;

	}

}
