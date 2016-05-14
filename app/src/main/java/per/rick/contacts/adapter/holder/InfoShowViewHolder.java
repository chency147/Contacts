package per.rick.contacts.adapter.holder;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * 信息展示列表Item控件Holder类
 * Created by Rick on 2016/5/13.
 */
public class InfoShowViewHolder {
	private TextView textViewTag = null;// 标签
	private TextView textViewInfo = null;// 内容
	private ImageView actionView = null;// 动作图示

	/* set and get methods BEGIN */
	public TextView getTextViewTag() {
		return textViewTag;
	}

	public void setTextViewTag(TextView textViewTag) {
		this.textViewTag = textViewTag;
	}

	public TextView getTextViewInfo() {
		return textViewInfo;
	}

	public void setTextViewInfo(TextView textViewInfo) {
		this.textViewInfo = textViewInfo;
	}

	public ImageView getActionView() {
		return actionView;
	}

	public void setActionView(ImageView actionView) {
		this.actionView = actionView;
	}
	/* set and get methods END */
}
