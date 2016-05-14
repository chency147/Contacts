package per.rick.contacts.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
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
 * 联系人添加Activity
 */
public class PersonInsertActivity extends AppCompatActivity {
	private List<Phone> phoneList = null;// 手机列表
	private List<Email> emailList = null;// 邮箱列表
	private EditText editTextName = null;// 名字编辑栏
	private ListView listViewPhone = null;// 手机ListView
	private ListView listViewEmail = null;// 邮箱ListView
	private InfoEditAdapter infoEditPhoneAdapter = null;// 手机ListView适配器
	private InfoEditAdapter infoEditEmailAdapter = null;// 邮箱ListView适配器
	private Persistor persistor = null;// 持久器对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_edit);
		phoneList = new ArrayList<Phone>();
		emailList = new ArrayList<Email>();
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
	 * 初始化适配器
	 */
	private void initAdapter() {
		infoEditPhoneAdapter = new InfoEditAdapter(this, InfoEditAdapter
				.PHONE, phoneList);
		infoEditEmailAdapter = new InfoEditAdapter(this, InfoEditAdapter
				.EMAIL, emailList);
		listViewPhone.setAdapter(infoEditPhoneAdapter);
		listViewEmail.setAdapter(infoEditEmailAdapter);
	}

	/**
	 * 添加手机
	 *
	 * @param view
	 */
	public void addPhone(View view) {
		phoneList.add(new Phone());
		refreshListViewPhoneHeight();
		infoEditPhoneAdapter.refresh(true);
	}

	/**
	 * 刷新手机ListView的高度
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
		emailList.add(new Email());
		refreshListViewEmailHeight();
		infoEditEmailAdapter.refresh(true);
	}

	/**
	 * 刷新邮箱ListView的高速度
	 */
	public void refreshListViewEmailHeight() {
		listViewEmail.getLayoutParams().height =
				getResources().getDimensionPixelSize(R.dimen
						.infoEditHeight) * +emailList.size();
	}

	/**
	 * 保存联系人
	 *
	 * @param view
	 */
	public void savePerson(View view) {
		PersonDAO personDAO = persistor.getPersonDAO();
		GroupDAO groupDAO = persistor.getGroupDAO();
		PhoneDAO phoneDAO = persistor.getPhoneDAO();
		EmailDAO emailDAO = persistor.getEmailDAO();
		Person person = new Person();
		person.setName(editTextName.getText().toString());// 提取联系人名字
		if (person.getName() == null ||
				person.getName().replace(" ", "").equals("")) {// 检查名字有效性
			Toast.makeText(getApplicationContext(), R.string
					.str_contacts_save_fail, Toast
					.LENGTH_SHORT).show();
			finish();// 提示无效并取消保存
			return;
		}
		long rowid = personDAO.insert(person);
		if (rowid == -1) {
			finish();
			return;
		}
		int personID = personDAO.getPrimaryKey(rowid);// 查询新增联系人的id
		if (personID == 0) {
			Toast.makeText(getApplicationContext(), R.string
					.str_contacts_save_fail, Toast
					.LENGTH_SHORT).show();
			finish();// 提示失败并取消保存
			return;
		}
		person.setId(personID);
		for (Phone phone : phoneList) {
			if (phone.getNumber() != null && !phone.getNumber().replace(" ", "")
					.equals("")) {
				phone.setPersonID(personID);
				phoneDAO.insert(phone);
			}
		}
		for (Email email : emailList) {
			if (email.getAddress() != null && !email.getAddress().replace(" ",
					"").equals("")) {
				email.setPersonID(personID);
				emailDAO.insert(email);
			}
		}
		// 添加群粗
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
