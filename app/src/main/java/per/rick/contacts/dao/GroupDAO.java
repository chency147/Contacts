package per.rick.contacts.dao;

import per.rick.contacts.entity.Group;

/**
 * 群组DAO接口
 * Created by Rick on 2016/5/9.
 */
public interface GroupDAO extends BaseDAO<Group, Integer> {
	public static final String TABLE_NAME = "`tbl_group`";// 表格名称
	/* 表格中各字段名称 */
	public static final String ID = "`id`";
	public static final String NAME = "`name`";
}
