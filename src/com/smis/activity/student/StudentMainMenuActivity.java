package com.smis.activity.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.smis.activity.LoginActivity;
import com.smis.activity.R;
import com.smis.model.LoginModel;
import com.smis.view.StudentMainMenuView;

public class StudentMainMenuActivity extends Activity implements StudentMainMenuView.LoginDelegate {

	private StudentMainMenuView studentMainMenuView;
	private Class<?>[] activities = new Class<?>[] { StudentDetailActivity.class, StudentCourseScoresActivity.class,
			StudentApplicationsActivity.class, StudentApplicationAddActivity.class, StudentUpdatePwdActivity.class };
	private LoginModel loginModel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_main_menu);
		studentMainMenuView = new StudentMainMenuView(this, this);
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
		if (item.getItemId() == 1) { // 退出登录
			loginModel.logoutStudent();
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