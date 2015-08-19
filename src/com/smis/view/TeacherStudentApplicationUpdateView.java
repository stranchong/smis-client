package com.smis.view;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.smis.activity.R;
import com.smis.adapter.StudentApplicationsAdapter;

public class TeacherStudentApplicationUpdateView implements OnClickListener {
	public interface LoginDelegate {
		public void updateInput(Map<String, String> params);

	}

	private AlertDialog.Builder dlgUpdateApplication;
	private LinearLayout llytUpdateApplication;
	private RadioGroup rgConfirmMark;
	private EditText etConfirmReply;
	// 定义接口对象
	private TeacherStudentApplicationUpdateView.LoginDelegate dlg;

	public TeacherStudentApplicationUpdateView(Activity ac, TeacherStudentApplicationUpdateView.LoginDelegate delegate) {
		dlg = delegate;
		llytUpdateApplication = (LinearLayout) ac.getLayoutInflater().inflate(R.layout.dialog_teacher_student_application_update,
				null);
		rgConfirmMark = (RadioGroup) llytUpdateApplication.findViewById(R.id.rgConfirmMark);
		etConfirmReply = (EditText) llytUpdateApplication.findViewById(R.id.etConfirmReply);
		dlgUpdateApplication = new AlertDialog.Builder(ac).setTitle(R.string.approve_student_application)
				.setView(llytUpdateApplication).setPositiveButton(R.string.ok, this).setNegativeButton(R.string.cancel, null);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// 点击的是确定按钮
		if (which == DialogInterface.BUTTON_POSITIVE) {
			// 获取审批状态、审批意见
			String confirmReply = etConfirmReply.getText().toString().trim();
			String confirmMark = null;
			switch (rgConfirmMark.getCheckedRadioButtonId()) {
			case R.id.rbAgree:
				confirmMark = String.valueOf(StudentApplicationsAdapter.STATE_AGREE);
				break;
			case R.id.rbDisagree:
				confirmMark = String.valueOf(StudentApplicationsAdapter.STATE_DISAGREE);
				break;
			}
			// 封装请求参数
			Map<String, String> params = new HashMap<String, String>();
			params.put("confirmMark", confirmMark);
			params.put("confirmReply", confirmReply);
			// 发送请求参数准备
			dlg.updateInput(params);
		}
		// 不加这句代码，第二次打开对话框会报错
		((ViewGroup) llytUpdateApplication.getParent()).removeView(llytUpdateApplication);
	}

	/**
	 * 弹出对话框
	 */
	public void showDialog() {
		dlgUpdateApplication.show();
	}

}