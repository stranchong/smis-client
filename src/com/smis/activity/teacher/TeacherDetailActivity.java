package com.smis.activity.teacher;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.smis.activity.R;
import com.smis.activity.BaseActivity;
import com.smis.domain.Teacher;
import com.smis.model.TeacherModel;
import com.smis.view.TeacherDetailView;

public class TeacherDetailActivity extends BaseActivity {

	private TeacherDetailView teacherDetailView;
	// 定义业务逻辑层对象
	private TeacherModel teacherModel = new TeacherModel();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_detail);
		teacherDetailView = new TeacherDetailView(this);
		// 注册响应监听器
		teacherModel.addResponseListener(this);
		// 获取登录教职工的主键tid
		String tid = sharedPreferences.getString("id", null);
		// 加载个人信息
		teacherModel.getTeacherInfo(tid);
	}

	/**
	 * 响应回调方法-->>在UI控件上显示个人信息
	 */
	@Override
	public void OnMessageResponse(Object... results) {
		// 获取教职工个人信息
		Teacher teacher = JSON.parseObject((String) results[0], Teacher.class);
		// 在UI控件上显示教师个人信息
		teacherDetailView.printTeacherDetail(teacher);
	}
}