package per.rick.contacts.activity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import per.rick.contacts.R;
import per.rick.contacts.adapter.InfoShowAdapter;
import per.rick.contacts.entity.Email;
import per.rick.contacts.entity.Person;
import per.rick.contacts.entity.Phone;
import per.rick.contacts.persist.Persistor;

/**
 * 联系人信息展示Activity
 */
public class PersonInfoActivity extends AppCompatActivity {
	public static final String PERSON_TO_SHOW_ID = "PERSON_TO_SHOW_ID";
	// extra的key
	public static List<Phone> phoneList = null;// 手机列表
	public static List<Email> emailList = null;// 邮箱列表
	private int personID;// 联系人ID
	public static Person person = null;// 联系人对象
	private Persistor persistor = null;// 持久器对象
	private TextView textViewName = null;// 名字标签
	private ListView listViewPhone = null;// 手机ListView
	private ListView listViewEmail = null;// 邮箱ListView
	private InfoShowAdapter phoneShowAdapter = null;// 手机ListView适配器
	private InfoShowAdapter emailShowAdapter = null;// 邮箱ListView适配器
	private DialogInterface.OnClickListener deleteConfirmListener = null;
	// 删除确认框监听器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_info);
		personID = (int) getIntent().getExtras().get(PERSON_TO_SHOW_ID);
		persistor = Persistor.getInstance(this);
		initView();
		initAdapter();
	}

	protected void onResume() {
		super.onResume();
		/* 更新联系人信息的显示 */
		person = persistor.getPersonDAO().findByID(personID);
		phoneList = persistor.getPhoneDAO().queryByPersonID(personID);
		emailList = persistor.getEmailDAO().queryByPersonID(personID);
		textViewName.setText(person.getName());
		phoneShowAdapter.setPhoneList(phoneList);
		phoneShowAdapter.notifyDataSetChanged();
		emailShowAdapter.setEmailList(emailList);
		emailShowAdapter.notifyDataSetChanged();
		listViewPhone.getLayoutParams().height =
				getResources().getDimensionPixelSize(R.dimen.infoItemHeight)
						* phoneList.size();
		listViewPhone.setLayoutParams(listViewPhone.getLayoutParams());
		listViewEmail.getLayoutParams().height =
				getResources().getDimensionPixelSize(R.dimen.infoItemHeight)
						* emailList.size();
		listViewEmail.setLayoutParams(listViewEmail.getLayoutParams());
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		textViewName = (TextView) findViewById(R.id.tv_name);
		listViewPhone = (ListView) findViewById(R.id.lv_phone);
		listViewEmail = (ListView) findViewById(R.id.lv_email);
		deleteConfirmListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				persistor.getPhoneDAO().deleteByPersonID(personID);
				persistor.getEmailDAO().deleteByPersonID(personID);
				persistor.getPersonDAO().delete(person);
				Toast.makeText(getApplicationContext(),
						R.string.str_contacts_delete,
						Toast.LENGTH_SHORT).show();// 删除联系人并提示
				finish();
			}
		};
		listViewPhone.setOnItemClickListener(
				new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent,
											View view, int position, long id) {
						Uri uri = Uri.parse(
								"tel:" + phoneList.get(position).getNumber());
						Intent intent = new Intent(Intent.ACTION_DIAL, uri);
						startActivity(intent);// 打开拨号界面
					}
				});
		listViewEmail.setOnItemClickListener(
				new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent,
											View view, int position, long id) {
						Intent intent = new Intent(Intent.ACTION_SEND);
						String[] tos = {emailList.get(position).getAddress()};
						intent.putExtra(Intent.EXTRA_EMAIL, tos);
						intent.setType("message/rfc822");
						startActivity(intent);// 打开点击邮件软件
					}
				});
	}

	/**
	 * 初始化适配器
	 */
	private void initAdapter() {
		phoneShowAdapter = new InfoShowAdapter(this, InfoShowAdapter.PHONE,
				phoneList);
		emailShowAdapter = new InfoShowAdapter(this, InfoShowAdapter.EMAIL,
				emailList);
		listViewPhone.setAdapter(phoneShowAdapter);
		listViewEmail.setAdapter(emailShowAdapter);
	}

	/**
	 * 返回
	 *
	 * @param view
	 */
	public void back(View view) {
		finish();
	}

	/**
	 * 弹出删除确认框
	 *
	 * @param view
	 */
	public void deletePerson(View view) {
		new AlertDialog.Builder(this).setTitle(R.string.str_contacts_delete)
				.setMessage(R.string.str_contacts_delete_confirm)
				.setNegativeButton(R.string.str_cancel, null)
				.setPositiveButton(R.string.str_confirm, deleteConfirmListener)
				.show();
	}

	/**
	 * 打开联系人编辑activity
	 *
	 * @param view
	 */
	public void edit(View view) {
		ComponentName comp = new ComponentName(this, PersonEditActivity.class);
		Intent intent = new Intent();
		intent.setComponent(comp);
		startActivity(intent);
	}
}
