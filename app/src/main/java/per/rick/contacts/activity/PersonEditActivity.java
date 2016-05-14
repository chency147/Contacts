package per.rick.contacts.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import per.rick.contacts.R;
import per.rick.contacts.adapter.InfoEditAdapter;
import per.rick.contacts.dao.EmailDAO;
import per.rick.contacts.dao.GroupDAO;
import per.rick.contacts.dao.PersonDAO;
import per.rick.contacts.dao.PhoneDAO;
import per.rick.contacts.entity.Email;
import per.rick.contacts.entity.Person;
import per.rick.contacts.entity.Phone;
import per.rick.contacts.persist.Persistor;

/**
 * 联系人信息编辑Activity
 * Created by Rick on 2016/5/13.
 */
public class PersonEditActivity extends PersonInsertActivity {
	private Person person = null;// 联系人对象
	private List<Phone> phoneList = null;// 手机列表
	private List<Email> emailList = null;// 邮箱列表
	private EditText editTextName = null;// 联系人名字编辑栏
	private ListView listViewPhone = null;// 手机ListView
	private ListView listViewEmail = null;// 邮箱ListView
	private InfoEditAdapter infoEditPhoneAdapter = null;// 手机ListView适配器
	private InfoEditAdapter infoEditEmailAdapter = null;// 邮箱ListView适配器
	private Persistor persistor = null;// 持久器对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_edit);
		person = PersonInfoActivity.person;
		phoneList = PersonInfoActivity.phoneList;
		emailList = PersonInfoActivity.emailList;
		persistor = Persistor.getInstance(this);
		initView();
		initAdapter();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		editTextName = (EditText) findViewById(R.id.et_name);
		listViewPhone = (ListView) findViewById(R.id.lv_phone);
		listViewEmail = (ListView) findViewById(R.id.lv_email);
	}

	/**
	 * 初始化监听器
	 */
	private void initAdapter() {
		infoEditPhoneAdapter = new InfoEditAdapter(this, InfoEditAdapter
				.PHONE, phoneList, true);
		infoEditEmailAdapter = new InfoEditAdapter(this, InfoEditAdapter
				.EMAIL, emailList, true);
		listViewPhone.setAdapter(infoEditPhoneAdapter);
		listViewEmail.setAdapter(infoEditEmailAdapter);
	}

	protected void onResume() {
		super.onResume();
		editTextName.setText(person.getName());
		this.refreshListViewPhoneHeight();// 更新ListView的高度
		this.refreshListViewEmailHeight();
	}

	/**
	 * 添加手机
	 *
	 * @param view
	 */
	public void addPhone(View view) {
		Phone phone = new Phone();
		phone.setNew(true);
		phoneList.add(phone);
		refreshListViewPhoneHeight();
		infoEditPhoneAdapter.refresh(true);
	}

	/**
	 * 更新手机ListView的高度
	 */
	public void refreshListViewPhoneHeight() {
		listViewPhone.getLayoutParams().height =
				getResources().getDimensionPixelSize(R.dimen
						.infoEditHeight) * +phoneList.size();
	}

	/**
	 * 添加邮箱
	 *
	 * @param view
	 */
	public void addEmail(View view) {
		Email email = new Email();
		email.setNew(true);
		emailList.add(email);
		refreshListViewEmailHeight();
		infoEditEmailAdapter.refresh(true);
	}

	/**
	 * 更新邮箱ListView的高度
	 */
	public void refreshListViewEmailHeight() {
		listViewEmail.getLayoutParams().height =
				getResources().getDimensionPixelSize(R.dimen
						.infoEditHeight) * emailList.size();
	}

	/**
	 * 保存联系人信息
	 *
	 * @param view
	 */
	public void savePerson(View view) {
		String newName = editTextName.getText().toString();// 提取联系人名字
		if (newName == null ||
				newName.replace(" ", "").equals("")) {// 检查名字的有效性
			Toast.makeText(getApplicationContext(), R.string
							.str_contacts_save_fail,
					Toast.LENGTH_SHORT).show();// 提示无效
			finish();// 推出activity
			return;
		}
		PersonDAO personDAO = persistor.getPersonDAO();
		GroupDAO groupDAO = persistor.getGroupDAO();
		PhoneDAO phoneDAO = persistor.getPhoneDAO();
		EmailDAO emailDAO = persistor.getEmailDAO();
		List<Phone> phoneListToDelete = infoEditPhoneAdapter
				.getPhoneListToDelete();
		List<Email> emailListToDelete = infoEditEmailAdapter
				.getEmailListToDelete();
		// 删除待删除的手机和邮箱
		for (Phone phone : phoneListToDelete) {
			phoneDAO.delete(phone);
		}
		for (Email email : emailListToDelete) {
			emailDAO.delete(email);
		}
		// 检查输入有效性并执行操作
		for (Phone phone : phoneList) {
			if (phone.isEdited()) {// 是否已经被编辑过
				if (phone.getNumber() != null &&
						!phone.getNumber().replace(" ", "").equals("")) {
					// 检查输入有效性
					phoneDAO.update(phone);// 有效更新
				} else {
					phoneDAO.delete(phone);// 无效删除
				}
			}
			if (phone.isNew()) {// 是否是新增的
				if (phone.getNumber() != null &&
						!phone.getNumber().replace(" ", "").equals("")) {
					phone.setPersonID(person.getId());
					phoneDAO.insert(phone);// 有效添加
				}
			}
		}
		for (Email email : emailList) {
			if (email.isEdited()) {
				if (email.getAddress() != null &&
						!email.getAddress().replace(" ", "").equals("")) {
					emailDAO.update(email);
				} else {
					emailDAO.delete(email);
				}
			}
			if (email.isNew()) {
				if (email.getAddress() != null &&
						!email.getAddress().replace(" ", "").equals("")) {
					email.setPersonID(person.getId());
					emailDAO.insert(email);
				}
			}
		}
		person.setName(newName);// 设置联系人新的姓名
		personDAO.update(person);
		Toast.makeText(getApplicationContext(), R.string.str_contacts_saved,
				Toast.LENGTH_SHORT).show();
		finish();
	}

	/**
	 * 取消保存
	 *
	 * @param view
	 */
	public void cancel(View view) {
		Toast.makeText(getApplicationContext(), R.string
						.str_contacts_save_cancel,
				Toast.LENGTH_SHORT).show();
		finish();
	}
}
