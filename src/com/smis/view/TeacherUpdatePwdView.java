package com.smis.view;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smis.activity.R;

public class TeacherUpdatePwdView implements OnClickListener {
	public interface LoginDelegate {
		public void updatePwdInput(String pwd);
	}

	private Activity ac;
	private EditText etUpdatePwd;
	private EditText etUpdateConfirmPwd;
	private Button btnConfirm;
	// 定义接口对象
	private TeacherUpdatePwdView.LoginDelegate dlg;

	public TeacherUpdatePwdView(Activity ac, TeacherUpdatePwdView.LoginDelegate delegate) {
		this.ac = ac;
		dlg = delegate;
		etUpdatePwd = (EditText) ac.findViewById(R.id.etUpdatePwd);
		etUpdateConfirmPwd = (EditText) ac.findViewById(R.id.etUpdateConfirmPwd);
		btnConfirm = (Button) ac.findViewById(R.id.btnConfirm);
		btnConfirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String pwd = etUpdatePwd.getText().toString();
		String confirmPwd = etUpdateConfirmPwd.getText().toString();

		if (pwd == null || pwd.equals("")) { // 新密码为空，对应输入框获得焦点
			etUpdatePwd.requestFocus();
			return;
		} else if (confirmPwd == null || confirmPwd.equals("")) { // 确认新密码为空，对应输入框获得焦点
			etUpdateConfirmPwd.requestFocus();
			return;
		} else if (pwd.length() < 6 || pwd.length() > 20) {
			etUpdatePwd.requestFocus();
			Toast.makeText(ac, R.string.pwd_between_6_and_20, Toast.LENGTH_SHORT).show();
			return;
		} else if (!pwd.equals(confirmPwd)) {
			Toast.makeText(ac, R.string.pwds_do_not_match, Toast.LENGTH_SHORT).show();
			return;
		}

		// 传递修改后的密码参数
		dlg.updatePwdInput(pwd);
	}

}