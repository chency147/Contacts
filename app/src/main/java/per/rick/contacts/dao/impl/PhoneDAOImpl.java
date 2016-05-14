package per.rick.contacts.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import per.rick.contacts.dao.PersonDAO;
import per.rick.contacts.dao.PhoneDAO;
import per.rick.contacts.entity.Person;
import per.rick.contacts.entity.Phone;

/**
 * Created by Rick on 2016/5/11.
 */
public class PhoneDAOImpl extends BaseDAOImpl implements PhoneDAO {
	private static final String[] COLUMNS = {ID, PERSON_ID, TAG, NUMBER};

	public PhoneDAOImpl(SQLiteDatabase database) {
		super(database);
	}

	@Override
	public long insert(Phone phone) {
		ContentValues values = new ContentValues();
		values.put(PERSON_ID, phone.getPersonID());
		values.put(TAG, phone.getTag());
		values.put(NUMBER, phone.getNumber());
		return database.insert(TABLE_NAME, null, values);
	}

	@Override
	public boolean update(Phone phone) {
		ContentValues values = new ContentValues();
		values.put(PERSON_ID, phone.getPersonID());
		values.put(TAG, phone.getTag());
		values.put(NUMBER, phone.getNumber());
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ID).append("=?");
		String[] whereArgs = {String.valueOf(phone.getId())};
		return database.update(TABLE_NAME, values, stringBuilder.toString(),
				whereArgs) > 0;
	}

	@Override
	public boolean delete(Phone phone) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ID).append("=?");
		String[] whereArgs = {String.valueOf(phone.getId())};
		return database.delete(TABLE_NAME, stringBuilder.toString(),
				whereArgs) != 0;
	}

	@Override
	public Phone findByID(Integer id) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ID).append("=?");
		String[] whereArgs = {String.valueOf(id)};
		Cursor cursor = database.query(TABLE_NAME, COLUMNS, stringBuilder
				.toString(), whereArgs, null, null, null);
		if (cursor != null && cursor.moveToNext()) {
			Phone result = new Phone();
			result.setId(cursor.getInt(0));
			result.setPersonID(cursor.getInt(1));
			result.setTag(cursor.getString(2));
			result.setNumber(cursor.getString(3));
			return result;
		} else {
			return null;
		}
	}

	@Override
	public List<Phone> queryAll() {
		Cursor cursor = database.query(TABLE_NAME, COLUMNS, null, null, null,
				null, PERSON_ID);
		if (cursor == null) {
			return null;
		}
		List<Phone> phones = new ArrayList<Phone>();
		while (cursor.moveToNext()) {
			Phone phone = new Phone();
			phone.setId(cursor.getInt(0));
			phone.setPersonID(cursor.getInt(1));
			phone.setTag(cursor.getString(2));
			phone.setNumber(cursor.getString(3));
			phones.add(phone);
		}
		return phones;
	}

	@Override
	public String getCreateTableSQL() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("CREATE TABLE IF NOT EXISTS ")
				.append(TABLE_NAME).append(" (")
				.append(ID).append(" INTEGER ")
				.append("PRIMARY KEY, ")
				.append(PERSON_ID).append("INTEGER NOT NULL, ")
				.append(TAG).append(" VARCHAR(50) NOT NULL, ")
				.append(NUMBER).append(" VARCHAR(50) NOT NULL, ")
				.append("CONSTRAINT ").append(FOREIGN_KEY_PERSON_ID)
				.append("FOREIGN KEY (").append(PERSON_ID)
				.append(") REFERENCES ").append(PersonDAO.TABLE_NAME)
				.append("(").append(PhoneDAO.ID).append(")")
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
	public List<Phone> queryByPersonID(Integer personID) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(PERSON_ID).append("=?");
		String[] whereArgs = {String.valueOf(personID)};
		Cursor cursor = database.query(TABLE_NAME, COLUMNS, stringBuilder
				.toString(), whereArgs, null, null, null);
		if (cursor == null) {
			return null;
		}
		List<Phone> phoneList = new ArrayList<Phone>();
		while (cursor.moveToNext()) {
			Phone phone = new Phone();
			phone.setId(cursor.getInt(0));
			phone.setPersonID(cursor.getInt(1));
			phone.setTag(cursor.getString(2));
			phone.setNumber(cursor.getString(3));
			phoneList.add(phone);
		}
		return phoneList;
	}

	@Override
	public boolean deleteByPersonID(Integer personID) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(PERSON_ID).append("=?");
		String[] whereArgs = {String.valueOf(personID)};
		return database.delete(TABLE_NAME, stringBuilder.toString(),
				whereArgs) != 0;
	}
}
