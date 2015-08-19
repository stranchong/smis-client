package com.smis.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.smis.activity.R;

public class TeacherStudentScoreAddView implements OnClickListener, OnItemSelectedListener {
	public interface LoginDelegate {
		public void addInput(Map<String, String> params);

		public void loadClasses(Map<String, String> params);

		public void loadStudents(Map<String, String> params);
	}

	private Activity ac;
	private Spinner spinCname;
	private Spinner spinClname;
	private Spinner spinSname;
	private EditText etScore;
	private Button btnSubmit;
	// 定义接口对象
	private TeacherStudentScoreAddView.LoginDelegate dlg;
	// 课程、班级、学生适配器
	private SimpleAdapter courseAdapter;
	private SimpleAdapter classAdapter;
	private SimpleAdapter studentAdapter;
	// 适配器的数据
	private List<HashMap<String, String>> courses;
	private List<HashMap<String, String>> classes;
	private List<HashMap<String, String>> students;

	public TeacherStudentScoreAddView(Activity ac, TeacherStudentScoreAddView.LoginDelegate delegate) {
		this.ac = ac;
		dlg = delegate;
		spinCname = (Spinner) ac.findViewById(R.id.spinCname);
		spinClname = (Spinner) ac.findViewById(R.id.spinClname);
		spinSname = (Spinner) ac.findViewById(R.id.spinSname);
		etScore = (EditText) ac.findViewById(R.id.etScore);
		btnSubmit = (Button) ac.findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(this);
		spinCname.setOnItemSelectedListener(this);
		spinClname.setOnItemSelectedListener(this);
	}

	@Override
	public void onClick(View v) {
		// 输入框数据合法性验证不通过的处理
		if (spinCname.getSelectedItem() == null) {
			Toast.makeText(ac, R.string.course_not_null, Toast.LENGTH_SHORT).show();
			return;
		}
		if (spinClname.getSelectedItem() == null) {
			Toast.makeText(ac, R.string.class_not_null, Toast.LENGTH_SHORT).show();
			return;
		}
		if (spinSname.getSelectedItem() == null) {
			Toast.makeText(ac, R.string.student_not_null, Toast.LENGTH_SHORT).show();
			return;
		}
		if (etScore.getText().toString().equals("")) {
			Toast.makeText(ac, R.string.score_not_null, Toast.LENGTH_SHORT).show();
			etScore.requestFocus();
			return;
		} else if (Integer.parseInt(etScore.getText().toString()) > 100) {
			Toast.makeText(ac, R.string.score_must_between_0_and_100, Toast.LENGTH_SHORT).show();
			return;
		}

		Log.d("debug", "spinCname cid -->> " + ((Map<String, String>) spinCname.getSelectedItem()).get("cid"));
		Log.d("debug", "spinSname sid -->> " + ((Map<String, String>) spinSname.getSelectedItem()).get("sid"));
		Log.d("debug", "score -->> " + etScore.getText().toString());

		// 设置录入请假信息的参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("cid", (String) ((Map<String, String>) spinCname.getSelectedItem()).get("cid"));
		params.put("sid", (String) ((Map<String, String>) spinSname.getSelectedItem()).get("sid"));
		params.put("score", etScore.getText().toString());
		// 传递参数
		dlg.addInput(params);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Log.d("debug", "onItemSelected -->> ");
		Map<String, String> params = new HashMap<String, String>();
		switch (parent.getId()) {
		case R.id.spinCname:
			Log.d("debug", "cid -->> " + courses.get(position).get("cid"));
			params.put("cid", courses.get(position).get("cid"));
			dlg.loadClasses(params);
			break;
		case R.id.spinClname:
			Log.d("debug", "clid -->> " + classes.get(position).get("clid"));
			params.put("clid", classes.get(position).get("clid"));
			dlg.loadStudents(params);
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	public void setSpinCnameData(List<HashMap<String, String>> data) {
		courses = data;
		// 实例化adapter
		courseAdapter = new SimpleAdapter(ac, courses, R.layout.spinner_item, new String[] { "cname" },
				new int[] { R.id.ctvSpinnerItem });
		// 显示spinCname数据
		spinCname.setAdapter(courseAdapter);
	}

	public void setSpinClnameData(List<HashMap<String, String>> data) {
		classes = data;
		// 实例化adapter
		classAdapter = new SimpleAdapter(ac, classes, R.layout.spinner_item, new String[] { "clname" },
				new int[] { R.id.ctvSpinnerItem });
		// 设置spinClname数据
		spinClname.setAdapter(classAdapter);
	}

	public void setSpinSnameData(List<HashMap<String, String>> data) {
		students = data;
		// 实例化adapter
		studentAdapter = new SimpleAdapter(ac, students, R.layout.spinner_item, new String[] { "sname" },
				new int[] { R.id.ctvSpinnerItem });
		// 设置tnameSpinner数据
		spinSname.setAdapter(studentAdapter);
	}

}