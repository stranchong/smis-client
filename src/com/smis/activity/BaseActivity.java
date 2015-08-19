package com.smis.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.smis.activity.student.StudentMainMenuActivity;
import com.smis.activity.teacher.TeacherMainMenuActivity;
import com.smis.model.BusinessResponse;

public class BaseActivity extends Activity implements BusinessResponse {
	// 轻量级存储类
	protected static SharedPreferences sharedPreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获得SharedPreferences对象
		if (sharedPreferences == null) {
			sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, R.string.back_to_main_interface);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		if (item.getItemId() == 1) {
			// 设置跳转的activity
			String identity = sharedPreferences.getString(IDENTITY, null);
			if (identity.equals(STUDENT)) {
				intent.setClass(this, StudentMainMenuActivity.class);
			} else if (identity.equals(TEACHER)) {
				intent.setClass(this, TeacherMainMenuActivity.class);
			}
		}
		startActivity(intent);
		finish();
		return true;
	}

	@Override
	public void OnRequestFailure() {
		Toast.makeText(this, REQUEST_SEND_FAIL, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void OnMessageResponse(Object... results) {

	}

	@Override
	public void OnLoginResponse(Object... results) {

	}

	@Override
	public void OnGetTotalCountResponse(Object... results) {

	}

	@Override
	public void OnGetMoreDataResponse(Object... results) {

	}

	@Override
	public void OnGetManageTeacherResponse(Object... results) {

	}

	@Override
	public void OnDeleteApplicationResponse(Object... results) {

	}

	@Override
	public void OnGetSuperiorLevelResponse(Object... results) {

	}

	@Override
	public void OnGetTeachCourseResponse(Object... results) {

	}

	@Override
	public void OnGetTeachClassponse(Object... results) {

	}

	@Override
	public void OnGetTeachStudentResponse(Object... results) {

	}

	@Override
	public void OnUpdateStudentScoreResponse(Object... results) {

	}

	@Override
	public void OnUpdateStudentApplicationResponse(Object... results) {
	}

}