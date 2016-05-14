package per.rick.contacts.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import per.rick.contacts.R;
import per.rick.contacts.activity.PersonEditActivity;
import per.rick.contacts.activity.PersonInsertActivity;
import per.rick.contacts.adapter.holder.InfoEditViewHolder;
import per.rick.contacts.entity.Email;
import per.rick.contacts.entity.Phone;

/**
 * 信息编辑列表适配器
 * Created by Rick on 2016/5/12.
 */
public class InfoEditAdapter extends BaseAdapter {
	public static final int PHONE = 0;// 手机标识
	public static final int EMAIL = 1;// 邮箱标识
	public static String[] phoneTags = null;// 手机标签数组
	public static String[] emailTags = null; // 邮箱标签数组
	private int infoType = PHONE;// 字段类型，默认为手机
	private static final int ITEM_LAYOUT_ID = R.layout.item_info_insert;
	// item布局资源id
	private List<Phone> phoneList = null;// 手机列表
	private List<Email> emailList = null;// 邮箱列表
	private List<Phone> phoneListToDelete = null;// 待删除手机列表
	private List<Email> emailListToDelete = null;// 待删除邮箱列表
	private ListView.LayoutParams layoutParams = null;// 布局参数对象
	private Context context;// 上下文对象
	private ImageButton.OnClickListener removeListener = null;
	// 删除按钮监听器对象
	private Spinner.OnItemSelectedListener onItemSelectedListener = null;
	// 标签选择器item被选中监听器对象
	private int nowFocused = Integer.MIN_VALUE;// 当前正在被编辑的编辑栏的位置
	private boolean isUpdateMode = false;// 是否是信息更新模式

	/**
	 * 构造方法
	 *
	 * @param context  上下文对象
	 * @param infoType 信息类型
	 * @param list     手机或者邮箱列表
	 */
	public InfoEditAdapter(final Context context, final int infoType, List
			list) {
		this(context, infoType, list, false);
	}

