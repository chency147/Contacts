package per.rick.contacts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import per.rick.contacts.R;
import per.rick.contacts.adapter.holder.InfoShowViewHolder;
import per.rick.contacts.entity.Email;
import per.rick.contacts.entity.Phone;

/**
 * 信息展示列表适配器
 * Created by Rick on 2016/5/13.
 */
public class InfoShowAdapter extends BaseAdapter {
	public static final int PHONE = 0;// 手机标识
	public static final int EMAIL = 1;// 邮箱标识
	private int infoType = PHONE;// 字段类型，默认为手机
	private static final int ITEM_LAYOUT_ID = R.layout.item_info_show;
	// item布局资源id
	private String emailTagInitial = null;// 邮箱标签前缀
	List<Phone> phoneList = null;// 手机列表
	List<Email> emailList = null;// 邮箱列表
	private ListView.LayoutParams layoutParams = null;
	// 布局参数对象
	private Context context;// 上下文对象

	/**
	 * 构造方法
	 *
	 * @param context  上下文对象
	 * @param infoType 信息类型
	 * @param list     手机或邮箱列表
	 */
	public InfoShowAdapter(Context context, int infoType, List list) {
		this.context = context;
		this.infoType = infoType;
		if (infoType == PHONE) {
			this.phoneList = list;
		}
		if (infoType == EMAIL) {
			this.emailList = list;
		}
		this.layoutParams = new ListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				context.getResources().getDimensionPixelSize(R.dimen
						.infoItemHeight));// 初始化布局参数
		emailTagInitial = context.getResources().getString(R.string
				.str_email) + "/";// 初始化邮箱标签前缀
	}

	/**
	 * 设置手机列表
	 *
	 * @param phoneList 手机列表
	 */
	public void setPhoneList(List<Phone> phoneList) {
		if (infoType != PHONE) {
			return;
		}
		this.phoneList = phoneList;
	}

	/**
	 * 设置邮箱列表
	 *
	 * @param emailList 邮箱列表
	 */
	public void setEmailList(List<Email> emailList) {
		if (infoType != EMAIL) {
			return;
		}
		this.emailList = emailList;
	}

	@Override
	public int getCount() {
		if (infoType == PHONE) {
			return phoneList == null ? 0 : phoneList.size();
		}
		if (infoType == EMAIL) {
			return emailList == null ? 0 : emailList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (infoType == PHONE) {
			return phoneList == null ? null : phoneList.get(position);
		}
		if (infoType == EMAIL) {
			return emailList == null ? null : emailList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		InfoShowViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new InfoShowViewHolder();
			convertView = LayoutInflater.from(context).inflate
					(ITEM_LAYOUT_ID, null);
			convertView.setLayoutParams(layoutParams);
			viewHolder.setTextViewTag((TextView) convertView.findViewById(R.id
					.tv_tag));
			viewHolder.setTextViewInfo((TextView) convertView.findViewById(R
					.id.tv_info));
			viewHolder.setActionView((ImageView) convertView.findViewById
					(R.id.iv_action));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (InfoShowViewHolder) convertView.getTag();
		}
		if (infoType == PHONE) {// 根据信息类型来设置控件的不同显示
			viewHolder.getTextViewTag().setText(
					phoneList.get(position).getTag());
			viewHolder.getTextViewInfo().setText(
					phoneList.get(position).getNumber());
			viewHolder.getActionView().setImageResource(
					R.drawable.ic_phone_green_500_48dp);
		} else {
			viewHolder.getTextViewTag().setText(
					emailTagInitial + emailList.get(position).getTag());
			viewHolder.getTextViewInfo().setText(
					emailList.get(position).getAddress());
			viewHolder.getActionView().setImageResource(
					R.drawable.ic_email_blue_grey_400_48dp);
		}
		return convertView;
	}
}
