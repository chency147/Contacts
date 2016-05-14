package per.rick.contacts.adapter.holder;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

/**
 * 信息编辑列表Item控件Holder类
 * Created by Rick on 2016/5/12.
 */
public class InfoEditViewHolder {
	private int position;// 位置
	private EditText editTextInfo = null;// 内容编辑器
	private Spinner SpinnerTag = null;// 标签选择器
	private ImageButton removeButton = null;// 删除按钮
	private String[] tags = null;// 标签数组
	private int selected = 0;// 是否被选中

	/* set and get methods BEGIN */

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public EditText getEditTextInfo() {
		return editTextInfo;
	}

	public void setEditTextInfo(EditText editTextInfo) {
		this.editTextInfo = editTextInfo;
	}

	public Spinner getSpinnerTag() {
		return SpinnerTag;
	}

	public void setSpinnerTag(Spinner spinnerTag) {
		SpinnerTag = spinnerTag;
	}

	public ImageButton getRemoveButton() {
		return removeButton;
	}

	public void setRemoveButton(ImageButton removeButton) {
		this.removeButton = removeButton;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}
/* set and get methods END */
}
