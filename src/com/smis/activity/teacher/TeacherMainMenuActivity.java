package com.smis.activity.teacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.smis.activity.LoginActivity;
import com.smis.activity.R;
import com.smis.model.LoginModel;
import com.smis.view.TeacherMainMenuView;

public class TeacherMainMenuActivity extends Activity implements TeacherMainMenuView.LoginDelegate {

	private TeacherMainMenuView teacherMainMenuView;
	private Class<?>[] activities = new Class<?>[] { TeacherDetailActivity.class, TeacherStudentScoresActivity.class,
			TeacherStudentScoreAddActivity.class, TeacherStudentApplicationsActivity.class, TeacherUpdatePwdActivity.class };
	private LoginModel loginModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_main_menu);
		teacherMainMenuView = new TeacherMainMenuView(this, this);
		loginModel = new LoginModel();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, R.string.logout);
		menu.add(0, 2, 2, R.string.exit);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) { // 重新登录
			loginModel.logoutTeacher();
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
			finish();
		} else if (item.getItemId() == 2) { // 退出系统
			System.exit(0);
		}
		return true;
	}

	/**
	 * 跳转activity
	 */
	@Override
	public void switchActivity(int position) {
		Intent intent = new Intent(this, activities[position]);
		startActivity(intent);
	}

}