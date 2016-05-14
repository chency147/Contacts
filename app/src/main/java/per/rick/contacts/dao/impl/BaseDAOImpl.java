package per.rick.contacts.dao.impl;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 基础DAO实现类
 * Created by Rick on 2016/5/9.
 */
public abstract class BaseDAOImpl {
	public static final String DB_ERROR = "dbError";
	protected SQLiteDatabase database = null;

	public BaseDAOImpl(SQLiteDatabase database) {
		this.database = database;
	}

	public abstract String getCreateTableSQL();

	public boolean createTable() {
		database.beginTransaction();
		try {
			database.execSQL(getCreateTableSQL());
			database.setTransactionSuccessful();
		} catch (Exception e) {
			Log.v(DB_ERROR, this.getClass().getName()
					+ ": get error when creating table.");
			database.endTransaction();
			return false;
		} finally {
			database.endTransaction();
			return true;
		}
	}

	/* set and get methods BEGIN */
	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}
	/* set and get methods END */
}
