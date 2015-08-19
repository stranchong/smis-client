package com.smis.adapter;

import java.util.List;
import java.util.Map;

import com.smis.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class StudentApplicationsAdapter extends SimpleAdapter {

	public final static int STATE_AGREE = 0;
	public final static int STATE_PROPOSED_AGREE = 1;
	public final static int STATE_DISAGREE = 2;
	public final static int STATE_DO_NOT_APPROVE = 3;

	// 上下文
	private Context context = null;
	// 需要绑定的数据
	private List<? extends Map<String, ?>> data;
	// 解析的xml资源文件id
	private int resource;
	// map集合关键字数组
	private String[] from;
	// 需要绑定的控件资源id
	private int[] to;
	// 解析器
	private LayoutInflater inflater;

	public StudentApplicationsAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.context = context;
		this.data = data;
		this.resource = resource;
		this.from = from;
		this.to = to;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 第一次装载时convertView为null，调用inflate新建convertView
		if (convertView == null) {
			convertView = inflater.inflate(resource, parent, false);
		}
		// 设置各个控件的展示内容
		TextView tv = null;
		for (int i = 0; i < to.length; i++) {
			tv = (TextView) convertView.findViewById(to[i]);
			if (from[i].equals("confirmMark")) { // tvConfirmMark的显示要单独处理
				if (data.get(position).get("confirmMark") != null) {
					int confirmMark = Integer.parseInt((String) data.get(position).get("confirmMark"));
					if (confirmMark == STATE_AGREE) {
						tv.setText(context.getResources().getString(R.string.agree));
					} else if (confirmMark == STATE_PROPOSED_AGREE) {
						tv.setText(context.getResources().getString(R.string.proposed_agree));
					} else if (confirmMark == STATE_DISAGREE) {
						tv.setText(context.getResources().getString(R.string.disagree));
					} else if (confirmMark == STATE_DO_NOT_APPROVE) {
						tv.setText(context.getResources().getString(R.string.do_not_approve));
					}
				}
				continue;
			}
			tv.setText((String)data.get(position).get(from[i]));
		}
		// 返回显示内容的convertView
		return convertView;
	}
}