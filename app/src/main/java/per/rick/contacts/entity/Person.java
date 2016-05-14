package per.rick.contacts.entity;

/**
 * 联系人类
 * Created by Rick on 2016/5/8.
 */
public class Person extends BaseEntity{
	private int id;// 联系人的id
	private String name = null;// 联系人姓名
	private int groupID;// 所属组号
	private Group group = null;//所属组对象
	private String sortKey; // 首字母的大写

	/* set and get methods BEGIN */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
/* set and get methods END */
}
