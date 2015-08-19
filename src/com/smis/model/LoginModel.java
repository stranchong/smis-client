package com.smis.model;

import java.util.Map;

import android.util.Log;

import com.lidroid.xutils.http.RequestParams;
import com.smis.util.CustomHttpUtils;

public class LoginModel extends BaseModel {

	/**
	 * 用户登录
	 * 
	 * @param params
	 *            封装请求参数用户名和密码的Map
	 */
	public void login(Map<String, Object> params) {
		Log.d(DEBUG, "urlSuffix -->> " + params.get(URL_SUFFIX));
		// 设置HTTP请求参数
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter((String) params.get(USERNAME_KEY), (String) params.get(USERNAME_VALUE));
		requestParams.addBodyParameter(PASSWORD, (String) params.get(PASSWORD));
		// 发送Post请求
		CustomHttpUtils.sendPostRequest((String) params.get(URL_SUFFIX), requestParams, LoginModel.this, LOGIN);
	}

	public void logoutStudent() {
		// 发送Get请求
		CustomHttpUtils.sendGetRequest("student/logout.html", LoginModel.this);
	}

	public void logoutTeacher() {
		// 发送Get请求
		CustomHttpUtils.sendGetRequest("teacher/logout.html", LoginModel.this);
	}

}