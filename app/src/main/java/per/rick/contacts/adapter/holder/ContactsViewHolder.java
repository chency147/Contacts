package per.rick.contacts.adapter.holder;

import android.view.View;
import android.widget.TextView;

/**
 * 联系人列表Item控件Holder类
 * Created by Rick on 2016/5/10.
 */
public class ContactsViewHolder {
	private TextView textViewName = null;// 姓名标签
	private TextView textViewSortKey = null;// 字母索引标签
	private View divider = null;// 分割线

	/* set and get methods BEGIN */
	public TextView getTextViewName() {
		return textViewName;
	}

	public void setTextViewName(TextView textViewName) {
		this.textViewName = textViewName;
	}

	public TextView getTextViewSortKey() {
		return textViewSortKey;
	}

	public void setTextViewSortKey(TextView textViewSortKey) {
		this.textViewSortKey = textViewSortKey;
	}

	public View getDivider() {
		return divider;
	}

	public void setDivider(View divider) {
		this.divider = divider;
	}
	/* set and get methods END */
}
