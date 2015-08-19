package com.smis.activity.teacher;

import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smis.activity.LoginActivity;
import com.smis.activity.R;
import com.smis.activity.BaseActivity;
import com.smis.model.TeacherModel;
import com.smis.view.TeacherUpdatePwdView;

public class TeacherUpdatePwdActivity extends BaseActivity implements TeacherUpdatePwdView.LoginDelegate {

	private TeacherUpdatePwdView teacherUpdatePwdView;
	// 定义业务逻辑层对象
	private TeacherModel teacherModel = new TeacherModel();
	// 定义登录教职工的主键tid和新密码newPwd
	private String tid;
	private String newPwd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_update_pwd);
		teacherUpdatePwdView = new TeacherUpdatePwdView(this, this);
		// 注册响应监听器
		teacherModel.addResponseListener(this);
		// 获取tid
		tid = sharedPreferences.getString("id", null);
	}

	/**
	 * 发送请求-->>发送修改密码请求
	 */
	@Override
	public void updatePwdInput(String pwd) {
		newPwd = pwd;
		teacherModel.updatePwdByTid(tid, newPwd);
	}

	/**
	 * 响应回调方法-->>修改密码的请求响应
	 */
	@Override
	public void OnMessageResponse(Object... results) {
		// 响应字符串解析为JSONObject对象
		JSONObject jsonObject = JSON.parseObject((String) results[0]);

		// 修改密码成功，那么要修改SharedPreferences存储类中的密码
		if (!jsonObject.getBoolean(SUCCESS)) {
			sharedPreferences.edit().putString(LoginActivity.PASSWORD, newPwd).commit();
		}

		// 修改成功或失败，都弹出信息框
		Toast.makeText(this, jsonObject.getString(MSG), Toast.LENGTH_SHORT).show();
	}

}