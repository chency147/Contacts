package per.rick.contacts.dao;


import java.util.List;

/**
 * 基础DAO接口
 * Created by Rick on 2016/5/9.
 */
public interface BaseDAO<T, PK> {
	public static final String ROW_ID = "`rowid`";

	/**
	 * 添加一个对象到数据库
	 *
	 * @param t 欲添加的对象
	 * @return 添加的结果
	 */
	public long insert(T t);


	/**
	 * 更新一个对象的信息到数据库
	 *
	 * @param t 欲更新的对象
	 * @return 更新的结果
	 */
	public boolean update(T t);

	/**
	 * 删除数据库中对象对应的数据
	 *
	 * @param t 欲删除的对象
	 * @return 删除的结果
	 */
	public boolean delete(T t);

	/**
	 * 通过主键获取对象
	 *
	 * @param id 主键
	 * @return 查询到的对象，如果不存在则为null
	 */
	public T findByID(PK id);

	/**
	 * 查询所有的对象
	 *
	 * @return 对象列表，如果不存在则为null，或者列表中没有对象
	 */
	public List<T> queryAll();

	/**
	 * 创建表格
	 *
	 * @return 创建的结果
	 */
	public boolean createTable();

	/**
	 * 获得创建表格的SQL语句
	 *
	 * @return 包含SQL语句的字符串
	 */
	public String getCreateTableSQL();

	public PK getPrimaryKey(long rowid);
}
