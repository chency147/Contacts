package per.rick.contacts.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import per.rick.contacts.dao.GroupDAO;
import per.rick.contacts.entity.Group;

/**
 * 群组DAO实现
 * Created by Rick on 2016/5/9.
 */
public class GroupDAOImpl extends BaseDAOImpl implements GroupDAO {
	private static final String[] COLUMNS = {ID, NAME};

	public GroupDAOImpl(SQLiteDatabase database) {
		super(database);
	}

	@Override
	public long insert(Group group) {
		ContentValues values = new ContentValues();
		values.put(NAME, group.getName());
		return database.insert(TABLE_NAME, null, values);
	}

	@Override
	public boolean update(Group group) {
		ContentValues values = new ContentValues();
		values.put(NAME, group.getName());
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ID).append("=?");
		String[] whereArgs = {String.valueOf(group.getId())};
		return database.update(TABLE_NAME, values, stringBuilder.toString(),
				whereArgs) > 0;
	}

	@Override
	public boolean delete(Group group) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ID).append("=?");
		String[] whereArgs = {String.valueOf(group.getId())};
		return database.delete(TABLE_NAME, stringBuilder.toString(),
				whereArgs) != 0;
	}

	@Override
	public Group findByID(Integer id) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ID).append("=?");
		String[] whereArgs = {String.valueOf(id)};
		Cursor cursor = database.query(TABLE_NAME, COLUMNS, stringBuilder
				.toString(), whereArgs, null, null, null);
		if (cursor != null && cursor.moveToNext()) {
			Group result = new Group();
			result.setId(cursor.getInt(0));
			result.setName(cursor.getString(1));
			return result;
		} else {
			return null;
		}
	}

	@Override
	public List<Group> queryAll() {
		Cursor cursor = database.query(TABLE_NAME, COLUMNS, null, null, null,
				null, NAME);
		if (cursor == null) {
			return null;
		}
		List<Group> groups = new ArrayList<Group>();
		while (cursor.moveToNext()) {
			Group group = new Group();
			group.setId(cursor.getInt(0));
			group.setName(cursor.getString(1));
			groups.add(group);
		}
		return groups;
	}

	@Override
	public String getCreateTableSQL() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("CREATE TABLE IF NOT EXISTS ")
				.append(TABLE_NAME).append(" (")
				.append(ID).append(" INTEGER ")
				.append("PRIMARY KEY, ")
				.append(NAME).append(" VARCHAR(20) NOT NULL ")
				.append(")");
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

}
