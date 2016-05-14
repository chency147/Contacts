package per.rick.contacts.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import per.rick.contacts.R;
import per.rick.contacts.adapter.ContactsAdapter;
import per.rick.contacts.db.DBUtils;
import per.rick.contacts.entity.Person;
import per.rick.contacts.persist.Persistor;
import per.rick.contacts.tool.PinyinComparator;
import per.rick.contacts.widget.SideBar;

/**
 * 主Activity
 */
public class MainActivity extends AppCompatActivity {

	private SQLiteDatabase database = null;// 数据库对象
	private ListView contactSListView = null;// 联系人ListView
	private List<Person> personList = null;// 联系人列表
	private EditText editTextSearch = null;// 搜索编辑栏
	private ImageView clearButton = null;// 清除按钮
	private Persistor persistor = null;// 持久器对象
	private ContactsAdapter contactsAdapter = null;// 联系人ListView适配器
	private PinyinComparator pinyinComparator = null;// 拼音比较器
	private SideBar sideBar = null;// 侧边字母索引栏
	private TextView alphaDialog = null;// 索引字母提示弹出对象
	private TextView textViewInfo = null;// 查询的人数信息
	private TextWatcher searchListener = null;// 搜索栏内容变化监听器
	private String searchKey = null;// 当前搜索的关键词
	private boolean isInSearchMode = false;// 是否是在搜索状态
	private static String infoCountPrefix = null;// 人数信息显示前缀（非搜索状态）
	private static String infoCountPostfix = null;// 人数信息显示后缀（非搜索状态）
	private static String infoSearchCountPrefix = null;// 人数显示前缀（搜索状态）
	private static String infoSearchCountPostfix = null;// 人数显示后缀（搜索状态）
	private static String noPerson = null;// 没有联系人信息（非搜索状态）
	private static String noPersonsSearched = null;// 没有联系人（搜索状态）

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		persistor = Persistor.getInstance(this);
		DBUtils.initTables(persistor.getDAOList());
		initSearchListener();
		initView();
		pinyinComparator = new PinyinComparator();
	}

	/**
	 * 打开联系人添加Activity
	 *
	 * @param view
	 */
	public void openPersonInsertActivity(View view) {
		ComponentName comp = new ComponentName(this,
				PersonInsertActivity.class);
		Intent intent = new Intent();
		intent.setComponent(comp);
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isInSearchMode) {//　判断是否是搜索状态
			queryPersonByName(searchKey);// 根据姓名查询联系人
		} else {
			queryAllPerson();// 显示所有联系人
		}
	}

	/**
	 * 查询所有联系人
	 */
	private void queryAllPerson() {
		isInSearchMode = false;
		personList = persistor.getPersonDAO().queryAll();
		Collections.sort(personList, pinyinComparator);// 根据拼音排序
		contactsAdapter.setPersonList(personList);
		if (personList.size() == 0) {// 设置人数信息标签
			textViewInfo.setText(noPerson);
		} else {
			textViewInfo.setText(infoCountPrefix + personList.size() +
					infoCountPostfix);
		}
	}

	/**
	 * 根据名字查询联系人
	 *
	 * @param name 名字
	 */
	private void queryPersonByName(String name) {
		personList = persistor.getPersonDAO().queryByName(name);
		Collections.sort(personList, pinyinComparator);
		contactsAdapter.setPersonList(personList);
		if (personList.size() == 0) {
			textViewInfo.setText(noPersonsSearched);
		} else {
			textViewInfo.setText(infoSearchCountPrefix + personList.size() +
					infoSearchCountPostfix);
		}
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		sideBar = (SideBar) findViewById(R.id.sideBar);
		alphaDialog = (TextView) findViewById(R.id.alphaDialog);
		editTextSearch = (EditText) findViewById(R.id.et_search);
		clearButton = (ImageView) findViewById(R.id.iv_search_clear);
		textViewInfo = (TextView) findViewById(R.id.tv_info);
		sideBar.setTextView(alphaDialog);
		infoCountPrefix = getResources().getString(R.string
				.str_contacts_count_show_prefix);
		infoCountPostfix = getResources().getString(R.string
				.str_contacts_count_show_postfix);
		infoSearchCountPrefix = getResources().getString(R.string
				.str_contacts_search_count_show_prefix);
		infoSearchCountPostfix = getResources().getString(R.string
				.str_contacts_search_count_show_postfix);
		noPerson = getResources().getString(R.string.str_contacts_no_person);
		noPersonsSearched = getResources().getString(R.string
				.str_contacts_search_no_person);
		contactSListView = (ListView) findViewById(R.id.lv_contacts);
		contactsAdapter = new ContactsAdapter(this, personList);
		contactSListView.setAdapter(contactsAdapter);
		contactSListView.setOnItemClickListener(new AdapterView
				.OnItemClickListener() {
			@Override
			public void onItemClick(
					AdapterView<?> parent, View view, int position, long id) {
				/* 打开联系人信息展示Activity，并传递联系人id */
				ComponentName comp = new ComponentName(MainActivity.this,
						PersonInfoActivity.class);
				Intent intent = new Intent();
				intent.setComponent(comp);
				intent.putExtra(PersonInfoActivity.PERSON_TO_SHOW_ID,
						personList.get(position).getId());
				startActivity(intent);
			}
		});
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = contactsAdapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					contactSListView.setSelection(position);
				}

			}
		});
		editTextSearch.addTextChangedListener(searchListener);
	}

	/**
	 * 设置搜索编辑栏内容变化监听器
	 */
	private void initSearchListener() {
		searchListener = new TextWatcher() {
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
				if (s.toString().length() > 0) {// 控制清除按钮的出现
					clearButton.setVisibility(View.VISIBLE);
				} else {
					clearButton.setVisibility(View.INVISIBLE);
				}
				searchKey = s.toString().replace(" ", "");
				// 按照字符输入的有效性决定是显示所有联系人还是进行搜索操作
				if (searchKey == null || searchKey.equals("")) {
					if (isInSearchMode) {
						queryAllPerson();
					}
				} else {
					isInSearchMode = true;
					queryPersonByName(searchKey);
				}
			}
		};
	}

	/**
	 * 清除搜索栏按钮
	 *
	 * @param view
	 */
	public void clearSearch(View view) {
		editTextSearch.setText("");
	}
}
