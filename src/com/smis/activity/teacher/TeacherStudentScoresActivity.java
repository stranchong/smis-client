package com.smis.activity.teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.smis.activity.R;
import com.smis.activity.BaseActivity;
import com.smis.model.TeacherModel;
import com.smis.view.TeacherStudentScoreUpdateView;
import com.smis.view.TeacherStudentScoresView;

public class TeacherStudentScoresActivity extends BaseActivity implements TeacherStudentScoresView.LoginDelegate,
		TeacherStudentScoreUpdateView.LoginDelegate {

	private TeacherStudentScoresView teacherStudentScoresView;
	private TeacherStudentScoreUpdateView teacherStudentScoreUpdateView;
	// 业务逻辑层对象
	private TeacherModel teacherModel = new TeacherModel();
	// 获取登录教职工的主键tid
	private String tid;
	// 保存long click的item在data中的下标
	private int position;
	// 保存修改过的成绩
	private String score;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_student_score_list);
		teacherStudentScoresView = new TeacherStudentScoresView(this, this);
		teacherStudentScoreUpdateView = new TeacherStudentScoreUpdateView(this, this);
		// 注册响应监听器
		teacherModel.addResponseListener(this);
		// 获取登录教职工信息的主键
		tid = sharedPreferences.getString("id", null);
		// 加载分页的学生成绩信息
		teacherModel.getStudentScoresByTid(tid);
		// 加载学生成绩信息的总记录数
		teacherModel.getStudentScoreTotalCountByTid(tid);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.add(0, 0, 0, R.string.update);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// 通过AdapterContextMenuInfo定位到用户按的是哪条数据，即获取position
		AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		// 保存所选item在List中的下标
		position = menuInfo.position;
		// 打开修改成绩对话框
		teacherStudentScoreUpdateView.showDialog(teacherStudentScoresView.getData().get(position));
		return super.onContextItemSelected(item);
	}

	/**
	 * 发送请求-->>加载更多数据
	 */
	@Override
	public void loadMoreData(int currentCount) {
		teacherModel.getMoreStudentScoresByTid(tid, currentCount);
	}

	/**
	 * 发送请求-->>修改学生成绩
	 */
	@Override
	public void updateInput(Map<String, String> params) {
		score = params.get("score");
		Map<String, String> itemData = teacherStudentScoresView.getData().get(position);
		params.put("cid", itemData.get("cid"));
		params.put("sid", itemData.get("sid"));
		teacherModel.updateStudentScore(params);
	}

	/**
	 * 响应回调方法-->>显示初始的数据
	 */
	@Override
	public void OnMessageResponse(Object... results) {
		teacherStudentScoresView.printInitData(JSON.parseObject((String) results[0],
				new TypeReference<List<HashMap<String, String>>>() {
				}));
	}

	/**
	 * 响应回调方法-->>设置总记录条数
	 */
	@Override
	public void OnGetTotalCountResponse(Object... results) {
		teacherStudentScoresView.setTotalCount(Integer.parseInt((String) results[0]));
	}

	/**
	 * 响应回调方法-->>显示更多分页数据
	 */
	@Override
	public void OnGetMoreDataResponse(Object... results) {
		teacherStudentScoresView.printMoreData(JSON.parseObject((String) results[0],
				new TypeReference<List<HashMap<String, String>>>() {
				}));
	}

	/**
	 * 响应回调方法-->>修改学生成绩
	 */
	@Override
	public void OnUpdateStudentScoreResponse(Object... results) {
		// 响应字符串解析为JSONObject对象
		JSONObject jsonObject = JSON.parseObject((String) results[0]);
		// 修改成功，那么刷新对应item的数据
		if (jsonObject.getBoolean(SUCCESS)) {
			Map<String, String> newItemData = new HashMap<String, String>();
			newItemData.put("score", score);
			teacherStudentScoresView.updateItemData(position, newItemData);
		}
		// 成功或失败都弹出提示信息
		Toast.makeText(this, jsonObject.getString(MSG), Toast.LENGTH_SHORT).show();
	}

}