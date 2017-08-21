package com.ctrlsoft.xm_pwjkxj.sll;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;

import org.apache.http.conn.ssl.SSLSocketFactory;

import com.ctrlsoft.xm_pwjkxj.MyApplication;
import com.ctrlsoft.xm_pwjkxj.util.LogUtil;

public class MySSLSocketFactory extends SSLSocketFactory {
	private SSLContext sslContext = SSLContext.getInstance("TLS");

	public MySSLSocketFactory(KeyStore keystore, String keystorePassword,
			KeyStore truststore) throws NoSuchAlgorithmException,
			KeyManagementException, KeyStoreException,
			UnrecoverableKeyException {
		super(keystore, keystorePassword, truststore);
		// TODO Auto-generated constructor stub
		// sslContext.init(null, new TrustManager[]{myTrustManager}, null);
		try {
			sslContext = SSLContextUtil.setCertificate(MyApplication
					.getInstance().getApplicationContext());
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e(e.getMessage());
		}
	}

	public Socket createSocket(Socket socket, String host, int port,
			boolean autoClose) throws IOException, UnknownHostException {
		SSLSocket sslSocket = (SSLSocket) sslContext.getSocketFactory()
				.createSocket(socket, host, port, autoClose);
		sslSocket.setEnabledProtocols(new String[] { "TLSv1", "TLSv1.1",
				"TLSv1.2" });
		return sslSocket;
	}

	public Socket createSocket() throws IOException {
		SSLSocket sslSocket = (SSLSocket) sslContext.getSocketFactory()
				.createSocket();
		sslSocket.setEnabledProtocols(new String[] { "TLSv1", "TLSv1.1",
				"TLSv1.2" });
		return sslSocket;
	}

	public SSLContext getSSLContext() {
		return sslContext;
	}
}