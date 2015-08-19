package com.smis.util;

import org.apache.http.Header;
import org.apache.http.HeaderElement;

import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.smis.model.BusinessResponse;

/**
 * HTTP通信的工具类
 */
public final class CustomHttpUtils {

	/** xUtis库的HTTP工具类对象 */
	public static HttpUtils httpUtils = new HttpUtils();
	/** 基础的请求URL */
	public static final String BASE_URL = "http://125.216.246.185:80/smis/";
	/** 请求连接失败的错误信息 */
	public static final String SEND_REQUEST_FAILURE = "-1";
	/** session id名 */
	public static final String JSESSIONID = "JSESSIONID";

	static {
		/** 设置响应报文的编码 */
		httpUtils.configResponseTextCharset("utf-8");
	}

	/**
	 * 发送Get请求
	 * 
	 * @param urlSuffix
	 *            url后缀
	 * @param businessResponse
	 *            业务类对象，这里是面向接口编程
	 */
	public static void sendGetRequest(String urlSuffix, BusinessResponse businessResponse) {

		sendRequest(HttpMethod.GET, urlSuffix, null, businessResponse, null);
	}

	/** 重载 */
	public static void sendGetRequest(String urlSuffix, BusinessResponse businessResponse, Integer invokeMethodCode) {

		sendRequest(HttpMethod.GET, urlSuffix, null, businessResponse, invokeMethodCode);
	}

	/**
	 * 发送Post请求
	 * 
	 * @param urlSuffix
	 *            url后缀
	 * @param requestParams
	 *            Post请求参数
	 * @param businessResponse
	 *            业务类对象，这里是面向接口编程
	 */
	public static void sendPostRequest(String urlSuffix, RequestParams requestParams, BusinessResponse businessResponse) {

		sendRequest(HttpMethod.POST, urlSuffix, requestParams, businessResponse, null);
	}

	/** 重载 */
	public static void sendPostRequest(String urlSuffix, RequestParams requestParams, BusinessResponse businessResponse,
			Integer invokeMethodCode) {

		sendRequest(HttpMethod.POST, urlSuffix, requestParams, businessResponse, invokeMethodCode);
	}

	/**
	 * 发送HTTP请求
	 * 
	 * @param httpMethod
	 *            发送请求的方式
	 * @param urlSuffix
	 *            url后缀
	 * @param requestParams
	 *            请求参数
	 * @param businessResponse
	 *            业务类对象，这里是面向接口编程
	 */
	private static void sendRequest(final HttpMethod httpMethod, final String urlSuffix, final RequestParams requestParams,
			final BusinessResponse businessResponse, final Integer invokeMethodCode) {

		if (httpMethod == HttpMethod.POST) {
			Log.d("debug", "发送POST请求的url -->> " + BASE_URL + urlSuffix);
		} else {
			Log.d("debug", "发送GET请求的url -->> " + BASE_URL + urlSuffix);
		}

		new Runnable() {
			@Override
			public void run() {
				// 打印cookie
//				DefaultHttpClient httpClient = (DefaultHttpClient) httpUtils.getHttpClient();
//				List<Cookie> cookies = httpClient.getCookieStore().getCookies();
//				for (Cookie cookie : cookies) {
//					Log.d("debug", "cookie.getName() " + cookie.getName());
//					Log.d("debug", "cookie.getValue() " + cookie.getValue());
//				}
				
				httpUtils.send(httpMethod, BASE_URL + urlSuffix, requestParams, new RequestCallBack<String>() {
					// 发送请求失败
					@Override
					public void onFailure(HttpException errorException, String msg) {
						Log.d("debug", "---------------------发送请求失败----------------------");
						businessResponse.OnRequestFailure();
					}

					// 发送请求成功，收到服务器响应报文
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.d("debug", "---------------收到服务器响应----------------");
						Log.d("debug", "响应信息 ->>" + responseInfo.result);

						if (invokeMethodCode == null) {
							businessResponse.OnMessageResponse(BusinessResponse.DEFAULT_METHOD_CODE, responseInfo.result);
						} else {
							businessResponse.OnMessageResponse(invokeMethodCode, responseInfo.result);
							// 如果响应的是登录请求，那么进行保持会话
//							if (invokeMethodCode == BusinessResponse.LOGIN) {
//								configSessionId(responseInfo);
//							}
						}
					}
				});
			}
		}.run();
	}

	/**
	 * 不过调用这方法后就不能保持会话，有可能是xUtils本身就已经保持了会话
	 * 将JSESSIONID存在在cookie中，保持用户登录会话状态
	 * 
	 * @param responseInfo
	 */
	public static void configSessionId(ResponseInfo<String> responseInfo) {
		Header[] headers = responseInfo.getHeaders("set-cookie");
		for (int i = 0; i < headers.length; i++) {
			HeaderElement[] headerElements = headers[i].getElements();
			for (HeaderElement he : headerElements) {
				Log.d("debug", "headerElement.getName() -->> " + he.getName());
				Log.d("debug", "headerElement.getValue() -->> " + he.getValue());
				if (he.getName().equals(JSESSIONID)) {
					
					return;
				}
			}
		}
	}

}