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
import com.smis.view.TeacherStudentApplicationUpdateView;
import com.smis.view.TeacherStudentApplicationsView;

public class TeacherStudentApplicationsActivity extends BaseActivity implements TeacherStudentApplicationsView.LoginDelegate,
		TeacherStudentApplicationUpdateView.LoginDelegate {

	private TeacherStudentApplicationsView teacherStudentApplicationsView;
	private TeacherStudentApplicationUpdateView teacherStudentApplicationUpdateView;
	// 业务逻辑层对象
	private TeacherModel teacherModel = new TeacherModel();
	// 获取登录教职工的主键tid，姓名tname
	private String tid;
	private String tname;
	// 打开上下文菜单时的item position
	private int position;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_student_application_list);
		teacherStudentApplicationsView = new TeacherStudentApplicationsView(this, this);
		teacherStudentApplicationUpdateView = new TeacherStudentApplicationUpdateView(this, this);
		// 注册响应监听器
		teacherModel.addResponseListener(this);
		// 获取tid,tname
		tid = sharedPreferences.getString("id", null);
		tname = sharedPreferences.getString("tname", null);
		// 发送请求，加载分页的学生请假信息
		teacherModel.getPageStudentApplicationsByTid(tid);
		// 发送请求，加载学生请假信息的总记录数
		teacherModel.getStudentApplicationTotalCountByTid(tid);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.add(0, 0, 0, R.string.approve);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// 通过AdapterContextMenuInfo定位到用户按的是哪条数据，即获取position
		AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		// 保存所选item在List中的下标
		position = menuInfo.position;
		// 打开审批请假信息对话框
		teacherStudentApplicationUpdateView.showDialog();
		return super.onContextItemSelected(item);
	}

	/**
	 * 发送请求-->>加载更多数据
	 */
	@Override
	public void loadMoreData(int currentCount) {
		teacherModel.getMoreStudentApplicationsByTid(tid, currentCount);
	}

	/**
	 * 发送请求-->>审批请假信息
	 */
	@Override
	public void updateInput(Map<String, String> params) {
		Map<String, String> itemData = teacherStudentApplicationsView.getData().get(position);
		params.put("aid", itemData.get("aid"));
		params.put("startDate", itemData.get("startDate"));
		params.put("countDay", itemData.get("countDay"));
		params.put("confirmTid", tid);
		teacherModel.updateApplicationByAid(params);
		// 保存审批意见在itemData中
		itemData.put("confirmReply", params.get("confirmReply"));
	}

	/**
	 * 响应回调方法-->>显示初始的数据
	 */
	@Override
	public void OnMessageResponse(Object... results) {
		teacherStudentApplicationsView.printInitData(JSON.parseObject((String) results[0],
				new TypeReference<List<HashMap<String, String>>>() {
				}));
	}

	/**
	 * 响应回调方法-->>设置总记录条数
	 */
	@Override
	public void OnGetTotalCountResponse(Object... results) {
		teacherStudentApplicationsView.setTotalCount(Integer.parseInt((String) results[0]));
	}

	/**
	 * 响应回调方法-->>显示更多分页数据
	 */
	@Override
	public void OnGetMoreDataResponse(Object... results) {
		teacherStudentApplicationsView.printMoreData(JSON.parseObject((String) results[0],
				new TypeReference<List<HashMap<String, String>>>() {
				}));
	}

	/**
	 * 响应回调方法-->>审批学生请假信息
	 */
	@Override
	public void OnUpdateStudentApplicationResponse(Object... results) {
		// 响应字符串解析为JSONObject对象
		JSONObject jsonObject = JSON.parseObject((String) results[0]);
		// 修改成功，那么刷新对应item的数据
		if (jsonObject.getBoolean(SUCCESS)) {
			Map<String, String> newItemData = new HashMap<String, String>();
			newItemData.put("confirmMark", jsonObject.getString(OBJ));
			newItemData.put("confirmName", tname);
			teacherStudentApplicationsView.updateItemData(position, newItemData);
		}
		// 成功或失败都弹出提示信息
		Toast.makeText(this, jsonObject.getString(MSG), Toast.LENGTH_SHORT).show();
	}

}