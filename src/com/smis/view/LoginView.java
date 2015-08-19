package com.smis.view;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.smis.activity.R;

public class LoginView implements OnClickListener, OnCheckedChangeListener {
	public interface LoginDelegate {
		public void loginInput(String usernameValue, String password);

		public void setStudentLoginInfo();

		public void setTeacherLoginInfo();
	}

	private Button btnLogin;
	private EditText etUserName;
	private EditText etPassword;
	private RadioGroup rgIdentity;
	// 定义接口对象
	private LoginView.LoginDelegate dlg;

	public LoginView(Activity ac, LoginView.LoginDelegate delegate) {
		dlg = delegate;
		btnLogin = (Button) ac.findViewById(R.id.btnLogin);
		etUserName = (EditText) ac.findViewById(R.id.etUserName);
		etPassword = (EditText) ac.findViewById(R.id.etPassword);
		rgIdentity = (RadioGroup) ac.findViewById(R.id.rgIdentity);
		btnLogin.setOnClickListener(this);
		rgIdentity.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		String usernameValue = etUserName.getText().toString().trim();
		String password = etPassword.getText().toString();
		// 用户名为空，对应输入框则获得焦点
		if (usernameValue == null || usernameValue.equals("")) {
			etUserName.requestFocus();
			return;
		} else if (password == null || password.equals("")) {
			// 密码为空，对应输入框则获得焦点
			etPassword.requestFocus();
			return;
		}
		// 登录
		dlg.loginInput(usernameValue, password);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rbStudent:
			dlg.setStudentLoginInfo();
			break;
		case R.id.rbTeacher:
			dlg.setTeacherLoginInfo();
			break;
		}
	}

}