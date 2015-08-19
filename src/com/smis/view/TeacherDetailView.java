package com.smis.view;

import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smis.activity.R;
import com.smis.domain.Course;
import com.smis.domain.Teacher;

public class TeacherDetailView {

	public final static String MANAGER = "1";

	private Activity ac;
	private TextView tvTname;
	private TextView tvIsManager;
	private TextView tvFname;
	private TextView tvLname;
	private TextView tvIsTeacher;
	private LinearLayout llytTeachCourse;
	private LinearLayout llytTeachClass;

	public TeacherDetailView(Activity ac) {
		this.ac = ac;
		tvTname = (TextView) ac.findViewById(R.id.tvTname);
		tvIsManager = (TextView) ac.findViewById(R.id.tvIsManager);
		tvFname = (TextView) ac.findViewById(R.id.tvFname);
		tvLname = (TextView) ac.findViewById(R.id.tvLname);
		tvIsTeacher = (TextView) ac.findViewById(R.id.tvIsTeacher);
		llytTeachCourse = (LinearLayout) ac.findViewById(R.id.llytTeachCourse);
		llytTeachClass = (LinearLayout) ac.findViewById(R.id.llytTeachClass);
	}

	public void printTeacherDetail(Teacher teacher) {
		// 在UI控件上显示个人信息
		tvTname.setText(teacher.getTname());
		tvIsManager.setText(teacher.getIsManager().equals(MANAGER) ? ac.getResources().getString(R.string.yes) : ac
				.getResources().getString(R.string.no));
		tvFname.setText(teacher.getFaculty() != null ? teacher.getFaculty().getFname() : ac.getResources().getString(
				R.string.none));
		tvLname.setText(teacher.getLevel() != null ? teacher.getLevel().getLname() : ac.getResources().getString(R.string.none));
		tvIsTeacher.setText(teacher.getIsTeacher().equals(MANAGER) ? ac.getResources().getString(R.string.yes) : ac
				.getResources().getString(R.string.no));
		// 所教班级和所管理班级要特殊处理
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		List<Course> courses = teacher.getCourses();
		if (courses != null && courses.size() > 0) { // 所教课程门数大于0
			for (Course course : courses) {
				TextView tv = new TextView(ac);
				tv.setLayoutParams(layoutParams);
				tv.setTextSize(20);
				tv.setText(course.getCname());
				llytTeachCourse.addView(tv);
			}
		} else {
			TextView tv = new TextView(ac);
			tv.setLayoutParams(layoutParams);
			tv.setTextSize(20);
			tv.setText(ac.getResources().getString(R.string.none));
			llytTeachCourse.addView(tv);
		}
		List<com.smis.domain.Class> classes = teacher.getClasses();
		if (classes != null && classes.size() > 0) { // 所教班级个数大于0
			for (com.smis.domain.Class clazz : classes) {
				TextView tv = new TextView(ac);
				tv.setLayoutParams(layoutParams);
				tv.setTextSize(20);
				tv.setText(clazz.getClname());
				llytTeachClass.addView(tv);
			}
		} else {
			TextView tv = new TextView(ac);
			tv.setLayoutParams(layoutParams);
			tv.setTextSize(20);
			tv.setText(ac.getResources().getString(R.string.none));
			llytTeachClass.addView(tv);
		}
	}

}