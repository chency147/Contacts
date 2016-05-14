package per.rick.contacts.persist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import per.rick.contacts.dao.BaseDAO;
import per.rick.contacts.dao.EmailDAO;
import per.rick.contacts.dao.GroupDAO;
import per.rick.contacts.dao.PersonDAO;
import per.rick.contacts.dao.PhoneDAO;
import per.rick.contacts.dao.impl.EmailDAOImpl;
import per.rick.contacts.dao.impl.GroupDAOImpl;
import per.rick.contacts.dao.impl.PersonDAOImpl;
import per.rick.contacts.dao.impl.PhoneDAOImpl;
import per.rick.contacts.db.DBUtils;

/**
 * 持久器类
 * Created by Rick on 2016/5/9.
 */
public class Persistor {
	private static Context context = null;// 上下文对象
	private SQLiteDatabase database = null;// 数据库对象
	private GroupDAO groupDAO = null;// 用户组DAO对象
	private PersonDAO personDAO = null;// 用户DAO对象
	private PhoneDAO phoneDAO = null;// 电话DAO对象
	private EmailDAO emailDAO = null;// 邮箱DAO对象
	private List<BaseDAO> daoList = null;// DAO对象列表

	/**
	 * 构造函数
	 *
	 * @param context 上下文对象
	 */
	private Persistor(Context context) {
		this.context = context;
		/* 初始化数据库和DAO对象 */
		database = DBUtils.initDatabase(context);
		groupDAO = new GroupDAOImpl(database);
		personDAO = new PersonDAOImpl(database);
		phoneDAO = new PhoneDAOImpl(database);
		emailDAO = new EmailDAOImpl(database);
	}

	/**
	 * 以内部类的形式实现单例模式
	 */
	private static class PersistorHolder {
		private static Persistor instance = new Persistor(context);
	}

	/**
	 * 获取单例
	 *
	 * @param paramContext 上下文对象
	 * @return 持久器对象
	 */
	public static Persistor getInstance(Context paramContext) {
		if (paramContext == null) {
			return null;
		}
		context = paramContext;
		return PersistorHolder.instance;
	}

	/**
	 * 获取DAO对象列表
	 *
	 * @return DAO对象列表
	 */
	public List<BaseDAO> getDAOList() {
		if (daoList == null) {
			daoList = new ArrayList<BaseDAO>();
			daoList.add(groupDAO);
			daoList.add(personDAO);
			daoList.add(phoneDAO);
			daoList.add(emailDAO);
		}
		return daoList;
	}

	/* set and get methods BEGIN */
	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}

	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	public PersonDAO getPersonDAO() {
		return personDAO;
	}

	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public PhoneDAO getPhoneDAO() {
		return phoneDAO;
	}

	public void setPhoneDAO(PhoneDAO phoneDAO) {
		this.phoneDAO = phoneDAO;
	}

	public EmailDAO getEmailDAO() {
		return emailDAO;
	}

	public void setEmailDAO(EmailDAO emailDAO) {
		this.emailDAO = emailDAO;
	}

	public List<BaseDAO> getDaoList() {
		return daoList;
	}

	public void setDaoList(List<BaseDAO> daoList) {
		this.daoList = daoList;
	}
/* set and get methods END */
}
