package com.smis.activity.student;

import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smis.activity.BaseActivity;
import com.smis.activity.LoginActivity;
import com.smis.activity.R;
import com.smis.model.StudentModel;
import com.smis.view.StudentUpdatePwdView;

public class StudentUpdatePwdActivity extends BaseActivity implements StudentUpdatePwdView.LoginDelegate {

	private StudentUpdatePwdView studentUpdatePwdView;
	// 定义业务逻辑层对象
	private StudentModel studentModel = new StudentModel();
	// 定义登录学生的主键sid和新密码newPwd
	private String sid;
	private String newPwd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_update_pwd);
		studentUpdatePwdView = new StudentUpdatePwdView(this, this);
		// 注册响应监听器
		studentModel.addResponseListener(this);
		// 获取登录学生的主键sid
		sid = sharedPreferences.getString("id", null);
	}

	/**
	 * 发送请求-->>修改密码
	 */
	@Override
	public void updatePwdInput(String pwd) {
		newPwd = pwd;
		studentModel.updatePwdBySid(sid, newPwd);
	}

	/**
	 * 响应回调方法-->>修改密码的请求响应
	 */
	@Override
	public void OnMessageResponse(Object... results) {
		// 响应字符串解析为JSONObject对象
		JSONObject jsonObject = JSON.parseObject((String) results[0]);

		// 修改密码成功，那么要修改SharedPreferences存储类中的密码
		if (!jsonObject.getBooleanValue(SUCCESS)) {
			sharedPreferences.edit().putString(LoginActivity.PASSWORD, newPwd).commit();
		}

		// 修改成功或失败，都弹出信息框
		Toast.makeText(this, jsonObject.getString(MSG), Toast.LENGTH_SHORT).show();
	}

}