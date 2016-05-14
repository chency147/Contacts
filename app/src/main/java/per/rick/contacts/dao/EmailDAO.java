package per.rick.contacts.dao;

import java.util.List;

import per.rick.contacts.entity.Email;

/**
 * 邮箱DAO接口
 * Created by Rick on 2016/5/11.
 */
public interface EmailDAO extends BaseDAO<Email, Integer> {
	public static final String TABLE_NAME = "`tbl_email`";// 表格名称
	/* 表格中各字段的名称 */
	public static final String ID = "`id`";
	public static final String PERSON_ID = "`person_id`";
	public static final String TAG = "`tag`";
	public static final String ADDRESS = "`address`";
	/* 外键名称 */
	public static final String FOREIGN_KEY_PERSON_ID =
			"`tbl_email_person_id`";

	/**
	 * 通过联系人ID查询邮箱
	 *
	 * @param personID 联系人id
	 * @return 邮箱链表
	 */
	public List<Email> queryByPersonID(Integer personID);

	/**
	 * 通过联系人ID删除邮箱
	 *
	 * @param personID 联系人id
	 * @return 删除的结果
	 */
	public boolean deleteByPersonID(Integer personID);
}
