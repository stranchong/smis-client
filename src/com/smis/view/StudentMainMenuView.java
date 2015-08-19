package com.smis.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.smis.activity.R;

public class StudentMainMenuView implements OnItemClickListener {
	public interface LoginDelegate {
		public void switchActivity(int position);
	}

	private GridView gvMenus;
	private StudentMainMenuView.LoginDelegate dlg;

	public StudentMainMenuView(Activity ac, StudentMainMenuView.LoginDelegate delegate) {
		dlg = delegate;
		gvMenus = (GridView) ac.findViewById(R.id.gvMenus);
		// 动画效果渲染界面
		AnimationSet set = new AnimationSet(false);
		Animation animation = new AlphaAnimation(0, 1);
		animation.setDuration(500);
		set.addAnimation(animation);

		animation = new TranslateAnimation(1, 13, 10, 50);
		animation.setDuration(300);
		set.addAnimation(animation);

		animation = new RotateAnimation(30, 10);
		animation.setDuration(300);
		set.addAnimation(animation);

		animation = new ScaleAnimation(5, 0, 2, 0);
		animation.setDuration(300);
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(set, 1);
		gvMenus.setLayoutAnimation(controller);
		gvMenus.setAdapter(new ImageAdapter(ac));
		gvMenus.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		dlg.switchActivity(position);
	}

	private class ImageAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		// 上下文
		private Context context;
		// 图片资源数组
		private Integer[] menuIconIds = { R.drawable.ic_menu_item, R.drawable.ic_menu_item, R.drawable.ic_menu_item,
				R.drawable.ic_menu_item, R.drawable.ic_menu_item };
		private String[] menuName = { "查看个人信息", "查看课程成绩", "查看请假信息", "请假申请", "修改密码" };

		public ImageAdapter(Context c) {
			context = c;
			inflater = LayoutInflater.from(context);
		}

		public int getCount() { // 组件个数
			return menuIconIds.length;
		}

		public Object getItem(int position) { // 当前组件
			return menuIconIds[position];
		}

		public long getItemId(int position) { // 当前组件id
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) { // 获得当前视图
			View view = inflater.inflate(R.layout.griditem_common_main_menu_, null);
			ImageView iv = (ImageView) view.findViewById(R.id.ivMenuIcon);
			TextView tv = (TextView) view.findViewById(R.id.tvMenuName);
			tv.setText(menuName[position]);
			iv.setImageResource(menuIconIds[position]);
			return view;
		}

	}

}