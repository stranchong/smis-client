package com.smis.activity.student;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
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
import com.smis.activity.BaseActivity;
import com.smis.activity.R;
import com.smis.adapter.StudentApplicationsAdapter;
import com.smis.model.StudentModel;
import com.smis.view.StudentApplicationsView;

public class StudentApplicationsActivity extends BaseActivity implements StudentApplicationsView.LoginDelegate {

	private static final int UPDATE = 0;
	private static final int DELETE = 1;

	private StudentApplicationsView studentApplicationsView;
	// 业务逻辑层对象
	private StudentModel studentModel = new StudentModel();
	// 获取登录学生的主键sid
	private String sid;
	// 打开上下文菜单时的item position
	private int position;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_application_list);
		studentApplicationsView = new StudentApplicationsView(this, this);
		// 注册响应监听器
		studentModel.addResponseListener(this);
		// 获取登录学生信息的主键
		sid = sharedPreferences.getString("id", null);
		// 加载学生分页的课程成绩信息
		studentModel.getPageApplicationsBySid(sid);
		// 加载课程成绩信息的总记录数
		studentModel.getApplicationTotalCountBySid(sid);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.add(0, UPDATE, 0, R.string.update);
		menu.add(0, DELETE, 1, R.string.delete);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// 通过AdapterContextMenuInfo定位到用户按的是哪条数据，即获取position
		AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		// 获取所选item在List中的下标
		position = menuInfo.position;
		// 获取所选item的数据
		HashMap<String, String> itemData = studentApplicationsView.getData().get(position);
		// 获取所选item的审批状态
		Integer confirmMark = Integer.parseInt(itemData.get("confirmMark"));

		switch (item.getItemId()) {
		case UPDATE:
			// 审批状态为未审批的请假信息才能修改
			if (confirmMark != StudentApplicationsAdapter.STATE_DO_NOT_APPROVE) {
				Toast.makeText(this, R.string.had_approved_can_not_operate, Toast.LENGTH_SHORT).show();
				break;
			}
			// 使用Bundle传递数据
			Bundle map = new Bundle();
			map.putString("aid", itemData.get("aid"));
			map.putString("startDate", itemData.get("startDate"));
			map.putString("overDate", itemData.get("overDate"));
			map.putString("leavePlace", itemData.get("leavePlace"));
			map.putString("leaveReason", itemData.get("leaveReason"));
			// map.putString("position", String.valueOf(position));
			Intent intent = new Intent(this, StudentApplicationUpdateActivity.class);
			intent.putExtras(map);
			// 跳转到修改请假信息的StudentApplicationUpdateActivity
			startActivityForResult(intent, 0);
			break;

		case DELETE:
			// 审批状态为同意的请假信息不能删除
			if (confirmMark == StudentApplicationsAdapter.STATE_AGREE) {
				Toast.makeText(this, R.string.approve_agree_application_can_not_delete, Toast.LENGTH_SHORT).show();
				break;
			}
			// 发送删除请假信息请求
			studentModel.deleteApplicationByAid(itemData.get("aid"));
			break;
		}

		return super.onContextItemSelected(item);
	}

	/**
	 * 从修改请假信息activity返回后调用
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
		if (resultCode == RESULT_OK) {
			Bundle map = resultData.getExtras();
			if (map.getString(SUCCESS).equals(TRUE)) {
				// 修改成功，那么刷新对应item的数据
				studentApplicationsView.updateItemData(position, map);
			}
		}
	}

	/**
	 * 发送请求-->>加载更多数据
	 */
	@Override
	public void loadMoreData(int currentCount) {
		studentModel.getMoreApplicationsBySid(sid, currentCount);
	}

	/**
	 * 响应回调方法-->>显示初始的数据
	 */
	@Override
	public void OnMessageResponse(Object... results) {
		studentApplicationsView.printInitData(JSON.parseObject((String) results[0],
				new TypeReference<List<HashMap<String, String>>>() {
				}));
	}

	/**
	 * 响应回调方法-->>设置总记录条数
	 */
	@Override
	public void OnGetTotalCountResponse(Object... results) {
		studentApplicationsView.setTotalCount(Integer.parseInt((String) results[0]));
	}

	/**
	 * 响应回调方法-->>显示更多分页数据
	 */
	@Override
	public void OnGetMoreDataResponse(Object... results) {
		studentApplicationsView.printMoreData(JSON.parseObject((String) results[0],
				new TypeReference<List<HashMap<String, String>>>() {
				}));
	}

	/**
	 * 响应回调方法-->>删除所点击的数据项
	 */
	@Override
	public void OnDeleteApplicationResponse(Object... results) {
		// 将响应的JSON字符串转化为JSONObject对象
		JSONObject jsonObject = JSON.parseObject((String) results[0]);
		studentApplicationsView.deleteItemData(position, Boolean.parseBoolean(jsonObject.getString(SUCCESS)),
				jsonObject.getString(MSG));
	}

}