package com.smis.view;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.smis.activity.R;
import com.smis.adapter.StudentApplicationsAdapter;
import com.smis.util.DateFormatUtils;

public class TeacherStudentApplicationsView implements OnClickListener, OnScrollListener {
	public interface LoginDelegate {
		public void loadMoreData(int currentCount);
	}

	private Activity ac;
	private ListView lvStudentApplications;
	private View listfoot;
	private Button btnFoot;
	private ProgressBar pbFoot;
	// 定义接口对象
	private TeacherStudentApplicationsView.LoginDelegate dlg;
	// 适配器
	private StudentApplicationsAdapter adapter;
	// 适配器的数据
	private List<HashMap<String, String>> data;
	// 学生请假信息的总记录数
	private int totalCount = 1000;
	// 判断是否滚到当前可见的最后一行
	private boolean isLastRow;

	public TeacherStudentApplicationsView(Activity ac, TeacherStudentApplicationsView.LoginDelegate delegate) {
		this.ac = ac;
		dlg = delegate;
		// 实例化ListView控件和其底部布局
		lvStudentApplications = (ListView) ac.findViewById(R.id.lvStudentApplications);
		listfoot = ac.getLayoutInflater().inflate(R.layout.listfoot_common, null);
		btnFoot = (Button) listfoot.findViewById(R.id.btnFoot);
		pbFoot = (ProgressBar) listfoot.findViewById(R.id.pbFoot);
		// 注册点击按钮、滚动监听器
		btnFoot.setOnClickListener(this);
		lvStudentApplications.setOnScrollListener(this);
		lvStudentApplications.setOnCreateContextMenuListener(ac);
	}

	/**
	 * 点击底部按钮
	 * 
	 * @param v
	 */
	public void onClick(View v) {
		if (adapter.getCount() < totalCount) { // 当前加载的数据条数小于总条数
			// 加载更多分页的课程成绩信息
			dlg.loadMoreData(adapter.getCount());
			// 进度条可见,按钮不可见
			pbFoot.setVisibility(View.VISIBLE);
			btnFoot.setVisibility(View.GONE);
			isLastRow = false;
		} else if (isLastRow) { // 所有的条目已经和最大条数相等，则移除底部的View，弹出提示信息
			lvStudentApplications.removeFooterView(listfoot);
			Toast.makeText(ac, R.string.complete_loading_all_data, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 滚动时一直回调，直到停止滚动时才停止回调。单击时回调一次。
	 * 
	 * @param view
	 * @param firstVisibleItem
	 *            当前能看见的第一个列表项ID（从0开始）
	 * @param visibleItemCount
	 *            当前能看见的列表项个数（小半个也算）
	 * @param totalItemCount
	 *            当前所加载列表项的总数
	 */
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// 判断是否滚到当前所加载的数据最后一行，是则将isLastRow置为true
		if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
			isLastRow = true;
		} else {
			isLastRow = false;
		}
	}

	/**
	 * 正在滚动时回调，回调2-3次，手指没抛则回调2次。scrollState = 2的这次不回调 回调顺序如下 第1次：scrollState =
	 * SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动 第2次：scrollState = SCROLL_STATE_FLING(2)
	 * 手指做了抛的动作（手指离开屏幕前，用力滑了一下） 第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
	 * 当屏幕停止滚动时为0；当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1； 由于用户的操作，屏幕产生惯性滑动时为2
	 * 当滚到最后一行且停止滚动时，执行加载
	 * 
	 * @param view
	 * @param scrollState
	 */
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 当滚到最后一行且停止滚动时，执行加载
		if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
			onClick(btnFoot);
		}
	}

	/**
	 * 显示第一次加载的数据
	 * 
	 * @param moreData
	 */
	public void printInitData(List<HashMap<String, String>> initData) {
		data = initData;
		// 实例化adapter
		adapter = new StudentApplicationsAdapter(ac, data, R.layout.listitem_teacher_student_application_list, new String[] {
				"sname", "clname", "createDate", "startDate", "overDate", "countDay", "leaveReason", "leavePlace", "confirmMark",
				"confirmName", "confirmDate", "confirmReply" }, new int[] { R.id.tvSname, R.id.tvClname, R.id.tvCreateDate,
				R.id.tvStartDate, R.id.tvOverDate, R.id.tvCountDay, R.id.tvLeaveReason, R.id.tvLeavePlace, R.id.tvConfirmMark,
				R.id.tvConfirmName, R.id.tvConfirmDate, R.id.tvConfirmReply });
		// lvCourseScore加上底部布局，注意要放在setAdapter方法前
		lvStudentApplications.addFooterView(listfoot);
		// 显示数据
		lvStudentApplications.setAdapter(adapter);
	}

	/**
	 * 显示更多加载的数据
	 * 
	 * @param moreData
	 */
	public void printMoreData(List<HashMap<String, String>> moreData) {
		data.addAll(moreData);
		// 刷新数据
		adapter.notifyDataSetChanged();
		// 按钮可见
		btnFoot.setVisibility(View.VISIBLE);
		// 进度不可见
		pbFoot.setVisibility(View.GONE);
	}

	/**
	 * 设置总记录条数
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 修改数据项
	 * 
	 * @param position
	 * @param map
	 */
	public void updateItemData(int position, Map<String, String> newItemData) {
		// 获取旧的数据对象
		HashMap<String, String> itemData = data.get(position);
		// 旧数据改为新数据
		itemData.put("confirmMark", newItemData.get("confirmMark"));
		itemData.put("confirmName", newItemData.get("confirmName"));
		itemData.put("confirmDate", DateFormatUtils.format(new Date()));
		// 刷新数据
		adapter.notifyDataSetChanged();
	}

	public List<HashMap<String, String>> getData() {
		return data;
	}

}