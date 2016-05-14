package per.rick.contacts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import per.rick.contacts.R;
import per.rick.contacts.adapter.holder.ContactsViewHolder;
import per.rick.contacts.entity.Person;

/**
 * 联系人列表适配器
 * Created by Rick on 2016/5/10.
 */
public class ContactsAdapter extends BaseAdapter {

	private List<Person> personList = null;// 联系人列表
	private Context context = null;// 山下文对象
	private ListView.LayoutParams layoutParams = null;// 普通item布局参数对象
	private ListView.LayoutParams layoutParamsFirst = null;
	// 索引首位item布局参数对象
	private static final int ITEM_LAYOUT_ID = R.layout.item_contacts;
	// item布局资源id

	/**
	 * 构造方法
	 *
	 * @param context    上下文对象
	 * @param personList 联系人列表
	 */
	public ContactsAdapter(Context context, List<Person> personList) {
		/* 初始化各种成员变量 */
		this.context = context;
		this.personList = personList;
		this.layoutParams = new ListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				context.getResources().getDimensionPixelSize(R.dimen
						.contacts_item_height)
		);
		this.layoutParamsFirst = new ListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				context.getResources().getDimensionPixelSize(R.dimen
						.contacts_first_item_height)
		);
	}

	/**
	 * 更新联系人列表并刷新控件
	 *
	 * @param personList 新的联系人列表
	 */
	public void setPersonList(List<Person> personList) {
		this.personList = personList;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (personList == null) {
			return 0;
		}
		return personList.size();
	}

	@Override
	public Object getItem(int position) {
		return personList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return personList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContactsViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ContactsViewHolder();
			/* 初始化 */
			convertView = LayoutInflater.from(context).inflate
					(ITEM_LAYOUT_ID, null);
			viewHolder.setTextViewName((TextView) convertView.findViewById(
					R.id.tv_name));
			viewHolder.setTextViewSortKey((TextView) convertView.findViewById(
					R.id.tv_sortKey));
			viewHolder.setDivider(convertView.findViewById(R.id.divider));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ContactsViewHolder) convertView.getTag();
		}
		int section = getSectionForPosition(position);
		viewHolder.getTextViewName().setText(personList.get(position)
				.getName());
		if (position == getPositionForSection(section)) {// 判断是否为索引首位
			viewHolder.getTextViewSortKey().setVisibility(View.VISIBLE);
			// 设置字母索引标签为显示状态
			viewHolder.getDivider().setVisibility(position == 0 ?
					View.GONE : View.VISIBLE);
			// 根据是否为列表第一位设置分割线的显示
			viewHolder.getTextViewSortKey().setText(personList.get(position)
					.getSortKey());
			convertView.setLayoutParams(layoutParamsFirst);
		} else {
			viewHolder.getTextViewSortKey().setVisibility(View.GONE);
			// 隐藏字符索引标签
			viewHolder.getDivider().setVisibility(View.GONE);
			// 隐藏分割线
			convertView.setLayoutParams(layoutParams);
		}
		return convertView;
	}

	/**
	 * 获取第一次出现该字符索引的位置
	 *
	 * @param section 字符对应的ascii码
	 * @return 目标位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortKey = personList.get(i).getSortKey();
			char firstChar = sortKey.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 通过位置获取字符索引
	 *
	 * @param position 位置
	 * @return 字符的ascii码
	 */
	public int getSectionForPosition(int position) {
		return personList.get(position).getSortKey().charAt(0);
	}
}
