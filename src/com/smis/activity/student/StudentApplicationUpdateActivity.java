package com.smis.activity.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smis.activity.BaseActivity;
import com.smis.activity.R;
import com.smis.model.StudentModel;
import com.smis.view.StudentApplicationUpdateView;

public class StudentApplicationUpdateActivity extends BaseActivity implements StudentApplicationUpdateView.LoginDelegate {

	private StudentApplicationUpdateView studentApplicationUpdateView;
	// 业务逻辑层对象
	private StudentModel studentModel = new StudentModel();
	// 获取传递数据和返回数据的intent
	private Intent intent;
	// intent传递数据的载体
	private Bundle params;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_application_update);
		studentApplicationUpdateView = new StudentApplicationUpdateView(this, this);
		// 注册响应监听器
		studentModel.addResponseListener(this);
		// 用intent传递过来的参数作为各控件的初始值
		intent = getIntent();
		params = intent.getExtras();
		// 初始化控件的值
		studentApplicationUpdateView.printInitData(params);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, R.string.back);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		if (item.getItemId() == 1) {
			intent.setClass(this, StudentApplicationsActivity.class);
		}
		startActivity(intent);
		finish();
		return true;
	}

	/**
	 * 发送请求-->>修改请假信息
	 */
	@Override
	public void updateInput(String startDate, String overDate, String leavePlace, String leaveReason) {
		// 设置请求参数
		params.putString("startDate", startDate);
		params.putString("overDate", overDate);
		params.putString("leavePlace", leavePlace);
		params.putString("leaveReason", leaveReason);
		studentModel.updateApplicationByAid(params);
	}

	/**
	 * 响应回调方法
	 */
	@Override
	public void OnMessageResponse(Object... results) {
		// 响应字符串解析为JSONObject对象
		JSONObject jsonObject = JSON.parseObject((String) results[0]);

		// 修改失败，弹出提示框
		if (jsonObject.getString(SUCCESS).equals(FALSE)) {
			Toast.makeText(this, jsonObject.getString(MSG), Toast.LENGTH_SHORT).show();
			return;
		}

		// 修改请假信息成功
		// 设置返回数据
		params.putString("countDay", jsonObject.getString(OBJ));
		params.putString(SUCCESS, jsonObject.getString(SUCCESS));
		params.putString(MSG, jsonObject.getString(MSG));
		intent.putExtras(params);
		setResult(RESULT_OK, intent);
		// 关闭activity
		finish();
	}

}