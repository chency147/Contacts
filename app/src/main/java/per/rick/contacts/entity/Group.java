package per.rick.contacts.entity;

/**
 * 用户组类
 * Created by Rick on 2016/5/8.
 */
public class Group extends BaseEntity{
	private int id;// 用户组id
	private String name = null;// 用户组名称

	/* set and get methods BEGIN */
	public int getId() {
		return id;
	}

	public Group setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Group setName(String name) {
		this.name = name;
		return this;
	}
	/* set and get methods END */
}
