package com.ctrlsoft.xm_pwjkxj.sll;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;

import com.ctrlsoft.xm_pwjkxj.util.LogUtil;

/**
 * DefaultHttpClient
 */
public class HttpsUtil3 {
	private static final String KEY_STORE_TYPE_BKS = "BKS";

	private static final String KEY_STORE_TYPE_P12 = "PKCS12";

	private static final String SCHEME_HTTPS = "https";

	private static final int HTTPS_PORT = 9512;

	public static final String KEY_STORE_CLIENT_PATH = "client.p12";

	private static final String KEY_STORE_TRUST_PATH = "client.truststore";

	public static final String KEY_STORE_P = "client@geostar";

	private static final String KEY_STORE_TRUST_P = "client@geostar";

	private static KeyStore keyStore;

	private static KeyStore trustStore;

	public static DefaultHttpClient getSslHttpClient(Context pContext) {
		DefaultHttpClient httpsClient = new DefaultHttpClient();
		try {
			// 服务器端需要验证的客户端证书
			keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
			// 客户端信任的服务器端证书
			trustStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);
			InputStream ksIn = pContext.getResources().getAssets()
					.open(KEY_STORE_CLIENT_PATH);
			InputStream tsIn = pContext.getResources().getAssets()
					.open(KEY_STORE_TRUST_PATH);
			try {
				keyStore.load(ksIn, KEY_STORE_P.toCharArray());
				trustStore.load(tsIn, KEY_STORE_TRUST_P.toCharArray());
			} catch (Exception e) {
				LogUtil.e(e.getMessage());
			} finally {
				try {
					ksIn.close();
				} catch (Exception ignore) {
					LogUtil.e(ignore.getMessage());
				}
				try {
					tsIn.close();
				} catch (Exception ignore) {
					LogUtil.e(ignore.getMessage());
				}
			}
			// SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore,
			// KEY_STORE_P, trustStore);
			SSLSocketFactory socketFactory = new MySSLSocketFactory(null, null,
					null);
			// socketFactory
			// .setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme(SCHEME_HTTPS, socketFactory, HTTPS_PORT);
			httpsClient.getConnectionManager().getSchemeRegistry()
					.register(sch);
		} catch (KeyManagementException e) {
			LogUtil.e(e.getMessage());
		} catch (UnrecoverableKeyException e) {
			LogUtil.e(e.getMessage());
		} catch (KeyStoreException e) {
			LogUtil.e(e.getMessage());
		} catch (FileNotFoundException e) {
			LogUtil.e(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			LogUtil.e(e.getMessage());
		} catch (ClientProtocolException e) {
			LogUtil.e(e.getMessage());
		} catch (IOException e) {
			LogUtil.e(e.getMessage());
		}
		return httpsClient;
	}
}
