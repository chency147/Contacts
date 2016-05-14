package per.rick.contacts.entity;

/**
 * 基础实体类
 * Created by Rick on 2016/5/13.
 */
public class BaseEntity {
	private boolean isEdited = false;// 是否已经编辑过
	private boolean isNew = false;// 是否是新建的

	/* set and get methods BEGIN */
	public boolean isEdited() {
		return isEdited;
	}

	public void setEdited(boolean edited) {
		isEdited = edited;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean aNew) {
		isNew = aNew;
	}
	/* set and get methods END */
}
