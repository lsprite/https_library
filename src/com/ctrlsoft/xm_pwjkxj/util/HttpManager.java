package com.ctrlsoft.xm_pwjkxj.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.ctrlsoft.xm_pwjkxj.MyApplication;
import com.ctrlsoft.xm_pwjkxj.sll.HttpsUtil;
import com.ctrlsoft.xm_pwjkxj.sll.HttpsUtil2;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * @COMPANY:sunnyTech
 * @CLASS:HttpManager
 * @DESCRIPTION:网络通讯
 * @AUTHOR:Sunny
 * @VERSION:v1.0
 * @DATE:2014-10-21 下午5:34:38
 */
public class HttpManager {

	public static String posturl(ArrayList<NameValuePair> nameValuePairs,
			String url, String encode) {
		BufferedReader responseReader = null;
		InputStream is = null;
		try {
			HttpsURLConnection urlConnection = HttpsUtil.getHttpsURLConnection(
					MyApplication.getInstance().getApplicationContext(), url,
					"POST");
			// Setup connection
			urlConnection.connect();
			// Get content, contentType and encoding
			is = urlConnection.getInputStream();
			StringBuffer sb = new StringBuffer();
			String readLine;

			// 处理响应流，必须与服务器响应流输出的编码一致
			responseReader = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append("\n");
			}

			LogUtil.e(SMS4.decodeSMS4toString(sb.toString().trim()));
			return SMS4.decodeSMS4toString(sb.toString().trim());
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e(e.getMessage());
			return "error";
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (RuntimeException | IOException e) {
				// TODO: handle exception
				LogUtil.e(e.getMessage());
			}
			try {
				if (responseReader != null) {
					responseReader.close();
				}
			} catch (RuntimeException | IOException e) {
				// TODO: handle exception
				LogUtil.e(e.getMessage());
			}
		}
	}

	public static String geturl(String url, String encode) {
		InputStream is = null;
		try {
			HttpsURLConnection urlConnection = HttpsUtil.getHttpsURLConnection(
					MyApplication.getInstance().getApplicationContext(), url,
					"POST");
			// Setup connection
			urlConnection.connect();
			// Get content, contentType and encoding
			is = urlConnection.getInputStream();
			StringBuffer sb = new StringBuffer();
			String readLine;
			BufferedReader responseReader;
			// 处理响应流，必须与服务器响应流输出的编码一致
			responseReader = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append("\n");
			}
			responseReader.close();
			LogUtil.e(SMS4.decodeSMS4toString(sb.toString().trim()));
			return SMS4.decodeSMS4toString(sb.toString().trim());
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e(e.getMessage());
			return "error";
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				LogUtil.e(e.getMessage());
			}
		}
	}

	/**
	 * @param url
	 *            servlet的地址
	 * @param params
	 *            要传递的参数
	 * @param files
	 *            要上传的文件
	 * @return true if upload success else false
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String uploadFiles(String url, Map<String, String> params,
			Map<String, File> filesMap) {
		String data = "";
		LogUtil.e(url);
		try {
			OkHttpClient client = HttpsUtil2.getSslHttpClient(MyApplication
					.getInstance().getApplicationContext());
			MultipartBuilder builder = new MultipartBuilder()
					.type(MultipartBuilder.FORM);
			// 添加上传的文件
			Iterator<String> iterator = filesMap.keySet().iterator();
			RequestBody fileBody = null;
			while (iterator.hasNext()) {
				String fileName = iterator.next();
				LogUtil.e("while-----iterator fileName:" + fileName + "  file:"
						+ filesMap.get(fileName));
				fileBody = RequestBody.create(
						MediaType.parse(guessMimeType(fileName)),
						filesMap.get(fileName));
				builder.addPart(
						Headers.of("Content-Disposition", "form-data; name=\""
								+ fileName + "\"; filename=\"" + fileName
								+ "\""), fileBody);
			}
			Iterator it = params.entrySet().iterator();
			String key;
			String value;
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				key = entry.getKey().toString();
				value = entry.getValue().toString();
				builder.addPart(
						Headers.of("Content-Disposition", "form-data; name=\""
								+ key + "\""), RequestBody.create(null, value));
			}
			RequestBody requestBody = builder.build();
			Request request = new Request.Builder().url(url).post(requestBody)
					.build();
			Response response = client.newCall(request).execute();
			data = response.body().string();
			LogUtil.e(SMS4.decodeSMS4toString(data.trim()));
			return SMS4.decodeSMS4toString(data.trim());
		} catch (ClientProtocolException e) {
			LogUtil.e(e.getMessage());
			return "error";
		} catch (IOException e) {
			LogUtil.e(e.getMessage());
			return "error";
		}
	}

	private static String guessMimeType(String path) {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String contentTypeFor = fileNameMap.getContentTypeFor(path);
		if (contentTypeFor == null) {
			contentTypeFor = "application/octet-stream";
		}
		return contentTypeFor;
	}

	// 供调用百度地图接口，无需https
	public static String posturlhttp(ArrayList<NameValuePair> nameValuePairs,
			String url, String encode) {
		String tmp = "";
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			String logString = url + "?";
			for (int i = 0; i < nameValuePairs.size(); i++) {
				NameValuePair nameValuePair = nameValuePairs.get(i);
				if (i == (nameValuePairs.size() - 1)) {
					logString += nameValuePair.getName() + "="
							+ nameValuePair.getValue();
				} else {
					logString += nameValuePair.getName() + "="
							+ nameValuePair.getValue() + "&";
				}
				LogUtil.e(nameValuePair.getName() + "="
						+ nameValuePair.getValue());
			}
			LogUtil.e("HttpManager", "HttpManager Post:" + logString);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (RuntimeException | IOException e) {
			return "error";
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, encode));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			tmp = sb.toString();
		} catch (RuntimeException | IOException e) {
			return "error";
		}
		LogUtil.e(tmp);
		return tmp;
	}

}
