package com.smis.activity.teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.smis.activity.R;
import com.smis.activity.BaseActivity;
import com.smis.model.TeacherModel;
import com.smis.view.TeacherStudentScoreAddView;

public class TeacherStudentScoreAddActivity extends BaseActivity implements TeacherStudentScoreAddView.LoginDelegate {

	private TeacherStudentScoreAddView teacherStudentScoreAddView;
	// 业务逻辑层对象
	private TeacherModel teacherModel = new TeacherModel();
	// 登录教职工的tid
	private String tid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_student_score_add);
		teacherStudentScoreAddView = new TeacherStudentScoreAddView(this, this);
		// 注册响应监听器
		teacherModel.addResponseListener(this);
		// 获取tid
		tid = sharedPreferences.getString("id", null);
		// 获取教职工所教课程
		teacherModel.getTeachCoursesByTid(tid);
	}

	/**
	 * 发送请求-->>根据cid加载教师所教的班级数据
	 */
	@Override
	public void loadClasses(Map<String, String> params) {
		teacherModel.getTeachClassesByCid(params.get("cid"));
	}

	/**
	 * 发送请求-->>根据clid加载教师所教的学生数据
	 */
	@Override
	public void loadStudents(Map<String, String> params) {
		teacherModel.getTeachStudentsByClid(params.get("clid"));
	}

	/**
	 * 发送请求-->>录入学生成绩
	 */
	@Override
	public void addInput(Map<String, String> params) {
		teacherModel.addStudentScore(params);
	}

	/**
	 * 响应回调方法-->>获取所教的课程数据
	 */
	@Override
	public void OnGetTeachCourseResponse(Object... results) {
		teacherStudentScoreAddView.setSpinCnameData(JSON.parseObject((String) results[0],
				new TypeReference<List<HashMap<String, String>>>() {
				}));
	}

	/**
	 * 响应回调方法-->>获取所教的班级数据
	 */
	@Override
	public void OnGetTeachClassponse(Object... results) {
		teacherStudentScoreAddView.setSpinClnameData(JSON.parseObject((String) results[0],
				new TypeReference<List<HashMap<String, String>>>() {
				}));
	}

	/**
	 * 响应回调方法-->>获取所教的学生数据
	 */
	@Override
	public void OnGetTeachStudentResponse(Object... results) {
		teacherStudentScoreAddView.setSpinSnameData(JSON.parseObject((String) results[0],
				new TypeReference<List<HashMap<String, String>>>() {
				}));
	}

	/**
	 * 响应回调方法-->>录入学生成绩
	 */
	@Override
	public void OnMessageResponse(Object... results) {
		// 响应字符串解析为JSONObject对象
		JSONObject jsonObject = JSON.parseObject((String) results[0]);
		// 添加请假信息成功或失败，都是弹出提示信息
		Toast.makeText(this, jsonObject.getString(MSG), Toast.LENGTH_SHORT).show();
	}

}