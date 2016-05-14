package per.rick.contacts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

import per.rick.contacts.dao.BaseDAO;

/**
 * 数据库工具类
 * Created by Rick on 2016/5/9.
 */
public class DBUtils {
	public final static String DATABASE_NAME = "rick_contacts.db3";
	//数据库对应文件名

	/**
	 * 打开或创建（不存在的情况下）数据库
	 *
	 * @param context 上下文对象
	 * @return 数据库对象
	 */
	public static SQLiteDatabase initDatabase(Context context) {
		Log.v("SQL", context.getFilesDir().toString() + "/" +
				DATABASE_NAME);
		return SQLiteDatabase.openOrCreateDatabase(
				context.getFilesDir().toString() + "/" + DATABASE_NAME,
				null);
	}

	/**
	 * 初始化各表
	 *
	 * @param baseDAOs DAO对象列表
	 * @return 初始化的结果
	 */
	public static boolean initTables(List<BaseDAO> baseDAOs) {
		if (baseDAOs == null) {
			return false;
		}
		Iterator<BaseDAO> iterator = baseDAOs.iterator();
		while (iterator.hasNext()) {
			if (!iterator.next().createTable()) {
				return false;
			}
		}
		return true;
	}
}
