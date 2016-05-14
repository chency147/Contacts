package per.rick.contacts.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import per.rick.contacts.dao.EmailDAO;
import per.rick.contacts.dao.PersonDAO;
import per.rick.contacts.dao.PhoneDAO;
import per.rick.contacts.entity.Email;
import per.rick.contacts.entity.Person;

/**
 * 邮箱DAO实现
 * Created by Rick on 2016/5/11.
 */
public class EmailDAOImpl extends BaseDAOImpl implements EmailDAO {
	private static final String[] COLUMNS = {ID, PERSON_ID, TAG, ADDRESS};

	public EmailDAOImpl(SQLiteDatabase database) {
		super(database);
	}

	@Override
	public long insert(Email email) {
		ContentValues values = new ContentValues();
		values.put(PERSON_ID, email.getPersonID());
		values.put(TAG, email.getTag());
		values.put(ADDRESS, email.getAddress());
		return database.insert(TABLE_NAME, null, values);
	}

	@Override
	public boolean update(Email email) {
		ContentValues values = new ContentValues();
		values.put(PERSON_ID, email.getPersonID());
		values.put(TAG, email.getTag());
		values.put(ADDRESS, email.getAddress());
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ID).append("=?");
		String[] whereArgs = {String.valueOf(email.getId())};
		return database.update(TABLE_NAME, values, stringBuilder.toString(),
				whereArgs) > 0;
	}

	@Override
	public boolean delete(Email email) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ID).append("=?");
		String[] whereArgs = {String.valueOf(email.getId())};
		return database.delete(TABLE_NAME, stringBuilder.toString(),
				whereArgs) != 0;
	}

	@Override
	public Email findByID(Integer id) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ID).append("=?");
		String[] whereArgs = {String.valueOf(id)};
		Cursor cursor = database.query(TABLE_NAME, COLUMNS, stringBuilder
				.toString(), whereArgs, null, null, null);
		if (cursor != null && cursor.moveToNext()) {
			Email result = new Email();
			result.setId(cursor.getInt(0));
			result.setPersonID(cursor.getInt(1));
			result.setTag(cursor.getString(2));
			result.setAddress(cursor.getString(3));
			return result;
		} else {
			return null;
		}
	}

	@Override
	public List<Email> queryAll() {
		Cursor cursor = database.query(TABLE_NAME, COLUMNS, null, null, null,
				null, PERSON_ID);
		if (cursor == null) {
			return null;
		}
		List<Email> emails = new ArrayList<Email>();
		while (cursor.moveToNext()) {
			Email email = new Email();
			email.setId(cursor.getInt(0));
			email.setPersonID(cursor.getInt(1));
			email.setTag(cursor.getString(2));
			email.setAddress(cursor.getString(3));
			emails.add(email);
		}
		return emails;
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
				.append(ADDRESS).append(" VARCHAR(50) NOT NULL, ")
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
	public List<Email> queryByPersonID(Integer personID) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(PERSON_ID).append("=?");
		String[] whereArgs = {String.valueOf(personID)};
		Cursor cursor = database.query(TABLE_NAME, COLUMNS, stringBuilder
				.toString(), whereArgs, null, null, null);
		if (cursor == null) {
			return null;
		}
		List<Email> emailList = new ArrayList<Email>();
		while (cursor.moveToNext()) {
			Email email = new Email();
			email.setId(cursor.getInt(0));
			email.setPersonID(cursor.getInt(1));
			email.setTag(cursor.getString(2));
			email.setAddress(cursor.getString(3));
			emailList.add(email);
		}
		return emailList;
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
