package per.rick.contacts.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import per.rick.contacts.R;
import per.rick.contacts.dao.GroupDAO;
import per.rick.contacts.dao.PersonDAO;
import per.rick.contacts.entity.Person;
import per.rick.contacts.tool.CharacterParser;

/**
 * 联系人DAO实现
 * Created by Rick on 2016/5/9.
 */
public class PersonDAOImpl extends BaseDAOImpl implements PersonDAO {
	private static final String[] COLUMNS = {ID, NAME, GROUP_ID};

	public PersonDAOImpl(SQLiteDatabase database) {
		super(database);
	}

	@Override
	public long insert(Person person) {
		ContentValues values = new ContentValues();
		values.put(NAME, person.getName());
		values.put(GROUP_ID, person.getGroupID());
		return database.insert(TABLE_NAME, null, values);
	}

	@Override
	public boolean update(Person person) {
		ContentValues values = new ContentValues();
		values.put(NAME, person.getName());
		values.put(GROUP_ID, person.getGroupID());
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ID).append("=?");
		String[] whereArgs = {String.valueOf(person.getId())};
		return database.update(TABLE_NAME, values, stringBuilder.toString(),
				whereArgs) > 0;
	}

	@Override
	public boolean delete(Person person) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ID).append("=?");
		String[] whereArgs = {String.valueOf(person.getId())};
		return database.delete(TABLE_NAME, stringBuilder.toString(),
				whereArgs) != 0;
	}

	@Override
	public Person findByID(Integer id) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ID).append("=?");
		String[] whereArgs = {String.valueOf(id)};
		Cursor cursor = database.query(TABLE_NAME, COLUMNS, stringBuilder
				.toString(), whereArgs, null, null, null);
		if (cursor != null && cursor.moveToNext()) {
			Person result = new Person();
			result.setId(cursor.getInt(0));
			result.setName(cursor.getString(1));
			result.setGroupID(cursor.getInt(2));
			return result;
		} else {
			return null;
		}
	}

	@Override
	public List<Person> queryAll() {
		Cursor cursor = database.query(TABLE_NAME, COLUMNS, null, null, null,
				null, NAME);
		if (cursor == null) {
			return null;
		}
		List<Person> persons = new ArrayList<Person>();
		while (cursor.moveToNext()) {
			Person person = new Person();
			person.setId(cursor.getInt(0));
			person.setName(cursor.getString(1));
			person.setGroupID(cursor.getInt(2));
			persons.add(person);
		}
		initSortKey(persons);
		return persons;
	}

	@Override
	public String getCreateTableSQL() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("CREATE TABLE IF NOT EXISTS ")
				.append(TABLE_NAME).append(" (")
				.append(ID).append(" INTEGER ")
				.append("PRIMARY KEY, ")
				.append(NAME).append(" VARCHAR(20) NOT NULL, ")
				.append(GROUP_ID).append(" INTEGER, ")
				.append("CONSTRAINT ").append(FOREIGN_KEY_GROUP_ID)
				.append("FOREIGN KEY (").append(GROUP_ID)
				.append(") REFERENCES ").append(GroupDAO.TABLE_NAME)
				.append("(").append(GroupDAO.ID).append(")")
				.append(")");
		Log.v("SQL", stringBuilder.toString());
		return stringBuilder.toString();
	}

	@Override
	public Integer getPrimaryKey(long rowid) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ROW_ID).append("=?");
		String[] whereArgs = {String.valueOf(rowid)};
		Cursor cursor = database.query(TABLE_NAME, new String[]{ID},
				stringBuilder.toString(), whereArgs, null, null, null);
		if (cursor != null && cursor.moveToNext()) {
			return cursor.getInt(0);
		} else {
			return 0;
		}
	}

	@Override
	public List<Person> queryByName(String name) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(NAME).append(" like ?");
		String[] whereArgs = {"%" + String.valueOf(name) + "%"};
		Cursor cursor = database.query(TABLE_NAME, COLUMNS, stringBuilder
				.toString(), whereArgs, null, null, NAME);
		if (cursor == null) {
			return null;
		}
		List<Person> persons = new ArrayList<Person>();
		while (cursor.moveToNext()) {
			Person person = new Person();
			person.setId(cursor.getInt(0));
			person.setName(cursor.getString(1));
			person.setGroupID(cursor.getInt(2));
			persons.add(person);
		}
		initSortKey(persons);
		return persons;
	}

	/**
	 * 初始化联系人的排序关键字符串
	 * @param persons 联系人列表
	 */
	private void initSortKey(List<Person> persons) {
		CharacterParser characterParser = CharacterParser.getInstance();
		// 初始化汉子转拼音工具
		for (Person p : persons) {
			String pinyin = characterParser.getSelling(p.getName());
			// 获取拼音
			String sortString = pinyin.substring(0, 1).toUpperCase();
			// 获取拼音首字母大写
			if (sortString.matches("[A-Z]")) {
				p.setSortKey(sortString.toUpperCase());
			} else {
				p.setSortKey("#");
			}
		}
	}
}
