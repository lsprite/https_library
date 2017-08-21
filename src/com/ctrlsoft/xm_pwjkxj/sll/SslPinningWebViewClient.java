package com.ctrlsoft.xm_pwjkxj.sll;

import java.io.IOException;
import java.io.InputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLHandshakeException;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ctrlsoft.xm_pwjkxj.MyApplication;
import com.ctrlsoft.xm_pwjkxj.util.FileUtil;
import com.ctrlsoft.xm_pwjkxj.util.LogUtil;

/**
 * Created by mennomorsink on 06/05/15.
 */
public class SslPinningWebViewClient extends WebViewClient {

	private Context context;

	public SslPinningWebViewClient(Context context) throws IOException {
		this.context = context;
	}

	@Override
	public WebResourceResponse shouldInterceptRequest(final WebView view,
			String url) {

		return processRequest(Uri.parse(url));
	}

	@Override
	@TargetApi(21)
	public WebResourceResponse shouldInterceptRequest(final WebView view,
			WebResourceRequest interceptedRequest) {
		return processRequest(interceptedRequest.getUrl());
	}

	private WebResourceResponse processRequest(Uri uri) {
		LogUtil.log("SSL_PINNING_WEBVIEWS", "GET: " + uri.toString());
		try {
			boolean passed = true;
			if (uri.toString().contains(".js")
					|| uri.toString().contains(".css")
					|| uri.toString().contains(".ttf")
					|| uri.toString().contains(".jpg")
					|| uri.toString().contains(".png")) {
				String url = uri.toString();
				String encoding = "utf-8";
				String contentType = "text/javascript";
				String cssContentType = "text/css";
				if (url.contains("dialog.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/dialog.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("jcarousellite.min.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/jcarousellite.min.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("jquery-1.7.2.min.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/jquery-1.7.2.min.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("jquery.cookie.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/jquery.cookie.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("jQuery.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/jQuery.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("jquery.mobile-1.4.5.min.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/jquery.mobile-1.4.5.min.js"));
					} catch (IOException e) {
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("lazyload-min.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/lazyload-min.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("md5.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/md5.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("mobiscroll.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/mobiscroll.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("mui.indexedlist.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/mui.indexedlist.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("mui.min.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/mui.min.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("mui.previewimage.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/mui.previewimage.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("mui.zoom.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/mui.zoom.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("public.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/public.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("mobiscroll.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/mobiscroll.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("swipe.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/swipe.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("iosOverlay.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/iosOverlay/iosOverlay.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("jquery.min.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/iosOverlay/jquery.min.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("spin.min.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("JS/iosOverlay/spin.min.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("layer.js")) {
					try {
						return new WebResourceResponse(contentType, encoding,
								MyApplication.getInstance().getAssets()
										.open("layer/layer.js"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("app.css")) {
					try {
						return new WebResourceResponse(cssContentType,
								encoding, MyApplication.getInstance()
										.getAssets().open("CSS/app.css"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("icon.css")) {
					try {
						return new WebResourceResponse(cssContentType,
								encoding, MyApplication.getInstance()
										.getAssets().open("CSS/icon.css"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("mobiscroll.css")) {
					try {
						return new WebResourceResponse(cssContentType,
								encoding, MyApplication.getInstance()
										.getAssets().open("CSS/mobiscroll.css"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("mui.indexedlist.css")) {
					try {
						return new WebResourceResponse(cssContentType,
								encoding, MyApplication.getInstance()
										.getAssets()
										.open("CSS/mui.indexedlist.css"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("mui.min.css")) {
					try {
						return new WebResourceResponse(cssContentType,
								encoding, MyApplication.getInstance()
										.getAssets().open("CSS/mui.min.css"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("qiandao.css")) {
					try {
						return new WebResourceResponse(cssContentType,
								encoding, MyApplication.getInstance()
										.getAssets().open("CSS/qiandao.css"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("style.css")) {
					try {
						return new WebResourceResponse(cssContentType,
								encoding, MyApplication.getInstance()
										.getAssets().open("CSS/style.css"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("custom.css")) {
					try {
						return new WebResourceResponse(cssContentType,
								encoding, MyApplication.getInstance()
										.getAssets()
										.open("JS/iosOverlay/css/custom.css"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("iosOverlay.css")) {
					try {
						return new WebResourceResponse(
								cssContentType,
								encoding,
								MyApplication
										.getInstance()
										.getAssets()
										.open("JS/iosOverlay/css/iosOverlay.css"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("layer.css")) {
					try {
						return new WebResourceResponse(cssContentType,
								encoding, MyApplication.getInstance()
										.getAssets()
										.open("layer/skin/layer.css"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (url.contains("mui.ttf")) {
					try {
						return new WebResourceResponse(cssContentType,
								encoding, MyApplication.getInstance()
										.getAssets().open("mui.ttf"));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (FileUtil.getReallyFileName(url).endsWith(".jpg")) {
					try {
						return new WebResourceResponse(
								"image/jpeg",
								encoding,
								MyApplication
										.getInstance()
										.getAssets()
										.open("images/"
												+ FileUtil
														.getReallyFileName(url)));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else if (FileUtil.getReallyFileName(url).endsWith(".png")) {
					try {
						return new WebResourceResponse(
								"image/png",
								encoding,
								MyApplication
										.getInstance()
										.getAssets()
										.open("images/"
												+ FileUtil
														.getReallyFileName(url)));
					} catch (IOException e) {
						passed = false;
						LogUtil.log("SSL_PINNING_WEBVIEWS",
								"no js file:" + e.getLocalizedMessage());
					}
				} else {
					passed = false;
				}
			} else {
				passed = false;
			}
			if (!passed) {
				HttpsURLConnection urlConnection = HttpsUtil
						.getHttpsURLConnection(context, uri.toString(), "GET");
				// Setup connection
				urlConnection.connect();
				// Get content, contentType and encoding
				InputStream is = urlConnection.getInputStream();
				//
				// Map<String, List<String>> headerFields = urlConnection
				// .getHeaderFields();
				// List<String> cookiesHeader = headerFields.get("Set-Cookie");
				// if (cookiesHeader != null) {
				// for (String cookie : cookiesHeader) {
				// LogUtil.log("urlconnection set-cookie", cookie.toString());
				// }
				// }
				// Map map = urlConnection.getHeaderFields();
				// Set ks = map.keySet();
				// for (Iterator iterator = ks.iterator(); iterator.hasNext();)
				// {
				// String key = (String) iterator.next();
				// System.out.println(key + ":" + map.get(key));
				// }
				// BufferedReader in = new BufferedReader(new
				// InputStreamReader(is,
				// "GBK"));
				// StringBuffer buffer = new StringBuffer();
				// String line = "";
				// while ((line = in.readLine()) != null) {
				// buffer.append(line);
				// }
				// String str = buffer.toString();
				// System.out.println("++++res:" + str);
				//
				String contentType = urlConnection.getContentType();
				String encoding = urlConnection.getContentEncoding();
				LogUtil.log("SSL_PINNING_WEBVIEWS", "contentType: "
						+ contentType + ",encoding:" + encoding);
				// If got a contentType header
				if (contentType != null) {
					String mimeType = contentType;
					// Parse mime type from contenttype string
					if (contentType.contains(";")) {
						mimeType = contentType.split(";")[0].trim();
					}
					LogUtil.log("SSL_PINNING_WEBVIEWS", "Mime: " + mimeType);
					// Return the response
					return new WebResourceResponse(mimeType, encoding, is);
				} else {
					if (uri.toString().contains("jquery")
							|| uri.toString().contains("js")) {
						return new WebResourceResponse("text/javascript",
								encoding, is);
					}
					return new WebResourceResponse("", encoding, is);
				}
			}
		} catch (SSLHandshakeException e) {
			LogUtil.log("SSL_PINNING_WEBVIEWS",
					"SSLHandshakeException:" + e.getLocalizedMessage());
		} catch (Exception e) {
			LogUtil.log("SSL_PINNING_WEBVIEWS",
					"Exception:" + e.getLocalizedMessage());
		}

		// Return empty response for this request
		return new WebResourceResponse(null, null, null);
	}
}