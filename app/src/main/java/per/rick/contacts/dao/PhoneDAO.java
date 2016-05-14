package per.rick.contacts.dao;

import java.util.List;

import per.rick.contacts.entity.Phone;

/**
 * 手机DAO接口
 * Created by Rick on 2016/5/11.
 */
public interface PhoneDAO extends BaseDAO<Phone, Integer> {
	public static final String TABLE_NAME = "`tbl_phone`";// 表格名称
	/* 表格中各字段的名称 */
	public static final String ID = "`id`";
	public static final String PERSON_ID = "`person_id`";
	public static final String TAG = "`tag`";
	public static final String NUMBER = "`number`";
	/* 外键名称 */
	public static final String FOREIGN_KEY_PERSON_ID =
			"`tbl_phone_person_id`";

	/**
	 * 通过联系人ID查询手机
	 *
	 * @param personID 联系人ID
	 * @return 手机链表
	 */
	public List<Phone> queryByPersonID(Integer personID);

	/**
	 * 通过联系人ID删除手机
	 *
	 * @param personID 联系人ID
	 * @return 删除的结果
	 */
	public boolean deleteByPersonID(Integer personID);
}
