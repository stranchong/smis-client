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
import android.widget.Toast;

import com.smis.activity.R;

public class TeacherStudentScoreUpdateView implements OnClickListener {
	public interface LoginDelegate {
		public void updateInput(Map<String, String> params);

	}

	private Activity ac;
	private AlertDialog.Builder dlgUpdateScore;
	private LinearLayout llytUpdateScore;
	private EditText etScore;
	// 定义接口对象
	private TeacherStudentScoreUpdateView.LoginDelegate dlg;

	public TeacherStudentScoreUpdateView(Activity ac, TeacherStudentScoreUpdateView.LoginDelegate delegate) {
		this.ac = ac;
		dlg = delegate;
		llytUpdateScore = (LinearLayout) ac.getLayoutInflater().inflate(R.layout.dialog_teacher_student_score_update, null);
		etScore = (EditText) llytUpdateScore.findViewById(R.id.etScore);
		dlgUpdateScore = new AlertDialog.Builder(ac).setTitle(R.string.input_score_two).setView(llytUpdateScore)
				.setPositiveButton(R.string.ok, this).setNegativeButton(R.string.cancel, null);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// 点击的是确定按钮
		if (which == DialogInterface.BUTTON_POSITIVE) {
			if (etScore.getText().toString().equals("")) {
				Toast.makeText(ac, R.string.score_not_null, Toast.LENGTH_SHORT).show();
				return;
			} else if (Integer.parseInt(etScore.getText().toString()) > 100) {
				Toast.makeText(ac, R.string.score_must_between_0_and_100, Toast.LENGTH_SHORT).show();
				return;
			}

			// 封装请求参数
			Map<String, String> params = new HashMap<String, String>();
			params.put("score", etScore.getText().toString());
			// 发送修改成绩请求
			dlg.updateInput(params);
		}
		// 不加这句代码，第二次打开对话框会报错
		((ViewGroup) llytUpdateScore.getParent()).removeView(llytUpdateScore);
	}

	/**
	 * 弹出对话框
	 * 
	 * @param itemData
	 */
	public void showDialog(HashMap<String, String> itemData) {
		etScore.setText(itemData.get("score"));
		dlgUpdateScore.show();
	}

}