package com.smis.view;

import java.text.ParseException;
import java.util.Calendar;

import org.joda.time.DateTime;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.smis.activity.R;
import com.smis.util.DateFormatUtils;

public class StudentApplicationUpdateView implements OnClickListener, OnFocusChangeListener {
	public interface LoginDelegate {
		public void updateInput(String startDate, String overDate, String leavePlace, String leaveReason);
	}

	private Activity ac;
	private EditText etStartDate;
	private EditText etOverDate;
	private EditText etLeavePlace;
	private EditText etLeaveReason;
	private Button btnUpdate;
	// 定义接口对象
	private StudentApplicationUpdateView.LoginDelegate dlg;
	private Calendar calendar;
	private int todayYear;
	private int todayMonthOfYear;
	private int todayDayOfMonth;
	// 开始日期，结束日期，今天日期对象
	private DateTime startDateTime, overDateTime, todayDateTime;

	public StudentApplicationUpdateView(Activity ac, StudentApplicationUpdateView.LoginDelegate delegate) {
		this.ac = ac;
		dlg = delegate;
		etStartDate = (EditText) ac.findViewById(R.id.etStartDate);
		etOverDate = (EditText) ac.findViewById(R.id.etOverDate);
		etLeavePlace = (EditText) ac.findViewById(R.id.etLeavePlace);
		etLeaveReason = (EditText) ac.findViewById(R.id.etLeaveReason);
		btnUpdate = (Button) ac.findViewById(R.id.btnUpdate);
		// 注册事件监听器
		etStartDate.setOnClickListener(this);
		etStartDate.setOnFocusChangeListener(this);
		etOverDate.setOnClickListener(this);
		etOverDate.setOnFocusChangeListener(this);
		btnUpdate.setOnClickListener(this);
		// 初始化今天的日期参数
		calendar = Calendar.getInstance();
		todayYear = calendar.get(Calendar.YEAR);
		todayMonthOfYear = calendar.get(Calendar.MONTH);
		todayDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		todayDateTime = new DateTime(todayYear, todayMonthOfYear, todayDayOfMonth, 0, 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.etStartDate:
			openStartDateDialog();
			break;
		case R.id.etOverDate:
			openOverDateDialog();
			break;
		case R.id.btnUpdate:
			onBtnUpdateClick();
			break;
		}
	}

	public void onBtnUpdateClick() {
		// 输入框数据合法性验证不通过的处理
		if (etStartDate.getText().toString().equals("")) {
			Toast.makeText(ac, R.string.choose_application_start_date, Toast.LENGTH_SHORT).show();
			return;
		}
		if (startDateTime.isBefore(todayDateTime)) {
			String tmp = todayYear + "-" + (todayMonthOfYear + 1) + "-" + todayDayOfMonth;
			Toast.makeText(ac, ac.getResources().getString(R.string.start_date_cant_lt_today) + tmp, Toast.LENGTH_SHORT).show();
			return;
		}
		if (etOverDate.getText().toString().equals("")) {
			Toast.makeText(ac, R.string.choose_application_over_date, Toast.LENGTH_SHORT).show();
			return;
		}
		if (startDateTime.isAfter(overDateTime)) {
			Toast.makeText(ac, R.string.start_date_can_not_gt_over_date, Toast.LENGTH_SHORT).show();
			return;
		}
		if (etLeavePlace.getText().toString().equals("")) {
			Toast.makeText(ac, R.string.leave_place_not_null, Toast.LENGTH_SHORT).show();
			etLeavePlace.requestFocus();
			return;
		}
		if (etLeaveReason.getText().toString().equals("")) {
			Toast.makeText(ac, R.string.leave_reason_not_null, Toast.LENGTH_SHORT).show();
			etLeaveReason.requestFocus();
			return;
		}
		// 传递修改请假信息的参数值
		dlg.updateInput(DateFormatUtils.format(startDateTime.toDate()), DateFormatUtils.format(overDateTime.toDate()),
				etLeavePlace.getText().toString(), etLeaveReason.getText().toString());
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			switch (v.getId()) {
			case R.id.etStartDate:
				openStartDateDialog();
				break;
			case R.id.etOverDate:
				openOverDateDialog();
				break;
			}
		}
	}

	public void openStartDateDialog() {
		new DatePickerDialog(ac, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				etStartDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
				startDateTime = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0);
			}
		}, todayYear, todayMonthOfYear, todayDayOfMonth).show();
	}

	public void openOverDateDialog() {
		new DatePickerDialog(ac, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				etOverDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
				overDateTime = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0);
			}
		}, todayYear, todayMonthOfYear, todayDayOfMonth).show();
	}

	/**
	 * 初始化数据
	 * 
	 * @param params
	 */
	public void printInitData(Bundle params) {
		// 初始化控件
		etStartDate.setText(params.getString("startDate"));
		etOverDate.setText(params.getString("overDate"));
		etLeavePlace.setText(params.getString("leavePlace"));
		etLeaveReason.setText(params.getString("leaveReason"));
		// 初始化startDateTime和overDateTime
		try {
			startDateTime = new DateTime(DateFormatUtils.parse(params.getString("startDate")));
			overDateTime = new DateTime(DateFormatUtils.parse(params.getString("overDate")));
		} catch (ParseException e) {
		}
	}

}