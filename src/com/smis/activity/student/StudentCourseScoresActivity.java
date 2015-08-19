package com.smis.activity.student;

import java.util.HashMap;
import java.util.List;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smis.activity.BaseActivity;
import com.smis.activity.R;
import com.smis.model.StudentModel;
import com.smis.view.StudentCourseScoresView;

public class StudentCourseScoresActivity extends BaseActivity implements StudentCourseScoresView.LoginDelegate {

	private StudentCourseScoresView studentCourseScoresView;
	// 业务逻辑层对象
	private StudentModel studentModel = new StudentModel();
	// 获取登录学生的主键sid
	private String sid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_course_score_list);
		studentCourseScoresView = new StudentCourseScoresView(this, this);
		// 注册响应监听器
		studentModel.addResponseListener(this);
		// 获取登录学生信息的主键
		sid = sharedPreferences.getString("id", null);
		// 加载学生分页的课程成绩信息
		studentModel.getPageCourseScoresBySid(sid);
		// 加载课程成绩信息的总记录数
		studentModel.getCourseScoreTotalCountBySid(sid);
	}

	/**
	 * 发送请求-->>加载更多数据
	 */
	@Override
	public void loadMoreData(int currentCount) {
		studentModel.getMoreCourseScoresBySid(sid, currentCount);
	}

	/**
	 * 响应回调方法-->>显示初始的数据
	 */
	@Override
	public void OnMessageResponse(Object... results) {
		studentCourseScoresView.printInitData(JSON.parseObject((String) results[0],
				new TypeReference<List<HashMap<String, String>>>() {
				}));
	}

	/**
	 * 响应回调方法-->>设置总记录条数
	 */
	@Override
	public void OnGetTotalCountResponse(Object... results) {
		studentCourseScoresView.setTotalCount(Integer.parseInt((String) results[0]));
	}

	/**
	 * 响应回调方法-->>显示更多分页数据
	 */
	@Override
	public void OnGetMoreDataResponse(Object... results) {
		studentCourseScoresView.printMoreData(JSON.parseObject((String) results[0],
				new TypeReference<List<HashMap<String, String>>>() {
				}));
	}

}