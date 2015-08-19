package com.smis.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smis.activity.student.StudentMainMenuActivity;
import com.smis.activity.teacher.TeacherMainMenuActivity;
import com.smis.model.LoginModel;

public class LogoActivity extends BaseActivity {

	// 定义跳转activity的意图
	private Intent intent = new Intent();
	// 定义业务逻辑层对象
	private LoginModel loginModel = new LoginModel();
	// 业务逻辑层对象LoginModel方法login的参数
	private Map<String, Object> params = new HashMap<String, Object>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE); // 去除标题栏
		setContentView(R.layout.activity_logo_);
		// 注册响应监听器
		loginModel.addResponseListener(this);
		// 获取登录需要的信息参数
		params.put(USERNAME_KEY, sharedPreferences.getString(USERNAME_KEY, ""));
		params.put(USERNAME_VALUE, sharedPreferences.getString(USERNAME_VALUE, ""));
		params.put(PASSWORD, sharedPreferences.getString(PASSWORD, ""));
		params.put(URL_SUFFIX, sharedPreferences.getString(URL_SUFFIX, ""));
		params.put(IDENTITY, sharedPreferences.getString(IDENTITY, ""));
		// 有参数为空的话，那么直接跳转到LoginActivity
		if (params.get(USERNAME_KEY).equals("") || params.get(USERNAME_VALUE).equals("") || params.get(PASSWORD).equals("")
				|| params.get(URL_SUFFIX).equals("")) {
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		// 自动登录
		loginModel.login(params);
	}

	/**
	 * 发送请求失败-->>跳转到登录activity
	 */
	@Override
	public void OnRequestFailure() {
		intent.setClass(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 响应回调方法-->>自动登录响应
	 */
	@Override
	public void OnLoginResponse(Object... results) {
		// 响应字符串解析为JSONObject对象
		JSONObject jsonObject = JSON.parseObject((String) results[0]);

		// 处理①：服务器验证登录失败，跳转到LoginActivity
		if (!jsonObject.getBoolean(SUCCESS)) {
			startActivity(intent);
			finish();
			return;
		}

		// 处理②：服务器验证登录成功
		// 设置跳转的activity
		if (params.get(IDENTITY).equals(STUDENT)) {
			intent.setClass(this, StudentMainMenuActivity.class);
		} else if (params.get(IDENTITY).equals(TEACHER)) {
			intent.setClass(this, TeacherMainMenuActivity.class);
		}
		// activity跳转
		startActivity(intent);
		finish();
	}
}