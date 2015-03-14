package com.zhitu.dzc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import com.zhitu.xxf.R;
import com.zhitu.xxf.UserMsg;

public class ListViewAdapter extends BaseAdapter {
	private ArrayList<UserMsg> listItems;
	private LayoutInflater listContainer; // 视图容器

	public final class ListViewItem { // 自定义控件集合
		public ImageView image;
		public TextView date;
		public TextView name;
		public TextView content;
		public ImageView commentImageView;
		public ImageView retweetImageView;
		public ImageView ilikeitImageView;
		

	}

	public ListViewAdapter(Context context, ArrayList<UserMsg> listItems) {
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
	}

	public int getCount() {
		return listItems.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		Log.e("method", "getView");
		// ListViewItem listItemView = null;
		if (convertView == null) {
			final ListViewItem listItemView = new ListViewItem();

			convertView = listContainer.inflate(R.layout.msg_card, null);
			listItemView.ilikeitImageView = (ImageView) convertView
					.findViewById(R.id.msg_ilike_image);
			listItemView.commentImageView = (ImageView) convertView
					.findViewById(R.id.msg_comment_image);
			listItemView.retweetImageView = (ImageView) convertView
					.findViewById(R.id.msg_retweet_image);
			listItemView.content = (TextView) convertView
					.findViewById(R.id.image_index_text);
			listItemView.date = (TextView) convertView
					.findViewById(R.id.time_text);
			listItemView.name = (TextView) convertView
					.findViewById(R.id.name_text);
			
			listItemView.content.setText((String) listItems.get(position)
					.getTextinfo());
			listItemView.date.setText((String) listItems.get(position)
					.getSharetime());
			listItemView.name.setText((String) listItems.get(position)
					.getUsername());
			listItemView.ilikeitImageView
					.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							listItemView.ilikeitImageView
									.setImageResource(R.drawable.page_icon_like);
							/*
							 * Toast toast = Toast.makeText(context, "赞 +1！",
							 * Toast.LENGTH_LONG); View toastView =
							 * toast.getView();
							 * toastView.setBackgroundColor(context
							 * .getResources().getColor(R.color.long_button));
							 * toast.setView(toastView); toast.show();
							 */

						}
					});
			listItemView.commentImageView
					.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub

						}
					});

			convertView.setTag(listItemView);
			return convertView;
		} else {
			final ListViewItem listItemView = (ListViewItem) convertView
					.getTag();
			listItemView.ilikeitImageView
					.setOnClickListener(new View.OnClickListener() {
						@SuppressLint("ResourceAsColor")
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							listItemView.ilikeitImageView
									.setImageResource(R.drawable.skin_feed_icon_praise);
							/*
							 * Toast toast = Toast.makeText(context, "赞 +1！",
							 * Toast.LENGTH_LONG); View toastView =
							 * toast.getView();
							 * toastView.setBackgroundColor(context
							 * .getResources().getColor(R.color.long_button));
							 * toast.setView(toastView); toast.show();
							 */

						}
					});
			listItemView.content.setText((String) listItems.get(position)
					.getTextinfo());
			listItemView.date.setText((String) listItems.get(position)
					.getSharetime());
			listItemView.name.setText((String) listItems.get(position)
					.getUsername());
			convertView.setTag(listItemView);
			return convertView;
		}

	}
}
