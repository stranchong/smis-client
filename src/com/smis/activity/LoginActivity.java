package com.smis.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smis.activity.student.StudentMainMenuActivity;
import com.smis.activity.teacher.TeacherMainMenuActivity;
import com.smis.model.LoginModel;
import com.smis.view.LoginView;

public class LoginActivity extends BaseActivity implements LoginView.LoginDelegate {
	// 与用户交互的类
	private LoginView loginView;
	// 定义业务逻辑层对象
	private LoginModel loginModel = new LoginModel();
	// 业务逻辑层对象LoginModel方法login的参数
	private Map<String, Object> params = new HashMap<String, Object>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginView = new LoginView(this, this);
		// 注册响应监听器
		loginModel.addResponseListener(this);
		// 初始化登录请求参数
		params.put(URL_SUFFIX, STUDENT_LOGIN_URL_SUFFIX);
		params.put(USERNAME_KEY, SID);
		params.put(IDENTITY, STUDENT);
		// 将SharedPreferences存储类的用户名、密码信息清除
		Editor editor = sharedPreferences.edit();
		editor.putString(USERNAME_VALUE, "");
		editor.putString(PASSWORD, "");
		editor.commit();
	}

	/**
	 * 获取登录请求参数，发送登录请求
	 */
	@Override
	public void loginInput(String usernameValue, String password) {
		// 设置此次请求连接的用户名和密码
		params.put(USERNAME_VALUE, usernameValue);
		params.put(PASSWORD, password);
		// 登录
		loginModel.login(params);
	}

	/**
	 * 设置学生登录的各种参数
	 */
	@Override
	public void setStudentLoginInfo() {
		params.put(URL_SUFFIX, STUDENT_LOGIN_URL_SUFFIX);
		params.put(USERNAME_KEY, SID);
		params.put(IDENTITY, STUDENT);
	}

	/**
	 * 设置教师登录的各种参数
	 */
	@Override
	public void setTeacherLoginInfo() {
		params.put(URL_SUFFIX, TEACHER_LOGIN_URL_SUFFIX);
		params.put(USERNAME_KEY, TID);
		params.put(IDENTITY, TEACHER);
	}

	/**
	 * 响应回调方法-->>登录请求的响应
	 */
	@Override
	public void OnLoginResponse(Object... results) {
		// 响应字符串解析为JSONObject对象
		JSONObject jsonObject = JSON.parseObject((String) results[0]);

		// 处理①：登录验证失败，弹出提示框
		if (!jsonObject.getBoolean(SUCCESS)) {
			Toast.makeText(this, jsonObject.getString(MSG), Toast.LENGTH_SHORT).show();
			return;
		}

		// 处理②：登录验证成功
		// 设置意图
		Intent intent = new Intent();
		if (params.get(IDENTITY).equals(STUDENT)) {
			intent.setClass(this, StudentMainMenuActivity.class);
		} else {
			intent.setClass(this, TeacherMainMenuActivity.class);
		}
		// 跳转activity
		startActivity(intent);
		finish();
		// 保存登录信息到SharedPreferences存储类中
		Editor editor = sharedPreferences.edit();
		editor.putString(URL_SUFFIX, params.get(URL_SUFFIX).toString());
		editor.putString(USERNAME_KEY, params.get(USERNAME_KEY).toString());
		editor.putString(USERNAME_VALUE, params.get(USERNAME_VALUE).toString());
		editor.putString(PASSWORD, params.get(PASSWORD).toString());
		editor.putString(IDENTITY, params.get(IDENTITY).toString());
		editor.putString("id", params.get(USERNAME_VALUE).toString());
		if (jsonObject.getString(OBJ) != null)
			editor.putString("tname", jsonObject.getString(OBJ));
		editor.commit();

		Log.d(DEBUG, "LoginActivity OnMessageResponse IDENTITY  ->> " + sharedPreferences.getString(IDENTITY, ""));
		Log.d(DEBUG, "LoginActivity OnMessageResponse USERNAMEKEY  ->> " + sharedPreferences.getString(USERNAME_KEY, ""));
		Log.d(DEBUG, "LoginActivity OnMessageResponse USERNAMEVALUE  ->> " + sharedPreferences.getString(USERNAME_VALUE, ""));
		Log.d(DEBUG, "LoginActivity OnMessageResponse PASSWORD  ->> " + sharedPreferences.getString(PASSWORD, ""));
		Log.d(DEBUG, "LoginActivity OnMessageResponse id  ->> " + sharedPreferences.getString("id", ""));
	}

}