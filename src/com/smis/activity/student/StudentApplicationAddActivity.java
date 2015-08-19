package com.smis.activity.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.smis.activity.BaseActivity;
import com.smis.activity.R;
import com.smis.model.StudentModel;
import com.smis.view.StudentApplicationAddView;

public class StudentApplicationAddActivity extends BaseActivity implements StudentApplicationAddView.LoginDelegate {

	private StudentApplicationAddView studentApplicationAddView;
	// 业务逻辑层对象
	private StudentModel studentModel = new StudentModel();
	// 登录学生的主键sid
	private String sid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_application_add);
		studentApplicationAddView = new StudentApplicationAddView(this, this);
		// 注册响应监听器
		studentModel.addResponseListener(this);
		// 获取登录学生的主键sid
		sid = sharedPreferences.getString("id", null);
		// 获取管理该学生的所有中队长
		studentModel.getManageTeachersBySid(sid);
	}

	/**
	 * 发送请求-->>录入请假信息
	 */
	@Override
	public void addInput(Map<String, String> params) {
		params.put("sid", sid);
		studentModel.addApplication(params);
	}

	/**
	 * 响应回调方法-->>获取管理该学生的所有中队长作为spinner的数据
	 */
	@Override
	public void OnGetManageTeacherResponse(Object... results) {
		studentApplicationAddView.setSpinTnameData(JSON.parseObject((String) results[0],
				new TypeReference<List<HashMap<String, String>>>() {
				}));
	}

	/**
	 * 响应回调方法-->>增加请假信息
	 */
	@Override
	public void OnMessageResponse(Object... results) {
		// 响应字符串解析为JSONObject对象
		JSONObject jsonObject = JSON.parseObject((String) results[0]);
		// 添加请假信息成功或失败，都是弹出提示信息
		Toast.makeText(this, jsonObject.getString(MSG), Toast.LENGTH_SHORT).show();
	}

}