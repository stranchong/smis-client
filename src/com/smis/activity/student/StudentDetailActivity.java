package com.smis.activity.student;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.smis.activity.BaseActivity;
import com.smis.activity.R;
import com.smis.domain.Student;
import com.smis.model.StudentModel;
import com.smis.view.StudentDetailView;

public class StudentDetailActivity extends BaseActivity {

	// 与用户交互的类
	private StudentDetailView studentDetailView;
	// 定义业务逻辑层对象
	private StudentModel studentModel = new StudentModel();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_detail);
		studentDetailView = new StudentDetailView(this);
		// 注册响应监听器
		studentModel.addResponseListener(this);
		// 加载个人信息
		studentModel.getStudentInfoBySid(sharedPreferences.getString("id", null));
	}

	/**
	 * 响应回调方法-->>在UI控件上显示个人信息
	 */
	@Override
	public void OnMessageResponse(Object... results) {
		// 获取学生个人信息
		Student student = JSON.parseObject((String) results[0], Student.class);
		// 在UI控件上显示学生个人信息
		studentDetailView.printStudentDetail(student);
	}
}