	/**
	 * 构造方法
	 *
	 * @param context      上下文对象
	 * @param infoType     信息类型
	 * @param list         手机或邮箱列表
	 * @param isUpdateMode 是否是更新模式
	 */
	public InfoEditAdapter(final Context context, final int infoType, List
			list, final boolean isUpdateMode) {
		this.context = context;
		this.infoType = infoType;
		this.isUpdateMode = isUpdateMode;
		if (infoType == PHONE) {
			this.phoneList = list;
		}
		if (infoType == EMAIL) {
			this.emailList = list;
		}
		this.layoutParams = new ListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				context.getResources().getDimensionPixelSize(R.dimen
						.infoEditHeight)
		);// 初始化布局参数
		// 初始化删除按钮监听器
		removeListener = new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				int index = (int) v.getTag();
				if (infoType == PHONE) {// 如果信息类型是手机
					if (isUpdateMode) {// 如果是更新模式
						if (!phoneList.get(index).isNew()) {
							// 如果不是新建的手机信息被删除，不写入待删除列表
							phoneListToDelete.add(phoneList.get(index));
						}
						phoneList.remove(index);
						((PersonEditActivity) context)
								.refreshListViewPhoneHeight();
					} else {
						phoneList.remove(index);
						((PersonInsertActivity) context)
								.refreshListViewPhoneHeight();
					}
				} else {
					if (isUpdateMode) {// 邮箱和上面同理
						if (!emailList.get(index).isNew()) {
							emailListToDelete.add(emailList.get(index));
						}
						phoneList.remove(index);
						((PersonEditActivity) context).
								refreshListViewEmailHeight();
					} else {
						emailList.remove(index);
						((PersonInsertActivity) context).
								refreshListViewEmailHeight();
					}
				}
				InfoEditAdapter.this.nowFocused = getCount() - 1;
				// 将焦点定位到刚加入的新信息编辑栏上
				notifyDataSetChanged();
			}
		};
		// 初始化标签选择器item被选中监听器
		onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(
					AdapterView<?> parent, View view, int position, long id) {
				int index = (int) parent.getTag();
				if (infoType == PHONE) {
					phoneList.get(index).setEdited(true);// 标记信息已经被修改
					phoneList.get(index).selectTag = position;
					//记录下标签在标签数组中的索引
					phoneList.get(index).setTag(phoneTags[position]);
					// 设置新的标签
				} else {
					emailList.get(index).setEdited(true);
					emailList.get(index).selectTag = position;
					emailList.get(index).setTag(emailTags[position]);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		};
		phoneTags = context.getResources().getStringArray(R.array.phoneTags);
		emailTags = context.getResources().getStringArray(R.array.emailTags);
		if (isUpdateMode) {// 更新模式需要初始化的成员变量
			phoneListToDelete = new ArrayList<Phone>();
			emailListToDelete = new ArrayList<Email>();
			if (infoType == PHONE) {
				for (Phone phone : phoneList) {
					phone.selectTag = getTagIndex(phone.getTag(), phoneTags);
				}
			} else {
				for (Email email : emailList) {
					email.selectTag = getTagIndex(email.getTag(), emailTags);
				}
			}
		}
	}

	/* set and get methods BEGIN */
	public int getInfoType() {
		return infoType;
	}

	public List<Phone> getPhoneListToDelete() {
		return phoneListToDelete;
	}

	public List<Email> getEmailListToDelete() {
		return emailListToDelete;
	}
	/* set and get methods END */

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
	public View getView(final int position, View convertView, ViewGroup parent) {
		InfoEditViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new InfoEditViewHolder();
			convertView = LayoutInflater.from(context).inflate
					(ITEM_LAYOUT_ID, null);
			convertView.setLayoutParams(layoutParams);
			viewHolder.setPosition(position);
			viewHolder.setEditTextInfo((EditText) convertView.findViewById(R
					.id.et_info));
			viewHolder.setSpinnerTag((Spinner) convertView.findViewById(R.id
					.sp_tag));
			viewHolder.setRemoveButton((ImageButton) convertView.findViewById
					(R.id.bt_remove));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (InfoEditViewHolder) convertView.getTag();
		}
		viewHolder.setPosition(position);
		if (infoType == PHONE) {
			viewHolder.getEditTextInfo().setInputType(InputType
					.TYPE_CLASS_PHONE);// 设置输入类型为手机号码
			viewHolder.getEditTextInfo().setText(phoneList.get(position)
					.getNumber());
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
					(context, android.R.layout.simple_spinner_item, phoneTags);
			arrayAdapter.setDropDownViewResource(R.layout.item_spinner);
			viewHolder.getSpinnerTag().setAdapter(arrayAdapter);
			viewHolder.getSpinnerTag().setSelection(phoneList.get(position)
					.selectTag);
			phoneList.get(position).setTag(phoneTags[phoneList.get(position)
					.selectTag]);
		} else {
			viewHolder.getEditTextInfo().setInputType(InputType
					.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			viewHolder.getEditTextInfo().setText(emailList.get(position)
					.getAddress());
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
					(context, android.R.layout.simple_spinner_item, emailTags);
			arrayAdapter.setDropDownViewResource(R.layout.item_spinner);
			viewHolder.getSpinnerTag().setAdapter(arrayAdapter);
			viewHolder.getSpinnerTag().setSelection(emailList.get(position)
					.selectTag);
			emailList.get(position).setTag(emailTags[emailList.get(position)
					.selectTag]);
		}
		viewHolder.getSpinnerTag().setTag(position);
		viewHolder.getSpinnerTag().setOnItemSelectedListener(
				onItemSelectedListener);
		TextChangeListener textChangeListener = new TextChangeListener
				(viewHolder);
		viewHolder.getEditTextInfo().setHint(this.getInfoHintRID());
		viewHolder.getEditTextInfo().setTag(position);
		viewHolder.getRemoveButton().setTag(position);
		viewHolder.getRemoveButton().setOnClickListener(removeListener);
		viewHolder.getRemoveButton().setVisibility(
				viewHolder.getEditTextInfo().hasFocus() ?
						View.VISIBLE : View.INVISIBLE);
		// 只有正在编辑的信息才会显示删除按钮
		viewHolder.getEditTextInfo().setOnFocusChangeListener(
				textChangeListener);
		viewHolder.getEditTextInfo().addTextChangedListener(
				textChangeListener);
		if (position == nowFocused) {
			viewHolder.getEditTextInfo().requestFocus();
		}
		return convertView;
	}

	/**
	 * 获取编辑器提示
	 *
	 * @return
	 */
	private int getInfoHintRID() {
		if (infoType == PHONE) {
			return R.string.str_phone_number;
		} else {
			return R.string.str_email;
		}
	}

	/**
	 * 编辑栏文本变化监听器
	 */
	class TextChangeListener implements TextWatcher, View.OnFocusChangeListener {
		private EditText editText = null;// 编辑栏对象
		private InfoEditViewHolder viewHolder = null;// 控件holder对象

		/**
		 * 构造方法
		 *
		 * @param viewHolder 控件holder对象
		 */
		public TextChangeListener(InfoEditViewHolder viewHolder) {
			this.viewHolder = viewHolder;
			this.editText = viewHolder.getEditTextInfo();
		}

		@Override
		public void beforeTextChanged(
				CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(
				CharSequence s, int start, int before, int count) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (editText.isFocused()) {// 只有正在编辑的编辑栏才会触发该监听器
				if (infoType == PHONE) {
					phoneList.get((int) editText
							.getTag()).setNumber(editText.getText().toString())
							.setEdited(true);
				} else {
					emailList.get((int) editText
							.getTag()).setAddress(editText.getText().toString())
							.setEdited(true);
				}
			}
		}

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			viewHolder.getRemoveButton().setVisibility(hasFocus ? View
					.VISIBLE : View.INVISIBLE);// 更新显示状态
		}
	}

	/**
	 * 刷新控件
	 *
	 * @param isAddAction 是否是添加操作
	 */
	public void refresh(boolean isAddAction) {
		if (isAddAction) {
			nowFocused = getCount() - 1;
			// 如果是添加操作，把焦点置于新增的信息编辑栏之上
		}
		this.notifyDataSetChanged();
	}

	/**
	 * 获取标签在标签数组中的索引
	 *
	 * @param tag  标签
	 * @param tags 标签数组
	 * @return 索引
	 */
	private int getTagIndex(String tag, String[] tags) {
		if (tag == null) {
			return 0;
		}
		int i;
		for (i = 0; i < tags.length; i++) {// 遍历标签数组
			if (tag.equals(tags[i])) {
				return i;
			}
		}
		return 0;// 不存则则默认返回第一个标签的索引
	}
}
