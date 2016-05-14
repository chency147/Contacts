package per.rick.contacts.dao;

import java.util.List;

import per.rick.contacts.entity.Person;

/**
 * 联系人DAO接口
 * Created by Rick on 2016/5/9.
 */
public interface PersonDAO extends BaseDAO<Person, Integer> {
	public static final String TABLE_NAME = "`tbl_person`";// 表格名称
	/* 表格中各字段的名称 */
	public static final String ID = "`id`";
	public static final String NAME = "`name`";
	public static final String GROUP_ID = "`group_id`";
	/* 外键名称 */
	public static final String FOREIGN_KEY_GROUP_ID =
			"`tbl_person_group_id`";

	/**
	 * 通过名字查询联系人
	 *
	 * @param name 联系人名字（部分）
	 * @return 联系人链表
	 */
	public List<Person> queryByName(String name);
}
