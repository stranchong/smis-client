package com.smis.view;

import android.app.Activity;
import android.widget.TextView;

import com.smis.activity.R;
import com.smis.domain.Student;

public class StudentDetailView {

	private TextView tvSid;
	private TextView tvSname;
	private TextView tvClname;

	public StudentDetailView(Activity ac) {
		tvSid = (TextView) ac.findViewById(R.id.tvSid);
		tvSname = (TextView) ac.findViewById(R.id.tvSname);
		tvClname = (TextView) ac.findViewById(R.id.tvClname);
	}

	public void printStudentDetail(Student student) {
		tvSid.setText(student.getSid());
		tvSname.setText(student.getSname());
		tvClname.setText(student.getClazz().getClname());
	}

}